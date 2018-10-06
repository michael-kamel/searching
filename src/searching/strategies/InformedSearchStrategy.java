package searching.strategies;

import searching.problems.SearchAction;
import searching.problems.SearchState;

public abstract class InformedSearchStrategy<T extends SearchState, V extends SearchAction> extends SearchStrategy<T, V> {
	
	private final HeuristicFunction<T> heuristicFunction;
	
	public InformedSearchStrategy(HeuristicFunction<T> heuristicFunction) {
		this.heuristicFunction = heuristicFunction;
	}
	
	protected HeuristicFunction<T> getHeuristic() {
		return this.heuristicFunction;
	}
}
