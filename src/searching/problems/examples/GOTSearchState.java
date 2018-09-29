package searching.problems.examples;

import searching.problems.SearchState;

public class GOTSearchState extends SearchState {
	private final int dragonStoneCarried;
	private final int row;
	private final int column;
	private final boolean[] whiteWalkerStatus; //false: alive  true: dead
	
	public GOTSearchState(int dragonStoneCarried, int row, int column, boolean[] whiteWalkerStatus) {
		super();
		this.dragonStoneCarried = dragonStoneCarried;
		this.row = row;
		this.column = column;
		this.whiteWalkerStatus = whiteWalkerStatus;
	}

	public int getDragonStoneCarried() {
		return dragonStoneCarried;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public boolean[] getWhiteWalkerStatus() {
		return whiteWalkerStatus;
	}
	
}
