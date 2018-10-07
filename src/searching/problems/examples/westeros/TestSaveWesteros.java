package searching.problems.examples.westeros;

import java.util.Random;

import searching.agents.SearchAgent;
import searching.problems.SearchProblemSolution;
import searching.strategies.AStarSearchStrategy;
import searching.strategies.BreadthFirstSearchStrategy;
import searching.strategies.DepthFirstSearchSearchStrategy;
import searching.strategies.GreedySearchStrategy;
import searching.strategies.IterativeDeepeningSearchStrategy;
import searching.strategies.SearchStrategy;
import searching.strategies.UniformCostSearchStrategy;
import searching.visualizers.ConsoleVisualizer;
import searching.visualizers.Visualizer;

public class TestSaveWesteros {
	
	public static void main(String[] args) {
		char[][] grid = 
			{
					{'O', 'O', 'W', 'W'},
					{'W', 'O', 'S', '.'},
					{'.', 'W', '.', '.'},
					{'W', 'O', '.', 'J'},
			};
		search(grid, "GR2", false);
	}
	
	public static void search(char[][] grid, String strategy, boolean visualize) {
		try {
			Random rnd = new Random();
			int dragonGlassCapacity = rnd.nextInt(10) + 1;
			Visualizer visualizer = new ConsoleVisualizer();
			SaveWesteros problem = new SaveWesteros(grid, dragonGlassCapacity, visualizer);
			SearchStrategy<GOTSearchState, GOTSearchAction> searchStrategy = getStrategy(problem, strategy);
			SearchAgent<GOTSearchState, GOTSearchAction> agent = new SearchAgent<GOTSearchState, GOTSearchAction>(1000000);
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
	
	public static SearchStrategy<GOTSearchState, GOTSearchAction> getStrategy(SaveWesteros problem, String strategy) throws Exception {
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
	
}