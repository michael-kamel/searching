package searching.problems;

import java.util.LinkedList;
import java.util.Optional;
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

	public abstract T getNewState(T node, V action);
	
	public Iterable<V> getPossibleActions() {
		return this.possibleActions;
	}
	
	public long pathCost(SearchTreeNode<T> node) {
		return node.getCost();
	}
	
	public Iterable<SearchTreeNode<T>> expand(SearchTreeNode<T> nodeToCheck) {
		T currentState = nodeToCheck.getCurrentState();
		
		LinkedList<SearchTreeNode<T>> newNodes = new LinkedList<SearchTreeNode<T>>();
		
		this.getPossibleActions().forEach(new Consumer<V>() {
			@Override
			public void accept(V action) {
				if(canTransition(currentState, action)) {
					T newState = getNewState(nodeToCheck.getCurrentState(), action);
					SearchTreeNode<T> newNode = new SearchTreeNode<T>(Optional.of(nodeToCheck), nodeToCheck.getCost()+action.getCost(), newState, action, nodeToCheck.getDepth()+1);   
					newNodes.add(newNode);
				}
			}
		});
		
		return newNodes;
	}
	
	public abstract boolean canTransition(T state, V action);
}
