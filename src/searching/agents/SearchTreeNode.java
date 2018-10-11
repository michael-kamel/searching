package searching.agents;

import java.util.Optional;

import searching.problems.SearchAction;
import searching.problems.SearchState;

public class SearchTreeNode<T extends SearchState, V extends SearchAction> implements Comparable<SearchTreeNode<T, V>> {
	private final Optional<SearchTreeNode<T, V>> parent; //Parent of node
	private final long cost; // Path cost from the root of the tree until this node
	private final T state; // The current state of the node
	private final Optional<V> action; // The action made to reach this node from its parent
	private final int depth; //depth from root to this node; root depth==0
	
	public SearchTreeNode(Optional<SearchTreeNode<T, V>> parent, long cost, T state, Optional<V> action, int depth) {
		this.parent = parent;
		this.cost = cost;
		this.state = state;
		this.action = action;
		this.depth = depth;
	}

	public Optional<SearchTreeNode<T, V>> getParent() {
		return parent;
	}

	public long getCost() {
		return cost;
	}

	public T getCurrentState() {
		return state;
	}

	public Optional<V> getAction() {
		return action;
	}
	
	public int getDepth() {
		return depth;
	}

	@Override
	public int compareTo(SearchTreeNode<T, V> node) {
		return this.cost > node.cost ? 1 : this.cost == node.cost ? 0 : -1;
	}
	
}
