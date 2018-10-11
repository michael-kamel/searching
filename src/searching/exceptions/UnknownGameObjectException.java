package searching.exceptions;

public class UnknownGameObjectException extends SearchProblemException {
	private static final long serialVersionUID = 1L;
	
	public UnknownGameObjectException(String message) {
		super(message);
	}
}
