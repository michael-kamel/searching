package searching.problems;

import java.util.LinkedList;
import java.util.function.Consumer;

import searching.exceptions.SearchProblemException;
import searching.strategies.SearchTreeNode;

public abstract class SearchProblem<T extends SearchState> {
	private final T initialState;
	private final Iterable<SearchAction> possibleActions;
	
	public SearchProblem(Iterable<SearchAction> possibleActions, T initialState) throws SearchProblemException {
		this.possibleActions = possibleActions;
		this.initialState = initialState;
	}
	
	public abstract boolean goalTest(T node);
	
	public T getInitialState() {
		return this.initialState;
	}

	public abstract SearchTreeNode<T> makeNode(SearchTreeNode<T> node, SearchAction action);
	
	public Iterable<SearchAction> getPossibleActions() {
		return this.possibleActions;
	}
	
	public Iterable<SearchTreeNode<T>> expand(SearchTreeNode<T> nodeToCheck) {
		T currentState = nodeToCheck.getCurrentState();
		
		LinkedList<SearchTreeNode<T>> newNodes = new LinkedList<SearchTreeNode<T>>();
		
		this.getPossibleActions().forEach(new Consumer<SearchAction>() {
			@Override
			public void accept(SearchAction action) {
				if(canTransition(currentState, action))
					newNodes.add(makeNode(nodeToCheck, action));
			}
		});
		
		return newNodes;
	}
	
	public abstract boolean canTransition(T state, SearchAction action);
}
