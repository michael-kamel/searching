package searching.strategies;

import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;

import searching.agents.SearchTreeNode;
import searching.problems.SearchState;

public class GreedySearchStrategy<T extends SearchState> extends InformedSearchStrategy<T> {
	private final PriorityQueue<SearchTreeNode<T>> queue;
	
	public GreedySearchStrategy(HeuristicFunction<T> heuristic) {
		super(heuristic);
		this.queue = new PriorityQueue<SearchTreeNode<T>>(new Comparator<SearchTreeNode<T>>() {
			@Override
			public int compare(SearchTreeNode<T> firstNode, SearchTreeNode<T> secondNode) {
				long firstNodeHeuristicCost = heuristic.expectedCostToSolution(
						firstNode.getCurrentState());
				long secondNodeHeuristicCost = heuristic.expectedCostToSolution(
						secondNode.getCurrentState());
				
				return firstNodeHeuristicCost > secondNodeHeuristicCost? 1 : 
					firstNodeHeuristicCost == secondNodeHeuristicCost ? 0 : -1;
			}
		});
	}

	@Override
	public void addNode(SearchTreeNode<T> node) {
		this.queue.add(node);
	}

	@Override
	public Optional<SearchTreeNode<T>> getNext() {
		SearchTreeNode<T> head = queue.poll();
		if(head == null)
			return Optional.empty();
		
		return Optional.of(head);
	}
}
