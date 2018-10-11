package searching.visualizers;

public class ConsoleVisualizer implements Visualizer {
	@Override
	public void visualizeLine(String text) {
		System.out.println(text);
	}

	@Override
	public void visualize(String text) {
		System.out.print(text);
	}
}
