package searching.problems;

import java.util.Enumeration;

import searching.strategies.SearchTreeNode;

public abstract class SearchProblem {
	private final GoalTest goalTest;
	private final SearchState initialState;
	private final Enumeration<SearchAction> possibleActions;
	
	public SearchProblem(GoalTest goalTest, Enumeration<SearchAction> possibleActions, SearchState initialState) {
		this.goalTest = goalTest;
		this.possibleActions = possibleActions;
		this.initialState = initialState;
	}
	
	public SearchState getInitialState() {
		return this.initialState;
	}
	
	public GoalTest getGoalTest() {
		return this.goalTest;
	}

	public abstract SearchTreeNode makeNode(SearchState initialState);
	
	public Enumeration<SearchAction> getPossibleActions() {
		return possibleActions;
	}
	
	public abstract Iterable<SearchTreeNode> expand(SearchTreeNode nodeToCheck);
}
