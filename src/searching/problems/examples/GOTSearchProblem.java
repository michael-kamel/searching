package searching.problems.examples;

import java.util.Random;

import searching.exceptions.SearchProblemException;
import searching.exceptions.SearchProblemGameConstructionConstraintsViolation;
import searching.problems.SearchAction;
import searching.problems.SearchProblem;
import searching.strategies.SearchTreeNode;

public class GOTSearchProblem extends SearchProblem<GOTSearchState> {
	private GameObject[][] grid;
	
	public GOTSearchProblem(Iterable<SearchAction> possibleActions, GOTSearchState initialState
			, int width, int height, int whiteWalkersCount, int obstacleCount) throws SearchProblemException {
		super(possibleActions, initialState);
		genGrid(width, height, whiteWalkersCount, obstacleCount);
	}
	
	public void genGrid(int width, int height, int whiteWalkersCount, int obstacleCount) throws SearchProblemGameConstructionConstraintsViolation {
		if(width < 4)
			throw new SearchProblemGameConstructionConstraintsViolation("Grid must have a width of 4 units or more");
		if(height < 4)
			throw new SearchProblemGameConstructionConstraintsViolation("Grid must have a height of 4 unite or more");
		
		grid = new GameObject[height][width];
		
		grid[height][width] = GameObject.JON_SNOW;
		
		Random rnd = new Random();
		boolean dragonStonePlaced = false;
		while(!dragonStonePlaced) {
			int x = rnd.nextInt(height);
			int y = rnd.nextInt(width);
			
			if(grid[x][y] == GameObject.EMPTY) {
				grid[x][y] = GameObject.DRAGON_STONE;
				dragonStonePlaced = true;
			}
		}
		
		while(whiteWalkersCount > 0) {
			int x = rnd.nextInt(height);
			int y = rnd.nextInt(width);
			
			if(grid[x][y] == GameObject.EMPTY) {
				grid[x][y] = GameObject.WHITE_WALKER;
				whiteWalkersCount--;
			}
		}
		
		while(obstacleCount > 0) {
			int x = rnd.nextInt(height);
			int y = rnd.nextInt(width);
			
			if(grid[x][y] == GameObject.EMPTY) {
				grid[x][y] = GameObject.OBSTACLE;
				obstacleCount--;
			}
		}
	}

	@Override
	public SearchTreeNode<GOTSearchState> makeNode(SearchTreeNode<GOTSearchState> node, SearchAction action) {
		return null;
	}
	
	@Override
	public boolean canTransition(GOTSearchState state, SearchAction action) {
		return false;
	}

	@Override
	public boolean goalTest(GOTSearchState node) {
		boolean[] whiteWalkerStatus = node.getWhiteWalkerStatus();
		
		for(int i = 0; i < whiteWalkerStatus.length; i++)
			if(whiteWalkerStatus[i] == false)
				return false;
		
		return true;
	}
}
