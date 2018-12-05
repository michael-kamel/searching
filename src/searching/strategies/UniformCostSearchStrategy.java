package searching.strategies;

import java.util.Optional;
import java.util.PriorityQueue;

import searching.agents.SearchTreeNode;
import searching.problems.SearchAction;
import searching.problems.SearchState;

public class UniformCostSearchStrategy<T extends SearchState, V extends SearchAction> extends UninformedSearchStrategy<T, V> {
	private final PriorityQueue<SearchTreeNode<T, V>> queue;
	
	public UniformCostSearchStrategy() {
		this.queue = new PriorityQueue<SearchTreeNode<T, V>>();
	}
	
	@Override
	public void addNode(SearchTreeNode<T, V> node) {
		queue.add(node);
	}

	@Override
	public Optional<SearchTreeNode<T, V>> getNext() {
		SearchTreeNode<T, V> head = queue.poll();
		if(head == null)
			return Optional.empty();
		
		return Optional.of(head);
	}
}
