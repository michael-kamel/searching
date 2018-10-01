package searching.problems.examples;

import java.util.LinkedList;

import searching.problems.SearchAction;

public enum GOTSearchAction implements SearchAction {
	MOVE_UP(10),
	MOVE_DOWN(10),
	MOVE_LEFT(10),
	MOVE_RIGHT(10),
	STAB(1);

	private final int cost;
	
	private GOTSearchAction(int cost) {
		this.cost = cost;
	}
	@Override
	public int getCost() {
		return this.cost;
	}

	public static Iterable<GOTSearchAction> getAll() {
		LinkedList<GOTSearchAction> allActions = new LinkedList<GOTSearchAction>();
		allActions.add(GOTSearchAction.MOVE_UP);
		allActions.add(GOTSearchAction.MOVE_DOWN);
		allActions.add(GOTSearchAction.MOVE_LEFT);
		allActions.add(GOTSearchAction.MOVE_RIGHT);
		allActions.add(GOTSearchAction.STAB);
		return allActions;
	}
}
