import java.util.ArrayList;

public class GameModel {
    private boolean[][] board;
    private int width;
    private int height;
    private int click;
    private Solution shortest; // stores the shortest solution found for the current model if needed
    
	public GameModel(int width, int height) {
		this.width = width;
		this.height = height;

		board = new boolean[height][width];
		click = 0;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public boolean isON(int i, int j) {
		return (board[i][j]);
	}
	
	public void reset() {
		board = new boolean[height][width];
		click = 0;
	}
	
	public void set(int i, int j, boolean value) {
		board[j][i] = value;
	}

	// updates the model after the dot at location row i and column j is clicked
	public void click(int i, int j) {
		if (board[i][j]) {
			set(j, i, false);
		} else {
			set(j, i, true);
		}
		if ((i > 0)) {
			if (board[i - 1][j]) {
				set(j, i - 1, false);
			} else {
				set(j, i - 1, true);
			}
		}
		if ((i < height - 1)) {
			if (board[i + 1][j]) {
				set(j, i + 1, false);
			} else {
				set(j, i + 1, true);
			}
		}

		if ((j > 0)) {
			if ((board[i][j - 1])) {
				set(j - 1, i, false);
			} else {
				set(j - 1, i, true);
			}
		}
		if ((j < (width - 1))) {
			if ((board[i][j + 1])) {
				set(j + 1, i, false);
			} else {
				set(j + 1, i, true);
			}
		}
		click++;

	}
	// returns the number of times the method click has been called since the last reset
	public int getNumberOfSteps() {
		return click;
	}
	
	// returns true if the model represents a completed game
	public boolean isFinished() {
		for(int i = 0; i< height;i++) {
			for(int j = 0; j< width; j++) {
				if(!isON(i,j)) {
					return false;
				}
			}
		}return true;
	}
	
	
	// restarts the game with a solvable random board instead of an all OFF board
	public void randomize() {
		reset();
		boolean done = true;
		while(done) {
			for(int i = 0; i< height;i++) {
				for(int j = 0;j<width;j++) {
					int booleanValue = (int)(Math.random() * 2); // represents true or false
					if(booleanValue==1) {
						set(j,i, true);
					}else {
						set(j,i,false);
					}
				}
				
			}if(LightsOut.solve(this).size()!=0 && !this.isFinished()) {
				done = false;
			}
		}
		
	}
	
	//forces the model to find a minimal size instance of Solution for the current model.
	public void setSolution() {
		shortest = LightsOut.solveShortest(this);
	}
	
	// returns true if the model has been forced to find a solution and the
	// position at row i, column j is tapped in that solution.
	public boolean solutionSelects(int i, int j) {
		if(shortest!=null && shortest.get(j, i)==true) {
			return true;
		}return false; // returns false otherwise
	}

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
