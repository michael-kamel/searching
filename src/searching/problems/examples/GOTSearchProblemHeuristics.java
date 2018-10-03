package searching.problems.examples;

import searching.problems.HeuristicFunctions;
import searching.strategies.HeuristicFunction;
import searching.utils.Geomtry;
import searching.utils.Tuple;

public class GOTSearchProblemHeuristics {
	
	/**
	 * <h1>Admissible<h1>
	 * At best the remaining cost from a state will be
	 * equal to the number of remaining whitewalkers / 3
	 * given that each stab kills 3 white walkers at best
	 * + at after each stab there would be at least one extra movement 
	 * step needed
	 * @param problem the problem to create a heuristic for
	 * @return {@link HeuristicFunction}
	 */
	public static HeuristicFunction<GOTSearchState> stabHeuristic(SaveWesteros problem) {
		return state -> {
			if(problem.goalTest(state)) // centering
				return 0;
			
			int whiteWalkersRemaining = (int)state.getWhiteWalkerStatus().stream()
					.filter(whiteWalkerState -> !whiteWalkerState.getRight())
					.count();
				
				long whiteWalkersRemainingStabCount = ((long)Math.ceil(((whiteWalkersRemaining / 3.0))));
				
				return  (whiteWalkersRemainingStabCount * problem.getActionCost(GOTSearchAction.STAB)) 
						+  whiteWalkersRemainingStabCount * problem.getActionCost(GOTSearchAction.MOVE_DOWN);
		};
	}
	
	/**
	 * <h1>Admissible<h1>
	 * Calculates the distance to a cell adjacent(-1) to
	 * the farthest alive white walker + the cost of a
	 * stab action
	 * @param problem the problem to create a heuristic for
	 * @return {@link HeuristicFunction}
	 */
	public static HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic(SaveWesteros problem) {
		return state -> {
			if(problem.goalTest(state)) // centering
				return 0;
			
			Tuple<Integer, Integer> currentLocation = state.getLocation();
			
			long maxDistance = state.getWhiteWalkerStatus().stream()
				.filter(whiteWalkerState -> !whiteWalkerState.getRight())
				.mapToLong(whiteWalkerState -> Geomtry.ManhattanDistance(currentLocation, whiteWalkerState.getLeft()))
				.max().orElse(0);
			
			return (maxDistance - 1) + problem.getActionCost(GOTSearchAction.STAB);
		};
	}
			
	/**
	 * <h1>Admissible<h1>
	 * Returns the composition of {@link #furthestWhiteWalkerHeuristic}
	 * & {@link #stabHeuristic}}
	 * @param problem the problem to create a heuristic for
	 * @return {@link HeuristicFunction}
	 */
	public static HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeuristic(SaveWesteros problem) {
		return HeuristicFunctions.dominating(furthestWhiteWalkerHeuristic(problem), stabHeuristic(problem));
	}
	
	/**
	 * <h1>Not Admissible<h1>
	 * Calculates the distance to a cell adjacent(-1) to
	 * the nearest alive white walker + the cost of a
	 * stab action
	 * @param problem the problem to create a heuristic for
	 * @return {@link HeuristicFunction}
	 */
	public static HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic(SaveWesteros problem) {
		return state -> {
			if(problem.goalTest(state)) // centering
				return 0;
			
			Tuple<Integer, Integer> currentLocation = state.getLocation();
			
			long maxDistance = state.getWhiteWalkerStatus().stream()
				.filter(whiteWalkerState -> !whiteWalkerState.getRight())
				.mapToLong(whiteWalkerState -> Geomtry.ManhattanDistance(currentLocation, whiteWalkerState.getLeft()))
				.min().orElse(0);
			
			return (maxDistance - 1) + problem.getActionCost(GOTSearchAction.STAB);
		};
	}
}