package searching.structs;

@FunctionalInterface
public interface PathCost {
	Integer pathCost(Iterable<SearchAction> actions);
}
