package searching.problems;

import searching.strategies.HeuristicFunction;

public class HeuristicFunctions {
	public static <T extends SearchState> HeuristicFunction<T> dominating(HeuristicFunction<T> first, HeuristicFunction<T> second) {
		return state -> {
			return Math.max(first.expectedCostToSolution(state), second.expectedCostToSolution(state));
		};
	}
}
