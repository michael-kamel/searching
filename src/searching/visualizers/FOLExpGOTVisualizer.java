package searching.visualizers;

import java.io.FileWriter;
import java.io.IOException;

import searching.exceptions.VisualizationException;
import searching.problems.examples.westeros.GOTSearchState;
import searching.problems.examples.westeros.SaveWesteros;

public class FOLExpGOTVisualizer implements StateVisualizer<SaveWesteros, GOTSearchState> {

	private FileWriter fileWriter;
	
	public FOLExpGOTVisualizer(FileWriter fileWriter) {
		this.fileWriter = fileWriter;
	}
	
	@Override
	public void visualize(SaveWesteros problem, GOTSearchState state) throws VisualizationException {
		try {
			this.fileWriter.write("DummyText");
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
