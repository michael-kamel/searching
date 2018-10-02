package searching.strategies;

import java.util.Optional;
import java.util.Stack;

import searching.problems.SearchState;

public class IterativeDeepeningSearchStrategy<T extends SearchState> extends UninformedSearchStrategy<T> {
	private final Stack<SearchTreeNode<T>> stack;
	private int currentMaxDepth = 0;
	private boolean addedFirstNode = false;
	private SearchTreeNode<T> rootNode;
	
	public IterativeDeepeningSearchStrategy() {
		this.stack = new Stack<SearchTreeNode<T>>();
	}

	@Override
	public void addNode(SearchTreeNode<T> node) {
		if(!addedFirstNode) {
			rootNode = node;
			addedFirstNode = true;
		}
		
		if(node.getDepth() > currentMaxDepth) {
			currentMaxDepth++;
			stack.clear();
			stack.push(rootNode);
			return;
		}
		
		this.stack.push(node);
	}

	@Override
	public Optional<SearchTreeNode<T>> getNext() {
		if(stack.isEmpty())
			return Optional.empty();
		
		return Optional.of(stack.pop());
	}
	
	public void setDepth(int depth) {
		this.currentMaxDepth = depth;
	}
}
