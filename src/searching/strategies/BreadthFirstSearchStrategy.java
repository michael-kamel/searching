package searching.strategies;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import searching.agents.SearchTreeNode;
import searching.problems.SearchState;

public class BreadthFirstSearchStrategy<T extends SearchState> extends UninformedSearchStrategy<T> {
	private final Queue<SearchTreeNode<T>> queue;
	
	public BreadthFirstSearchStrategy() {
		this.queue = new LinkedList<SearchTreeNode<T>>();
	}

	@Override
	public void addNode(SearchTreeNode<T> node) {
		queue.add(node);
	}

	@Override 
	public Optional<SearchTreeNode<T>> getNext() {
		SearchTreeNode<T> node = queue.poll();
		if(node == null)
			return Optional.empty();
		
		return Optional.of(node);
	}
}
