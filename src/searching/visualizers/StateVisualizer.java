package searching.visualizers;

import searching.exceptions.VisualizationException;
import searching.problems.SearchAction;
import searching.problems.SearchProblem;
import searching.problems.SearchState;

public interface StateVisualizer<T extends SearchProblem<V, ? extends SearchAction, T>, V extends SearchState> {
	void visualize(T problem, V state) throws VisualizationException;
	void visualizeLine(String str) throws VisualizationException;
}
