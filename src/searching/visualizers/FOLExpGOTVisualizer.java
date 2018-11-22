package searching.visualizers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import searching.exceptions.VisualizationException;
import searching.problems.examples.westeros.GOTSearchAction;
import searching.problems.examples.westeros.GOTSearchState;
import searching.problems.examples.westeros.SaveWesteros;
import searching.utils.Tuple;

public class FOLExpGOTVisualizer implements StateVisualizer<SaveWesteros, GOTSearchState> {

	private FileWriter fileWriter;
	private String stateName;
	
	public FOLExpGOTVisualizer(FileWriter fileWriter) {
		this(fileWriter, "s0");
	}
	
	public FOLExpGOTVisualizer(FileWriter fileWriter, String stateName) {
		this.fileWriter = fileWriter;
		this.stateName = "s0";
	}
	
	public FileWriter getFileWriter() {
		return fileWriter;
	}

	public String getStateName() {
		return stateName;
	}

	@Override
	public void visualize(SaveWesteros problem, GOTSearchState state) throws VisualizationException {
		try {
			ArrayList<String> locationOfPredicates = new ArrayList<String>();
			ArrayList<String> whiteWalkerPredicates = new ArrayList<String>();
			
			this.fileWriter.write(String.format("rowBound(%s).\n", problem.getGridRows() - 1));
			this.fileWriter.write(String.format("columnBound(%s).\n", problem.getGridColumns() - 1));
			
			int whiteWalkerIdx = 0;
			for(Tuple<Tuple<Integer, Integer>, Boolean> whiteWalkerState : state.getWhiteWalkerStatus()) {
				String whiteWalkerName = "whiteWalker_" + whiteWalkerIdx++;
				locationOfPredicates.add(String.format("locationOf(%s, (%s, %s)).\n", 
						whiteWalkerName, whiteWalkerState.getLeft().getLeft(), 
						whiteWalkerState.getLeft().getRight()));
				whiteWalkerPredicates.add(String.format("whiteWalker(%s).\n", whiteWalkerName));
			}
			
			for(int i = 0; i < problem.getGridRows(); i++)
				for(int j = 0; j < problem.getGridColumns(); j++)
					this.fileWriter.write(String.format("cell((%s, %s)).\n", i, j));
			
			int dragonStoneRow = problem.getDragonStoneLocation().getLeft();
			int dragonStoneColumn = problem.getDragonStoneLocation().getRight();
			
			this.fileWriter.write("dragonStone(dragonStone).\n");
			locationOfPredicates.add(String.format("locationOf(dragonStone, (%s, %s)).\n", dragonStoneRow, dragonStoneColumn));
			
			for(GOTSearchAction action : problem.getPossibleActions()) {
				this.fileWriter.write(String.format("action(%s).\n", action.toString().toLowerCase()));
			}
			
			int obstacleIdx = 0; 
			for(Tuple<Integer, Integer> obstacleLocation : problem.getObstacleLocations()) {
				String obstacleName = "obstacle_" + obstacleIdx++;
				this.fileWriter.write(String.format("obstacle(%s).\n", obstacleName));
				locationOfPredicates.add(String.format("locationOf(%s, (%s, %s)).\n", obstacleName,
						obstacleLocation.getLeft(), obstacleLocation.getRight()));
			}
			
			this.fileWriter.write(String.format("maxDragonGlass(%s).\n", problem.getMaxDragonGlass()));
			
			for(String locationOf : locationOfPredicates)
				this.fileWriter.write(locationOf);
			
			for(String whiteWalker : whiteWalkerPredicates)
				this.fileWriter.write(whiteWalker);
			
			this.fileWriter.write("allDead(S) :- \n");
			for(int i = 0; i < whiteWalkerPredicates.size(); i++) {
				this.fileWriter.write(String.format("\tdead(whiteWalker_%s, S)%s\n", i,
						i == whiteWalkerPredicates.size() - 1 ? "." : ","));
			}
			
		} catch (IOException e) {
			throw new VisualizationException(e);
		}
	}

	@Override
	public void visualizeLine(String str) throws VisualizationException  {
		throw new UnsupportedOperationException();
	}
	
	public void finalize() throws IOException {
		this.fileWriter.flush();
		this.fileWriter.close();
	}

}
