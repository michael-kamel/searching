package searching.agents;

import java.util.Optional;

import searching.problems.SearchAction;
import searching.problems.SearchProblem;
import searching.problems.SearchProblemSolution;
import searching.strategies.SearchStrategy;
import searching.strategies.SearchTreeNode;

public abstract class SearchAgent {
	private int maxTreeNodes; //control to avoid running forever
	
	public SearchAgent(int maxTreeNodes) {
		this.maxTreeNodes = maxTreeNodes;
	}
	
	public SearchProblemSolution search(SearchProblem problem, SearchStrategy searchStrategy) {
		
		SearchTreeNode rootNode = new SearchTreeNode(Optional.empty(), 0, problem.getInitialState(), SearchAction.NoAction(), 0);
		searchStrategy.addNode(rootNode);
		int count = 0;
		while(count <= maxTreeNodes) {
			Optional<SearchTreeNode> nodeToCheck = searchStrategy.getNext();
			
			if(!nodeToCheck.isPresent())
				return SearchProblemSolution.Failure(problem);
			
			
			if(problem.getGoalTest().isGoal(nodeToCheck.get().getCurrentState()))
				return new SearchProblemSolution(problem, Optional.of(nodeToCheck.get()));
			
			Iterable<SearchTreeNode> nodesToAdd = problem.expand(nodeToCheck.get());
			searchStrategy.addNodes(nodesToAdd);
			
			count++;
		}
		
		throw new UnsupportedOperationException();//change this
	}
}
