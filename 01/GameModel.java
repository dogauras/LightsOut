
public class GameModel {
    private boolean[][] board;
    private int width;
    private int height;
    private int click;
    
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
