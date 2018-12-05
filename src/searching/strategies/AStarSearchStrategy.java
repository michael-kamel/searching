package searching.strategies;

import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;

import searching.agents.SearchTreeNode;
import searching.problems.SearchAction;
import searching.problems.SearchState;

public class AStarSearchStrategy<T extends SearchState, V extends SearchAction> extends InformedSearchStrategy<T, V> {
	private final PriorityQueue<SearchTreeNode<T, V>> queue;
	
	public AStarSearchStrategy(HeuristicFunction<T> heuristic) {
		super(heuristic);
		this.queue = new PriorityQueue<SearchTreeNode<T, V>>(new Comparator<SearchTreeNode<T, V>>() {
			@Override
			public int compare(SearchTreeNode<T, V> firstNode, SearchTreeNode<T, V> secondNode) {
				long firstNodePathCost = firstNode.getCost();
				long secondNodePathCost = secondNode.getCost();
				long firstNodeHeuristicCost = heuristic.expectedCostToSolution(
						firstNode.getCurrentState());
				long secondNodeHeuristicCost = heuristic.expectedCostToSolution(
						secondNode.getCurrentState());
				
				long firstNodeTotalCost = firstNodePathCost + firstNodeHeuristicCost;
				long secondNodeTotalCost = secondNodePathCost + secondNodeHeuristicCost;
				return firstNodeTotalCost > secondNodeTotalCost ? 1 : 
					(firstNodeTotalCost == secondNodeTotalCost ? 0 : -1);
			}
		});
	}

	@Override
	public void addNode(SearchTreeNode<T, V> node) {
		this.queue.add(node);
	}

	@Override
	public Optional<SearchTreeNode<T, V>> getNext() {
		SearchTreeNode<T, V> head = queue.poll();
		if(head == null)
			return Optional.empty();
		
		return Optional.of(head);
	}

}
