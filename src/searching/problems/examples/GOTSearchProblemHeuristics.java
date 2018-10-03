package searching.problems.examples;

import searching.strategies.HeuristicFunction;

public class GOTSearchProblemHeuristics {
	
	/**
	 * **Admissible**
	 * At best the remaining cost from a state will be
	 * equal to the number of remaining whitewalkers / 3
	 * given that each stab kills 3 white walkers at best
	 * + at after each stab there would be at least one extra movement 
	 * step needed
	 */	
	public static HeuristicFunction<GOTSearchState> stabHeuristic(GOTSearchProblem problem) {
		return state -> {
			int whiteWalkersRemaining = (int)state.getWhiteWalkerStatus().stream()
					.filter(whiteWalkerState -> !whiteWalkerState.getRight())
					.count();
				
				long whiteWalkersRemainingStabCount = ((long)Math.ceil(((whiteWalkersRemaining / 3.0))));
				
				return  (whiteWalkersRemainingStabCount * problem.getActionCost(GOTSearchAction.STAB)) 
						+  whiteWalkersRemainingStabCount * problem.getActionCost(GOTSearchAction.MOVE_DOWN);
		};
	}
}
