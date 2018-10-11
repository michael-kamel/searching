package searching.problems.examples.westeros;

import java.util.Arrays;
import searching.problems.SearchAction;

public enum GOTSearchAction implements SearchAction {
	MOVE_UP,
	MOVE_DOWN,
	MOVE_LEFT,
	MOVE_RIGHT,
	STAB;

	public static Iterable<GOTSearchAction> getAll() {
		return Arrays.asList(GOTSearchAction.values());
	}
}
