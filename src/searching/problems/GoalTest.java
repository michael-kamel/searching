package searching.problems;

@FunctionalInterface
public interface GoalTest {
	boolean isGoal(SearchState state);
}
