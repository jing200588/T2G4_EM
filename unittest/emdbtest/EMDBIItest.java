package unittest.emdbtest;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import program.EventFlowEntry;

import budget.Item;
import venue.Venue;
import emdb.EMDBII;
import emdb.EMDBSettings;
import event.Eventitem;

public class EMDBIITest {
	
	
	private EMDBII 				db	= new EMDBII("unit.sqlite", true);; 				
	
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
	 * 
	 * *******************************
	 */
	
	
	
	@Test
	public void aSetVerifyDatabase() throws Exception{
	
		EMDBSettings.dMsg("\n\n<EMDB TEST> SET AND VERIFY DB");
		
		this.db.eventDB().setup();
		assertTrue(this.db.eventDB().verify());
		
		this.db.venueDB().setup();
		assertTrue(this.db.venueDB().verify());
		
		this.db.budgetDB().setup();
		assertTrue(this.db.budgetDB().verify());
		
		this.db.participantDB().setup();
		assertTrue(this.db.participantDB().verify());
		
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * ******************************* 
	 * 
	 * Test Section B
	 * 
	 * *******************************
	 */
	
	
	
	
	@Test
	public void bAddAndRetrieveEvent() throws Exception {
		
		EMDBSettings.dMsg("\n\n<EMDB TEST> SAVE AND RETRIEVE EVENT");
		
		
		Eventitem itemResult	=	null;
		
		int eventID = this.db.eventDB().addEvent(
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
		
		itemResult = this.db.eventDB().getEvent(eventID);
		
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
	public void bAddVenue(){
		EMDBSettings.dMsg("\n\n<EMDB TEST> ADD VENUE FROM LOCAL LIST OF SIZE " + venueList.size());

		int oldDbVenueSize = this.db.venueDB().getVenueList(null, 0, 0).size();
		EMDBSettings.dMsg("<EMDB TEST> OLD DB VENUE SIZE " + oldDbVenueSize);
		
		for (int i=0; i<5; i++){
			this.db.venueDB().addVenue(this.venueList.get(i));
		}
		
		int newDbVenueSize = this.db.venueDB().getVenueList(null, 0, 0).size();
		EMDBSettings.dMsg("<EMDB TEST> NEW DB VENUE SIZE " + newDbVenueSize);
		
		
		assertEquals( venueList.size(), (newDbVenueSize-oldDbVenueSize) );
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	/*
	 * ******************************* 
	 * 
	 * Test Section C
	 * 
	 * *******************************
	 */

	
	@Test
	public void cUpdateEvent(){
		EMDBSettings.dMsg("\n\n<EMDB TEST> UPDATE EVENT");
		
		
		int id = this.db.eventDB().addEvent(this.eventList.get(1));
		
		Eventitem eEvent = this.eventList.get(1);
		eEvent.setID(id);
		eEvent.setName("Getting a new name");
		
		
		this.db.eventDB().updateEvent(eEvent);
		
		Eventitem nEvent = this.db.eventDB().getEvent(id);
		
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * ******************************* 
	 * 
	 * Test Section D
	 * 
	 * *******************************
	 */
	

	@Test
	public void dDeleteVenue(){

		EMDBSettings.dMsg("\n\n<EMDB TEST> DELETE VENUE");
		
		int id = this.db.venueDB().addVenue(this.venueList.get(0));
	
		int oldDbVenueSize = this.db.venueDB().getVenueList(null, 0, 0).size();
		EMDBSettings.dMsg("<EMDB TEST> OLD DB VENUE SIZE " + oldDbVenueSize);
		
		this.db.venueDB().deleteVenue(id);
		
		int newDbVenueSize = this.db.venueDB().getVenueList(null, 0, 0).size();
		EMDBSettings.dMsg("<EMDB TEST> NEW DB VENUE SIZE " + newDbVenueSize);
		
		assertEquals( 1, Math.abs(newDbVenueSize-oldDbVenueSize) );
		
		
		Venue test = this.db.venueDB().getVenue(id);
		assertTrue( test.getName() == null || test.getName().isEmpty() );
		
		
	}
	
	
	
	@Test
	public void dDeleteEventNone(){
		EMDBSettings.dMsg("\n\n<EMDB TEST> DELETE NON-EXISTANT EVENT");
		
		assertEquals(0, this.db.eventDB().deleteEvent(0));
	}
	
	
	@Test
	public void dDeleteVenueNone(){
		EMDBSettings.dMsg("\n\n<EMDB TEST> DELETE NON-EXISTANT VENUE");
		
		assertEquals(0,this.db.venueDB().deleteVenue(0));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	/*
	 * ******************************* 
	 * 
	 * Test Section Y
	 * Cleanup
	 * 
	 * *******************************
	 */

	/*
	
	@Test
	public void deleteTables() throws Exception {
		
		EMDBSettings.dMsg("<EMDB TEST> DELETE / DROP TABLES");
		
		this.db.eventDB().drop();
		assertFalse(this.db.eventDB().verify());

		this.db.venueDB().drop();
		assertFalse(this.db.venueDB().verify());
		
		this.db.budgetDB().drop();
		assertFalse(this.db.budgetDB().verify());
		
		this.db.participantDB().drop();
		assertFalse(this.db.participantDB().verify());
	}*/
	


	
	
	
	
	
	
	
	
	
	
	/*
	 * ******************************* 
	 * 
	 * Test Section Z
	 * END
	 * 
	 * *******************************
	 */
	
	
	@Test
	public void zDeleteDB(){
		EMDBSettings.dMsg("\n\n<EMDB TEST> DESTROY DATABASE FILE");
		assertTrue(this.db.destroy(false));
	} 
	
	
}
