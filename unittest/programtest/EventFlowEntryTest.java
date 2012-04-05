package unittest.programtest;

import static org.junit.Assert.*;

import org.junit.Test;

import program.EventFlowEntry;
import venue.MyDateTime;
import venue.TimeSlot;

public class EventFlowEntryTest {
	
	/*
	 * Test constructors and getter methods
	 */
	@Test
	public void testOne() 
	{
		EventFlowEntry testObj = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 16, 10, 35)),
			 	 "Fundraising", "SoC", 15, "THis is for testing!");
		
		assertEquals("Fundraising", testObj.getActivityName());
		assertEquals("SoC", testObj.getVenueName());
		assertEquals(15, testObj.getVenueID());
		assertEquals("THis is for testing!", testObj.getUserNote());
		assertEquals("16/03/2012/07/21", testObj.getDuration().getStartDateTime().getDateTimeRepresentation());
		assertEquals("16/03/2012/10/35", testObj.getDuration().getEndDateTime().getDateTimeRepresentation());
	}
	
	/*
	 * Test methods that update information
	 */
	@Test
	public void testTwo() 
	{
		EventFlowEntry testObj = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 16, 10, 35)),
			 	 "Fundraising", "SoC", 15, "THis is for testing!");
		testObj.updateActivityName("IT Fair");
		testObj.updateVenue("Central Square NUS", 25);
		testObj.updateUserNote("Another testing");
		testObj.updateDuration(new TimeSlot(new MyDateTime("12/03/1991/17/35"), 
				new MyDateTime("14/03/1991/18/30")));
		
		assertEquals("IT Fair", testObj.getActivityName());
		assertEquals("Central Square NUS", testObj.getVenueName());
		assertEquals(25, testObj.getVenueID());
		assertEquals("Another testing", testObj.getUserNote());
		assertEquals("12/03/1991/17/35", testObj.getDuration().getStartDateTime().getDateTimeRepresentation());
		assertEquals("14/03/1991/18/30", testObj.getDuration().getEndDateTime().getDateTimeRepresentation());
	}
	
	/*
	 * Test getStringRepresentation method
	 */
	@Test
	public void testThree() 
	{
		EventFlowEntry testObj = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 16, 10, 35)),
			 	 "Fundraising", "SoC", 15, "THis is for testing!");
		
		String expected = "16/03/2012\n07:21\n16/03/2012\n10:35\nFundraising\nSoC\n15\nTHis is for testing!\n";
		
		assertEquals(expected, testObj.getStringRepresentation());
	}
	
	/*
	 * Test compareTo method
	 */
	@Test
	public void testFour() 
	{
		EventFlowEntry testObj1 = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 16, 10, 35)),
			 	 "Fundraising", "SoC", 15, "THis is for testing!");
		EventFlowEntry testObj2 = new EventFlowEntry(new TimeSlot(new MyDateTime(2011, 3, 15, 7, 21),
			 	 new MyDateTime(2011, 3, 16, 10, 35)),
			 	 "Fundraising", "SoC", 15, "THis is for testing!");
		
		assertEquals(1, testObj1.compareTo(testObj2));
		assertEquals(-1, testObj2.compareTo(testObj1));
	}
	
	/*
	 * Test compareTo method
	 */
	@Test
	public void testFive() 
	{
		EventFlowEntry testObj1 = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "SoC", 15, "THis is for testing!");
		EventFlowEntry testObj2 = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 55)),
			 	 "IT Fair", "SoC", 15, "THis is for testing!");
		
		assertEquals(-1, testObj1.compareTo(testObj2));
		assertEquals(1, testObj2.compareTo(testObj1));
	}
	
	/*
	 * Test compareTo method
	 */
	@Test
	public void testSix() 
	{
		EventFlowEntry testObj1 = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "SoC", 15, "THis is for testing!");
		EventFlowEntry testObj2 = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 55)),
			 	 "IT Fair", "SoC", 15, "THis is for testing!");
		
		assertEquals(-1, testObj1.compareTo(testObj2));
		assertEquals(1, testObj2.compareTo(testObj1));
	}
	
	/*
	 * Test compareTo method
	 */
	@Test
	public void testSeven() 
	{
		EventFlowEntry testObj1 = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "SoC", 15, "THis is for testing!");
		EventFlowEntry testObj2 = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "IT Fair", "SoC", 15, "THis is for testing!");
		
		assertEquals(-1, testObj1.compareTo(testObj2));
		assertEquals(1, testObj2.compareTo(testObj1));
	}
	
	/*
	 * Test compareTo method
	 */
	@Test
	public void testEight() 
	{
		EventFlowEntry testObj1 = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "SoC", 15, "THis is for testing!");
		EventFlowEntry testObj2 = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "Science", 15, "THis is for testing!");
		
		assertEquals(0, testObj1.compareTo(testObj2));
		assertEquals(0, testObj2.compareTo(testObj1));
	}
	
	/*
	 * Test compareTo method
	 */
	@Test
	public void testNine() 
	{
		EventFlowEntry testObj1 = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "SoC", 15, "THis is for testing!");
		EventFlowEntry testObj2 = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "FUNDraisING", "Science", 15, "THis is for testing!");
		
		assertEquals(0, testObj1.compareTo(testObj2));
		assertEquals(0, testObj2.compareTo(testObj1));
	}
}

