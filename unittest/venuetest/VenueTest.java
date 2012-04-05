package unittest.venuetest;

import static org.junit.Assert.*;

import org.junit.Test;

import venue.MyDateTime;
import venue.TimeSlot;
import venue.Venue;

public class VenueTest {

	/*
	 * Test constructors and getter methods
	 */
	@Test
	public void testOne()
	{
		Venue testObj = new Venue("DR7", "SoC NUS", "Discussion room", 30, 0);
		
		assertEquals("DR7", testObj.getName());
		assertEquals("SoC NUS", testObj.getAddress());
		assertEquals("Discussion room", testObj.getDescription());
		assertEquals(30, testObj.getMaxCapacity());
		assertEquals(0, testObj.getCost());
	}
	
	/*
	 * Test methods that update information.
	 */
	@Test
	public void testTwo()
	{
		Venue testObj = new Venue("DR7", "SoC NUS", "Discussion room", 30, 0);
		testObj.updateName("Tutorial room");
		testObj.updateAddress("COM1 - LEVEL2");
		testObj.updateDescription("Small");
		testObj.updateMaxCapacity(15);
		testObj.updateCost(10);
		
		assertEquals("Tutorial room", testObj.getName());
		assertEquals("COM1 - LEVEL2", testObj.getAddress());
		assertEquals("Small", testObj.getDescription());
		assertEquals(15, testObj.getMaxCapacity());
		assertEquals(10, testObj.getCost());
	}
	
	/*
	 * Test methods regarding booking time slots (i.e. bookTimeSlot, bookTimeSlotBlock, removeTimeSlot,
	 * 		isAvailable)
	 */
	@Test
	public void testThree()
	{
		// Initially, there is not booking
		Venue testObj = new Venue("DR7", "SoC NUS", "Discussion room", 30, 0);
		
		TimeSlot newSlot = new TimeSlot(new MyDateTime(2012, 4, 13, 19), new MyDateTime(2012, 4, 13, 23));
		assertEquals(true, testObj.isAvailable(newSlot));
		
		testObj.bookTimeSlot(newSlot);
		
		assertEquals(false, testObj.isAvailable(newSlot));
		
		testObj.removeTimeSlot(newSlot);
		
		assertEquals(true, testObj.isAvailable(newSlot));	
	}
	
	/*
	 * Test methods regarding booking time slots (i.e. bookTimeSlot, bookTimeSlotBlock, removeTimeSlot,
	 * 		isAvailable)
	 */
	@Test
	public void testFour()
	{
		// Initially, there is not booking
		Venue testObj = new Venue("DR7", "SoC NUS", "Discussion room", 30, 0);
		
		TimeSlot newSlot = new TimeSlot(new MyDateTime(2012, 4, 13, 19), new MyDateTime(2012, 4, 13, 23));
		TimeSlot newSlot2 = new TimeSlot(new MyDateTime(2012, 4, 13, 17), new MyDateTime(2012, 4, 13, 23));
		assertEquals(true, testObj.isAvailable(newSlot));
		assertEquals(true, testObj.isAvailable(newSlot2));
		
		testObj.bookTimeSlot(newSlot);
		
		assertEquals(false, testObj.isAvailable(newSlot));
		assertEquals(false, testObj.isAvailable(newSlot2));
		
		testObj.removeTimeSlot(newSlot);
		
		assertEquals(true, testObj.isAvailable(newSlot));
		assertEquals(true, testObj.isAvailable(newSlot2));
	}
}
