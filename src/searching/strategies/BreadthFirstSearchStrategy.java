package searching.strategies;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class BreadthFirstSearchStrategy implements UninformedSearchStrategy {

	private final Queue<SearchTreeNode> queue;
	
	public BreadthFirstSearchStrategy() {
		this.queue = new LinkedList<SearchTreeNode>();
	}
	
	@Override
	public void addNodes(Iterable<SearchTreeNode> nodes) {
		nodes.forEach(this::addNode);
	}

	@Override
	public void addNode(SearchTreeNode node) {
		queue.add(node);
	}

	@Override
	public Optional<SearchTreeNode> getNext() {
		SearchTreeNode node = queue.poll();
		if(node == null)
			return Optional.empty();
		
		return Optional.of(node);
	}
}
