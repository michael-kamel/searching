package searching.problems;

import java.util.Optional;

import searching.strategies.SearchTreeNode;

public class SearchProblemSolution<T extends SearchState> {
	
	private final Optional<SearchTreeNode<T>> node;
	private final SearchProblem<T> problem;
	private final int expandedNodesCount;
	
	public SearchProblemSolution(SearchProblem<T> problem, Optional<SearchTreeNode<T>> node, int expandedNodesCount) {
		this.problem = problem;
		this.node = node;
		this.expandedNodesCount = expandedNodesCount;
	}
	
	public Optional<SearchTreeNode<T>> getNode() {
		return this.node;
	}

	public SearchProblem<T> getProblem() {
		return this.problem;
	}
	
	public int getExpandedNodesCount() {
		return expandedNodesCount;
	}
	
	public static <T extends SearchState> SearchProblemSolution<T> NoSolution(SearchProblem<T> problem, int expandedNodesCount) {
		return new FailedSearchProblemSolution<T>(problem, Optional.empty(), expandedNodesCount);
	}
	
	public static final class FailedSearchProblemSolution<T extends SearchState> extends SearchProblemSolution<T> {
		public FailedSearchProblemSolution(SearchProblem<T> problem, Optional<SearchTreeNode<T>> node, int expandedNodesCount) {
			super(problem, node, expandedNodesCount);
		}
	}
	
	public static <T extends SearchState> SearchProblemSolution<T> Bottom(SearchProblem<T> problem, int expandedNodesCount) {
		return new FailedSearchProblemSolution<T>(problem, Optional.empty(), expandedNodesCount);
	}
	
	public static final class BottomProblemSolution<T extends SearchState> extends SearchProblemSolution<T> {
		public BottomProblemSolution(SearchProblem<T> problem, Optional<SearchTreeNode<T>> node, int expandedNodesCount) {
			super(problem, node, expandedNodesCount);
		}
	}
	
}
