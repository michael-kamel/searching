package searching.problems;

import searching.exceptions.SearchProblemException;

public abstract class SearchProblemWithHeuristic<T extends SearchState, V extends SearchAction>
	extends SearchProblem<T, V> {

	public SearchProblemWithHeuristic(Iterable<V> possibleActions) throws SearchProblemException {
		super(possibleActions);
	}
	
	public abstract long getHeuristicCost(T state);
}
