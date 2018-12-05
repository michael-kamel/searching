package searching.visualizers;

import searching.problems.examples.westeros.GOTGameObject;
import searching.problems.examples.westeros.GOTSearchState;
import searching.problems.examples.westeros.SaveWesteros;
import searching.utils.Tuple;

public class ConsoleGOTVisualizer implements StateVisualizer<SaveWesteros, GOTSearchState> {
	@Override
	public void visualize(SaveWesteros problem, GOTSearchState state) {
		int gridRows = problem.getGrid().length;
		int gridColumns = problem.getGrid()[0].length;
		Character[][] stateGrid = new Character[gridRows][gridColumns];
		
		state.getWhiteWalkerStatus().forEach(status -> {
			if(status.getRight())
				stateGrid[status.getLeft().getLeft()][status.getLeft().getRight()] = 
					GOTGameObject.DEAD_WHITE_WALKER.toChar();
			else
				stateGrid[status.getLeft().getLeft()][status.getLeft().getRight()] = 
					GOTGameObject.WHITE_WALKER.toChar();
		});
		
		stateGrid[problem.getDragonStoneLocation().getLeft()][problem.getDragonStoneLocation().getRight()] = 
				GOTGameObject.DRAGON_STONE.toChar();
		
		Tuple<Integer, Integer> jonSnowLocation = state.getLocation();
		stateGrid[jonSnowLocation.getLeft()][jonSnowLocation.getRight()] = GOTGameObject.JON_SNOW.toChar();
		
		problem.getObstacleLocations().forEach(location -> {
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
				System.out.print(stateGrid[i][j] + ", ");
			System.out.println();
		}
						
		System.out.println("Dragon Glass Count: " + state.getDragonGlassCarried());
		System.out.println("Picked Up Dragon Glass: " + state.getPickedUpDragonGlass());
		System.out.println();
	}
	
	public void visualizeLine(String str) {
		System.out.println(str);
	}
}
