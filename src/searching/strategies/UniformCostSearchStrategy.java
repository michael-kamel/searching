package searching.strategies;

import java.util.Optional;
import java.util.PriorityQueue;

import searching.problems.SearchState;

public class UniformCostSearchStrategy<T extends SearchState> extends UninformedSearchStrategy<T> {
	private final PriorityQueue<SearchTreeNode<T>> queue;
	
	public UniformCostSearchStrategy() {
		this.queue = new PriorityQueue<SearchTreeNode<T>>();
	}
	
	@Override
	public void addNode(SearchTreeNode<T> node) {
		queue.add(node);
	}

	@Override
	public Optional<SearchTreeNode<T>> getNext() {
		SearchTreeNode<T> head = queue.poll();
		if(head == null)
			return Optional.empty();
		
		return Optional.of(head);
	}
}
