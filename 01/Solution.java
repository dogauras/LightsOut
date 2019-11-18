public class Solution {

	/**
	 * our board. board[i][j] is true is in this solution, the cell (j,i) is tapped
	 */
	private boolean[][] board;

	/**
	 * width of the game
	 */
	private int width;

	/**
	 * height of the game
	 */
	private int height;

	/**
	 * how far along have we constructed that solution. values range between 0 and
	 * height*width-1
	 */
	private int currentIndex;

	/**
	 * Constructor. Creates an instance of Solution for a board of size
	 * <b>widthxheight</b>. That solution does not have any board position value
	 * explicitly specified yet.
	 *
	 * @param width  the width of the board
	 * @param height the height of the board
	 */
	public Solution(int width, int height) {

		this.width = width;
		this.height = height;

		board = new boolean[height][width];
		currentIndex = 0;

	}

	/**
	 * Constructor. Creates an instance of Solution wich is a deep copy of the
	 * instance received as parameter.
	 *
	 * @param other Instance of solution to deep-copy
	 */
	public Solution(Solution other) {

		this.width = other.width;
		this.height = other.height;
		this.currentIndex = other.currentIndex;

		board = new boolean[height][width];

		for (int i = 0; i < currentIndex; i++) {
			board[i / width][i % width] = other.board[i / width][i % width];
		}

	}

	/**
	 * returns <b>true</b> if and only the parameter <b>other</b> is referencing an
	 * instance of a Solution which is the ``same'' as this instance of Solution
	 * (its board as the same values and it is completed to the same degree)
	 *
	 * @param other referenced object to compare
	 */

	public boolean equals(Object other) {

		if (other == null) {
			return false;
		}
		if (this.getClass() != other.getClass()) {
			return false;
		}

		Solution otherSolution = (Solution) other;

		if (width != otherSolution.width || height != otherSolution.height
				|| currentIndex != otherSolution.currentIndex) {
			return false;
		}

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (board[i][j] != otherSolution.board[i][j]) {
					return false;
				}
			}
		}

		return true;

	}

	/**
	 * returns <b>true</b> if the solution has been entirely specified
	 *
	 * @return true if the solution is fully specified
	 */
	public boolean isReady() {
		return currentIndex == width * height;
	}

	/**
	 * specifies the ``next'' value of the solution. The first call to setNext
	 * specifies the value of the board location (1,1), the second call specifies
	 * the value of the board location (1,2) etc.
	 *
	 * If <b>setNext</b> is called more times than there are positions on the board,
	 * an error message is printed out and the call is ignored.
	 *
	 * @param nextValue the boolean value of the next position of the solution
	 */
	public void setNext(boolean nextValue) {

		if (currentIndex >= width * height) {
			System.out.println("Board already full");
			return;
		}
		board[currentIndex / width][currentIndex % width] = nextValue;
		currentIndex++;
	}

	/**
	 * returns <b>true</b> if the solution is completely specified and is indeed
	 * working, that is, if it will bring a board of the specified dimensions from
	 * being entirely ``off'' to being entirely ``on''.
	 *
	 * @return true if the solution is completely specified and works
	 */
	public boolean isSuccessful() {

		if (currentIndex < width * height) {
			System.out.println("Board not finished");
			return false;
		}

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (!oddNeighborhood(i, j)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * this method ensure that add <b>nextValue</b> at the currentIndex does not
	 * make the current solution impossible. It assumes that the Solution was built
	 * with a series of setNext on which stillPossible was always true.
	 * 
	 * @param nextValue The boolean value to add at currentIndex
	 * @return true if the board is not known to be impossible (which does not mean
	 *         that the board is possible!)
	 */
	public boolean stillPossible(boolean nextValue) {

		if (currentIndex >= width * height) {
			System.out.println("Board already full");
			return false;
		}

		int i = currentIndex / width;
		int j = currentIndex % width;
		boolean before = board[i][j];
		boolean possible = true;

		board[i][j] = nextValue;

		if ((i > 0) && (!oddNeighborhood(i - 1, j))) {
			possible = false;
		}
		if (possible && (i == (height - 1))) {
			if ((j > 0) && (!oddNeighborhood(i, j - 1))) {
				possible = false;
			}
			if (possible && (j == (width - 1)) && (!oddNeighborhood(i, j))) {
				possible = false;
			}
		}
		board[i][j] = before;
		return possible;
	}

	/**
	 * this method attempts to finish the board. It assumes that the Solution was
	 * built with a series of setNext on which stillPossible was always true. It
	 * cannot be called if the board can be extended with both true and false and
	 * still be possible.
	 *
	 * @return true if the board can be finished. the board is also completed
	 */
	public boolean finish() {

		int i = currentIndex / width;
		int j = currentIndex % width;

		/*
		 * if(i == 0 && height > 1) {
		 * System.out.println("First line incomplete, can't proceed"); return false; }
		 */

		while (currentIndex < height * width) {
			if (i < height - 1) {
				setNext(!oddNeighborhood(i - 1, j));
				i = currentIndex / width;
				j = currentIndex % width;
			} else { // last row
				if (j == 0) {
					setNext(!oddNeighborhood(i - 1, j));
				} else {
					if ((height > 1) && oddNeighborhood(i - 1, j) != oddNeighborhood(i, j - 1)) {
						return false;
					}
					setNext(!oddNeighborhood(i, j - 1));
				}
				i = currentIndex / width;
				j = currentIndex % width;
			}
		}
		if (!oddNeighborhood(height - 1, width - 1)) {
			return false;
		}
		// here we should return true because we could
		// successfully finish the board. However, as a
		// precaution, if someone called the method on
		// a board that was unfinishable before calling
		// the method, we do a complete check

		if (!isSuccessful()) {
			System.out.println("Warning, method called incorrectly");
			return false;
		}

		return true;

	}

	/**
	 * checks if board[i][j] and its neighborhood have an odd number of values
	 * ``true''
	 */

	private boolean oddNeighborhood(int i, int j) {

		if (i < 0 || i > height - 1 || j < 0 || j > width - 1) {
			return false;
		}

		int total = 0;
		if (board[i][j]) {
			total++;
		}
		if ((i > 0) && (board[i - 1][j])) {
			total++;
		}
		if ((i < height - 1) && (board[i + 1][j])) {
			total++;
		}
		if ((j > 0) && (board[i][j - 1])) {
			total++;
		}
		if ((j < (width - 1)) && (board[i][j + 1])) {
			total++;
		}
		return (total % 2) == 1;
	}

	/*
	 * Returns false if the current solution would be impossible to finalize into a
	 * working solution for a board in the state specified by the GameModel instance
	 * model, should it be extended from its current state with the value nextvalue.
	 */
	public boolean stillPossible(boolean nextValue, GameModel model) {

		int i = currentIndex / width;
		int j = currentIndex % width;
		boolean before = board[i][j];
		boolean possible = true;
		board[i][j] = nextValue;

		if ((i > 0) && ((model.isON(i - 1, j) && oddNeighborhood(i - 1, j))
				|| (!model.isON(i - 1, j) && !oddNeighborhood(i - 1, j)))) {
			possible = false;
		}
		if (possible && i == (height - 1)) {
			if ((j > 0) && ((model.isON(i, j - 1) && oddNeighborhood(i, j - 1))
					|| (!model.isON(i, j - 1) && !oddNeighborhood(i, j - 1)))) {
				possible = false;

			}
			if (possible && (j == (width - 1))
					&& ((model.isON(i, j) && oddNeighborhood(i, j)) || (!model.isON(i, j) && !oddNeighborhood(i, j)))) {
				possible = false;
			}
		}
		board[i][j] = before;
		return possible;

	}

	/**
	 * this method attempts to finish the board of GameModel model. this method
	 * assumes that the solution is currently still extendable for a board in the
	 * state specified by the GameModel instance model, but only one way.
	 *
	 * @return true if the board can be finished. the board is also completed
	 */
	public boolean finish(GameModel model) {

		while (currentIndex < height * width) {
			if (stillPossible(true, model)) {
				setNext(true);
			} else if (stillPossible(false, model)) {
				setNext(false);
			} else {
				return false; // no longer extendable, hence impossible game model
			}
		}
		return true;

	}

	/*
	 * this method returns true if the solution is completely specified and is
	 * indeed working, that is, if it will bring a board of the specified dimensions
	 * from the state specified by the GameModel instance model to being entirely
	 * “on”.
	 */
	public boolean isSuccesful(GameModel model) {
		if (currentIndex < width * height) {
			System.out.println("Board not finished");
			return false;
		}

		for (int i = 0; i < model.getHeight(); i++) {
			for (int j = 0; j < model.getWidth(); j++) {
				if ((!model.isON(i, j) && !oddNeighborhood(i, j)) || (model.isON(i, j) && oddNeighborhood(i, j))) {
					return false;
				}
			}
		}
		return true;
	}

	// returns the number of clicks needed to complete the game
	public int getSize() {
		int tap = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (board[i][j]) {
					tap++;
				}
			}
		}
		return tap;
	}

	/**
	 * returns a string representation of the solution
	 *
	 * @return the string representation
	 */
	public String toString() {
		String a = "[[";
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (j == board[0].length - 1) {
					if (i == board.length - 1) {
						a = a + board[i][j] + "]";
					} else {
						a = a + board[i][j] + "],\n[";
					}
				} else {
					a = a + board[i][j] + ",";
				}
			}
		}
		return a = a + "]";
	}

}
