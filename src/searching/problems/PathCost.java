package searching.problems;

@FunctionalInterface
public interface PathCost {
	int pathCost(Iterable<SearchAction> actions);
}
