package searching.strategies;

import java.util.Optional;
import java.util.Stack;

import searching.agents.SearchTreeNode;
import searching.problems.SearchAction;
import searching.problems.SearchState;

public class IterativeDeepeningSearchStrategy<T extends SearchState, V extends SearchAction> 
		extends UninformedSearchStrategy<T, V> {
	private final Stack<SearchTreeNode<T, V>> stack;
	private int currentMaxDepth;
	private boolean addedFirstNode;
	private SearchTreeNode<T, V> rootNode;
	private boolean hasMoreDepth; //checks if incrementing the depth introduces new nodes
	
	public IterativeDeepeningSearchStrategy() {
		this.stack = new Stack<SearchTreeNode<T, V>>();
		this.currentMaxDepth = 0;
		this.addedFirstNode = false;
		this.hasMoreDepth = false;
	}

	@Override
	public void addNode(SearchTreeNode<T, V> node) {
		if(!addedFirstNode) {
			rootNode = node;
			addedFirstNode = true;
		}
		
		if(node.getDepth() > this.currentMaxDepth) {
			this.hasMoreDepth = true;
			return;
		}
		
		this.stack.push(node);
	}

	@Override
	public Optional<SearchTreeNode<T, V>> getNext() {
		if(stack.isEmpty()) {
			if(this.hasMoreDepth)
				this.resetProblem();
			else
				return Optional.empty();
		}
		
		return Optional.of(stack.pop());
	}
	
	private void resetProblem() {
		this.currentMaxDepth++;
		this.stack.clear();
		this.stack.push(this.rootNode);
		this.hasMoreDepth = false;
	}
}
