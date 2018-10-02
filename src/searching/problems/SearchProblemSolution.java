package searching.problems;

import java.util.Optional;

import searching.strategies.SearchTreeNode;

public class SearchProblemSolution<T extends SearchState, V extends SearchAction> {
	
	private final Optional<SearchTreeNode<T>> node;
	private final SearchProblem<T, V> problem;
	private final int expandedNodesCount;
	
	public SearchProblemSolution(SearchProblem<T, V> problem, Optional<SearchTreeNode<T>> node, int expandedNodesCount) {
		this.problem = problem;
		this.node = node;
		this.expandedNodesCount = expandedNodesCount;
	}
	
	public Optional<SearchTreeNode<T>> getNode() {
		return this.node;
	}

	public SearchProblem<T, V> getProblem() {
		return this.problem;
	}
	
	public int getExpandedNodesCount() {
		return expandedNodesCount;
	}
	
	public void showNodeSequence() {
		if(this.node.isPresent()) {
			showNode(this.node.get());
		} else {
			System.out.println("No Solution");
		}
	}
	
	private void showNode(SearchTreeNode<T> node) {
		if(node.getParent().isPresent())
			showNode(node.getParent().get());
		
		System.out.println(node.getCurrentState());
	}
	
	public static <T extends SearchState, V extends SearchAction> SearchProblemSolution<T, V> NoSolution(SearchProblem<T, V> problem, int expandedNodesCount) {
		return new FailedSearchProblemSolution<T, V>(problem, Optional.empty(), expandedNodesCount);
	}
	
	public static final class FailedSearchProblemSolution<T extends SearchState, V extends SearchAction> extends SearchProblemSolution<T, V> {
		public FailedSearchProblemSolution(SearchProblem<T, V> problem, Optional<SearchTreeNode<T>> node, int expandedNodesCount) {
			super(problem, node, expandedNodesCount);
		}
	}
	
	public static <T extends SearchState, V extends SearchAction> SearchProblemSolution<T, V> Bottom(SearchProblem<T, V> problem, int expandedNodesCount) {
		return new BottomProblemSolution<T, V>(problem, Optional.empty(), expandedNodesCount);
	}
	
	public static final class BottomProblemSolution<T extends SearchState, V extends SearchAction> extends SearchProblemSolution<T, V> {
		public BottomProblemSolution(SearchProblem<T, V> problem, Optional<SearchTreeNode<T>> node, int expandedNodesCount) {
			super(problem, node, expandedNodesCount);
		}
	}
	
}
