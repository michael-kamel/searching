package searching.problems.examples;

import java.util.LinkedList;

import searching.problems.SearchAction;

public enum GOTSearchAction implements SearchAction {
	MOVE_UP(10),
	MOVE_DOWN(10),
	MOVE_LEFT(10),
	MOVE_RIGHT(10),
	STAB(1);
	
	static {
		LinkedList<GOTSearchAction> allGOTActions = new LinkedList<GOTSearchAction>();
		allGOTActions.add(GOTSearchAction.MOVE_UP);
		allGOTActions.add(GOTSearchAction.MOVE_DOWN);
		allGOTActions.add(GOTSearchAction.MOVE_LEFT);
		allGOTActions.add(GOTSearchAction.MOVE_RIGHT);
		allGOTActions.add(GOTSearchAction.STAB);
		allActions = allGOTActions;
	}
	
	private static final Iterable<GOTSearchAction> allActions;
	private final int cost;
	
	private GOTSearchAction(int cost) {
		this.cost = cost;
	}
	
	@Override
	public int getCost() {
		return this.cost;
	}

	public static Iterable<GOTSearchAction> getAll() {
		return allActions;
	}
}
