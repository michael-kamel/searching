package searching.exceptions;

public abstract class SearchingException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public SearchingException(String message) {
		super(message);
	}
}
