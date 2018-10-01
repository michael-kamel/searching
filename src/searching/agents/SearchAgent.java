package searching.agents;

import java.util.Optional;

import searching.problems.SearchAction;
import searching.problems.SearchProblem;
import searching.problems.SearchProblemSolution;
import searching.problems.SearchState;
import searching.strategies.SearchStrategy;
import searching.strategies.SearchTreeNode;

public class SearchAgent<T extends SearchState, V extends SearchAction> {
	private int maxTreeNodes; //control to avoid running forever
	
	public SearchAgent(int maxTreeNodes) {
		this.maxTreeNodes = maxTreeNodes;
	}
	
	public SearchProblemSolution<T, V> search(SearchProblem<T, V> problem, SearchStrategy<T> searchStrategy) {
		SearchTreeNode<T> rootNode = new SearchTreeNode<T>(Optional.empty(), 0, problem.getInitialState(), SearchAction.NoAction(), 0);
		searchStrategy.addNode(rootNode);
		int count = 0;
		
		while(count <= maxTreeNodes) {
			Optional<SearchTreeNode<T>> nodeToCheck = searchStrategy.getNext();
			if(!nodeToCheck.isPresent())
				return SearchProblemSolution.NoSolution(problem, count+1);
			
			System.out.println(nodeToCheck.get().getCurrentState());
			
			if(problem.goalTest(nodeToCheck.get().getCurrentState()))
				return new SearchProblemSolution<T, V>(problem, Optional.of(nodeToCheck.get()), count+1);
			
			searchStrategy.addNodes(problem.expand(nodeToCheck.get()));
			
				
			count++;
		}
		
		return SearchProblemSolution.Bottom(problem, count);
	}
}
