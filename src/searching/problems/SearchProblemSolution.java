package searching.problems;

import java.util.Optional;

import searching.strategies.SearchTreeNode;

public class SearchProblemSolution {
	
	private final Optional<SearchTreeNode> node;
	private final SearchProblem problem;
	
	public SearchProblemSolution(SearchProblem problem, Optional<SearchTreeNode> node) {
		this.problem = problem;
		this.node = node;
	}
	
	public Optional<SearchTreeNode> getNode() {
		return node;
	}

	public static SearchProblemSolution Failure(SearchProblem problem) {
		return new FailedSearchProblemSolution(problem, Optional.empty());
	}
	
	public static final class FailedSearchProblemSolution extends SearchProblemSolution {
		public FailedSearchProblemSolution(SearchProblem problem, Optional<SearchTreeNode> node) {
			super(problem, node);
		}
	}
	
}
