package unittest.venuetest;

import static org.junit.Assert.*;

import org.junit.Test;

import venue.MyDateTime;
import venue.TimeSlot;

public class TimeSlotTest {

	/*
	 * Test constructors and getter methods
	 */
	@Test
	public void testOne()
	{
		MyDateTime start = new MyDateTime(2012, 3, 12, 10, 20);
		MyDateTime end = new MyDateTime(2012, 4, 5, 10, 59);
		TimeSlot testObj = new TimeSlot(start, end);
		
		assertEquals("12/03/2012/10/20", testObj.getStartDateTime().getDateTimeRepresentation());
		assertEquals("05/04/2012/10/59", testObj.getEndDateTime().getDateTimeRepresentation());
	}
	
	/*
	 * Test isOverlapping method
	 */
	@Test
	public void testTwo()
	{
		MyDateTime start = new MyDateTime("12/03/2012/10/20");
		MyDateTime end = new MyDateTime("05/04/2012/10/59");
		TimeSlot testObjOne = new TimeSlot(start, end);
		
		MyDateTime startTwo = new MyDateTime("12/03/2011/10/20");
		MyDateTime endTwo = new MyDateTime("05/04/2011/10/59");
		TimeSlot testObjTwo = new TimeSlot(startTwo, endTwo);
		
		assertEquals(false, testObjOne.isOverlapping(testObjTwo));
		assertEquals(false, testObjTwo.isOverlapping(testObjOne));
	}
	
	/*
	 * Test isOverlapping method
	 */
	@Test
	public void testThree()
	{
		MyDateTime start = new MyDateTime("12/03/2012/10/20");
		MyDateTime end = new MyDateTime("05/04/2012/10/59");
		TimeSlot testObjOne = new TimeSlot(start, end);
		
		MyDateTime startTwo = new MyDateTime("05/04/2012/10/59");
		MyDateTime endTwo = new MyDateTime("05/05/2012/10/59");
		TimeSlot testObjTwo = new TimeSlot(startTwo, endTwo);
		
		assertEquals(false, testObjOne.isOverlapping(testObjTwo));
		assertEquals(false, testObjTwo.isOverlapping(testObjOne));
	}
	
	/*
	 * Test isOverlapping method
	 */
	@Test
	public void testFour()
	{
		MyDateTime start = new MyDateTime("12/03/2012/10/20");
		MyDateTime end = new MyDateTime("05/04/2012/10/59");
		TimeSlot testObjOne = new TimeSlot(start, end);
		
		MyDateTime startTwo = new MyDateTime("25/04/2000/10/59");
		MyDateTime endTwo = new MyDateTime("12/03/2012/10/20");
		TimeSlot testObjTwo = new TimeSlot(startTwo, endTwo);
		
		assertEquals(false, testObjOne.isOverlapping(testObjTwo));
		assertEquals(false, testObjTwo.isOverlapping(testObjOne));
	}

	/*
	 * Test isOverlapping method
	 */
	@Test
	public void testFive()
	{
		MyDateTime start = new MyDateTime("12/03/2012/10/20");
		MyDateTime end = new MyDateTime("05/04/2012/10/59");
		TimeSlot testObjOne = new TimeSlot(start, end);
		
		MyDateTime startTwo = new MyDateTime("15/03/2012/10/59");
		MyDateTime endTwo = new MyDateTime("19/03/2012/10/20");
		TimeSlot testObjTwo = new TimeSlot(startTwo, endTwo);
		
		assertEquals(true, testObjOne.isOverlapping(testObjTwo));
		assertEquals(true, testObjTwo.isOverlapping(testObjOne));
	}
	
	/*
	 * Test isContained method
	 */
	@Test
	public void testSix()
	{
		MyDateTime start = new MyDateTime("12/03/2012/10/20");
		MyDateTime end = new MyDateTime("05/04/2012/10/59");
		TimeSlot testObjOne = new TimeSlot(start, end);
		
		MyDateTime startTwo = new MyDateTime("15/03/2012/10/59");
		MyDateTime endTwo = new MyDateTime("19/03/2012/10/20");
		TimeSlot testObjTwo = new TimeSlot(startTwo, endTwo);
		
		assertEquals(false, testObjOne.isContained(testObjTwo));
		assertEquals(true, testObjTwo.isContained(testObjOne));
	}
	
	/*
	 * Test isContained method
	 */
	@Test
	public void testSeven()
	{
		MyDateTime start = new MyDateTime("12/03/2012/10/20");
		MyDateTime end = new MyDateTime("05/04/2012/10/59");
		TimeSlot testObjOne = new TimeSlot(start, end);
		
		MyDateTime startTwo = new MyDateTime("15/03/2012/10/59");
		MyDateTime endTwo = new MyDateTime("19/03/2013/10/20");
		TimeSlot testObjTwo = new TimeSlot(startTwo, endTwo);
		
		assertEquals(false, testObjOne.isContained(testObjTwo));
		assertEquals(false, testObjTwo.isContained(testObjOne));
	}
	
	/*
	 * Test isContained method
	 */
	@Test
	public void testEight()
	{
		MyDateTime start = new MyDateTime("12/03/2012/10/20");
		MyDateTime end = new MyDateTime("05/04/2012/10/59");
		TimeSlot testObjOne = new TimeSlot(start, end);
		
		MyDateTime startTwo = new MyDateTime("12/03/2012/10/20");
		MyDateTime endTwo = new MyDateTime("05/04/2012/10/59");
		TimeSlot testObjTwo = new TimeSlot(startTwo, endTwo);
		
		assertEquals(true, testObjOne.isContained(testObjTwo));
		assertEquals(true, testObjTwo.isContained(testObjOne));
	}
	
	/*
	 * Test compareTo method
	 */
	@Test
	public void testNine()
	{
		MyDateTime start = new MyDateTime("12/03/2012/10/20");
		MyDateTime end = new MyDateTime("05/04/2012/10/59");
		TimeSlot testObjOne = new TimeSlot(start, end);
		
		MyDateTime startTwo = new MyDateTime("12/03/2012/10/20");
		MyDateTime endTwo = new MyDateTime("05/04/2012/10/59");
		TimeSlot testObjTwo = new TimeSlot(startTwo, endTwo);
		
		assertEquals(0, testObjOne.compareTo(testObjTwo));
	}
	
	/*
	 * Test compareTo method
	 */
	@Test
	public void testTen()
	{
		MyDateTime start = new MyDateTime("12/03/2012/10/20");
		MyDateTime end = new MyDateTime("05/04/2012/10/59");
		TimeSlot testObjOne = new TimeSlot(start, end);
		
		MyDateTime startTwo = new MyDateTime("12/03/2012/10/20");
		MyDateTime endTwo = new MyDateTime("05/04/2012/11/00");
		TimeSlot testObjTwo = new TimeSlot(startTwo, endTwo);
		
		assertEquals(-1, testObjOne.compareTo(testObjTwo));
		assertEquals(1, testObjTwo.compareTo(testObjOne));
	}
	
	/*
	 * Test compareTo method
	 */
	@Test
	public void testEleven()
	{
		MyDateTime start = new MyDateTime("10/03/2012/10/20");
		MyDateTime end = new MyDateTime("05/04/2012/10/59");
		TimeSlot testObjOne = new TimeSlot(start, end);
		
		MyDateTime startTwo = new MyDateTime("12/03/2012/10/20");
		MyDateTime endTwo = new MyDateTime("05/04/2020/11/00");
		TimeSlot testObjTwo = new TimeSlot(startTwo, endTwo);
		
		assertEquals(-1, testObjOne.compareTo(testObjTwo));
		assertEquals(1, testObjTwo.compareTo(testObjOne));
	}
	
	/*
	 * Test compareTo method
	 */
	@Test
	public void testTwelve()
	{
		MyDateTime start = new MyDateTime("10/03/2013/10/20");
		MyDateTime end = new MyDateTime("05/04/2013/10/59");
		TimeSlot testObjOne = new TimeSlot(start, end);
		
		MyDateTime startTwo = new MyDateTime("12/03/2012/10/20");
		MyDateTime endTwo = new MyDateTime("05/04/2020/11/00");
		TimeSlot testObjTwo = new TimeSlot(startTwo, endTwo);
		
		assertEquals(1, testObjOne.compareTo(testObjTwo));
		assertEquals(-1, testObjTwo.compareTo(testObjOne));
	}
	
	/*
	 * Test compareTo method
	 */
	@Test
	public void testThirteen()
	{
		MyDateTime start = new MyDateTime("10/03/2013/10/20");
		MyDateTime end = new MyDateTime("05/04/2013/10/59");
		TimeSlot testObjOne = new TimeSlot(start, end);
		
		MyDateTime startTwo = new MyDateTime("10/03/2013/10/20");
		MyDateTime endTwo = new MyDateTime("05/04/2020/11/00");
		TimeSlot testObjTwo = new TimeSlot(startTwo, endTwo);
		
		assertEquals(-1, testObjOne.compareTo(testObjTwo));
		assertEquals(1, testObjTwo.compareTo(testObjOne));
	}
	
	/*
	 * Test happenBefore method
	 */
	@Test
	public void testFourteen()
	{
		MyDateTime start = new MyDateTime("10/03/2013/10/20");
		MyDateTime end = new MyDateTime("05/04/2013/10/59");
		TimeSlot testObjOne = new TimeSlot(start, end);
		
		MyDateTime testObjTwo = new MyDateTime("10/03/2013/10/20");
		
		assertEquals(false, testObjOne.happenBefore(testObjTwo));
	}
	
	/*
	 * Test happenBefore method
	 */
	@Test
	public void testFifteen()
	{
		MyDateTime start = new MyDateTime("10/03/2013/10/20");
		MyDateTime end = new MyDateTime("05/04/2013/10/59");
		TimeSlot testObjOne = new TimeSlot(start, end);
		
		MyDateTime testObjTwo = new MyDateTime("9/03/2013/10/20");
		
		assertEquals(false, testObjOne.happenBefore(testObjTwo));
	}
	
	/*
	 * Test happenBefore method
	 */
	@Test
	public void testSixteen()
	{
		MyDateTime start = new MyDateTime("10/03/2013/10/20");
		MyDateTime end = new MyDateTime("05/04/2013/10/59");
		TimeSlot testObjOne = new TimeSlot(start, end);
		
		MyDateTime testObjTwo = new MyDateTime("12/03/2013/10/20");
		
		assertEquals(true, testObjOne.happenBefore(testObjTwo));
	}
}
