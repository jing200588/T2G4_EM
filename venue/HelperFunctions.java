/**
 * This class contains miscellaneous helper functions for the whole system.
 */
package venue;

import java.util.Vector;

/**
 * @author Truong Duy - Team 31
 *
 */
public class HelperFunctions {
	
	/**
	 * Removes redundant white spaces (heading, trailing and duplicated) in the given input string
	 *  
	 * @param text - String
	 * @return resultStr - String
	 */
	public static String removeRedundantWhiteSpace(String text)
	{
		text = text.trim();
		
		Vector<Character> buffer = new Vector<Character>();
		
		boolean previousSpace = false;
		
		for(int index = 0; index < text.length(); index++)
		{
			if(Character.isWhitespace(text.charAt(index)) == false)
			{
				buffer.add(text.charAt(index));
				previousSpace = false;
			}
			else
			{
				if(previousSpace == false)
				{
					buffer.add(text.charAt(index));
					previousSpace = true;
				}
			}
		}
		
		// Convert the character vector into a character array
		char[] arr = new char[buffer.size()];
		for(int index = 0; index < arr.length; index++)
			arr[index] = buffer.get(index);
		
		return new String(arr);
	} 
	
	public static String removeAllWhiteSpace(String text)
	{
		Vector<Character> buffer = new Vector<Character>();
		
		for(int index = 0; index < text.length(); index++)
			if(Character.isWhitespace(text.charAt(index)) == false)
			{
				buffer.add(text.charAt(index));
			}
		
		// Convert the character vector into a character array
		char[] arr = new char[buffer.size()];
		for(int index = 0; index < arr.length; index++)
			arr[index] = buffer.get(index);
		
		return new String(arr);
	} 
}
