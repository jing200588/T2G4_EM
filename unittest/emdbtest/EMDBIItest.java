package unittest.emdbtest;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import program.EventFlowEntry;

import budget.Item;
import venue.TimeSlot;
import venue.Venue;
import emdb.EMDBII;
import emdb.EMDBSettings;
import event.Eventitem;


public class EMDBIITest {
	
	
	private static EMDBII 		db 				= new EMDBII("unit.sqlite", true); 			
	
	private Eventitem			event;
	private Vector<Eventitem>	eventList		=	new Vector<Eventitem>();
	private Vector<Venue>		venueList		=	new Vector<Venue>();
	private Vector<Item> 		itemList		=	new Vector<Item>();
	

	
	
	
	public EMDBIITest(){		

		//Add 5 items to the list
		this.event = new Eventitem("The NUS Event", "01/12/2014", "12/12/2014", "01:00", "13:00");
		this.event.setDescription("Just another event");
		
		this.eventList.add(new Eventitem("My Event", "19/12/2014", "20/12/2014", "14:00", "18:00"));
		this.eventList.add(new Eventitem("This Event", "19/10/2014", "20/10/2014", "14:00", "18:00"));
		this.eventList.add(new Eventitem("That Event", "20/10/2014", "20/11/2014", "11:56", "14:23"));
		this.eventList.add(new Eventitem("Your Event", "19/01/2014", "20/05/2014", "17:34", "23:56"));
		this.eventList.add(new Eventitem("The NUSSU Stuff", "06/10/2013", "12/12/2014", "01:00", "13:00"));
		

		//Add 5 Venues	
		this.venueList.add(new Venue("DR1", "COM1-B-14B", "Discussion Room", 100, 0));
		this.venueList.add(new Venue("CR2", "COM1-B-14A", "Computer Room", 10000, 100));
		this.venueList.add(new Venue("LT3", "COM1-B-07", "Lecture Theatre", 876, 98));
		this.venueList.add(new Venue("MR4", "COM1-B-06", "Meeting Room", 47, 2345));
		this.venueList.add(new Venue("AS5", "ICUBE-03-18", "Arts Faculty Block 5", 3, 8));
		
		
		//5 items
		this.itemList.add(new Item("Windows 7", 562.00, 1));
		this.itemList.add(new Item("Windows 8", 2262.00, 2));
		this.itemList.add(new Item("GNU Linux", 0.00, 120));
		this.itemList.add(new Item("Food", 1000.00, 400));
		this.itemList.add(new Item("Lodging", 23.00, 100));
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * ******************************* 
	 * 
	 * Test Section A
	 * START / END
	 * 
	 * *******************************
	 */
	
	
	
	@BeforeClass
	public static void oneTimeSetUp(){
	
		EMDBSettings.dMsg("\n\n<EMDB TEST> SET AND VERIFY DB");
		
		db.eventDB().setup();
		assertTrue(db.eventDB().verify());
		
		db.venueDB().setup();
		assertTrue(db.venueDB().verify());
		
		db.budgetDB().setup();
		assertTrue(db.budgetDB().verify());
		
		db.participantDB().setup();
		assertTrue(db.participantDB().verify());
		
	}
	
	

	
	
	@AfterClass
	public static void oneTimeTearDown(){
		
		EMDBSettings.dMsg("\n<EMDB TEST> DESTROY DATABASE FILE\n");
		
		assertTrue(db.destroy(false));
	} 
	
	
	
	
	@After
	public void tearDown(){
		db.eventDB().truncate();
		db.venueDB().truncate();
		db.budgetDB().truncate();
		db.participantDB().truncate();
	}
	

	
	
	
	
	
	
	
	
	
	/*
	 * ******************************* 
	 * 
	 * Test Section B
	 * 
	 * *******************************
	 */
	
	
	
	
	@Test
	public void addAndRetrieveEvent() throws Exception {
		
		EMDBSettings.dMsg("\n\n<EMDB TEST> SAVE AND RETRIEVE EVENT");
		
		Eventitem itemResult	=	null;
		
		int eventID = db.eventDB().addEvent(
				this.event.getName(), 
				this.event.getDescription(), 
				this.event.getBudget(), 
				this.event.getStartDateTime().getDateRepresentation(), 
				this.event.getEndDateTime().getDateRepresentation(),
				this.event.getStartDateTime().getTimeRepresentation(), 
				this.event.getEndDateTime().getTimeRepresentation(), 
				EventFlowEntry.getStringRepresentation(this.event.getEventFlow())
				);
		
		assertTrue(	(eventID > 0) );
		
		itemResult = db.eventDB().getEvent(eventID);
		
		assertEquals(
				this.event.getName(), 
				itemResult.getName()
				);
		
		assertEquals(
				this.event.getDescription(), 
				itemResult.getDescription()
				);
		
		assertTrue(	(this.event.getBudget() == itemResult.getBudget()) );
		
		assertEquals(
				this.event.getStartDateTime().getDateRepresentation(), 
				itemResult.getStartDateTime().getDateRepresentation()
				);
		
		assertEquals(
				this.event.getEndDateTime().getDateRepresentation(), 
				itemResult.getEndDateTime().getDateRepresentation()
				);
		
		assertEquals(
				this.event.getStartDateTime().getTimeRepresentation(), 
				itemResult.getStartDateTime().getTimeRepresentation()
				);
		
		assertEquals(
				this.event.getEndDateTime().getTimeRepresentation(), 
				itemResult.getEndDateTime().getTimeRepresentation()
				);
		
		assertEquals(
				EventFlowEntry.getStringRepresentation(this.event.getEventFlow()),
				EventFlowEntry.getStringRepresentation(itemResult.getEventFlow())
				);
		
		
	}


	
	
	@Test
	public void addVenue(){
		EMDBSettings.dMsg("\n\n<EMDB TEST> ADD VENUE FROM LOCAL LIST OF SIZE " + venueList.size());

		int oldDbVenueSize = db.venueDB().getVenueList(null, 0, 0).size();
		EMDBSettings.dMsg("<EMDB TEST> OLD DB VENUE SIZE " + oldDbVenueSize);
		
		for (int i=0; i<5; i++){
			db.venueDB().addVenue(this.venueList.get(i));
		}
		
		int newDbVenueSize = db.venueDB().getVenueList(null, 0, 0).size();
		EMDBSettings.dMsg("<EMDB TEST> NEW DB VENUE SIZE " + newDbVenueSize);
		
		
		assertEquals( venueList.size(), (newDbVenueSize-oldDbVenueSize) );
		
	}
	
	
	@Test
	public void addBooking(){
		EMDBSettings.dMsg("\n\n<EMDB TEST> ADD BOOKING");
		
		int id = db.venueDB().addBooking(1, 1, "10/05/2012/15/00", "11/05/2012/16/00");
		TimeSlot temp = db.venueDB().getBooking(id);

		assertEquals("10/05/2012/15/00", temp.getStartDateTime().getDateTimeRepresentation());
		assertEquals("11/05/2012/16/00", temp.getEndDateTime().getDateTimeRepresentation());
	}
	
	
	@Test
	public void addBudget(){
		EMDBSettings.dMsg("\n\n<EMDB TEST> ADD BUDGET");

		db.budgetDB().addBudget(9, "Test", 3000, 21, "Noth", 2);
		db.budgetDB().addBudget(8, "Test1", 3000, 21, "Noth", 2);
		db.budgetDB().addBudgetOptimized(6, "Test", 3000, 21, "Noth", 2);
		
		Vector<Item> list = new Vector<Item>();
		list.add(new Item("Item1", 10.0, 1, "smth"));
		list.add(new Item("Item2", 10.0, 1, "smth"));
		list.add(new Item("Item3", 10.0, 1, "smth"));
		list.add(new Item("Item4", 10.0, 1, "smth"));
		
		
		//db.budgetDB().addBudgetList(list, 9);
		//db.budgetDB().addBudgetListOptimized(list, 7);
		
		//assertEquals(5,db.budgetDB().getBudgetList(9).size());
		assertEquals(1,db.budgetDB().getBudgetList(8).size());
		//assertEquals(4,db.budgetDB().getBudgetListOptimized(7).size());
		assertEquals(1,db.budgetDB().getBudgetListOptimized(6).size());
		
		

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	/*
	 * ******************************* 
	 * 
	 * Test Section C
	 * 
	 * *******************************
	 */

	
	@Test
	public void updateEvent(){
		EMDBSettings.dMsg("\n\n<EMDB TEST> UPDATE EVENT");
		
		
		Eventitem eEvent = this.eventList.get(3);
		
		int id = db.eventDB().addEvent(eEvent);

		eEvent.setID(id);
		eEvent.setName("Getting a new name");
		
		
		db.eventDB().updateEvent(eEvent);
		
		Eventitem nEvent = db.eventDB().getEvent(id);
		
		assertEquals(
				eEvent.getName(), 
				nEvent.getName()
				);
		
		
		assertEquals(
				eEvent.getDescription(), 
				nEvent.getDescription()
				);
		
		assertTrue(	(eEvent.getBudget() == nEvent.getBudget()) );
		
		assertEquals(
				eEvent.getStartDateTime().getDateRepresentation(), 
				nEvent.getStartDateTime().getDateRepresentation()
				);
		
		assertEquals(
				eEvent.getEndDateTime().getDateRepresentation(), 
				nEvent.getEndDateTime().getDateRepresentation()
				);
		
		assertEquals(
				eEvent.getStartDateTime().getTimeRepresentation(), 
				nEvent.getStartDateTime().getTimeRepresentation()
				);
		
		assertEquals(
				eEvent.getEndDateTime().getTimeRepresentation(), 
				nEvent.getEndDateTime().getTimeRepresentation()
				);
		
		assertEquals(
				EventFlowEntry.getStringRepresentation(eEvent.getEventFlow()),
				EventFlowEntry.getStringRepresentation(nEvent.getEventFlow())
				);
		
	}
	
	
	
	
	
	
	public void addAndRetrievePartipicant(){
		
	}
	
	
	
	
	
	
	public void addAndRetrieveBudget(){
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * ******************************* 
	 * 
	 * Test Section D
	 * 
	 * *******************************
	 */
	

	@Test
	public void deleteVenue(){

		EMDBSettings.dMsg("\n\n<EMDB TEST> DELETE VENUE");
		
		int id = db.venueDB().addVenue(this.venueList.get(0));
	
		int oldDbVenueSize = db.venueDB().getVenueList(null, 0, 0).size();
		EMDBSettings.dMsg("<EMDB TEST> OLD DB VENUE SIZE " + oldDbVenueSize);
		
		db.venueDB().deleteVenue(id);
		
		int newDbVenueSize = db.venueDB().getVenueList(null, 0, 0).size();
		EMDBSettings.dMsg("<EMDB TEST> NEW DB VENUE SIZE " + newDbVenueSize);
		
		assertEquals( 1, Math.abs(newDbVenueSize-oldDbVenueSize) );
		
		
		Venue test = db.venueDB().getVenue(id);
		assertTrue( test.getName() == null || test.getName().isEmpty() );

	}
	
	
	
	@Test
	public void deleteEventNone(){
		EMDBSettings.dMsg("\n\n<EMDB TEST> DELETE NON-EXISTANT EVENT");
		
		assertEquals(0, db.eventDB().deleteEvent(0));
	}
	
	
	@Test
	public void deleteVenueNone(){
		EMDBSettings.dMsg("\n\n<EMDB TEST> DELETE NON-EXISTANT VENUE");
		
		assertEquals(0,db.venueDB().deleteVenue(0));
	}
	
	
	
	@Test
	public void deleteBookingNone(){
		EMDBSettings.dMsg("\n\n<EMDB TEST> DELETE NON-EXISTANT BOOKING");
		
		assertEquals(0,db.venueDB().deleteBooking(0));
		assertEquals(0,db.venueDB().deleteBookingAll(0));
	}
	
	
	@Test
	public void deleteBudgetNone(){
		EMDBSettings.dMsg("\n\n<EMDB TEST> DELETE NON-EXISTANT BUDGETS");
			
		assertEquals(0,db.budgetDB().deleteBudget(0));	
		assertEquals(0,db.budgetDB().deleteBudgetList(0));
	}
	
	
	
	
	
	
	
	
	
	/*
	 * ******************************* 
	 * 
	 * Test Section Y
	 * 
	 * *******************************
	 */
	
	/*
	@Test
	public void deleteTables(){
		EMDBSettings.dMsg("<EMDB TEST> DELETE / DROP TABLES");
		
		db.eventDB().drop();
		assertFalse(db.eventDB().verify());

		db.venueDB().drop();
		assertFalse(db.venueDB().verify());
		
		db.budgetDB().drop();
		assertFalse(db.budgetDB().verify());
		
		db.participantDB().drop();
		assertFalse(db.participantDB().verify());
		
	}
	*/

	



	
	
	
	
	

	

	
	
}
