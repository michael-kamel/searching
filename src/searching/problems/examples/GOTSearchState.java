package searching.problems.examples;

import java.awt.Point;
import java.util.ArrayList;

import searching.problems.SearchState;
import searching.utils.Tuple;

public class GOTSearchState extends SearchState {
	private final int dragonStoneCarried;
	private final int row;
	private final int column;
	private final ArrayList<Tuple<Point, Boolean>> whiteWalkerStatus; //false: alive  true: dead
	
	public GOTSearchState(int dragonStoneCarried, int row, int column, ArrayList<Tuple<Point, Boolean>> whiteWalkerStatus) {
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

	public ArrayList<Tuple<Point, Boolean>> getWhiteWalkerStatus() {
		return whiteWalkerStatus;
	}
	
	public String toString() {
		String str = "";
		str += "Dragon Stone: " + this.dragonStoneCarried + " \n";
		str += "Location: " + this.row + ":" + this.column + " \n";
		for(Tuple<Point, Boolean> whiteWalkerState : this.whiteWalkerStatus)
			str += "  WW(" + whiteWalkerState.getLeft().getX() + ":" + whiteWalkerState.getLeft().getY() + ")" + (whiteWalkerState.getRight()?"D":"A") + "\n";
		str += "\n\n";
		return str;
	}
	
}
