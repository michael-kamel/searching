package searching.strategies;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import searching.agents.SearchTreeNode;
import searching.problems.SearchAction;
import searching.problems.SearchState;

public class BreadthFirstSearchStrategy<T extends SearchState, V extends SearchAction> extends UninformedSearchStrategy<T, V> {
	private final Queue<SearchTreeNode<T, V>> queue;
	
	public BreadthFirstSearchStrategy() {
		this.queue = new LinkedList<SearchTreeNode<T, V>>();
	}

	@Override
	public void addNode(SearchTreeNode<T, V> node) {
		queue.add(node);
	}

	@Override 
	public Optional<SearchTreeNode<T, V>> getNext() {
		SearchTreeNode<T, V> node = queue.poll();
		if(node == null)
			return Optional.empty();
		
		return Optional.of(node);
	}
}
