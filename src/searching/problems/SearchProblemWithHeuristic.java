package searching.problems;

import searching.exceptions.SearchProblemException;
import searching.strategies.HeuristicFunction;

public abstract class SearchProblemWithHeuristic<T extends SearchState, V extends SearchAction>
	extends SearchProblem<T, V> {

	public SearchProblemWithHeuristic(Iterable<V> possibleActions) throws SearchProblemException {
		super(possibleActions);
	}
	
	protected abstract long getHeuristicCost(T state);
	
	public HeuristicFunction<T> getHeuristicFunction() {
		return this::getHeuristicCost;
	}
}
