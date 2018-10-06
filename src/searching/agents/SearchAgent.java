package searching.agents;

import java.util.Optional;

import searching.problems.SearchAction;
import searching.problems.SearchProblem;
import searching.problems.SearchProblemSolution;
import searching.problems.SearchState;
import searching.strategies.SearchStrategy;

public class SearchAgent<T extends SearchState, V extends SearchAction> {
	//sets an upper-bound to the number of expanded nodes to avoid running forever
	private int maxTreeNodes; 
	
	public SearchAgent(int maxTreeNodes) {
		this.maxTreeNodes = maxTreeNodes;
	}
	
	public SearchProblemSolution<T, V> search(SearchProblem<T, V> problem, SearchStrategy<T, V> searchStrategy) {
		SearchTreeNode<T, V> rootNode = new SearchTreeNode<T, V>(Optional.empty(), 0, 
				problem.getInitialState(), Optional.empty(), 0);
		
		searchStrategy.addNode(rootNode);
		int count = 0; //number of _expanded_ nodes, so far
		
		while(count <= maxTreeNodes) {
			//gets the next node; dequeuing/popping (the data structure is strategy-dependant)
			Optional<SearchTreeNode<T, V>> nodeToCheck = searchStrategy.getNext();  
			
			if(!nodeToCheck.isPresent()) //emptied the data structure; no more nodes to expand 
				return SearchProblemSolution.NoSolution(problem, count); 
			
			if(problem.goalTest(nodeToCheck.get().getCurrentState()))
				return new SearchProblemSolution<T, V>(problem, Optional.of(nodeToCheck.get()), count);
			
			searchStrategy.addNodes(problem.expand(nodeToCheck.get()));//pushes/queues nodes
			count++; 
		}
		
		return SearchProblemSolution.Bottom(problem, count); //when resources are exhausted
	}
}
