package searching.strategies;

import java.util.Optional;
import java.util.Stack;

public class IterativeDeepeningSearchStrategy extends UninformedSearchStrategy {
	private final Stack<SearchTreeNode> stack;
	private int currentMaxDepth = 0;
	private boolean addedFirstNode = false;
	private SearchTreeNode rootNode;
	
	public IterativeDeepeningSearchStrategy() {
		this.stack = new Stack<SearchTreeNode>();
	}

	@Override
	public void addNode(SearchTreeNode node) {
		if(!addedFirstNode)
			rootNode = node;
		
		if(node.getDepth() > currentMaxDepth) {
			currentMaxDepth++;
			stack.clear();
			stack.push(rootNode);
			return;
		}
		
		this.stack.push(node);
	}

	@Override
	public Optional<SearchTreeNode> getNext() {
		if(stack.isEmpty())
			return Optional.empty();
		
		return Optional.of(stack.pop());
	}
}
