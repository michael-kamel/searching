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

public class TestGOT {
	public static void main(String[] args) {
		//ucsGOTSearchProblemTest();
		//ucsGOTHardCodedSearchProblem1Test();
		//ucsGOTHardCodedSearchProblem2Test();
		//ucsGOTHardCodedSearchProblem3Test();
		//ucsGOTHardCodedSearchProblem4Test();
		//ucsGOTHardCodedSearchProblem5Test();
		//ucsGOTHardCodedSearchProblem6Test();
		ucsGOTHardCodedSearchProblem7Test();
	}
	
	public static void ucsGOTSearchProblemTest() {
		try {
			SaveWesteros problem = new SaveWesteros(5, 5, 4, 5, 4, false);
			HeuristicFunction<GOTSearchState> stabHeuristic = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState>(stabHeuristic);
			System.out.println("Max cost: " + problem.getMaxPathCost());
			problem.visualize();
			SearchProblemSolution<GOTSearchState, GOTSearchAction> sol = agent.search(problem, astarSearchStrategy);
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
	
	public static void ucsGOTHardCodedSearchProblem1Test() {
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
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, true);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState>(furthestWhiteWalkerAndStabHeurisitc);
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
	
	public static void ucsGOTHardCodedSearchProblem2Test() {
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
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, true);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState>(furthestWhiteWalkerAndStabHeurisitc);
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
	
	public static void ucsGOTHardCodedSearchProblem3Test() {
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
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, true);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState>(furthestWhiteWalkerAndStabHeurisitc);
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

	public static void ucsGOTHardCodedSearchProblem4Test() {
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
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, true);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState>(furthestWhiteWalkerAndStabHeurisitc);
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
	
	public static void ucsGOTHardCodedSearchProblem5Test() {
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
			SaveWesteros problem = new SaveWesteros(4, 4, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, true);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState>(stabHeurisitc);
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
	
	public static void ucsGOTHardCodedSearchProblem6Test() {
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
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, true);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState>(furthestWhiteWalkerAndStabHeurisitc);
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
	

	public static void ucsGOTHardCodedSearchProblem7Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 1));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 5));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 4));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 3));
			//whiteWalkers.add(new Tuple<Integer, Integer>(0, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 3));
			//whiteWalkers.add(new Tuple<Integer, Integer>(4, 1));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 4));
			//obstacleLocations.add(new Tuple<Integer, Integer>(4, 1));
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
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, false);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState>(furthestWhiteWalkerAndStabHeurisitc);
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
	
	public static void ucsGOTHardCodedSearchProblem8Test() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 1));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 5));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 4));
			whiteWalkers.add(new Tuple<Integer, Integer>(5, 3));
			//whiteWalkers.add(new Tuple<Integer, Integer>(0, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(3, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(2, 3));
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 3));
			//whiteWalkers.add(new Tuple<Integer, Integer>(0, 4));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(4, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(3, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 4));
			obstacleLocations.add(new Tuple<Integer, Integer>(1, 4));
			//obstacleLocations.add(new Tuple<Integer, Integer>(4, 1));
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
			SaveWesteros problem = new SaveWesteros(6, 6, whiteWalkers, obstacleLocations, dragonStone, dragonGlassCapacity, true);
			HeuristicFunction<GOTSearchState> stabHeurisitc = GOTSearchProblemHeuristics.stabHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerHeuristic(problem);
			HeuristicFunction<GOTSearchState> furthestWhiteWalkerAndStabHeurisitc = 
					GOTSearchProblemHeuristics.furthestWhiteWalkerAndStabHeuristic(problem);
			HeuristicFunction<GOTSearchState> nearestWhiteWalkerHeuristic = 
					GOTSearchProblemHeuristics.nearestWhiteWalkerHeuristic(problem);
			
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> greedySearchStrategy = new GreedySearchStrategy<GOTSearchState>(nearestWhiteWalkerHeuristic);
			SearchStrategy<GOTSearchState> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState>(furthestWhiteWalkerAndStabHeurisitc);
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