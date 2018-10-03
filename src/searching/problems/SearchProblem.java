package searching.problems;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Consumer;

import searching.agents.SearchTreeNode;
import searching.exceptions.SearchProblemException;

public abstract class SearchProblem<T extends SearchState, V extends SearchAction> {
	private final Iterable<V> possibleActions;
	
	public SearchProblem(Iterable<V> possibleActions) throws SearchProblemException {
		this.possibleActions = possibleActions;
	}
		
	public Iterable<V> getPossibleActions() {
		return this.possibleActions;
	}
	
	public long pathCost(SearchTreeNode<T> node) {
		return node.getCost();
	}
 
	/**
	 * called by the agent; expands a node, and the returned nodes are added to the
	 * data structure (queue-priority queue-stack)
	 * 
	 * @param nodeToCheck
	 * @return an Iterable of all possible next nodes
	 */
	public Iterable<SearchTreeNode<T>> expand(SearchTreeNode<T> nodeToCheck) {
		T currentState = nodeToCheck.getCurrentState();
		
		//newNodes: all possible nodes from curr node
		LinkedList<SearchTreeNode<T>> newNodes = new LinkedList<SearchTreeNode<T>>();
		
		this.getPossibleActions().forEach(new Consumer<V>() {//Consumer::FunctionalInterface
			@Override
			public void accept(V action) {
				if(canTransition(currentState, action)) {
					T newState = transition(nodeToCheck.getCurrentState(), action);
					
					SearchTreeNode<T> newNode = new SearchTreeNode<T>(Optional.of(nodeToCheck), 
							/* cost from root to nodeToCheck(parent node of the new node)  
							   + cost of action we executed to get the new node
							*/
							nodeToCheck.getCost() + getActionCost(action), newState, action, 
							nodeToCheck.getDepth()+1); 
					newNodes.add(newNode);
				}
			}
		});
		
		return newNodes;
	}
	
	public abstract boolean goalTest(T state);
	
	public abstract T getInitialState();

	public abstract T transition(T state, V action); //single-state problem

	public abstract boolean canTransition(T state, V action); 
	
	public abstract void visualize(); //visualizes problem itself
	
	public abstract void visualize(T state); //visualizes one state
	
	protected abstract long getActionCost(V action);
}
