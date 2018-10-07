package searching.problems;

import java.util.LinkedList;
import java.util.Optional;

import searching.agents.SearchTreeNode;
import searching.visualizers.Visualizer;

public class SearchProblemSolution<T extends SearchState, V extends SearchAction> {
	
	private final Optional<SearchTreeNode<T, V>> node;
	private final SearchProblem<T, V> problem;
	private final int expandedNodesCount;
	private final LinkedList<SearchTreeNode<T, V>> nodeSequence;
	
	public SearchProblemSolution(SearchProblem<T, V> problem, Optional<SearchTreeNode<T, V>> node, 
			int expandedNodesCount) {
		this.problem = problem;
		this.node = node;
		this.expandedNodesCount = expandedNodesCount;
		this.nodeSequence = getNodeSequence();
	}
	
	public Optional<SearchTreeNode<T, V>> getNode() {
		return this.node; //goalNode
	}

	public SearchProblem<T, V> getProblem() {
		return this.problem;
	}
	
	public int getExpandedNodesCount() {
		return expandedNodesCount;
	}
	
	public LinkedList<SearchTreeNode<T, V>> getNodeSequence() {
		if(this.nodeSequence != null)
			return this.nodeSequence;
		
		LinkedList<SearchTreeNode<T, V>> list = new LinkedList<SearchTreeNode<T, V>>();
		
		Optional<SearchTreeNode<T, V>> node = this.node;
		while(node.isPresent()) {
			list.addFirst(node.get()); 
			node = node.get().getParent();
		}
		
		return list;
	}
	
	public void visualizeSolution(boolean actionsOnly, boolean showActionCost) {
		
		Iterable<SearchTreeNode<T, V>> solution = this.getNodeSequence();
		solution.forEach(node -> {
			if(node.getParent().isPresent())
				this.problem.getVisualizer().visualizeLine(node.getAction().get() +
						(showActionCost ? ": " + problem.getActionCost(
						node.getParent().get().getCurrentState(), node.getAction().get()) : ""));
			
			if(!actionsOnly)
				this.problem.visualize(node.getCurrentState());
		});
	}
	
	private void showNode(SearchTreeNode<T, V> node) {
		if(node.getParent().isPresent())
			showNode(node.getParent().get());
		
		this.problem.getVisualizer().visualizeLine(node.getCurrentState().toString());
	}
	
	public void showNodeSequence() {//sequence of any nodes (not necessarily a solution)
		if(this.node.isPresent()) {
			showNode(this.node.get());
		} else {
			this.problem.getVisualizer().visualizeLine("No Solution");
		}
	}
	
	public int getSolutionStepCount() {
		return this.getNodeSequence().size();
	}

	public static final class FailedSearchProblemSolution<T extends SearchState, V extends SearchAction> 
			extends SearchProblemSolution<T, V> {
		public FailedSearchProblemSolution(SearchProblem<T, V> problem, Optional<SearchTreeNode<T, V>> 
			node, int expandedNodesCount) {
			super(problem, node, expandedNodesCount);
		}
	}
	
	public static final class BottomProblemSolution<T extends SearchState, V extends SearchAction> 
			extends SearchProblemSolution<T, V> {
		public BottomProblemSolution(SearchProblem<T, V> problem, Optional<SearchTreeNode<T, V>> 
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
