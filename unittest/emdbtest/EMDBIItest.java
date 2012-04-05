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
	
	private Vector<Eventitem>	eventList		=	new Vector<Eventitem>();
	private Vector<Venue>		venueList		=	new Vector<Venue>();
	private Vector<Item> 		itemList		=	new Vector<Item>();
	
	private Vector<Eventitem>	eventListVar	=	new Vector<Eventitem>();
	private Vector<Venue>		venueListVar	=	new Vector<Venue>();
	private Vector<Item> 		itemListVar		=	new Vector<Item>();
	
	
	
	public EMDBIITest(){		

		//Add 5 items to the list
		this.eventList.add(new Eventitem("The NUS Event", "01/12/2014", "12/12/2014", "01:00", "13:00"));
		this.eventList.get(0).setDescription("Just another event");
		
		this.eventList.add(new Eventitem("My Event", "19/12/2014", "20/12/2014", "14:00", "18:00"));
		this.eventList.add(new Eventitem("This Event", "19/10/2014", "20/10/2014", "14:00", "18:00"));
		this.eventList.add(new Eventitem("That Event", "20/10/2014", "20/11/2014", "11:56", "14:23"));
		this.eventList.add(new Eventitem("Your Event", "19/01/2014", "20/05/2014", "17:34", "23:56"));
		

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

	
	
	@Test
	public void setVerifyDatabase() throws Exception{
	
		EMDBSettings.dMsg("<EMDB TEST> SET AND VERIFY DB");
		
		this.db.eventDB().setup();
		assertTrue(this.db.eventDB().verify());
		
		this.db.venueDB().setup();
		assertTrue(this.db.venueDB().verify());
		
		this.db.budgetDB().setup();
		assertTrue(this.db.budgetDB().verify());
		
		this.db.participantDB().setup();
		assertTrue(this.db.participantDB().verify());
		
	}
	
	

	
	@Test
	public void saveAndRetrieveEvent() throws Exception {
		
		EMDBSettings.dMsg("<EMDB TEST> SAVE AND RETRIEVE EVENT");
		
		
		Eventitem itemOriginal 	= 	this.eventList.get(0);
		Eventitem itemResult	=	null;
		
		int id = this.db.eventDB().addEvent(
				itemOriginal.getName(), 
				itemOriginal.getDescription(), 
				itemOriginal.getBudget(), 
				itemOriginal.getStartDateTime().getDateRepresentation(), 
				itemOriginal.getEndDateTime().getDateRepresentation(),
				itemOriginal.getStartDateTime().getTimeRepresentation(), 
				itemOriginal.getEndDateTime().getTimeRepresentation(), 
				EventFlowEntry.getStringRepresentation(itemOriginal.getEventFlow())
				);
		
		assertTrue(	(id > 0) );
		
		itemResult = this.db.eventDB().getEvent(id);
		
		assertEquals(
				itemOriginal.getName(), 
				itemResult.getName()
				);
		
		assertEquals(
				itemOriginal.getDescription(), 
				itemResult.getDescription()
				);
		
		assertTrue(	(itemOriginal.getBudget() == itemResult.getBudget()) );
		
		assertEquals(
				itemOriginal.getStartDateTime().getDateRepresentation(), 
				itemResult.getStartDateTime().getDateRepresentation()
				);
		
		assertEquals(
				itemOriginal.getEndDateTime().getDateRepresentation(), 
				itemResult.getEndDateTime().getDateRepresentation()
				);
		
		assertEquals(
				itemOriginal.getStartDateTime().getTimeRepresentation(), 
				itemResult.getStartDateTime().getTimeRepresentation()
				);
		
		assertEquals(
				itemOriginal.getEndDateTime().getTimeRepresentation(), 
				itemResult.getEndDateTime().getTimeRepresentation()
				);
		
		assertEquals(
				EventFlowEntry.getStringRepresentation(itemOriginal.getEventFlow()),
				EventFlowEntry.getStringRepresentation(itemResult.getEventFlow())
				);
		
	}
	
	
	

	
	@Test
	public void clearTables() throws Exception {
		
		EMDBSettings.dMsg("<EMDB TEST> CLEAR / TRUNCATE TABLES");
		
	
		
	}

	
	@Test
	public void deleteTables() throws Exception {
		
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
	
	
}
