package searching.strategies;

import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;

import searching.agents.SearchTreeNode;
import searching.problems.SearchState;

public class AStarSearchStrategy<T extends SearchState> extends InformedSearchStrategy<T> {
	private final PriorityQueue<SearchTreeNode<T>> queue;
	
	public AStarSearchStrategy(HeuristicFunction<T> heuristic) {
		super(heuristic);
		this.queue = new PriorityQueue<SearchTreeNode<T>>(new Comparator<SearchTreeNode<T>>() {
			@Override
			public int compare(SearchTreeNode<T> firstNode, SearchTreeNode<T> secondNode) {
				long firstNodeCost = firstNode.getCost();
				long secondNodeCost = secondNode.getCost();
				long firstNodeHeuristicCost = getHeuristic().expectedCostToSolution(firstNode.getCurrentState());
				long secondNodeHeuristicCost = getHeuristic().expectedCostToSolution(secondNode.getCurrentState());
				
				long firstNodeTotalCost = firstNodeCost + firstNodeHeuristicCost;
				long secondNodeTotalCost = secondNodeCost + secondNodeHeuristicCost;
				return firstNodeTotalCost > secondNodeTotalCost ? 1 : firstNodeTotalCost == secondNodeTotalCost ? 0 : -1;
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
