package searching.problems.examples.westeros;

import searching.exceptions.UnknownGameObjectException;

public enum GOTGameObject{
	JON_SNOW('J'),
	WHITE_WALKER('W'),
	OBSTACLE('O'),
	DRAGON_STONE('S'),
	EMPTY('.'),
	DEAD_WHITE_WALKER('X');
	
	private final char character;
	
	private GOTGameObject(char character) {
		this.character = character;
	}
	
	public char toChar() {
		return this.character;
	}
	
	public static GOTGameObject fromChar(char c) throws UnknownGameObjectException {
		switch(c) {
			case 'j':
			case 'J': return JON_SNOW;
			case 'e':
			case 'E':
			case '.': return EMPTY;
			case 'o':
			case 'O': return OBSTACLE;
			case 'w':
			case 'W': return WHITE_WALKER;
			case 'x':
			case 'X': return DEAD_WHITE_WALKER;
			case 's':
			case 'd':
			case 'S': 
			case 'D': return DRAGON_STONE;
		}
		throw new UnknownGameObjectException("Can not map char(" + c + ")to a game object");
	}
}
