package searching.agents;

import java.util.Optional;

import searching.problems.SearchAction;
import searching.problems.SearchProblem;
import searching.problems.SearchProblemSolution;
import searching.problems.SearchState;
import searching.strategies.SearchStrategy;
import searching.strategies.SearchTreeNode;

public abstract class SearchAgent<T extends SearchState> {
	private int maxTreeNodes; //control to avoid running forever
	
	public SearchAgent(int maxTreeNodes) {
		this.maxTreeNodes = maxTreeNodes;
	}
	
	public SearchProblemSolution<T> search(SearchProblem<T> problem, SearchStrategy<T> searchStrategy) {
		
		SearchTreeNode<T> rootNode = new SearchTreeNode<T>(Optional.empty(), 0, problem.getInitialState(), SearchAction.NoAction(), 0);
		searchStrategy.addNode(rootNode);
		int count = 0;
		
		while(count <= maxTreeNodes) {
			Optional<SearchTreeNode<T>> nodeToCheck = searchStrategy.getNext();
			
			if(!nodeToCheck.isPresent())
				return SearchProblemSolution.NoSolution(problem, count+1);
			
			if(problem.goalTest(nodeToCheck.get().getCurrentState()))
				return new SearchProblemSolution<T>(problem, Optional.of(nodeToCheck.get()), count+1);
			
			Iterable<SearchTreeNode<T>> nodesToAdd = problem.expand(nodeToCheck.get());
			searchStrategy.addNodes(nodesToAdd);
				
			count++;
		}
		
		return SearchProblemSolution.Bottom(problem, count);
	}
}
