package searching.problems.examples.westeros;

import java.util.ArrayList;
import java.util.Random;
import searching.exceptions.SearchProblemException;
import searching.exceptions.GameConstructionConstraintsViolation;
import searching.exceptions.UnknownGameObjectException;
import searching.problems.SearchProblem;
import searching.problems.examples.westeros.GOTSearchState.GOTSearchStateBuilder;
import searching.utils.Geomtry;
import searching.utils.Tuple;
import searching.visualizers.Visualizer;
import searching.utils.ObjectUtils;

public class SaveWesteros extends SearchProblem<GOTSearchState, GOTSearchAction> {
	private GOTGameObject[][] grid;
	private int gridRows;
	private int gridColumns;
	private ArrayList<Tuple<Integer,Integer>> whiteWalkerLocations;
	private ArrayList<Tuple<Integer,Integer>> obstacleLocations;
	private Tuple<Integer, Integer> dragonStoneLocation;
	private int whiteWalkersCount;
	private int maxDragonGlass;
	private int rowLowerBound;
	private int columnLowerBound;
	private int maxPathCost;
	private int obstacleCount;
	
	public SaveWesteros(int width, int height, int whiteWalkersCount, int obstacleCount, 
			int maxDragonGlass, Visualizer visualizer) 
			throws SearchProblemException {
		super(GOTSearchAction.getAll(), visualizer);
		this.gridRows = height;
		this.gridColumns = width;
		this.whiteWalkersCount = whiteWalkersCount;
		this.whiteWalkerLocations = new ArrayList<Tuple<Integer, Integer>>(whiteWalkersCount);
		this.obstacleLocations = new ArrayList<Tuple<Integer, Integer>>(obstacleCount);
		this.obstacleCount = obstacleCount;
		this.maxDragonGlass = maxDragonGlass;
		genGrid(width, height, whiteWalkersCount, obstacleCount);
		calculateLowerBounds();
		calculateLongestPathCost();
	}
	
	public SaveWesteros(int width, int height, ArrayList<Tuple<Integer,Integer>> whiteWalkerLocations,
			ArrayList<Tuple<Integer,Integer>> obstacleLocations, Tuple<Integer, Integer> dragonStoneLocation,
			int maxDragonStones, Visualizer visualizer) throws SearchProblemException {
		super(GOTSearchAction.getAll(), visualizer);
		this.gridRows = height;
		this.gridColumns = width;
		this.whiteWalkersCount = whiteWalkerLocations.size();
		this.whiteWalkerLocations = whiteWalkerLocations;
		this.obstacleLocations = obstacleLocations;
		this.maxDragonGlass = maxDragonStones;
		this.dragonStoneLocation = dragonStoneLocation;
		this.obstacleCount = obstacleLocations.size();
		genGrid(width, height, whiteWalkerLocations, obstacleLocations, dragonStoneLocation);
		calculateLowerBounds();
		calculateLongestPathCost();
	}
	
	public SaveWesteros(char[][] grid, int maxDragonGlass, Visualizer visualizer) throws SearchProblemException {
		super(GOTSearchAction.getAll(), visualizer);
		this.maxDragonGlass = maxDragonGlass;
		this.fromGrid(grid);
		calculateLowerBounds();
		calculateLongestPathCost();
	}

	public int getObstacleCount() {
		return this.obstacleCount;
	}
	
	private void fromGrid(char[][] grid) throws GameConstructionConstraintsViolation, UnknownGameObjectException {
		if(GOTGameObject.fromChar(grid[grid.length - 1][grid[grid.length - 1].length - 1]) != GOTGameObject.JON_SNOW)
			throw new GameConstructionConstraintsViolation("Jon Snow must be at the bottom right cell");
		
		this.gridRows = grid.length;
		this.gridColumns = grid[0].length;
		ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
		ArrayList<Tuple<Integer, Integer>> obstacles = new ArrayList<Tuple<Integer, Integer>>();
		Tuple<Integer, Integer> dragonStone = null;
		
		for(int i = 0; i < grid.length; i++) {
			if(grid[i].length != this.gridColumns)
				throw new GameConstructionConstraintsViolation("Grid must be of a rectangular shape");
			for(int j = 0; j < grid[i].length; j++) {
				switch(GOTGameObject.fromChar(grid[i][j])) {
					case WHITE_WALKER: whiteWalkers.add(new Tuple<Integer, Integer>(i, j)); break;
					case OBSTACLE: obstacles.add(new Tuple<Integer, Integer>(i, j)); break;
					case DEAD_WHITE_WALKER: 
						throw new GameConstructionConstraintsViolation("Initial grid can not have a dead white walker");
					case DRAGON_STONE:
						if(dragonStone == null)
							dragonStone = new Tuple<Integer, Integer>(i, j);
						else
							throw new GameConstructionConstraintsViolation("Grid can not have more than one dragon stone");
						break;
					case JON_SNOW:
						if(i != grid.length - 1 || j != grid[grid.length -1].length - 1)
							throw new GameConstructionConstraintsViolation("Grid must only have one Jon Snow");
					default:break;
				}
			}
		}
		if(dragonStone == null)
			throw new GameConstructionConstraintsViolation("Grid must have a dragon stone");
		
		this.whiteWalkersCount = whiteWalkers.size();
		this.obstacleCount = obstacles.size();
		this.whiteWalkerLocations = whiteWalkers;
		this.obstacleLocations = obstacles;
		this.dragonStoneLocation = dragonStone;
		this.genGrid(grid[0].length, grid.length, whiteWalkers, obstacles, dragonStone);
	}
	
	private void initGrid(int width, int height) throws GameConstructionConstraintsViolation {
		if(width < 4)
			throw new GameConstructionConstraintsViolation("Grid must have a width of 4 units or more");
		if(height < 4)
			throw new GameConstructionConstraintsViolation("Grid must have a height of 4 units or more");
		
		int gridCellCount = width*height;
		int requiredGridCellCount = 2 + whiteWalkersCount + obstacleCount;
		if(gridCellCount < requiredGridCellCount)
			throw new GameConstructionConstraintsViolation("Grid size can't accomodate given parameters for the problem");
		
		this.grid = new GOTGameObject[height][width];
		
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
				this.grid[i][j] = GOTGameObject.EMPTY;
		
		this.grid[height-1][width-1] = GOTGameObject.JON_SNOW;
	}
	
	private void genGrid(int width, int height, ArrayList<Tuple<Integer, Integer>> whiteWalkersLocations,
			ArrayList<Tuple<Integer, Integer>> obstacleLocations,
			Tuple<Integer, Integer> dragonStoneLocation) throws GameConstructionConstraintsViolation {
		initGrid(width, height);
		whiteWalkersLocations.forEach(whiteWalkerLocation -> {
			this.grid[whiteWalkerLocation.getLeft()][whiteWalkerLocation.getRight()] = GOTGameObject.WHITE_WALKER;
		});
		
		obstacleLocations.forEach(obstacleLocation -> {
			this.grid[obstacleLocation.getLeft()][obstacleLocation.getRight()] = GOTGameObject.OBSTACLE;
		});
		
		this.grid[dragonStoneLocation.getLeft()][dragonStoneLocation.getRight()] = GOTGameObject.DRAGON_STONE;	
	}

	private void genGrid(int width, int height, int whiteWalkersCount, int obstacleCount) 
			throws GameConstructionConstraintsViolation {
		initGrid(width, height);
		Random rnd = new Random();
		boolean dragonStonePlaced = false;
		while(!dragonStonePlaced) {
			int row = rnd.nextInt(height);
			int col = rnd.nextInt(width);
			if(grid[row][col] == GOTGameObject.EMPTY) {
				grid[row][col] = GOTGameObject.DRAGON_STONE;
				dragonStonePlaced = true;
				dragonStoneLocation = new Tuple<Integer, Integer>(row, col);
			}
		}
		
		while(whiteWalkersCount > 0) {
			int row = rnd.nextInt(height);
			int col = rnd.nextInt(width);
			
			if(grid[row][col] == GOTGameObject.EMPTY) {
				grid[row][col] = GOTGameObject.WHITE_WALKER;
				this.whiteWalkerLocations.add(new Tuple<Integer, Integer>(row,col)); 
				whiteWalkersCount--;
			}
		}
		
		while(obstacleCount > 0) {
			int row = rnd.nextInt(height);
			int col = rnd.nextInt(width);
			
			if(grid[row][col] == GOTGameObject.EMPTY) {
				grid[row][col] = GOTGameObject.OBSTACLE;
				this.obstacleLocations.add(new Tuple<Integer, Integer>(row, col));
				obstacleCount--;
			}
		}
	}
	
	/**
	 * redefines the grid's bounds from the left/top sides so as not to allow transitions from one state to another
	 * if the entire grid, with white walkers, obstacles, DragonStone, etc. is centered in a part(sub-grid)
	 * This reduces the number of expanded nodes, instead of expanding nodes with move actions that do not
	 * represent any importance (move to empty part of the grid) 
	 * we subtract 1 from this boundary, to handle specific cases 
	 */
	private void calculateLowerBounds() {
		int currentRowLowerBound = Integer.MAX_VALUE;
		int currentColumnLowerBound = Integer.MAX_VALUE;
		
		currentRowLowerBound = this.whiteWalkerLocations.stream().
				mapToInt(location -> location.getLeft()).min().orElse(Integer.MAX_VALUE);
		currentRowLowerBound = Math.min(currentRowLowerBound, this.obstacleLocations.stream().
				mapToInt(location -> location.getLeft()).min().orElse(Integer.MAX_VALUE));
		currentRowLowerBound = Math.min(currentRowLowerBound, this.dragonStoneLocation.getLeft());
		
		currentColumnLowerBound = this.whiteWalkerLocations.stream().
				mapToInt(location -> location.getRight()).min().orElse(Integer.MAX_VALUE);
		currentColumnLowerBound = Math.min(currentColumnLowerBound, this.obstacleLocations.stream().
				mapToInt(location -> location.getRight()).min().orElse(Integer.MAX_VALUE));
		currentColumnLowerBound = Math.min(currentColumnLowerBound, this.dragonStoneLocation.getRight());
		
		//maxes with zero to avoid negative values (negative values; outOfBoundException)
		this.rowLowerBound = Math.max(currentRowLowerBound - 1, 0); 		
		this.columnLowerBound = Math.max(currentColumnLowerBound - 1, 0);
	}
	
	private GOTGameObject getGridCellContent(int idx) {
		int normalizedColumnCount = this.gridColumns - this.columnLowerBound;
		int normalizedRow = idx / normalizedColumnCount;
		int normalizedColumn = idx % normalizedColumnCount;
		int row = normalizedRow + this.rowLowerBound;
		int column = normalizedColumn + this.columnLowerBound;
		return this.grid[row][column];
	}
	
	/**
	 * checks if this is a cell JON SNOW can step into (no obstacle - no alive WW)
	 * @param idx
	 * @return boolean
	 */
	private boolean isPositionCell(int idx) {
		GOTGameObject content = this.getGridCellContent(idx);
		if(content == GOTGameObject.OBSTACLE || content == GOTGameObject.WHITE_WALKER)
			return false;
		return true;
	}
	
	/**
	 * checks if the cell if it's a cell we can stab from (adjacent to an alive white walker) 
	 * or a dragon stone
	 * @param idx
	 * @return
	 */
	private boolean isTargetCell(int idx) {
		GOTGameObject content = getGridCellContent(idx);
		
		if(content == GOTGameObject.DRAGON_STONE)
			return true;
		
		if(content == GOTGameObject.OBSTACLE || content == GOTGameObject.WHITE_WALKER)
			return false;
		
		int boundedColumns = this.gridColumns - this.columnLowerBound;
		int boundedRows = this.gridRows - this.rowLowerBound;
		int maxCount = boundedColumns * boundedRows;
		
		return ((idx % boundedColumns != 1) && (idx > 0) 
					&& getGridCellContent(idx - 1) == GOTGameObject.WHITE_WALKER)
			|| ((idx % boundedColumns != boundedColumns - 1) && (idx + 1 < maxCount) 
					&& getGridCellContent(idx + 1) == GOTGameObject.WHITE_WALKER)
			|| ((idx + boundedColumns < maxCount) 
					&& getGridCellContent(idx + boundedColumns) == GOTGameObject.WHITE_WALKER)
			|| ((idx - boundedColumns >= 0) 
					&& getGridCellContent(idx - boundedColumns) == GOTGameObject.WHITE_WALKER);
	}
	
	/**
	 * checks if two cells are adjacent, given their indices (not locations)
	 * @param firstIdx
	 * @param secondIdx
	 * @return
	 */
	private boolean isAdjacentCellIdx(int firstIdx, int secondIdx) {
		int diff = Math.abs(firstIdx - secondIdx);
		if(diff == 1) {
			int columnCount = this.gridColumns - this.columnLowerBound;
			int firstColumn = firstIdx / columnCount;
			int secondColumn = secondIdx / columnCount;
			return firstColumn == secondColumn;
		}
		if(diff == this.gridColumns - this.columnLowerBound)
			return true;
		return false;
	}
	
	/**
	 * Floyd Warshall: calculates the distance between any two nodes in the graph
	 * 
	 */
	private void calculateLongestPathCost() {
		int maxCost = 0;
		
		int boundedRowSize = this.gridRows - this.rowLowerBound;
		int boundedColumnSize = this.gridColumns - this.columnLowerBound;
		int nodesCount = boundedRowSize * boundedColumnSize;
		int[][] cost = new int[nodesCount][nodesCount];
		/*
		 *  Floyd's Initialization Step
		 * */
		for(int i = 0; i < nodesCount; i++)
			for(int j = 0; j < nodesCount; j++)
				if(i == j)
					cost[i][j] = 0;
				else
					if(this.isPositionCell(i) && this.isPositionCell(j) && this.isAdjacentCellIdx(i, j))
						cost[i][j] = 1;
					else
						cost[i][j] = Integer.MAX_VALUE; //initializes nodes paths costs to infinity

		/*
		 *  All-pairs shortest path Algorithm
		 * */
		for(int nodeIdx = 0; nodeIdx < nodesCount; nodeIdx++)
			for(int nodeI = 0; nodeI < nodesCount; nodeI++)
				for(int nodeJ = 0; nodeJ < nodesCount; nodeJ++) {
					int newCost = cost[nodeI][nodeIdx] + cost[nodeIdx][nodeJ];
					if(newCost < 0)
						newCost = Integer.MAX_VALUE;
					cost[nodeI][nodeJ] = Math.min(cost[nodeI][nodeJ], newCost);
				}	
		
		
		/**
		 * calculates the maximum path cost between any two reachable-from-each-other target cells
		 * where target cell = {DragonStone, Cell JS can stab from}
		 */
		for(int i = 0; i < nodesCount; i++)
			for(int j = 0; j < nodesCount; j++)
				if(i != j && (isTargetCell(i) && isTargetCell(j)))
					if(cost[i][j] != Integer.MAX_VALUE)
						if(cost[i][j] > maxCost)
							//two target cells are not reachable from each other; infinite cost
							maxCost = Math.max(maxCost, cost[i][j]);
		
		this.maxPathCost = maxCost;
	}
	
	public long getMaxPathCost() {
		return this.maxPathCost;
	}
	
	@Override
	protected long getActionCost(GOTSearchState state, GOTSearchAction action) {
		int deadWhiteWalkers = (int) state.getWhiteWalkerStatus().stream()
				.filter(status -> status.getRight()).count();
		int aliveWhiteWalkers = this.whiteWalkersCount - deadWhiteWalkers;
		
		switch (action) {
			case MOVE_DOWN:
			case MOVE_LEFT:
			case MOVE_RIGHT:
			case MOVE_UP: {
				if(state.getDragonGlassCarried() == 0 || state.getPickedUpDragonGlass())
					return 0;
				return 1;
			}
			case STAB: return Math.min((this.gridColumns * this.gridRows) - this.obstacleCount - aliveWhiteWalkers,
					this.maxPathCost * deadWhiteWalkers);
			default: return 0;
		}
	}
	
	@Override
	public GOTSearchState getInitialState() {
		ArrayList<Tuple<Tuple<Integer, Integer>, Boolean>> whiteWalkersStatus = 
				new ArrayList<Tuple<Tuple<Integer, Integer>, Boolean>>(this.whiteWalkersCount);
		
		for(Tuple<Integer, Integer> point : this.whiteWalkerLocations)
			whiteWalkersStatus.add(new Tuple<Tuple<Integer, Integer>, Boolean>(point, false));
		
		Boolean[][] currentlyExplored = new Boolean[this.gridRows][this.gridColumns];
		currentlyExplored[gridRows-1][gridColumns-1] = true;
		
		return new GOTSearchState(0, new Tuple<Integer, Integer>(gridRows-1, gridColumns-1), 
				whiteWalkersStatus, currentlyExplored, false);
	}

	@Override
	public GOTSearchState transition(GOTSearchState state, GOTSearchAction action) {
		Tuple<Integer, Integer> location = state.getLocation();
		int newRow = location.getLeft();
		int newColumn = location.getRight();
		GOTSearchStateBuilder builder = new GOTSearchStateBuilder();
		
		builder.setWhiteWalkerStatus(state.getWhiteWalkerStatus())
			.setDragonGlassCarried(state.getDragonGlassCarried());
		
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
				ArrayList<Tuple<Tuple<Integer, Integer>, Boolean>> newWhiteWalkerState = 
						new ArrayList<Tuple<Tuple<Integer, Integer>, Boolean>>();
				
				for(Tuple<Tuple<Integer, Integer>, Boolean> whiteWalkerState : state.getWhiteWalkerStatus()) {
					if(!whiteWalkerState.getRight())
						if(Geomtry.isAdjacent(whiteWalkerState.getLeft(), location)) {
							newWhiteWalkerState.add(new Tuple<Tuple<Integer, Integer>, Boolean>(
									whiteWalkerState.getLeft(), true));
							continue;
						}
					
					newWhiteWalkerState.add(whiteWalkerState);
				}
				
				builder.setWhiteWalkerStatus(newWhiteWalkerState);
				builder.setDragonGlassCarried(state.getDragonGlassCarried() - 1);
			} break;
		}
		
		Tuple<Integer, Integer> newLocation = new Tuple<Integer, Integer>(newRow, newColumn);
		builder.setLocation(newLocation);
		
		Boolean[][] newCurrentlyExplored;
		if(!action.equals(GOTSearchAction.STAB))
			if(newLocation.equals(this.dragonStoneLocation) && state.getDragonGlassCarried() == 0)
				newCurrentlyExplored = new Boolean[this.gridRows][this.gridColumns];
			else
				newCurrentlyExplored = ObjectUtils.clone2DArray(state.getCurrentlyExplored());
		 else
			newCurrentlyExplored = new Boolean[this.gridRows][this.gridColumns];
		
		builder.setPickedUpDragonGlass(state.getPickedUpDragonGlass());
		
		if(action == GOTSearchAction.STAB)
			builder.setPickedUpDragonGlass(false);
		 
		if(action != GOTSearchAction.STAB || !newLocation.equals(this.dragonStoneLocation) 
				|| state.getDragonGlassCarried() != this.maxDragonGlass)
			newCurrentlyExplored[newRow][newColumn] = true;
		
		builder.setCurrentlyExplored(newCurrentlyExplored);
		
		if(newLocation.equals(this.dragonStoneLocation) && action != GOTSearchAction.STAB) {
			builder.setDragonGlassCarried(this.maxDragonGlass);
			if(state.getDragonGlassCarried() == 0)
				builder.setPickedUpDragonGlass(true);
		}
			
		return builder.build();
	}
	
	@Override
	public boolean canTransition(GOTSearchState state, GOTSearchAction action) {
		Tuple<Integer, Integer> location = state.getLocation();
		int newRow = location.getLeft();
		int newColumn = location.getRight();
		
		switch(action) {
			case STAB: {
				if(state.getDragonGlassCarried() == 0)
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
		
		Boolean currentlyExploredStatus = state.getCurrentlyExplored()[newRow][newColumn];
		if(currentlyExploredStatus != null && currentlyExploredStatus)
			return false;

		for(Tuple<Tuple<Integer, Integer>, Boolean> whiteWalkerState : state.getWhiteWalkerStatus())
			if(!whiteWalkerState.getRight())
				if(newLocation.equals(whiteWalkerState.getLeft()))
					return false;
		
		if(grid[newLocation.getLeft()][newLocation.getRight()].equals(GOTGameObject.OBSTACLE))
			return false;
		
		return true;
	}

	private boolean isValidLocation(Tuple<Integer, Integer> location) {
		//lower bounds; reduction by not allowing transition to these locations
		if(location.getLeft() >= this.gridRows || location.getLeft() < this.rowLowerBound)
			return false;
		if(location.getRight() >= this.gridColumns || location.getRight() < this.columnLowerBound)
			return false;
		
		return true;
	}
	
	@Override
	public boolean goalTest(GOTSearchState state) {
		for(Tuple<Tuple<Integer, Integer>, Boolean> whiteWalkerState : state.getWhiteWalkerStatus())
			if(!whiteWalkerState.getRight()) //if any WW is alive, return false
				return false;
				
		return true;
	}
	
	/*
	 * visualizes the problem before searching begins
	 * initial grid generation
	 * */
	public void visualize() {
		for(int i = 0; i < this.gridRows; i++) {
			for(int j = 0; j < this.gridColumns; j++) 
				this.getVisualizer().visualize(this.grid[i][j].toChar() + ", ");
			
			this.getVisualizer().visualizeLine("");
		}		
	}
	
	public void visualize(GOTSearchState state) {
		Character[][] stateGrid = new Character[this.gridRows][this.gridColumns];
		
		state.getWhiteWalkerStatus().forEach(status -> {
			if(status.getRight())
				stateGrid[status.getLeft().getLeft()][status.getLeft().getRight()] = 
					GOTGameObject.DEAD_WHITE_WALKER.toChar();
			else
				stateGrid[status.getLeft().getLeft()][status.getLeft().getRight()] = 
					GOTGameObject.WHITE_WALKER.toChar();
		});
		
		stateGrid[this.dragonStoneLocation.getLeft()][this.dragonStoneLocation.getRight()] = 
				GOTGameObject.DRAGON_STONE.toChar();
		
		Tuple<Integer, Integer> jonSnowLocation = state.getLocation();
		stateGrid[jonSnowLocation.getLeft()][jonSnowLocation.getRight()] = GOTGameObject.JON_SNOW.toChar();
		
		this.obstacleLocations.forEach(location -> {
			stateGrid[location.getLeft()][location.getRight()] = GOTGameObject.OBSTACLE.toChar();
		});
		
		/*for(int i = 0; i < state.getCurrentlyExplored().length; i++)
			for(int j = 0; j < state.getCurrentlyExplored()[i].length; j++)
				if(state.getCurrentlyExplored()[i][j] != null && state.getCurrentlyExplored()[i][j])
					stateGrid[i][j] = 'T';*/
		
		for(int i = 0; i < gridRows; i++)
			for(int j = 0; j < gridColumns; j++)
				if(stateGrid[i][j] == null) 
					stateGrid[i][j] = GOTGameObject.EMPTY.toChar();
		
		for(int i = 0; i < gridRows; i++) {
			for(int j = 0; j < gridColumns; j++)
				this.getVisualizer().visualize(stateGrid[i][j] + ", ");
			this.getVisualizer().visualizeLine("");
		}
						
		System.out.println("Dragon Glass Count: " + state.getDragonGlassCarried());
		System.out.println("Picked Up Dragon Glass: " + state.getPickedUpDragonGlass());
		System.out.println();
	}
}
