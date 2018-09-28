package searching.structs;

import java.util.Optional;

public class SearchTreeNode {
	private final Optional<SearchTreeNode> parent; // The parent of the node in the tree
	private final int cost; // The cost from the root of the tree up to this node
	private final SearchState state; // The current state of the node
	private final SearchAction action; // The action made to reach this state from the previous
	
	public SearchTreeNode(Optional<SearchTreeNode> parent, int cost, SearchState state, SearchAction action) {
		this.parent = parent;
		this.cost = cost;
		this.state = state;
		this.action = action;
	}

	public Optional<SearchTreeNode> getParent() {
		return parent;
	}

	public int getCost() {
		return cost;
	}

	public SearchState getCurrentState() {
		return state;
	}

	public SearchAction getAction() {
		return action;
	}
	
}
