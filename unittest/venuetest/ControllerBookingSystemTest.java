package unittest.venuetest;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import venue.ControllerBookingSystem;
import venue.ModelBookingSystem;
import venue.Venue;

public class ControllerBookingSystemTest {
	/*
	 * Test sorter
	 */
	@Test
	public void testOne() {
		Vector<Venue> testVector = new Vector<Venue>();
		
		testVector.add(new Venue("DR7", "SoC NUS", "Discussion room", 30, 0));
		testVector.add(new Venue("UCC", "NUS", "For big events", 5000, 1000000));
		testVector.add(new Venue("LT31", "Science", "Lecture Theater", 300, 10000));
		testVector.add(new Venue("AS6", "FASS", "Happy", 25, 1200));
		
		ControllerBookingSystem.sortVenueList(testVector, ControllerBookingSystem.SortCriteria.NAME);
		
		assertEquals("AS6", testVector.get(0).getName());
		assertEquals("DR7", testVector.get(1).getName());
		assertEquals("LT31", testVector.get(2).getName());
		assertEquals("UCC", testVector.get(3).getName());	
	}

	/*
	 * Test sorter
	 */
	@Test
	public void testTwo() {
		Vector<Venue> testVector = new Vector<Venue>();
		
		testVector.add(new Venue("DR7", "SoC NUS", "Discussion room", 30, 0));
		testVector.add(new Venue("UCC", "NUS", "For big events", 5000, 1000000));
		testVector.add(new Venue("LT31", "Science", "Lecture Theater", 300, 10000));
		testVector.add(new Venue("AS6", "FASS", "Happy", 25, 1200));
		
		ControllerBookingSystem.sortVenueList(testVector, ControllerBookingSystem.SortCriteria.ADDRESS);
		
		assertEquals("AS6", testVector.get(0).getName());
		assertEquals("UCC", testVector.get(1).getName());
		assertEquals("LT31", testVector.get(2).getName());
		assertEquals("DR7", testVector.get(3).getName());	
	}
	
	/*
	 * Test sorter
	 */
	@Test
	public void testThree() {
		Vector<Venue> testVector = new Vector<Venue>();
		
		testVector.add(new Venue("DR7", "SoC NUS", "Discussion room", 30, 0));
		testVector.add(new Venue("UCC", "NUS", "For big events", 5000, 1000000));
		testVector.add(new Venue("LT31", "Science", "Lecture Theater", 300, 10000));
		testVector.add(new Venue("AS6", "FASS", "Happy", 25, 1200));
		
		ControllerBookingSystem.sortVenueList(testVector, ControllerBookingSystem.SortCriteria.CAPACITY);
		
		assertEquals("AS6", testVector.get(0).getName());
		assertEquals("DR7", testVector.get(1).getName());
		assertEquals("LT31", testVector.get(2).getName());
		assertEquals("UCC", testVector.get(3).getName());	
	}
	
	/*
	 * Test sorter
	 */
	@Test
	public void testFour() {
		Vector<Venue> testVector = new Vector<Venue>();
		
		testVector.add(new Venue("DR7", "SoC NUS", "Discussion room", 30, 0));
		testVector.add(new Venue("UCC", "NUS", "For big events", 5000, 1000000));
		testVector.add(new Venue("LT31", "Science", "Lecture Theater", 300, 10000));
		testVector.add(new Venue("AS6", "FASS", "Happy", 25, 1200));
		
		ControllerBookingSystem.sortVenueList(testVector, ControllerBookingSystem.SortCriteria.COST);
		
		assertEquals("DR7", testVector.get(0).getName());
		assertEquals("AS6", testVector.get(1).getName());
		assertEquals("LT31", testVector.get(2).getName());
		assertEquals("UCC", testVector.get(3).getName());	
	}
	
	/*
	 * Test searching method and isInTheList method
	 */
	@Test
	public void testFive()
	{
		ModelBookingSystem modelTest = new ModelBookingSystem("unit.sqlite");
		
		ControllerBookingSystem testObj = new ControllerBookingSystem();
		
		Vector<Venue> searchResult = testObj.findVenueByName("icube");
		
		assertEquals(4, searchResult.size());
		testObj.isInTheList(6);
		testObj.isInTheList(19);
		testObj.isInTheList(20);
		testObj.isInTheList(21);
	}
	
	/*
	 *  
	 */
}
