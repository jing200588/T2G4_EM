package unittest.programtest;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import program.ControllerEventFlow;
import program.EventFlowEntry;
import venue.MyDateTime;
import venue.TimeSlot;

public class ControllerEventFlowTest {
	
	/*
	 * Test insertSortedList method
	 */
	@Test
	public void testOne() {
		Vector<EventFlowEntry> testList = new Vector<EventFlowEntry>();
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "SoC", 15, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 20, 7, 21),
			 	 new MyDateTime(2012, 3, 25, 10, 20)),
			 	 "Fundraising 2", "SoC", 15, "THis is for testing!"));
		// Currently, testList is in sorted order
		EventFlowEntry testObj = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 15, 7, 21),
			 	 new MyDateTime(2012, 3, 15, 10, 20)),
			 	 "Meeting and final preparation", "SoC", 15, "THis is for testing!");
		
		ControllerEventFlow.insertSortedList(testList, testObj);
		
		assertEquals("Meeting and final preparation", testList.get(0).getActivityName());
		assertEquals("Fundraising", testList.get(1).getActivityName());
		assertEquals("Fundraising 2", testList.get(2).getActivityName());
	}

	/*
	 * Test insertSortedList method
	 */
	@Test
	public void testTwo() {
		Vector<EventFlowEntry> testList = new Vector<EventFlowEntry>();
		
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "SoC", 15, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 20, 7, 21),
			 	 new MyDateTime(2012, 3, 25, 10, 20)),
			 	 "Fundraising 2", "SoC", 15, "THis is for testing!"));
		// Currently, testList is in sorted order
		EventFlowEntry testObj = new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 30, 16, 21),
			 	 new MyDateTime(2012, 3, 30, 19, 20)),
			 	 "Party", "SoC", 15, "THis is for testing!");
		
		ControllerEventFlow.insertSortedList(testList, testObj);
		
		assertEquals("Fundraising", testList.get(0).getActivityName());
		assertEquals("Fundraising 2", testList.get(1).getActivityName());
		assertEquals("Party", testList.get(2).getActivityName());
	}

	/*
	 * Test filterByActivityName method
	 */
	@Test
	public void testThree() {
		Vector<EventFlowEntry> testList = new Vector<EventFlowEntry>();
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 15, 7, 21),
			 	 new MyDateTime(2012, 3, 15, 10, 20)),
			 	 "Meeting and final preparation", "SoC", 15, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "SoC", 15, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 20, 7, 21),
			 	 new MyDateTime(2012, 3, 25, 10, 20)),
			 	 "Fundraising 2", "SoC", 15, "THis is for testing!"));
		// Currently, testList is in sorted order
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 30, 16, 21),
			 	 new MyDateTime(2012, 3, 30, 19, 20)),
			 	 "Party", "SoC", 15, "THis is for testing!"));
		
		Vector<Integer> filterList = ControllerEventFlow.filterByActivityName(testList, "FUNDRAISING");
		assertEquals(2, filterList.size());
		assertEquals(1, (int) filterList.get(0));
		assertEquals(2, (int) filterList.get(1));
		assertEquals("Fundraising", testList.get(filterList.get(0)).getActivityName());
		assertEquals("Fundraising 2", testList.get(filterList.get(1)).getActivityName());
	}
	
	/*
	 * Test filterByActivityName method
	 */
	@Test
	public void testFour() {
		Vector<EventFlowEntry> testList = new Vector<EventFlowEntry>();
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 15, 7, 21),
			 	 new MyDateTime(2012, 3, 15, 10, 20)),
			 	 "Meeting and final preparation", "SoC", 15, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "SoC", 15, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 20, 7, 21),
			 	 new MyDateTime(2012, 3, 25, 10, 20)),
			 	 "Fundraising 2", "SoC", 15, "THis is for testing!"));
		// Currently, testList is in sorted order
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 30, 16, 21),
			 	 new MyDateTime(2012, 3, 30, 19, 20)),
			 	 "Party", "SoC", 15, "THis is for testing!"));
		
		Vector<Integer> indexList = new Vector<Integer>();
		indexList.add(0);
		indexList.add(2);
		indexList.add(3);
		
		Vector<Integer> filterList = ControllerEventFlow.filterByActivityName(testList, indexList,
				"FUNDRAISING");
		assertEquals(1, filterList.size());
		assertEquals(2, (int) filterList.get(0));
		assertEquals("Fundraising 2", testList.get(filterList.get(0)).getActivityName());
	}
	
	/*
	 * Test filterByVenue method
	 */
	@Test
	public void testFive() {
		Vector<EventFlowEntry> testList = new Vector<EventFlowEntry>();
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 15, 7, 21),
			 	 new MyDateTime(2012, 3, 15, 10, 20)),
			 	 "Meeting and final preparation", "SoC", 15, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "FASS", 16, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 20, 7, 21),
			 	 new MyDateTime(2012, 3, 25, 10, 20)),
			 	 "Fundraising 2", "Science", 17, "THis is for testing!"));
		// Currently, testList is in sorted order
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 30, 16, 21),
			 	 new MyDateTime(2012, 3, 30, 19, 20)),
			 	 "Party", "SoC", 15, "THis is for testing!"));
		
		Vector<Integer> filterList = ControllerEventFlow.filterByVenue(testList, 15);
		assertEquals(2, filterList.size());
		assertEquals(0, (int) filterList.get(0));
		assertEquals(3, (int) filterList.get(1));
		assertEquals("Meeting and final preparation", testList.get(filterList.get(0)).getActivityName());
		assertEquals("Party", testList.get(filterList.get(1)).getActivityName());
	}
	
	/*
	 * Test filterByVenue method
	 */
	@Test
	public void testSix() {
		Vector<EventFlowEntry> testList = new Vector<EventFlowEntry>();
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 15, 7, 21),
			 	 new MyDateTime(2012, 3, 15, 10, 20)),
			 	 "Meeting and final preparation", "SoC", 15, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "FASS", 16, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 20, 7, 21),
			 	 new MyDateTime(2012, 3, 25, 10, 20)),
			 	 "Fundraising 2", "Science", 17, "THis is for testing!"));
		// Currently, testList is in sorted order
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 30, 16, 21),
			 	 new MyDateTime(2012, 3, 30, 19, 20)),
			 	 "Party", "SoC", 15, "THis is for testing!"));
		
		Vector<Integer> indexList = new Vector<Integer>();
		indexList.add(1);
		indexList.add(2);
		
		Vector<Integer> filterList = ControllerEventFlow.filterByVenue(testList, indexList, 15);
		assertEquals(true, filterList.isEmpty());
	}
	
	/*
	 * Test filterByTimeSlot method
	 */
	@Test
	public void testSeven() {
		Vector<EventFlowEntry> testList = new Vector<EventFlowEntry>();
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 15, 7, 21),
			 	 new MyDateTime(2012, 3, 15, 10, 20)),
			 	 "Meeting and final preparation", "SoC", 15, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "FASS", 16, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 20, 7, 21),
			 	 new MyDateTime(2012, 3, 25, 10, 20)),
			 	 "Fundraising 2", "Science", 17, "THis is for testing!"));
		// Currently, testList is in sorted order
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 30, 16, 21),
			 	 new MyDateTime(2012, 3, 30, 19, 20)),
			 	 "Party", "SoC", 15, "THis is for testing!"));
		
		TimeSlot testObj = new TimeSlot(new MyDateTime("14/3/2012/00/00"), new MyDateTime("18/3/2012/00/00"));
		Vector<Integer> filterList = ControllerEventFlow.filterByTimeSlot(testList, testObj);
		assertEquals(2, filterList.size());
		assertEquals(0, (int) filterList.get(0));
		assertEquals(1, (int) filterList.get(1));
		assertEquals("Meeting and final preparation", testList.get(filterList.get(0)).getActivityName());
		assertEquals("Fundraising", testList.get(filterList.get(1)).getActivityName());
	}
	
	/*
	 * Test filterByTimeSlot method
	 */
	@Test
	public void testEight() {
		Vector<EventFlowEntry> testList = new Vector<EventFlowEntry>();
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 15, 7, 21),
			 	 new MyDateTime(2012, 3, 15, 10, 20)),
			 	 "Meeting and final preparation", "SoC", 15, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "FASS", 16, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 20, 7, 21),
			 	 new MyDateTime(2012, 3, 25, 10, 20)),
			 	 "Fundraising 2", "Science", 17, "THis is for testing!"));
		// Currently, testList is in sorted order
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 30, 16, 21),
			 	 new MyDateTime(2012, 3, 30, 19, 20)),
			 	 "Party", "SoC", 15, "THis is for testing!"));
		
		Vector<Integer> indexList = new Vector<Integer>();
		indexList.add(1);
		indexList.add(2);
		
		TimeSlot testObj = new TimeSlot(new MyDateTime("14/3/2012/00/00"), new MyDateTime("18/3/2012/00/00"));
		Vector<Integer> filterList = ControllerEventFlow.filterByTimeSlot(testList, indexList, testObj);
		assertEquals(1, filterList.size());
		assertEquals(1, (int) filterList.get(0));
		assertEquals("Fundraising", testList.get(filterList.get(0)).getActivityName());
	}
	
	/*
	 * Test selectEntry method
	 */
	@Test
	public void testNine() {
		Vector<EventFlowEntry> testList = new Vector<EventFlowEntry>();
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 15, 7, 21),
			 	 new MyDateTime(2012, 3, 15, 10, 20)),
			 	 "Meeting and final preparation", "SoC", 15, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "FASS", 16, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 20, 7, 21),
			 	 new MyDateTime(2012, 3, 25, 10, 20)),
			 	 "Fundraising 2", "Science", 17, "THis is for testing!"));
		// Currently, testList is in sorted order
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 30, 16, 21),
			 	 new MyDateTime(2012, 3, 30, 19, 20)),
			 	 "Party", "SoC", 15, "THis is for testing!"));
		
		Vector<Integer> indexList = new Vector<Integer>();
		indexList.add(1);
		indexList.add(2);
		
		Vector<EventFlowEntry> filterList = ControllerEventFlow.selectEntry(testList, indexList);
		assertEquals(2, filterList.size());
		assertEquals("Fundraising", filterList.get(0).getActivityName());
		assertEquals("Fundraising 2", filterList.get(1).getActivityName());
	}
	
	/*
	 * Test selectEntry method
	 */
	@Test
	public void testTen() {
		Vector<EventFlowEntry> testList = new Vector<EventFlowEntry>();
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 15, 7, 21),
			 	 new MyDateTime(2012, 3, 15, 10, 20)),
			 	 "Meeting and final preparation", "SoC", 15, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 16, 7, 21),
			 	 new MyDateTime(2012, 3, 17, 10, 20)),
			 	 "Fundraising", "FASS", 16, "THis is for testing!"));
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 20, 7, 21),
			 	 new MyDateTime(2012, 3, 25, 10, 20)),
			 	 "Fundraising 2", "Science", 17, "THis is for testing!"));
		// Currently, testList is in sorted order
		testList.add(new EventFlowEntry(new TimeSlot(new MyDateTime(2012, 3, 30, 16, 21),
			 	 new MyDateTime(2012, 3, 30, 19, 20)),
			 	 "Party", "SoC", 15, "THis is for testing!"));
		
		Vector<Integer> indexList = new Vector<Integer>();
		indexList.add(9);
		indexList.add(10);
		
		Vector<EventFlowEntry> filterList = ControllerEventFlow.selectEntry(testList, indexList);
		assertEquals(0, filterList.size());
		
		filterList = ControllerEventFlow.selectEntry(testList, null);
		assertEquals(0, filterList.size());
		
		filterList = ControllerEventFlow.selectEntry(null, null);
		assertEquals(0, filterList.size());
	}
}
