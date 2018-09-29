package searching.strategies;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Stack;

import searching.problems.SearchAction;
import searching.problems.SearchState;

public class DepthFirstSearchSearchStrategy extends UninformedSearchStrategy {
	private final Stack<SearchTreeNode> stack;
	
	public DepthFirstSearchSearchStrategy() {
		this.stack = new Stack<SearchTreeNode>();
	}

	@Override
	public void addNode(SearchTreeNode node) {
		this.stack.push(node);
	}

	@Override
	public Optional<SearchTreeNode> getNext() {
		if(stack.isEmpty())
			return Optional.empty();
		
		return Optional.of(stack.pop());
	}
	
	public static void main(String[] args) {
		DepthFirstSearchSearchStrategy strat = new DepthFirstSearchSearchStrategy();
		
		LinkedList<SearchTreeNode> ll = new LinkedList<SearchTreeNode>();
		ll.add(new SearchTreeNode(null, 1, new SearchState() {}, new SearchAction(0) {}, 0));
		ll.add(new SearchTreeNode(null, 2, new SearchState() {}, new SearchAction(0) {}, 0));
		ll.add(new SearchTreeNode(null, 3, new SearchState() {}, new SearchAction(0) {}, 0));
		ll.add(new SearchTreeNode(null, 4, new SearchState() {}, new SearchAction(0) {}, 0));
		
		Iterator<SearchTreeNode> it = new Iterator<SearchTreeNode>() {
			
			@Override
			public SearchTreeNode next() {
				return ll.removeFirst();
			}
			
			@Override
			public boolean hasNext() {
				return ll.size() > 0;
			}
		};
		strat.addNodes(new Iterable<SearchTreeNode>() {
			@Override
			public Iterator<SearchTreeNode> iterator() {
				return it;
			}
		});
		
		Optional<SearchTreeNode> nextNode;
		while(true) {
			nextNode = strat.getNext();
			if(!nextNode.isPresent())
				break;
			System.out.println(nextNode.get().getCost());
		}
	}

}
