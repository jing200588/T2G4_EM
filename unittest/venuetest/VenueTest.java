package unittest.venuetest;

import static org.junit.Assert.*;

import org.junit.Test;

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

}
