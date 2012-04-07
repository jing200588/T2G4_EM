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
		assertEquals(true, testObj.isInTheList(5));
		assertEquals(true, testObj.isInTheList(19));
		assertEquals(true, testObj.isInTheList(20));
		assertEquals(true, testObj.isInTheList(21));
		
		modelTest.deleteDatabase();
	}
	
	/*
	 * Test searching (by capacity, cost, time slot) method and isInTheList method
	 */
	@Test
	public void testSix()
	{
		ModelBookingSystem modelTest = new ModelBookingSystem("unit.sqlite");
		
		ControllerBookingSystem testObj = new ControllerBookingSystem();
		
		int[] capacityRange = {25, 30};
		Vector<Venue> searchResult = testObj.findVenueByCriteria(null, capacityRange, null, 
				ControllerBookingSystem.SearchCriteria.CAPACITY);
		
		assertEquals(2, searchResult.size());
		assertEquals(true, testObj.isInTheList(12));
		assertEquals(true, testObj.isInTheList(22));
		assertEquals(false, testObj.isInTheList(5));
		modelTest.deleteDatabase();
	}
	
	/*
	 * Test searching (by capacity, cost, time slot) method and isInTheList method
	 */
	@Test
	public void testSeven()
	{
		ModelBookingSystem modelTest = new ModelBookingSystem("unit.sqlite");
		
		ControllerBookingSystem testObj = new ControllerBookingSystem();
		
		int[] costRange = {1000, 1200};
		Vector<Venue> searchResult = testObj.findVenueByCriteria(costRange, null, null, 
				ControllerBookingSystem.SearchCriteria.COST);
		
		assertEquals(3, searchResult.size());
		assertEquals(true, testObj.isInTheList(1));
		assertEquals(true, testObj.isInTheList(3));
		assertEquals(true, testObj.isInTheList(2));
		
		modelTest.deleteDatabase();
	}
}
