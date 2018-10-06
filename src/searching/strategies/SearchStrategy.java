package searching.strategies;

import java.util.Optional;

import searching.agents.SearchTreeNode;
import searching.problems.SearchAction;
import searching.problems.SearchState;

public abstract class SearchStrategy<T extends SearchState, V extends SearchAction> {
	public void addNodes(Iterable<SearchTreeNode<T, V>> nodes) {
		nodes.forEach(this::addNode);
	}

	public abstract void addNode(SearchTreeNode<T, V> node);
	public abstract Optional<SearchTreeNode<T, V>> getNext(); 
}
