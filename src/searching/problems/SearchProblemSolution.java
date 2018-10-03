package searching.problems;

import java.util.LinkedList;
import java.util.Optional;

import searching.agents.SearchTreeNode;

public class SearchProblemSolution<T extends SearchState, V extends SearchAction> {
	
	private final Optional<SearchTreeNode<T>> node;
	private final SearchProblem<T, V> problem;
	private final int expandedNodesCount;
	private final LinkedList<SearchTreeNode<T>> nodeSequence;
	
	public SearchProblemSolution(SearchProblem<T, V> problem, Optional<SearchTreeNode<T>> node, 
			int expandedNodesCount) {
		this.problem = problem;
		this.node = node;
		this.expandedNodesCount = expandedNodesCount;
		this.nodeSequence = getNodeSequence();
	}
	
	public Optional<SearchTreeNode<T>> getNode() {
		return this.node; //goalNode
	}

	public SearchProblem<T, V> getProblem() {
		return this.problem;
	}
	
	public int getExpandedNodesCount() {
		return expandedNodesCount;
	}
	
	public LinkedList<SearchTreeNode<T>> getNodeSequence() {
		if(this.nodeSequence != null)
			return this.nodeSequence;
		
		LinkedList<SearchTreeNode<T>> list = new LinkedList<SearchTreeNode<T>>();
		
		Optional<SearchTreeNode<T>> node = this.node;
		while(node.isPresent()) {
			list.addFirst(node.get()); 
			node = node.get().getParent();
		}
		
		return list;
	}
	
	public void visualizeSolution() {
		Iterable<SearchTreeNode<T>> solution = this.getNodeSequence();
		solution.forEach(node -> {
			this.problem.visualize(node.getCurrentState());
		});
	}
	
	private void showNode(SearchTreeNode<T> node) {
		if(node.getParent().isPresent())
			showNode(node.getParent().get());
		
		System.out.println(node.getCurrentState());
	}
	
	public void showNodeSequence() {//sequence of any nodes (not necessarily a solution)
		if(this.node.isPresent()) {
			showNode(this.node.get());
		} else {
			System.out.println("No Solution");
		}
	}
	
	public int getSolutionStepCount() {
		return this.getNodeSequence().size();
	}

	public static final class FailedSearchProblemSolution<T extends SearchState, V extends SearchAction> 
			extends SearchProblemSolution<T, V> {
		public FailedSearchProblemSolution(SearchProblem<T, V> problem, Optional<SearchTreeNode<T>> 
			node, int expandedNodesCount) {
			super(problem, node, expandedNodesCount);
		}
	}
	
	public static final class BottomProblemSolution<T extends SearchState, V extends SearchAction> 
			extends SearchProblemSolution<T, V> {
		public BottomProblemSolution(SearchProblem<T, V> problem, Optional<SearchTreeNode<T>> 
			node, int expandedNodesCount) {
			super(problem, node, expandedNodesCount);
		}
	}

	public static <T extends SearchState, V extends SearchAction> SearchProblemSolution<T, V> NoSolution(
			SearchProblem<T, V> problem, int expandedNodesCount) {
		return new FailedSearchProblemSolution<T, V>(problem, Optional.empty(), expandedNodesCount);
	}
	
	
	public static <T extends SearchState, V extends SearchAction> SearchProblemSolution<T, V> Bottom(
			SearchProblem<T, V> problem, int expandedNodesCount) {
		return new BottomProblemSolution<T, V>(problem, Optional.empty(), expandedNodesCount);
	}
	
}
