package searching.strategies;

import java.util.Optional;
import java.util.Stack;

import searching.problems.SearchState;

public class IterativeDeepeningSearchStrategy<T extends SearchState> extends UninformedSearchStrategy<T> {
	private final Stack<SearchTreeNode<T>> stack;
	private int currentMaxDepth = 0;
	private boolean addedFirstNode = false;
	private SearchTreeNode<T> rootNode;
	private boolean hasMoreDepth;
	
	public IterativeDeepeningSearchStrategy() {
		this.stack = new Stack<SearchTreeNode<T>>();
		this.hasMoreDepth = false;
	}

	@Override
	public void addNode(SearchTreeNode<T> node) {
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
	public Optional<SearchTreeNode<T>> getNext() {
		if(stack.isEmpty()) {
			if(this.hasMoreDepth)
				this.resetProblem();
			else
				return Optional.empty();
		}
		
		return Optional.of(stack.pop());
	}
	
	public void setDepth(int depth) {
		this.currentMaxDepth = depth;
	}

	private void resetProblem() {
		currentMaxDepth++;
		stack.clear();
		stack.push(rootNode);
		this.hasMoreDepth = false;
	}
}
