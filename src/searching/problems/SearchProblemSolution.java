package searching.problems;

import java.util.Optional;

import searching.strategies.SearchTreeNode;

public class SearchProblemSolution {
	
	private final Optional<SearchTreeNode> node;
	private final SearchProblem problem;
	private final int expandedNodesCount;
	
	public SearchProblemSolution(SearchProblem problem, Optional<SearchTreeNode> node, int expandedNodesCount) {
		this.problem = problem;
		this.node = node;
		this.expandedNodesCount = expandedNodesCount;
	}
	
	public Optional<SearchTreeNode> getNode() {
		return this.node;
	}

	public SearchProblem getProblem() {
		return this.problem;
	}
	
	public int getExpandedNodesCount() {
		return expandedNodesCount;
	}
	
	public static SearchProblemSolution Failure(SearchProblem problem, int expandedNodesCount) {
		return new FailedSearchProblemSolution(problem, Optional.empty(), expandedNodesCount);
	}
	
	public static final class FailedSearchProblemSolution extends SearchProblemSolution {
		public FailedSearchProblemSolution(SearchProblem problem, Optional<SearchTreeNode> node, int expandedNodesCount) {
			super(problem, node, expandedNodesCount);
		}
	}
	
}
