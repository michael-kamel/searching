package searching.problems;

public interface SearchAction {
	public long getCost();
	
	public static final class NoAction implements SearchAction {
		@Override
		public long getCost() {
			return 0;
		}
	} //used for root nodes
	
	public static SearchAction NoAction() { return new NoAction(); }
}
