package searching.strategies;

import searching.problems.SearchState;

public abstract class InformedSearchStrategy<T extends SearchState> extends SearchStrategy<T> {
	
	private final HeuristicFunction<T> heuristicFunction;
	
	public InformedSearchStrategy(HeuristicFunction<T> heuristicFunction) {
		this.heuristicFunction = heuristicFunction;
	}
	
	protected HeuristicFunction<T> getHeuristic() {
		return this.heuristicFunction;
	}
}
