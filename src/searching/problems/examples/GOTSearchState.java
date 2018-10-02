package searching.problems.examples;

import java.util.ArrayList;

import searching.problems.SearchState;
import searching.utils.Tuple;

public class GOTSearchState extends SearchState {
	private final int dragonGlassCarried;
	private final Tuple<Integer, Integer> location;
	private final ArrayList<Tuple<Tuple<Integer, Integer>, Boolean>> whiteWalkerStatus; //false: alive  true: dead
	
	public GOTSearchState(int dragonStoneCarried, Tuple<Integer, Integer> location, 
			ArrayList<Tuple<Tuple<Integer, Integer>, Boolean>> whiteWalkerStatus) {
		super();
		this.dragonGlassCarried = dragonStoneCarried;
		this.location = location;
		this.whiteWalkerStatus = whiteWalkerStatus;
	}

	public int getDragonStoneCarried() {
		return this.dragonGlassCarried;
	}

	public Tuple<Integer, Integer> getLocation() {
		return this.location;
	}

	public ArrayList<Tuple<Tuple<Integer, Integer>, Boolean>> getWhiteWalkerStatus() {
		return this.whiteWalkerStatus;
	}
	
	public String toString() {
		String str = "";
		str += "Dragon Stone: " + this.dragonGlassCarried + " \n";
		str += "Location: " + this.location + " \n";
		for(Tuple<Tuple<Integer, Integer>, Boolean> whiteWalkerState : this.whiteWalkerStatus)
			str += "  WW(" + whiteWalkerState.getLeft() + ")" + (whiteWalkerState.getRight()?"D":"A") + "\n";
		str += "\n\n";
		return str;
	}
	
	
	public static class GOTSearchStateBuilder{
		private int dragonStoneCarried;
		private Tuple<Integer, Integer> location;
		private ArrayList<Tuple<Tuple<Integer, Integer>, Boolean>> whiteWalkerStatus; //false: alive  true: dead
		
		public GOTSearchStateBuilder setDragonStoneCarried(int dragonStoneCarried) {
			this.dragonStoneCarried = dragonStoneCarried;
			return this;
		}
		public GOTSearchStateBuilder setLocation(Tuple<Integer, Integer> location) {
			this.location = location; 
			return this;
		}

		public GOTSearchStateBuilder setWhiteWalkerStatus(ArrayList<Tuple<Tuple<Integer, Integer>, Boolean>> whiteWalkerStatus) {
			this.whiteWalkerStatus = whiteWalkerStatus;
			return this;
		}
		
		public GOTSearchState build() {
			return new GOTSearchState(dragonStoneCarried, location, whiteWalkerStatus);
		}
	}
}
