package searching.problems;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Consumer;

import searching.agents.SearchTreeNode;
import searching.exceptions.SearchProblemException;
import searching.visualizers.Visualizer;

public abstract class SearchProblem<T extends SearchState, V extends SearchAction> {
	private final Iterable<V> possibleActions;
	private Visualizer visualizer;
	
	public SearchProblem(Iterable<V> possibleActions, Visualizer visualizer) throws SearchProblemException {
		this.possibleActions = possibleActions;
		this.visualizer = visualizer;
	}
		
	public Visualizer getVisualizer() {
		return this.visualizer;
	}
	
	public void setVisualizer(Visualizer visualizer) {
		this.visualizer = visualizer;
	}
	
	public Iterable<V> getPossibleActions() {
		return this.possibleActions;
	}
	
	public long pathCost(SearchTreeNode<T, V> node) {
		return node.getCost();
	}
 
	/**
	 * called by the agent; expands a node, and the returned nodes are added to the
	 * data structure (queue-priority queue-stack)
	 * 
	 * @param nodeToCheck
	 * @return an Iterable of all possible next nodes
	 */
	public Iterable<SearchTreeNode<T, V>> expand(SearchTreeNode<T, V> nodeToCheck) {
		T currentState = nodeToCheck.getCurrentState();
		
		//newNodes: all possible nodes from curr node
		LinkedList<SearchTreeNode<T, V>> newNodes = new LinkedList<SearchTreeNode<T, V>>();
		
		this.getPossibleActions().forEach(new Consumer<V>() {//Consumer::FunctionalInterface
			@Override
			public void accept(V action) {
				if(canTransition(currentState, action)) {
					T newState = transition(currentState, action);
					
					SearchTreeNode<T, V> newNode = new SearchTreeNode<T, V>( Optional.of(nodeToCheck), 
							/* cost from root to nodeToCheck(parent node of the new node)  
							   + cost of action we executed to get the new node
							*/
							(nodeToCheck.getCost() + getActionCost(currentState, action)), 
							newState, Optional.of(action), nodeToCheck.getDepth()+1 ); 
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
	
	protected abstract long getActionCost(T state, V action);
}
