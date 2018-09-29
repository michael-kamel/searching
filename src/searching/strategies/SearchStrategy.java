package searching.strategies;

import java.util.Optional;

public abstract class SearchStrategy {
	public void addNodes(Iterable<SearchTreeNode> nodes) {
		nodes.forEach(this::addNode);
	}
	
	public abstract void addNode(SearchTreeNode node);
	public abstract Optional<SearchTreeNode> getNext(); //dequeue
}
