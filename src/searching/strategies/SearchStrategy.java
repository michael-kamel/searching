package searching.strategies;

import java.util.Optional;

import searching.agents.SearchTreeNode;
import searching.problems.SearchState;

public abstract class SearchStrategy<T extends SearchState> {
	public void addNodes(Iterable<SearchTreeNode<T>> nodes) {
		nodes.forEach(this::addNode);
	}
	
	public abstract void addNode(SearchTreeNode<T> node);
	public abstract Optional<SearchTreeNode<T>> getNext(); //dequeue
}
