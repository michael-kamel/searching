package searching.problems.examples;

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
import searching.strategies.IterativeDeepeningSearchStrategy;
import searching.strategies.SearchStrategy;
import searching.strategies.UniformCostSearchStrategy;
import searching.utils.Geomtry;
import searching.utils.Tuple;

public class GOTSearchProblem extends SearchProblem<GOTSearchState, GOTSearchAction> {
	private GameObject[][] grid;
	private int gridRows;
	private int gridColumns;
	private ArrayList<Tuple<Integer,Integer>> whiteWalkerLocations;
	private ArrayList<Tuple<Integer,Integer>> obstacleLocations;
	private Tuple<Integer, Integer> dragonStoneLocation;
	private int whiteWalkersCount;
	private int maxDragonStones;
	
	public GOTSearchProblem(int width, int height, int whiteWalkersCount, int obstacleCount, int maxDragonStones) throws SearchProblemException {
		super(GOTSearchAction.getAll());
		this.gridRows = height;
		this.gridColumns = width;
		this.whiteWalkersCount = whiteWalkersCount;
		this.whiteWalkerLocations = new ArrayList<Tuple<Integer, Integer>>(whiteWalkersCount);
		this.obstacleLocations = new ArrayList<Tuple<Integer, Integer>>(obstacleCount);
		this.maxDragonStones = maxDragonStones;
		genGrid(width, height, whiteWalkersCount, obstacleCount);
	}
	
	public GOTSearchProblem(int width, int height, ArrayList<Tuple<Integer,Integer>> whiteWalkerLocations,
			ArrayList<Tuple<Integer,Integer>> obstacleLocations, Tuple<Integer, Integer> dragonStoneLocation,
			int maxDragonStones) throws SearchProblemException {
		super(GOTSearchAction.getAll());
		this.gridRows = height;
		this.gridColumns = width;
		this.whiteWalkersCount = whiteWalkerLocations.size();
		this.whiteWalkerLocations = whiteWalkerLocations;
		this.obstacleLocations = obstacleLocations;
		this.maxDragonStones = maxDragonStones;
		this.dragonStoneLocation = dragonStoneLocation;
		genGrid(width, height, whiteWalkerLocations, obstacleLocations, dragonStoneLocation);
	}
	
	private void initGrid(int width, int height) throws SearchProblemGameConstructionConstraintsViolation {
		if(width < 4)
			throw new SearchProblemGameConstructionConstraintsViolation("Grid must have a width of 4 units or more");
		if(height < 4)
			throw new SearchProblemGameConstructionConstraintsViolation("Grid must have a height of 4 units or more");
		
		grid = new GameObject[height][width];
		
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
				grid[i][j] = GameObject.EMPTY;
		
		grid[height-1][width-1] = GameObject.JON_SNOW;
	}
	
	private void genGrid(int width, int height, ArrayList<Tuple<Integer, Integer>> whiteWalkersLocations,
			ArrayList<Tuple<Integer, Integer>> obstacleLocations,
			Tuple<Integer, Integer> dragonStoneLocation) throws SearchProblemGameConstructionConstraintsViolation {
		
		initGrid(width, height);
		whiteWalkersLocations.forEach(whiteWalkerLocation -> {
			this.grid[whiteWalkerLocation.getLeft()][whiteWalkerLocation.getRight()] = GameObject.WHITE_WALKER;
		});
		
		obstacleLocations.forEach(obstacleLocation -> {
			this.grid[obstacleLocation.getLeft()][obstacleLocation.getRight()] = GameObject.OBSTACLE;
		});
		
		this.grid[dragonStoneLocation.getLeft()][dragonStoneLocation.getRight()] = GameObject.DRAGON_STONE;	
	}

	private void genGrid(int width, int height, int whiteWalkersCount, int obstacleCount) throws SearchProblemGameConstructionConstraintsViolation {
		
		initGrid(width, height);
		Random rnd = new Random();
		boolean dragonStonePlaced = false;
		while(!dragonStonePlaced) {
			int row = rnd.nextInt(height);
			int col = rnd.nextInt(width);
			if(grid[row][col] == GameObject.EMPTY) {
				grid[row][col] = GameObject.DRAGON_STONE;
				dragonStonePlaced = true;
				dragonStoneLocation = new Tuple<Integer, Integer>(row, col);
			}
		}
		
		while(whiteWalkersCount > 0) {
			int row = rnd.nextInt(height);
			int col = rnd.nextInt(width);
			
			if(grid[row][col] == GameObject.EMPTY) {
				grid[row][col] = GameObject.WHITE_WALKER;
				this.whiteWalkerLocations.add(new Tuple<Integer, Integer>(row,col)); 
				whiteWalkersCount--;
			}
		}
		
		while(obstacleCount > 0) {
			int row = rnd.nextInt(height);
			int col = rnd.nextInt(width);
			
			if(grid[row][col] == GameObject.EMPTY) {
				grid[row][col] = GameObject.OBSTACLE;
				this.obstacleLocations.add(new Tuple<Integer, Integer>(row, col));
				obstacleCount--;
			}
		}
	}
	
	@Override
	public GOTSearchState getInitialState() {
		ArrayList<Tuple<Tuple<Integer, Integer>, Boolean>> whiteWalkersStatus = new ArrayList<Tuple<Tuple<Integer, Integer>, Boolean>>(whiteWalkersCount);
		
		for(Tuple<Integer, Integer> point : this.whiteWalkerLocations)
			whiteWalkersStatus.add(new Tuple<Tuple<Integer, Integer>, Boolean>(point, false));
		
		return new GOTSearchState(0, new Tuple<Integer, Integer>(gridRows-1, gridColumns-1), whiteWalkersStatus);
	}

	@Override
	public GOTSearchState getNewState(GOTSearchState state, GOTSearchAction action) {
		Tuple<Integer, Integer> location = state.getLocation();
		int newRow = location.getLeft();
		int newColumn = location.getRight();
		GOTSearchStateBuilder builder = new GOTSearchStateBuilder();
		
		builder.setWhiteWalkerStatus(state.getWhiteWalkerStatus())
			.setDragonStoneCarried(state.getDragonStoneCarried());
		
		switch(action) {
			case MOVE_DOWN:
				newRow = location.getLeft()+1; break;
			case MOVE_UP:
				newRow = location.getLeft()-1; break;
			case MOVE_LEFT:
				newColumn = location.getRight()-1; break;
			case MOVE_RIGHT:
				newColumn = location.getRight()+1; break;
			case STAB: {
				ArrayList<Tuple<Tuple<Integer, Integer>, Boolean>> newWhiteWalkerState = new ArrayList<Tuple<Tuple<Integer, Integer>, Boolean>>();
				
				for(Tuple<Tuple<Integer, Integer>, Boolean> whiteWalkerState : state.getWhiteWalkerStatus()) {
					if(!whiteWalkerState.getRight())
						if(Geomtry.isAdjacent(whiteWalkerState.getLeft(), location)) {
							newWhiteWalkerState.add(new Tuple<Tuple<Integer, Integer>, Boolean>(whiteWalkerState.getLeft(), true));
							continue;
						}
					
					newWhiteWalkerState.add(whiteWalkerState);
				}
				
				builder.setWhiteWalkerStatus(newWhiteWalkerState);
				builder.setDragonStoneCarried(state.getDragonStoneCarried() - 1);
			} break;
		}
		
		Tuple<Integer, Integer> newLocation = new Tuple<Integer, Integer>(newRow, newColumn);
		builder.setLocation(newLocation);
		
		if(newLocation.equals(dragonStoneLocation) && action != GOTSearchAction.STAB)
			builder.setDragonStoneCarried(maxDragonStones);
			
		return builder.build();
	}
	
	@Override
	public boolean canTransition(GOTSearchState state, GOTSearchAction action) {
		Tuple<Integer, Integer> location = state.getLocation();
		int newRow = location.getLeft();
		int newColumn = location.getRight();
		
		switch(action) {
			case STAB: {
				if(state.getDragonStoneCarried() <= 0)
					return false;
				
				for(Tuple<Tuple<Integer, Integer>, Boolean> whiteWalkerState : state.getWhiteWalkerStatus())
					if(!whiteWalkerState.getRight())
						if(Geomtry.isAdjacent(whiteWalkerState.getLeft(), location))
							return true;
				
				return false;
			}
			case MOVE_DOWN:
				newRow += 1;
				break;
			case MOVE_UP:
				newRow -= 1;
				break;
			case MOVE_LEFT:
				newColumn -= 1;
				break;
			case MOVE_RIGHT:
				newColumn += 1;
				break;
		}
		
		Tuple<Integer, Integer> newLocation = new Tuple<Integer, Integer>(newRow, newColumn);
		if(!isValidLocation(newLocation))
			return false;

		for(Tuple<Tuple<Integer, Integer>, Boolean> whiteWalkerState : state.getWhiteWalkerStatus())
			if(!whiteWalkerState.getRight())
				if(newLocation.equals(whiteWalkerState.getLeft()))
					return false;
		
		if(grid[newLocation.getLeft()][newLocation.getRight()] == GameObject.OBSTACLE)
			return false;
		
		return true;
	}

	public boolean isValidLocation(Tuple<Integer, Integer> location) {
		if(location.getLeft() >= this.gridRows || location.getLeft() < 0)
			return false;
		if(location.getRight() >= this.gridColumns || location.getRight() < 0)
			return false;
		
		return true;
	}
	
	@Override
	public boolean goalTest(GOTSearchState state) {
		for(Tuple<Tuple<Integer, Integer>, Boolean> whiteWalkerState : state.getWhiteWalkerStatus())
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
	
	public static void main2(String[] args) {
		try {
			GOTSearchProblem problem = new GOTSearchProblem(4, 4, 3, 2, 2);
			problem.visualize();
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState>();
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, ucsSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.showNodeSequence();
				System.out.println(sol.getExpandedNodesCount());
				System.out.println(sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 1));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 3));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 1));
			
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(1, 0);
			GOTSearchProblem problem = new GOTSearchProblem(4, 4, whiteWalkers, obstacleLocations, dragonStone, 2);
			problem.visualize();
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState>();
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, bfsSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.showNodeSequence();
				System.out.println(sol.getExpandedNodesCount());
				System.out.println(sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
