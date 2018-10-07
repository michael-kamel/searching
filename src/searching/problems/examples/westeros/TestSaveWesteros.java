package searching.problems.examples.westeros;

import java.io.IOException;
import java.util.ArrayList;

import searching.agents.SearchAgent;
import searching.exceptions.SearchProblemException;
import searching.problems.SearchProblemSolution;
import searching.strategies.AStarSearchStrategy;
import searching.strategies.BreadthFirstSearchStrategy;
import searching.strategies.DepthFirstSearchSearchStrategy;
import searching.strategies.GreedySearchStrategy;
import searching.strategies.HeuristicFunction;
import searching.strategies.IterativeDeepeningSearchStrategy;
import searching.strategies.SearchStrategy;
import searching.strategies.UniformCostSearchStrategy;
import searching.utils.Tuple;
import searching.visualizers.ConsoleVisualizer;
import searching.visualizers.Visualizer;

public class TestSaveWesteros {
	public static void main(String[] args) {
		sampleProblem1();
//		try {
//			Visualizer visualizer = new ConsoleVisualizer();
//			SaveWesteros problem = genSaveWesteros(4, 4, 4, 4, 2, visualizer);
//			SearchStrategy<GOTSearchState, GOTSearchAction> strategy = 
//					new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
//			
//			solveSaveWesteros(problem, strategy, 1000000);
//		} catch (SearchProblemException | IOException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//		try {
//			Visualizer visualizer = new ConsoleVisualizer();
//			SaveWesteros problem = genSaveWesteros(4, 4, 4, 4, 2, visualizer);
//			SearchStrategy<GOTSearchState, GOTSearchAction> strategy =
//					new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(GOTSearchProblemHeuristics.stabHeuristic(problem));
//			
//			solveSaveWesteros(problem, strategy, 1000000);
//		} catch (SearchProblemException | IOException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
		
		//GOTSearchProblemTest();
		//GOTHardCodedSearchProblem1Test();
		//GOTHardCodedSearchProblem2Test();
		//GOTHardCodedSearchProblem3Test();
		//GOTHardCodedSearchProblem4Test();
		//GOTHardCodedSearchProblem5Test();
		//GOTHardCodedSearchProblem6Test();
		//GOTHardCodedSearchProblem7Test();
		//GOTHardCodedSearchProblem7_2Test();
		//GOTHardCodedSearchProblem8Test();
		//GOTHardCodedSearchProblem9Test();
		//GOTHardCodedSearchProblem10Test();
		//GOTHardCodedSearchProblem11Test();
		//GOTHardCodedSearchProblem12Test();
		//GOTHardCodedSearchProblem13Test();
		//GOTHardCodedSearchProblem14Test();
	}
	
	public static SaveWesteros genSaveWesteros(int rowCount, int columnCount, int whiteWalkerCount, int obstacleCount, 
			int dragonGlassCapacity, Visualizer visualizer) throws SearchProblemException {
		SaveWesteros problem = new SaveWesteros(columnCount, rowCount, whiteWalkerCount, obstacleCount, 
				dragonGlassCapacity, visualizer);
		return problem;
	}
	
	public static void solveSaveWesteros(SaveWesteros problem, SearchStrategy<GOTSearchState, GOTSearchAction> strategy,
			int maxTreeNodesToExpandBeforeFailing) throws IOException {
		problem.visualize();
		System.out.println("Press any key to solve");
		System.in.read();
		SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>
			(maxTreeNodesToExpandBeforeFailing);
		SearchProblemSolution<GOTSearchState, GOTSearchAction> solution = agent.search(problem, strategy);
		
		if(solution instanceof SearchProblemSolution.FailedSearchProblemSolution) {
			System.out.println("No Solution");
			System.out.println("Nodes expanded: " + solution.getExpandedNodesCount());
		} else if(solution instanceof SearchProblemSolution.BottomProblemSolution) {
			System.out.println("Resources Exhausted");
			System.out.println("Nodes expanded: " + solution.getExpandedNodesCount());
		} else {
			System.out.println("Nodes expanded: " + solution.getExpandedNodesCount());
			System.out.println("Cost: " + solution.getNode().get().getCost());
			System.out.println("Press any key to visualize solution...");
			System.in.read();
			solution.visualizeSolution();
		}
	}
	public static void sampleProblem1() {
		try {
			char[][] grid = 
			{
					{'O', 'O', 'W', 'W'},
					{'W', 'O', 'S', '.'},
					{'.', 'W', '.', '.'},
					{'W', 'O', '.', 'J'},
			};
			int dragonGlassCapacity = 4;
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(grid, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeuristic = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestHeuristic = GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> nstabHeuristic = GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			SearchStrategy<GOTSearchState, GOTSearchAction> astar = 
					new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(nearestHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucs = 
					new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			System.out.println("Max cost: " + problem.getMaxPathCost());
			problem.visualize();
			System.out.println("Press any key to solve");
			SearchProblemSolution<GOTSearchState, GOTSearchAction> solution = agent.search(problem, astar);
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
				System.out.println("Press any key to visualize solution...");
				System.in.read();
				solution.visualizeSolution();
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	public static void GOTSearchProblemTest() {
		try {
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(5, 5, 4, 6, 2, visualizer);
			HeuristicFunction<GOTSearchState> stabHeuristic = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(stabHeuristic);
			System.out.println("Max cost: " + problem.getMaxPathCost());
			problem.visualize();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, ucsSearchStrategy);
			System.in.read();
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void GOTHardCodedSearchProblem1Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 4));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 4));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 5));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 5));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 2));
			
			int dragonGlassCapacity = 4;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(3, 2);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(furthestWhiteWalkerAndStabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, astarSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void GOTHardCodedSearchProblem2Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(0, 4));
			//whiteWalkers.add(new Tuple<Integer, Integer>(0, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 0));
			//whiteWalkers.add(new Tuple<Integer, Integer>(2, 3));
			//whiteWalkers.add(new Tuple<Integer, Integer>(2, 4));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 0));

			
			int dragonGlassCapacity = 1;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(3, 5);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(furthestWhiteWalkerAndStabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, astarSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void GOTHardCodedSearchProblem3Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(0, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(4, 5));
			//whiteWalkers.add(new Tuple<Integer, Integer>(2, 4));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 1));
			//obstacleLocations.add(new Tuple<Integer, Integer>(3, 1));
			//obstacleLocations.add(new Tuple<Integer, Integer>(4, 4));
			//obstacleLocations.add(new Tuple<Integer, Integer>(5, 2));
			
			int dragonGlassCapacity = 4;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(3, 3);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(furthestWhiteWalkerAndStabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, astarSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void GOTHardCodedSearchProblem4Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(0, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(4, 5));
			whiteWalkers.add(new Tuple<Integer, Integer>(0, 5));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 2));
			//obstacleLocations.add(new Tuple<Integer, Integer>(1, 3));
			//obstacleLocations.add(new Tuple<Integer, Integer>(1, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 0));
			//obstacleLocations.add(new Tuple<Integer, Integer>(5, 2));
			
			int dragonGlassCapacity = 4;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(3, 3);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(furthestWhiteWalkerAndStabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, astarSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void GOTHardCodedSearchProblem5Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(0, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 1));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 2));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 3));
			
			int dragonGlassCapacity = 4;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(3, 2);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(4, 4, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(stabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, astarSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void GOTHardCodedSearchProblem6Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 1));
			whiteWalkers.add(new Tuple<Integer, Integer>(0, 4));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 4));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(4, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(4, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 0));
			//whiteWalkers.add(new Tuple<Integer, Integer>(2, 3));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 0));
			
			int dragonGlassCapacity = 1;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(4, 5);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(furthestWhiteWalkerAndStabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, astarSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void GOTHardCodedSearchProblem7Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 1));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 4));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 5));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 0));
			
			int dragonGlassCapacity = 1;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(4, 5);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(furthestWhiteWalkerAndStabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, astarSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void GOTHardCodedSearchProblem7_2Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 1));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 4));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 1));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 4));
			//obstacleLocations.add(new Tuple<Integer, Integer>(1, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 2));
			//obstacleLocations.add(new Tuple<Integer, Integer>(3, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 0));
			
			int dragonGlassCapacity = 3;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(0, 5);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(furthestWhiteWalkerAndStabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, astarSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void GOTHardCodedSearchProblem8Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(6, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(6, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(6, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(4, 2));
			
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 3));
			
			int dragonGlassCapacity = 4;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(5, 4);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(5, 7, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(furthestWhiteWalkerAndStabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, astarSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void GOTHardCodedSearchProblem9Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(7, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(7, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(7, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(4, 2));
			
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(6, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(6, 0));
			
			int dragonGlassCapacity = 1;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(6, 4);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(5, 8, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(10000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(furthestWhiteWalkerAndStabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, astarSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void GOTHardCodedSearchProblem10Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(4, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(6, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(8, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(8, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(8, 3));
			
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(6, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(6, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(6, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(7, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(7, 0));
			
			int dragonGlassCapacity = 2;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(7, 4);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(5, 9, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(10000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(furthestWhiteWalkerAndStabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, astarSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void GOTHardCodedSearchProblem11Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(0, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 5));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 5));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 2));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 4));
			
			int dragonGlassCapacity = 10;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(4, 5);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(furthestWhiteWalkerAndStabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, ucsSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void GOTHardCodedSearchProblem12Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(0, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 5));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 5));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 2));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 4));
			
			int dragonGlassCapacity = 10;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(0, 0);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(furthestWhiteWalkerAndStabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, ucsSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void GOTHardCodedSearchProblem13Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(0, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(0, 4));
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 1));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 1));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 5));
			whiteWalkers.add(new Tuple<Integer, Integer>(6, 4));
			whiteWalkers.add(new Tuple<Integer, Integer>(6, 1));
			whiteWalkers.add(new Tuple<Integer, Integer>(4, 0));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(6, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 4));
			
			int dragonGlassCapacity = 3;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(3, 5);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(6, 7, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(furthestWhiteWalkerAndStabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, ucsSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void GOTHardCodedSearchProblem14Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(0, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 1));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 1));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(4, 0));
			whiteWalkers.add(new Tuple<Integer, Integer>(0, 3));
				
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(6, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 18));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 18));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 18));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 18));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 18));
			for(int i = 4; i < 18; i++) {
				
				obstacleLocations.add(new Tuple<Integer, Integer>(1, i));
				obstacleLocations.add(new Tuple<Integer, Integer>(2, i));
				obstacleLocations.add(new Tuple<Integer, Integer>(3, i));
				obstacleLocations.add(new Tuple<Integer, Integer>(4, i));
				obstacleLocations.add(new Tuple<Integer, Integer>(5, i));
			}
				
			
			int dragonGlassCapacity = 100;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(0, 4);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(20, 7, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(10000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(furthestWhiteWalkerAndStabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, ucsSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void GOTHardCodedSearchProblem15Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 4));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 4));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 0));
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 5));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 2));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 5));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 1));
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(5, 2));
			
			int dragonGlassCapacity = 4;
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(3, 2);
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, visualizer);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState, GOTSearchAction> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState, GOTSearchAction>();
			SearchStrategy<GOTSearchState, GOTSearchAction> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState, GOTSearchAction>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState, GOTSearchAction> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState, GOTSearchAction>(furthestWhiteWalkerAndStabHeurisitc);
			problem.visualize();
			System.out.println("Max cost: " + problem.getMaxPathCost());
			System.in.read();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, astarSearchStrategy);
			if(sol instanceof SearchProblemSolution.FailedSearchProblemSolution) {
				System.out.println("No Solution");
			} else if(sol instanceof SearchProblemSolution.BottomProblemSolution) {
				System.out.println("Resources Exhausted");
			} else {
				System.out.println("Press enter...");
				System.in.read();
				sol.visualizeSolution();
				System.out.println("Nodes expanded: " + sol.getExpandedNodesCount());
				System.out.println("Cost: " + sol.getNode().get().getCost());
			}
		} catch (SearchProblemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}