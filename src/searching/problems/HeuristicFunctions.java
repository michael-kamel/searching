package searching.problems;

import searching.strategies.HeuristicFunction;

public class HeuristicFunctions {
	/**
	 * a function that calculates the dominant of two heuristic functions
	 * @param first 
	 * @param second
	 * @return {@link HeuristicFunction}
	 */
	public static <T extends SearchState> HeuristicFunction<T> dominating(HeuristicFunction<T> first, 
			HeuristicFunction<T> second) {
		return state -> {
			return Math.max(first.expectedCostToSolution(state), second.expectedCostToSolution(state));
		};
	}
}
