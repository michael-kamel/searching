package searching.strategies;

import searching.problems.SearchState;

@FunctionalInterface
public interface HeuristicFunction<T extends SearchState> {
	public long expectedCostToSolution(T state);
}
