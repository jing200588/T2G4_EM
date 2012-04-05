package unittest.venuetest;

import static org.junit.Assert.*;

import org.junit.Test;

import venue.MyDateTime;

public class MyDateTimeTest {
	
	/*
	 * Test constructors and getter methods
	 */
	@Test
	public void testOne()
	{
		MyDateTime testObj = new MyDateTime(2012, 3, 12, 10, 20);
		
		assertEquals(2012, testObj.getYear());
		assertEquals(3, testObj.getMonth());
		assertEquals(12, testObj.getDay());
		assertEquals(10, testObj.getHour());
		assertEquals(20, testObj.getMinute());
		assertEquals("12/03/2012", testObj.getDateRepresentation());
		assertEquals("10:20", testObj.getTimeRepresentation());
		assertEquals("12/03/2012/10/20", testObj.getDateTimeRepresentation());
	}
	
	/*
	 * Test constructors and getter methods
	 */
	@Test
	public void testTwo()
	{
		MyDateTime testObj = new MyDateTime(599, 3, 2, 10);
		
		assertEquals(599, testObj.getYear());
		assertEquals(3, testObj.getMonth());
		assertEquals(2, testObj.getDay());
		assertEquals(10, testObj.getHour());
		assertEquals(0, testObj.getMinute());
		assertEquals("02/03/0599", testObj.getDateRepresentation());
		assertEquals("10:00", testObj.getTimeRepresentation());
		assertEquals("02/03/0599/10/00", testObj.getDateTimeRepresentation());
	}
	
	/*
	 * Test constructors and getter methods
	 */
	@Test
	public void testThree()
	{
		MyDateTime anotherObj = new MyDateTime(99, 3, 31, 9);
		MyDateTime testObj = new MyDateTime(anotherObj);
		
		assertEquals(99, testObj.getYear());
		assertEquals(3, testObj.getMonth());
		assertEquals(31, testObj.getDay());
		assertEquals(9, testObj.getHour());
		assertEquals(0, testObj.getMinute());
		assertEquals("31/03/0099", testObj.getDateRepresentation());
		assertEquals("09:00", testObj.getTimeRepresentation());
		assertEquals("31/03/0099/09/00", testObj.getDateTimeRepresentation());
	}
	
	/*
	 * Test constructors and getter methods
	 */
	@Test
	public void testFour()
	{
		MyDateTime testObj = new MyDateTime("13/4/1/1/1");
		
		assertEquals(1, testObj.getYear());
		assertEquals(4, testObj.getMonth());
		assertEquals(13, testObj.getDay());
		assertEquals(1, testObj.getHour());
		assertEquals(1, testObj.getMinute());
		assertEquals("13/04/0001", testObj.getDateRepresentation());
		assertEquals("01:01", testObj.getTimeRepresentation());
		assertEquals("13/04/0001/01/01", testObj.getDateTimeRepresentation());
	}
	
	/*
	 * Test constructors and getter methods
	 */
	@Test
	public void testFive()
	{
		MyDateTime testObj = new MyDateTime("18/2/1990", "1:13");
		
		assertEquals(1990, testObj.getYear());
		assertEquals(2, testObj.getMonth());
		assertEquals(18, testObj.getDay());
		assertEquals(1, testObj.getHour());
		assertEquals(13, testObj.getMinute());
		assertEquals("18/02/1990", testObj.getDateRepresentation());
		assertEquals("01:13", testObj.getTimeRepresentation());
		assertEquals("18/02/1990/01/13", testObj.getDateTimeRepresentation());
	}
	
	/*
	 * Test compareTo method
	 */
	@Test
	public void testSix()
	{
		MyDateTime objOne = new MyDateTime("18/2/1990", "1:13");
		MyDateTime objTwo = new MyDateTime(objOne);
		
		int result = objOne.compareTo(objTwo);
		
		assertEquals(0, result);
	}
	
	/*
	 * Test compareTo method
	 */
	@Test
	public void testSeven()
	{
		MyDateTime objOne = new MyDateTime("18/2/1990", "1:13");
		MyDateTime objTwo = new MyDateTime("21/2/1990", "0:00");
		
		int result = objOne.compareTo(objTwo);
		
		assertEquals(-1, result);
	}
	
	/*
	 * Test compareTo method
	 */
	@Test
	public void testEight()
	{
		MyDateTime objOne = new MyDateTime("18/2/1991", "1:13");
		MyDateTime objTwo = new MyDateTime("21/2/1990", "0:00");
		
		int result = objOne.compareTo(objTwo);
		
		assertEquals(1, result);
	}
	
	/*
	 * Test isLeapYear method
	 */
	@Test
	public void testNine()
	{
		assertEquals(true, MyDateTime.isLeapYear(2012));
		assertEquals(false, MyDateTime.isLeapYear(2011));
		assertEquals(false, MyDateTime.isLeapYear(600));
		assertEquals(false, MyDateTime.isLeapYear(-1));
		assertEquals(true, MyDateTime.isLeapYear(1200));
	}
	
	/*
	 * Test isValidDateTime method
	 */
	@Test
	public void testTen()
	{
		assertEquals(true, MyDateTime.isValidDateTime(2012, 2, 29, 0, 0));
		assertEquals(false, MyDateTime.isValidDateTime(2011, 2, 29, 5, 31));
		assertEquals(false, MyDateTime.isValidDateTime(-1, 1, 1, 1, 1));
		assertEquals(false, MyDateTime.isValidDateTime(19, 13, 13, 13, 13));
		assertEquals(true, MyDateTime.isValidDateTime(1999, 12, 30, 23, 59));
		assertEquals(true, MyDateTime.isValidDateTime(1999, 1, 31, 0, 0));
		assertEquals(false, MyDateTime.isValidDateTime(1999, 12, 30, 23, 60));
		assertEquals(false, MyDateTime.isValidDateTime(1999, 12, 30, 24, 59));
		assertEquals(false, MyDateTime.isValidDateTime(1999, -12, 30, 24, 59));
		assertEquals(false, MyDateTime.isValidDateTime(1999, 12, -30, 24, 59));
		assertEquals(false, MyDateTime.isValidDateTime(1999, 12, 30, -24, 59));
		assertEquals(false, MyDateTime.isValidDateTime(1999, 12, 30, 24, -59));
	}
}
