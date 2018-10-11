package searching.strategies;

import searching.problems.SearchState;

@FunctionalInterface
public interface HeuristicFunction<T extends SearchState> {
	long expectedCostToSolution(T state);
}
