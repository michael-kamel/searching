package searching.problems;

import java.util.Enumeration;

import searching.strategies.SearchTreeNode;

public abstract class SearchProblem {
	private final GoalTest goalTest;
	private final Enumeration<SearchAction> possibleActions;
	
	public SearchProblem(GoalTest goalTest, Enumeration<SearchAction> possibleActions) {
		this.goalTest = goalTest;
		this.possibleActions = possibleActions;
	}
	public abstract SearchState getInitialState();

	public abstract SearchTreeNode makeNode(SearchState initialState);

	public GoalTest getGoalTest() {
		return goalTest;
	}
	
	public Enumeration<SearchAction> getPossibleActions() {
		return possibleActions;
	}
	
	public abstract Iterable<SearchTreeNode> expand(SearchTreeNode nodeToCheck);
}
