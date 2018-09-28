package searching.agents;

import java.util.Optional;

import searching.problems.SearchProblem;
import searching.structs.SearchAction;
import searching.structs.SearchProblemSolution;
import searching.structs.SearchTree;
import searching.structs.SearchTreeNode;

public abstract class SearchAgent {
	private SearchTree stateTree;
	private int maxTreeNodes;
	
	public SearchAgent(int maxTreeNodes) {
		this.maxTreeNodes = maxTreeNodes;
	}
	
	public SearchProblemSolution search(SearchProblem problem, SearchStrategy searchStrategy) {
		
		SearchTreeNode rootNode = new SearchTreeNode(Optional.empty(), 0, problem.getInitialState(), SearchAction.NoAction());
		stateTree.append(rootNode);
		int count = 0;
		while(count <= maxTreeNodes) {
			Optional<SearchTreeNode> nodeToCheck = searchStrategy.getNext();
			
			if(!nodeToCheck.isPresent())
				return SearchProblemSolution.Failure();
			
			
			if(problem.getGoalTest().isGoal(nodeToCheck.get().getCurrentState()))
				return new SearchProblemSolution(Optional.of(nodeToCheck.get()));
			
			Iterable<SearchTreeNode> nodesToAdd = problem.expand(nodeToCheck.get());
			searchStrategy.addNodes(nodesToAdd);
			
			count++;
		}
		
		throw new UnsupportedOperationException();//change this
	}
}
