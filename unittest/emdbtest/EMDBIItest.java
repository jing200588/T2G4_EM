package unittest.emdbtest;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import budget.Item;
import venue.Venue;
import event.Eventitem;

public class EMDBIITest {
	private Eventitem			itemStart;
	private Vector<Eventitem>	eventList		=	new Vector<Eventitem>();
	private Vector<Venue>		venueList		=	new Vector<Venue>();
	private Vector<Item> 		itemList		=	new Vector<Item>();
	
	private Eventitem			itemVar;
	private Vector<Eventitem>	eventListVar	=	new Vector<Eventitem>();
	private Vector<Venue>		venueListVar	=	new Vector<Venue>();
	private Vector<Item> 		itemListVar		=	new Vector<Item>();
	
	
	
	public EMDBIITest(){		

		
		//Add 5 items to the list
		eventList.add(new Eventitem("The NUS Event", "01/12/2014", "12/12/2014", "01:00", "13:00"));
		eventList.add(new Eventitem("My Event", "19/12/2014", "20/12/2014", "14:00", "18:00"));
		eventList.add(new Eventitem("This Event", "19/10/2014", "20/10/2014", "14:00", "18:00"));
		eventList.add(new Eventitem("That Event", "20/10/2014", "20/11/2014", "11:56", "14:23"));
		eventList.add(new Eventitem("Your Event", "19/01/2014", "20/05/2014", "17:34", "23:56"));
		

		//Add 5 Venues	
		venueList.add(new Venue("DR1", "COM1-B-14B", "Discussion Room", 100, 0));
		venueList.add(new Venue("CR2", "COM1-B-14A", "Computer Room", 10000, 100));
		venueList.add(new Venue("LT3", "COM1-B-07", "Lecture Theatre", 876, 98));
		venueList.add(new Venue("MR4", "COM1-B-06", "Meeting Room", 47, 2345));
		venueList.add(new Venue("AS5", "ICUBE-03-18", "Arts Faculty Block 5", 3, 8));
		
		
		//5 items
		itemList.add(new Item("Windows 7", 562.00, 1));
		itemList.add(new Item("Windows 8", 2262.00, 2));
		itemList.add(new Item("GNU Linux", 0.00, 120));
		itemList.add(new Item("Food", 1000.00, 400));
		itemList.add(new Item("Lodging", 23.00, 100));
		
		
	}
	
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void verifyDatabase() throws Exception{
		
	}
	
	
	@Test
	public void saveAndRetrieve() throws Exception {
		
	}
	

}
