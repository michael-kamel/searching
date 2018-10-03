package searching.problems;

public interface SearchAction {
	
	public static final class NoAction implements SearchAction {} //used for root nodes
	
	public static SearchAction NoAction() { return new NoAction(); }
}
