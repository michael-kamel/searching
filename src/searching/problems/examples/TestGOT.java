package searching.problems.examples;

import java.io.IOException;
import java.util.ArrayList;

import searching.agents.SearchAgent;
import searching.exceptions.SearchProblemException;
import searching.problems.SearchProblemSolution;
import searching.strategies.AStarSearchStrategy;
import searching.strategies.BreadthFirstSearchStrategy;
import searching.strategies.DepthFirstSearchSearchStrategy;
import searching.strategies.IterativeDeepeningSearchStrategy;
import searching.strategies.SearchStrategy;
import searching.strategies.UniformCostSearchStrategy;
import searching.utils.Tuple;

public class TestGOT {
	public static void main(String[] args) {
		ucsGOTSearchProblemTest();
		//ucsGOTHardCodedSearchProblemTest();
	}
	
	public static void ucsGOTSearchProblemTest() {
		try {
			GOTSearchProblem problem = new GOTSearchProblem(6, 6, 3, 2, 2);
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState>(problem.getHeuristicFunction());
			System.out.println("Max cost: " + problem.getMaxPathCost());
			problem.visualize();
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
	public static void ucsGOTHardCodedSearchProblemTest() {
		try {
			ArrayList<Tuple<Integer, Integer>> whiteWalkers = new ArrayList<Tuple<Integer, Integer>>();
			whiteWalkers.add(new Tuple<Integer, Integer>(0, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 2));
			whiteWalkers.add(new Tuple<Integer, Integer>(1, 1));
			//whiteWalkers.add(new Tuple<Integer, Integer>(2, 3));
			
			ArrayList<Tuple<Integer, Integer>> obstacleLocations = new ArrayList<Tuple<Integer, Integer>>();
			obstacleLocations.add(new Tuple<Integer, Integer>(0, 3));
			obstacleLocations.add(new Tuple<Integer, Integer>(2, 2));
			
			Tuple<Integer, Integer> dragonStone = new Tuple<Integer, Integer>(3, 2);
			GOTSearchProblem problem = new GOTSearchProblem(4, 4, whiteWalkers, obstacleLocations, dragonStone, 1);
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
			SearchStrategy<GOTSearchState> ucsSearchStrategy = new UniformCostSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> bfsSearchStrategy = new BreadthFirstSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> dfsSearchStrategy = new DepthFirstSearchSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> idsSearchStrategy = new IterativeDeepeningSearchStrategy<GOTSearchState>();
			SearchStrategy<GOTSearchState> astarSearchStrategy = new AStarSearchStrategy<GOTSearchState>(problem.getHeuristicFunction());
			problem.visualize();
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
