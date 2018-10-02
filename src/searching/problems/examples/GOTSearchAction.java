package searching.problems.examples;

import java.util.Arrays;
import searching.problems.SearchAction;

public enum GOTSearchAction implements SearchAction {
	MOVE_UP(2),
	MOVE_DOWN(2),
	MOVE_LEFT(2),
	MOVE_RIGHT(2),
	STAB(1);
	
	private final long cost;
	
	private GOTSearchAction(int cost) {
		this.cost = cost;
	}
	
	@Override
	public long getCost() {
		return this.cost;
	}

	public static Iterable<GOTSearchAction> getAll() {
		return Arrays.asList(GOTSearchAction.values());
	}
}
