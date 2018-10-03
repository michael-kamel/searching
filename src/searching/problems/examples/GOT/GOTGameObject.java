package searching.problems.examples.GOT;

public enum GOTGameObject{
	JON_SNOW('J'),
	WHITE_WALKER('W'),
	OBSTACLE('O'),
	DRAGON_STONE('D'),
	EMPTY('.'),
	DEAD_WHITE_WALKER('X');
	
	private final char character;
	
	private GOTGameObject(char character) {
		this.character = character;
	}
	
	public char toChar() {
		return this.character;
	}
}
