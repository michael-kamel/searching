package searching.problems;

public interface SearchAction {
	public static final NoAction noAction = new NoAction(); 
	//used for root nodes for the .action attribute
	public static final class NoAction implements SearchAction {} 
	
	static SearchAction NoAction() { return noAction; }
}
