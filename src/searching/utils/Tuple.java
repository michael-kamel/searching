package searching.utils;

public class Tuple<L, R> {
	L left;
	R right;
	
	public Tuple(L left, R right) {
		this.left = left;
		this.right = right;
	}

	public L getLeft() {
		return this.left;
	}

	public R getRight() {
		return this.right;
	}
	
	public String toString() {
		return this.left + " " + this.right;
	}
}
