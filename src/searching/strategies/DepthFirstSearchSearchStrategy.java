package searching.strategies;

import java.util.Optional;
import java.util.Stack;

import searching.agents.SearchTreeNode;
import searching.problems.SearchAction;
import searching.problems.SearchState;

public class DepthFirstSearchSearchStrategy<T extends SearchState, V extends SearchAction> extends UninformedSearchStrategy<T, V> {
	private final Stack<SearchTreeNode<T, V>> stack;
	
	public DepthFirstSearchSearchStrategy() {
		this.stack = new Stack<SearchTreeNode<T, V>>();
	}

	@Override
	public void addNode(SearchTreeNode<T, V> node) {
		this.stack.push(node);
	}

	@Override
	public Optional<SearchTreeNode<T, V>> getNext() {
		if(stack.isEmpty())
			return Optional.empty();
		
		return Optional.of(stack.pop());
	}
}
