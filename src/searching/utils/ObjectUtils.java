package searching.utils;

public class ObjectUtils {
	public static Boolean[][] clone2DArray(Boolean[][] array) {
		Boolean[][] newArray = new Boolean[array.length][];
		
		for(int i = 0; i < array.length; i++)
			newArray[i] = cloneArray(array[i]);
		
		return newArray;
	}
	
	public static Boolean[] cloneArray(Boolean[] array) {
		Boolean[] newArray = new Boolean[array.length];
		
		for(int i = 0; i < array.length; i++)
			newArray[i] = array[i] == null ? false : array[i] ? true : false;
		
		return newArray;
	}
}
