package searching.strategies;

import searching.problems.SearchState;

public abstract class InformedSearchStrategy<T extends SearchState> extends SearchStrategy<T> {
	
	private final HeuristicFunction<T> heuristic;
	
	public InformedSearchStrategy(HeuristicFunction<T> heuristic) {
		this.heuristic = heuristic;
	}
	
	protected HeuristicFunction<T> getHeuristic() {
		return this.heuristic;
	}
}
