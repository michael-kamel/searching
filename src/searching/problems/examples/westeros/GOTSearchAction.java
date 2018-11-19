package searching.problems.examples.westeros;

import java.util.Arrays;
import searching.problems.SearchAction;

public enum GOTSearchAction implements SearchAction {
	STAB,
	MOVE_UP,
	MOVE_DOWN,
	MOVE_LEFT,
	MOVE_RIGHT;

	public static Iterable<GOTSearchAction> getAll() {
		return Arrays.asList(GOTSearchAction.values());
	}
}
