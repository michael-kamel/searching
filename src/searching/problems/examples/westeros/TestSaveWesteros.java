package searching.problems.examples.westeros;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import searching.agents.SearchAgent;
import searching.exceptions.GameConstructionConstraintsViolation;
import searching.exceptions.UnknownGameObjectException;
import searching.exceptions.VisualizationException;
import searching.problems.SearchProblemSolution;
import searching.strategies.AStarSearchStrategy;
import searching.strategies.BreadthFirstSearchStrategy;
import searching.strategies.DepthFirstSearchSearchStrategy;
import searching.strategies.GreedySearchStrategy;
import searching.strategies.IterativeDeepeningSearchStrategy;
import searching.strategies.SearchStrategy;
import searching.strategies.UniformCostSearchStrategy;
import searching.visualizers.ConsoleGOTVisualizer;
import searching.visualizers.FOLExpGOTVisualizer;
import searching.visualizers.StateVisualizer;

public class TestSaveWesteros {
	
	public static void main(String[] args) {
		try {
			//char[][] grid = genGrid(4,4);
			char[][] grid = 
				{
						{'W', 'W', '.'},
						{'D', '.', 'O'},
						{'O', '.', 'J'},
				};
			genFOLGrid(grid, "/home/mike/dev/prolog/savewesteros.pl", 1);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void search(char[][] grid, String strategy, boolean visualize) {
		Random rnd = new Random();
		search(grid, strategy, visualize, rnd.nextInt(10)+1);
	}
	
	public static void genFOLGrid(char[][] grid, String path, int dragonGlassCapacity) 
			throws IOException, VisualizationException, GameConstructionConstraintsViolation, UnknownGameObjectException {
		File KB = new File(path);
		KB.createNewFile();
		FileWriter fileWriter = new FileWriter(KB);
		
		FOLExpGOTVisualizer folVisualizer = new FOLExpGOTVisualizer(fileWriter);
		ConsoleGOTVisualizer consoleVisualizer = new ConsoleGOTVisualizer();
		SaveWesteros problem = new SaveWesteros(grid, dragonGlassCapacity, consoleVisualizer);
		problem.visualize();
		System.out.println("Press any key to write KB.");
		System.in.read();
		problem.setVisualizer(folVisualizer);
		problem.visualize();
		folVisualizer.finalize();
		System.out.println("KB written successfully");
	}
	
	public static void search(char[][] grid, String strategy, boolean visualize, int dragonGlassCapacity) {
		try {
			StateVisualizer<SaveWesteros, GOTSearchState> visualizer = new ConsoleGOTVisualizer();
			SaveWesteros problem = new SaveWesteros(grid, dragonGlassCapacity, visualizer);
			SearchStrategy<GOTSearchState, GOTSearchAction> searchStrategy = getStrategy(problem, strategy);
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, 
					GOTSearchAction>(1000000);
			System.out.println("Max cost: " + problem.getMaxPathCost());
			problem.visualize();
			System.out.println("Press enter to solve");
			SearchProblemSolution<GOTSearchState, GOTSearchAction> solution = agent.search(problem, searchStrategy);
			System.in.read();
			if(solution instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
				System.out.println("Nodes expanded: " + solution.getExpandedNodesCount());
			} else if(solution instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
				System.out.println("Nodes expanded: " + solution.getExpandedNodesCount());
			} else {
				System.out.println("Nodes expanded: " + solution.getExpandedNodesCount());
				System.out.println("Cost: " + solution.getNode().get().getCost());
				System.out.println("Press enter to visualize solution...");
				System.in.read();
				solution.visualizeSolution(!visualize, false); //!visualize == visualiza actionsOnly
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static SearchStrategy<GOTSearchState, GOTSearchAction> getStrategy(SaveWesteros problem, 
			String strategy) throws Exception {
		switch(strategy) {
			case "BF": return new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			case "DF": return new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			case "ID": return new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			case "UC": return new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			case "GR1": return new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem));
			case "GR2": return new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem));
			case "AS1": return new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(
					GOTSearchProblemHeuristics.stabHeuristic(problem));
			case "AS2": return new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem));
			default: throw new Exception("Unknown strategy string");
		}
	}
	
	public static char[][] genGrid(int rowsUpperBound, int columnsUpperBound) throws Exception {
		if(rowsUpperBound < 4 || columnsUpperBound < 4) 
			throw new Exception("Invalid grid size");
		
		Random rnd = new Random();
		int rows = rnd.nextInt(rowsUpperBound - 3) + 4;
		int columns = rnd.nextInt(columnsUpperBound - 3) + 4;
		
		char[][] grid = new char[rows][columns];
		
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++)
				grid[i][j] = '.';
		
		int availableNodes = rows * columns - 2;
		
		int whiteWalkersCount = rnd.nextInt(availableNodes / 2) + 1;
		availableNodes -= whiteWalkersCount;
		
		int obstacleCount = rnd.nextInt(availableNodes / 2) + 1;
		availableNodes -= obstacleCount;
		
		grid[rows - 1][columns - 1] = 'J';
		
		int rndRow = rnd.nextInt(rows);
		int rndColumn = rnd.nextInt(columns);
		boolean dragonStonePlaced = false;
		
		while(!dragonStonePlaced) {
			if(grid[rndRow][rndColumn] != '.') {
				rndRow = rnd.nextInt(rows);
				rndColumn = rnd.nextInt(columns);
				continue;
			}
			dragonStonePlaced = true;
			grid[rndRow][rndColumn] = 'S';
		}
		
		while(whiteWalkersCount > 0) {
			if(grid[rndRow][rndColumn] != '.') {
				rndRow = rnd.nextInt(rows);
				rndColumn = rnd.nextInt(columns);
				continue;
			}
			whiteWalkersCount--;
			grid[rndRow][rndColumn] = 'W';
		}
		
		while(obstacleCount > 0) {
			if(grid[rndRow][rndColumn] != '.') {
				rndRow = rnd.nextInt(rows);
				rndColumn = rnd.nextInt(columns);
				continue;
			}
			obstacleCount--;
			grid[rndRow][rndColumn] = 'O';
		}
		
		return grid;
				
	}
	
}