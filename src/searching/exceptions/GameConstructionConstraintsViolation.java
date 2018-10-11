package searching.exceptions;
/*
 * used for handling _problem-specific_ constraints
 * */
public class GameConstructionConstraintsViolation extends SearchProblemException {
	private static final long serialVersionUID = 1L;
	
	public GameConstructionConstraintsViolation(String message) {
		super(message);
	}
	
}
