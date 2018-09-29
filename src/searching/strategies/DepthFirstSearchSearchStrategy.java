package searching.strategies;

import java.util.Optional;
import java.util.Stack;
import searching.problems.SearchState;

public class DepthFirstSearchSearchStrategy<T extends SearchState> extends UninformedSearchStrategy<T> {
	private final Stack<SearchTreeNode<T>> stack;
	
	public DepthFirstSearchSearchStrategy() {
		this.stack = new Stack<SearchTreeNode<T>>();
	}

	@Override
	public void addNode(SearchTreeNode<T> node) {
		this.stack.push(node);
	}

	@Override
	public Optional<SearchTreeNode<T>> getNext() {
		if(stack.isEmpty())
			return Optional.empty();
		
		return Optional.of(stack.pop());
	}
}
