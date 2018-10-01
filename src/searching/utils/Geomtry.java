package searching.utils;

public class Geomtry {
	public static boolean isAdjacent(Tuple<Integer, Integer> firstPoint, Tuple<Integer, Integer> secondPoint) {
		return Math.abs(firstPoint.left - secondPoint.left) + Math.abs(firstPoint.right - secondPoint.right) == 1;
	}
}
