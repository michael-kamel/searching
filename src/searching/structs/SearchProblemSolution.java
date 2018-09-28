package searching.structs;

import java.util.Optional;

public class SearchProblemSolution {
	
	private final Optional<SearchTreeNode> node;
	
	public SearchProblemSolution(Optional<SearchTreeNode> node) {
		this.node = node;
	}
	
	public Optional<SearchTreeNode> getNode() {
		return node;
	}

	public static SearchProblemSolution Failure() {
		return new FailedSearchProblemSolution(Optional.empty());
	}
	
	public static final class FailedSearchProblemSolution extends SearchProblemSolution {
		public FailedSearchProblemSolution(Optional<SearchTreeNode> node) {
			super(node);
		}
	}
	
}
