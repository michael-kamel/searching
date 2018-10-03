package searching.exceptions;
/*
 * used for handling _problem-specific_ constraints
 * */
public class SearchProblemGameConstructionConstraintsViolation extends SearchProblemException {
	private static final long serialVersionUID = 1L;
	
	public SearchProblemGameConstructionConstraintsViolation(String message) {
		super(message);
	}
	
}
