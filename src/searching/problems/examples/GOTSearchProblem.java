package searching.problems.examples;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import searching.agents.SearchAgent;
import searching.exceptions.SearchProblemException;
import searching.exceptions.SearchProblemGameConstructionConstraintsViolation;
import searching.problems.SearchProblem;
import searching.problems.SearchProblemSolution;
import searching.strategies.SearchStrategy;
import searching.strategies.SearchTreeNode;
import searching.strategies.UniformCostSearchStrategy;
import searching.utils.Tuple;

public class GOTSearchProblem extends SearchProblem<GOTSearchState, GOTSearchAction> {
	private GameObject[][] grid;
	private int gridRows;
	private int gridColumns;
	private Point[] whiteWalkerLocations;
	private Point dragonStoneLocation;
	private int whiteWalkersCount;
	private int maxDragonStones;
	
	public GOTSearchProblem(Iterable<GOTSearchAction> possibleActions, int width, int height, int whiteWalkersCount, int obstacleCount, int maxDragonStones) throws SearchProblemException {
		super(possibleActions);
		this.gridRows = height;
		this.gridColumns = width;
		this.whiteWalkersCount = whiteWalkersCount;
		this.whiteWalkerLocations = new Point[whiteWalkersCount];
		this.maxDragonStones = maxDragonStones;
		genGrid(width, height, whiteWalkersCount, obstacleCount);
	}
	
	@Override
	public GOTSearchState getInitialState() {
		ArrayList<Tuple<Point, Boolean>> whiteWalkersStatus = new ArrayList<Tuple<Point, Boolean>>(whiteWalkersCount);
		
		for(Point point : this.whiteWalkerLocations)
			whiteWalkersStatus.add(new Tuple<Point, Boolean>(point, false));
		
		return new GOTSearchState(0, gridRows-1, gridColumns-1, whiteWalkersStatus);
	}
	
	public void genGrid(int width, int height, int whiteWalkersCount, int obstacleCount) throws SearchProblemGameConstructionConstraintsViolation {
		if(width < 4)
			throw new SearchProblemGameConstructionConstraintsViolation("Grid must have a width of 4 units or more");
		if(height < 4)
			throw new SearchProblemGameConstructionConstraintsViolation("Grid must have a height of 4 unite or more");
		
		grid = new GameObject[height][width];
		
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
				grid[i][j] = GameObject.EMPTY;
		
		grid[height-1][width-1] = GameObject.JON_SNOW;
		
		Random rnd = new Random();
		boolean dragonStonePlaced = false;
		while(!dragonStonePlaced) {
			int x = rnd.nextInt(height);
			int y = rnd.nextInt(width);
			if(grid[x][y] == GameObject.EMPTY) {
				grid[x][y] = GameObject.DRAGON_STONE;
				dragonStonePlaced = true;
				dragonStoneLocation = new Point(x, y);
			}
		}
		
		int whiteWalkerIdx = 0;
		while(whiteWalkersCount > 0) {
			int x = rnd.nextInt(height);
			int y = rnd.nextInt(width);
			
			if(grid[x][y] == GameObject.EMPTY) {
				grid[x][y] = GameObject.WHITE_WALKER;
				this.whiteWalkerLocations[whiteWalkerIdx++] = new Point(x,y);
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
	public SearchTreeNode<GOTSearchState> makeNode(SearchTreeNode<GOTSearchState> node, GOTSearchAction action) {
		GOTSearchState state = node.getCurrentState();
		int row = state.getRow();
		int column = state.getColumn();
		Point location = new Point(row, column);
		GOTSearchState newState = null;
		
		switch(action) {
			case MOVE_DOWN:
				newState = new GOTSearchState(state.getDragonStoneCarried(), row+1, column, state.getWhiteWalkerStatus()); break;
			case MOVE_UP:
				newState = new GOTSearchState(state.getDragonStoneCarried(), row-1, column, state.getWhiteWalkerStatus()); break;
			case MOVE_LEFT:
				newState = new GOTSearchState(state.getDragonStoneCarried(), row, column-1, state.getWhiteWalkerStatus()); break;
			case MOVE_RIGHT:
				newState = new GOTSearchState(state.getDragonStoneCarried(), row, column+1, state.getWhiteWalkerStatus()); break;
			case STAB: {
				int deadCount = 0;
				for(Tuple<Point, Boolean> whiteWalkerState : state.getWhiteWalkerStatus())
					if(whiteWalkerState.getRight())
						deadCount++;
				
				System.out.println("STAB");
				System.out.println(deadCount);
				
				ArrayList<Tuple<Point, Boolean>> newWhiteWalkerState = new ArrayList<Tuple<Point, Boolean>>();
				
				for(Tuple<Point, Boolean> whiteWalkerState : state.getWhiteWalkerStatus()) {
					if(!whiteWalkerState.getRight())
						if(whiteWalkerState.getLeft().distance(location) <= 1.0) {
							newWhiteWalkerState.add(new Tuple<Point, Boolean>(whiteWalkerState.getLeft(), true));
							continue;
						}
					
					newWhiteWalkerState.add(whiteWalkerState);
				}
				
				newState = new GOTSearchState(state.getDragonStoneCarried(), row, column, newWhiteWalkerState);
				
			} break;
		}
			
		return new SearchTreeNode<GOTSearchState>(Optional.of(node), action.getCost(), newState, action, node.getDepth()+1);
	}
	
	@Override
	public boolean canTransition(GOTSearchState state, GOTSearchAction action) {
		int row = state.getRow();
		int column = state.getColumn();
		Point location = new Point(row, column);
		boolean canTransition = true;
		switch(action) {
			case STAB: {
				if(state.getDragonStoneCarried() <= 0)
					return false;
				
				for(Tuple<Point, Boolean> whiteWalkerState : state.getWhiteWalkerStatus())
					if(!whiteWalkerState.getRight())
						if(whiteWalkerState.getLeft().distance(location) <= 1.0)
							return true;
				
				return false;
			}
			case MOVE_DOWN:
				location.translate(1, 0);
				break;
			case MOVE_LEFT:
				location.translate(0, -1);
				break;
			case MOVE_RIGHT:
				location.translate(0, 1);
				break;
			case MOVE_UP:
				location.translate(-1, 0);
				break;
		}
		
		if(!isValidLocation(location))
			canTransition = false;
		
		for(Tuple<Point, Boolean> whiteWalkerState : state.getWhiteWalkerStatus())
			if(!whiteWalkerState.getRight())
				if(location.equals(whiteWalkerState.getLeft()))
					canTransition = false;
		
		return canTransition;
	}

	public boolean isValidLocation(Point point) {
		if(point.getX() >= this.gridRows || point.getX() < 0)
			return false;
		if(point.getY() >= this.gridColumns || point.getY() < 0)
			return false;
		
		return true;
	}
	
	@Override
	public boolean goalTest(GOTSearchState state) {
		for(Tuple<Point, Boolean> whiteWalkerState : state.getWhiteWalkerStatus())
			if(!whiteWalkerState.getRight())
				return false;
				
		return true;
	}
	
	public void visualize() {
		for(int i = 0; i < this.gridRows; i++) {
			for(int j = 0; j < this.gridColumns; j++) {
				switch (grid[i][j])
				{
				case DRAGON_STONE:
					System.out.print("D, "); break;
				case EMPTY:
					System.out.print("E, "); break;
				case JON_SNOW:
					System.out.print("J, "); break;
				case OBSTACLE:
					System.out.print("O, "); break;
				case WHITE_WALKER:
					System.out.print("W, "); break;
				default:
					break;
				
				}
			}
			System.out.println();
		}
		
				
	}
	
	public static void main(String[] args) {
		try {
			GOTSearchProblem problem = new GOTSearchProblem(GOTSearchAction.getAll(), 4, 4, 2, 1, 2);
			problem.visualize();
			SearchAgent agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState> strategy = new UniformCostSearchStrategy<GOTSearchState>();
			SearchProblemSolution sol = agent.search(problem, strategy);
			System.out.println(sol);
		} catch (SearchProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
