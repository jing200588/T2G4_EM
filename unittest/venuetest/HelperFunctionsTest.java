package unittest.venuetest;

import static org.junit.Assert.*;

import org.junit.Test;

import venue.HelperFunctions;

public class HelperFunctionsTest {

	/*
	 * Test removeRedundantWhiteSpace and removeAllWhiteSpace method
	 */
	@Test
	public void testOne()
	{
		String testObj = "                                                ab                ";
		
		assertEquals("ab", HelperFunctions.removeRedundantWhiteSpace(testObj));
		assertEquals("ab", HelperFunctions.removeAllWhiteSpace(testObj));
	}
	
	/*
	 * Test removeRedundantWhiteSpace and removeAllWhiteSpace method
	 */
	@Test
	public void testTwo()
	{
		String testObj = "                                                a       b                ";
		
		assertEquals("a b", HelperFunctions.removeRedundantWhiteSpace(testObj));
		assertEquals("ab", HelperFunctions.removeAllWhiteSpace(testObj));
	}
	
	/*
	 * Test removeRedundantWhiteSpace and removeAllWhiteSpace method
	 */
	@Test
	public void testThree()
	{
		String testObj = "       h   a    p    p         y    ?   ?                ";
		
		assertEquals("h a p p y ? ?", HelperFunctions.removeRedundantWhiteSpace(testObj));
		assertEquals("happy??", HelperFunctions.removeAllWhiteSpace(testObj));
	}
	
	/*
	 * Test removeRedundantWhiteSpace and removeAllWhiteSpace method
	 */
	@Test
	public void testFour()
	{
		String testObj = "    13  / 04  / 1991   ";
		
		assertEquals("13 / 04 / 1991", HelperFunctions.removeRedundantWhiteSpace(testObj));
		assertEquals("13/04/1991", HelperFunctions.removeAllWhiteSpace(testObj));
	}
	
	/*
	 * Test removeRedundantWhiteSpace and removeAllWhiteSpace method
	 */
	@Test
	public void testFive()
	{
		String testObj = "This is a test case";
		
		assertEquals("This is a test case", HelperFunctions.removeRedundantWhiteSpace(testObj));
		assertEquals("Thisisatestcase", HelperFunctions.removeAllWhiteSpace(testObj));
	}
	
	/*
	 * Test removeRedundantWhiteSpace and removeAllWhiteSpace method
	 */
	@Test
	public void testSix()
	{
		String testObj = "maxCapacity";
		
		assertEquals("maxCapacity", HelperFunctions.removeRedundantWhiteSpace(testObj));
		assertEquals("maxCapacity", HelperFunctions.removeAllWhiteSpace(testObj));
	}
}
