package searching.problems.examples;

public enum GameObject{
	JON_SNOW('J'),
	WHITE_WALKER('W'),
	OBSTACLE('O'),
	DRAGON_STONE('D'),
	EMPTY('E'),
	DEAD_WHITE_WALKER('X');
	
	private final char character;
	
	private GameObject(char character) {
		this.character = character;
	}
	
	public char toChar() {
		return this.character;
	}
}
