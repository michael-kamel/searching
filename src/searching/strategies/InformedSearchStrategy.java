package searching.strategies;

import searching.problems.SearchAction;
import searching.problems.SearchProblemWithHeuristic;
import searching.problems.SearchState;

public abstract class InformedSearchStrategy<T extends SearchState, V extends SearchAction>
	extends SearchStrategy<T> {
	
	private final HeuristicFunction<T> heuristic;
	
	public InformedSearchStrategy(SearchProblemWithHeuristic<T, V> problem) {
		this.heuristic = problem::getHeuristicCost;
	}
	
	protected HeuristicFunction<T> getHeuristic() {
		return this.heuristic;
	}
}
