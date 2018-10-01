package searching.utils;

public class Tuple<L, R> {
	L left;
	R right;
	
	public Tuple(L left, R right) {
		this.left = left;
		this.right = right;
	}

	public L getLeft() {
		return left;
	}

	public R getRight() {
		return right;
	}
}
