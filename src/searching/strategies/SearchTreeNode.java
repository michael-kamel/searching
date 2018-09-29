package searching.strategies;

import java.util.Optional;

import searching.problems.SearchAction;
import searching.problems.SearchState;

public class SearchTreeNode {
	private final Optional<SearchTreeNode> parent; // The parent of the node in the tree
	private final int cost; // The cost from the root of the tree up to this node
	private final SearchState state; // The current state of the node
	private final SearchAction action; // The action made to reach this state from the previous
	private final int depth; //root depth:0; depth from root 
	
	public SearchTreeNode(Optional<SearchTreeNode> parent, int cost, SearchState state, SearchAction action, int depth) {
		this.parent = parent;
		this.cost = cost;
		this.state = state;
		this.action = action;
		this.depth = depth; 
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
	
	public int getDepth() {
		return depth;
	}
	
}
