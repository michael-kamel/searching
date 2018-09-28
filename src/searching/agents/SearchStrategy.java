package searching.agents;

import java.util.Optional;

import searching.structs.SearchTreeNode;

public interface SearchStrategy {
	void addNodes(Iterable<SearchTreeNode> nodes);
	Optional<SearchTreeNode> getNext();
}
