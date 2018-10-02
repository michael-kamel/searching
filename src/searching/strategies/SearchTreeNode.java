package searching.strategies;

import java.util.Optional;

import searching.problems.SearchAction;
import searching.problems.SearchState;

public class SearchTreeNode<T extends SearchState> implements Comparable<SearchTreeNode<T>> {
	private final Optional<SearchTreeNode<T>> parent; // The parent of the node in the tree
	private final long cost; // The cost from the root of the tree up to this node
	private final T state; // The current state of the node
	private final SearchAction action; // The action made to reach this state from the previous
	private final int depth; //root depth:0; depth from root 
	
	public SearchTreeNode(Optional<SearchTreeNode<T>> parent, long cost, T state, SearchAction action, int depth) {
		this.parent = parent;
		this.cost = cost;
		this.state = state;
		this.action = action;
		this.depth = depth;
	}

	public Optional<SearchTreeNode<T>> getParent() {
		return parent;
	}

	public long getCost() {
		return cost;
	}

	public T getCurrentState() {
		return state;
	}

	public SearchAction getAction() {
		return action;
	}
	
	public int getDepth() {
		return depth;
	}

	@Override
	public int compareTo(SearchTreeNode<T> node) {
		return this.cost > node.cost ? 1 : this.cost == node.cost ? 0 : -1;
	}
	
}
