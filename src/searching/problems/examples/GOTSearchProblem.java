package searching.problems.examples;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import searching.agents.SearchAgent;
import searching.exceptions.SearchProblemException;
import searching.exceptions.SearchProblemGameConstructionConstraintsViolation;
import searching.problems.SearchProblem;
import searching.problems.SearchProblemSolution;
import searching.problems.examples.GOTSearchState.GOTSearchStateBuilder;
import searching.strategies.BreadthFirstSearchStrategy;
import searching.strategies.DepthFirstSearchSearchStrategy;
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
	
	public GOTSearchProblem(int width, int height, int whiteWalkersCount, int obstacleCount, int maxDragonStones) throws SearchProblemException {
		super(GOTSearchAction.getAll());
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
			int row = rnd.nextInt(height);
			int col = rnd.nextInt(width);
			if(grid[row][col] == GameObject.EMPTY) {
				grid[row][col] = GameObject.DRAGON_STONE;
				dragonStonePlaced = true;
				dragonStoneLocation = new Point(row, col);
			}
		}
		
		int whiteWalkerIdx = 0;
		while(whiteWalkersCount > 0) {
			int row = rnd.nextInt(height);
			int col = rnd.nextInt(width);
			
			if(grid[row][col] == GameObject.EMPTY) {
				grid[row][col] = GameObject.WHITE_WALKER;
				this.whiteWalkerLocations[whiteWalkerIdx++] = new Point(row,col);
				whiteWalkersCount--;
			}
		}
		
		while(obstacleCount > 0) {
			int row = rnd.nextInt(height);
			int col = rnd.nextInt(width);
			
			if(grid[row][col] == GameObject.EMPTY) {
				grid[row][col] = GameObject.OBSTACLE;
				obstacleCount--;
			}
		}
	}

	@Override
	public GOTSearchState getNewState(SearchTreeNode<GOTSearchState> node, GOTSearchAction action) {
		GOTSearchState state = node.getCurrentState();
		int row = state.getRow();
		int column = state.getColumn();
		int newRow = row;
		int newColumn = column;
		Point location = new Point(row, column);
		GOTSearchStateBuilder builder = new GOTSearchStateBuilder();
		
		builder.setColumn(state.getColumn())
			.setRow(state.getRow())
			.setWhiteWalkerStatus(state.getWhiteWalkerStatus())
			.setDragonStoneCarried(state.getDragonStoneCarried());
		
		switch(action) {
			case MOVE_DOWN:
				newRow = row+1; break;
			case MOVE_UP:
				newRow = row-1; break;
			case MOVE_LEFT:
				newColumn = column-1; break;
			case MOVE_RIGHT:
				newColumn = column+1; break;
			case STAB: {
				ArrayList<Tuple<Point, Boolean>> newWhiteWalkerState = new ArrayList<Tuple<Point, Boolean>>();
				
				for(Tuple<Point, Boolean> whiteWalkerState : state.getWhiteWalkerStatus()) {
					if(!whiteWalkerState.getRight())
						if(whiteWalkerState.getLeft().distance(location) <= 1.0) {
							newWhiteWalkerState.add(new Tuple<Point, Boolean>(whiteWalkerState.getLeft(), true));
							continue;
						}
					
					newWhiteWalkerState.add(whiteWalkerState);
				}
				
				builder.setWhiteWalkerStatus(newWhiteWalkerState);
				builder.setDragonStoneCarried(state.getDragonStoneCarried() - 1);
				
			} break;
		}
		
		builder.setColumn(newColumn)
			.setRow(newRow);
		
		if(newRow == dragonStoneLocation.getX() && newColumn == dragonStoneLocation.getX())
			builder.setDragonStoneCarried(maxDragonStones);
			
		return builder.build();
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
		if(grid[(int) point.getX()][(int) point.getY()] == GameObject.OBSTACLE)
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
			GOTSearchProblem problem = new GOTSearchProblem(4, 4, 2, 1, 2);
			problem.visualize();
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(10000000);
			SearchStrategy<GOTSearchState> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState>();
			System.in.read();
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, ucsSearchStrategy);
			System.out.println(sol);
			
			System.in.read();
			sol.showNodeSequence();
		} catch (SearchProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
