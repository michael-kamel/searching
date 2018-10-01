package searching.problems;

public interface SearchAction {
	public int getCost();
	public static final class NoAction implements SearchAction {
		public NoAction() {
			super();
		}

		@Override
		public int getCost() {
			return 0;
		}
	} //used for root nodes
	
	public static SearchAction NoAction() { return new NoAction(); }
}
