package searching.problems;

public abstract class SearchAction {
	private final int cost;
	
	public SearchAction(int cost) {
		this.cost = cost;
	}
	
	public int getCost() {
		return cost;
	}
	
	public static final class NoAction extends SearchAction{
		public NoAction() {
			super(0);
		}
	} //used for root nodes
	
	public static SearchAction NoAction() { return new NoAction(); }
}
