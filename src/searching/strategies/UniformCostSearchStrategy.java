package searching.strategies;

import java.util.Optional;
import java.util.PriorityQueue;

public class UniformCostSearchStrategy extends UninformedSearchStrategy {
	private final PriorityQueue<SearchTreeNode> queue;
	
	public UniformCostSearchStrategy() {
		this.queue = new PriorityQueue<SearchTreeNode>();
	}
	
	@Override
	public void addNode(SearchTreeNode node) {
		queue.add(node);
	}

	@Override
	public Optional<SearchTreeNode> getNext() {
		SearchTreeNode head = queue.poll();
		if(head == null)
			return Optional.empty();
		
		return Optional.of(head);
	}
}
