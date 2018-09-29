package searching.problems;

@FunctionalInterface
public interface PathCost {
	Integer pathCost(Iterable<SearchAction> actions);
}
