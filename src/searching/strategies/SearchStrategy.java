package searching.strategies;

import java.util.Optional;

public interface SearchStrategy {
	void addNodes(Iterable<SearchTreeNode> nodes);
	void addNode(SearchTreeNode node);
	Optional<SearchTreeNode> getNext(); //dequeue
}
