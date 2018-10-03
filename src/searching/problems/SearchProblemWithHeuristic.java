package searching.problems;

import searching.exceptions.SearchProblemException;
import searching.strategies.HeuristicFunction;

public abstract class SearchProblemWithHeuristic<T extends SearchState, V extends SearchAction>
	extends SearchProblem<T, V> {
	HeuristicFunction<T> heuristicFunction;
	
	public SearchProblemWithHeuristic(Iterable<V> possibleActions) throws SearchProblemException {
		super(possibleActions);
		this.heuristicFunction = this::getHeuristicCost;
	}
	
	protected abstract long getHeuristicCost(T state);
	
	public HeuristicFunction<T> getHeuristicFunction() {
		return this.heuristicFunction;
	}
}
