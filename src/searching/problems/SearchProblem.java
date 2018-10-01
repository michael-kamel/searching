package searching.problems;

import java.util.LinkedList;
import java.util.function.Consumer;

import searching.exceptions.SearchProblemException;
import searching.strategies.SearchTreeNode;

public abstract class SearchProblem<T extends SearchState, V extends SearchAction> {
	private final Iterable<V> possibleActions;
	
	public SearchProblem(Iterable<V> possibleActions) throws SearchProblemException {
		this.possibleActions = possibleActions;
	}
	
	public abstract boolean goalTest(T node);
	
	public abstract T getInitialState();

	public abstract SearchTreeNode<T> makeNode(SearchTreeNode<T> node, V action);
	
	public Iterable<V> getPossibleActions() {
		return this.possibleActions;
	}
	
	public Iterable<SearchTreeNode<T>> expand(SearchTreeNode<T> nodeToCheck) {
		T currentState = nodeToCheck.getCurrentState();
		
		LinkedList<SearchTreeNode<T>> newNodes = new LinkedList<SearchTreeNode<T>>();
		
		this.getPossibleActions().forEach(new Consumer<V>() {
			@Override
			public void accept(V action) {
				if(canTransition(currentState, action))
					newNodes.add(makeNode(nodeToCheck, action));
			}
		});
		
		return newNodes;
	}
	
	public abstract boolean canTransition(T state, V action);
}
