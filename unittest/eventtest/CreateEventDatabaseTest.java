package unittest.eventtest;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.junit.Test;

import event.*;

public class CreateEventDatabaseTest {
	
	private Eventitem currentEvent;
	private Eventitem[] currentEvents;
	private ModelEvent model;
	private static final int TOTAL = 5;
//	private Vector<Eventitem> eventList;
//	private ViewMain eMan;

	public CreateEventDatabaseTest() {
		String inputName = "School Orientation Camp";
		String inputDescription = "This is a orientation event for NUS students for the 2012/2013 intake.";
		int inputStartYear = 2012;
		int inputEndYear = 2012;
		int inputStartMonth = 8;
		int inputEndMonth = 8;
		int inputStartDay = 1;
		int inputEndDay = 7;
		int inputStartHour = 10;
		int inputStartMinute = 0;
		int inputEndHour = 23;
		int inputEndMinute = 0;
		
		currentEvent = new Eventitem(inputName, inputStartYear, inputStartMonth, inputStartDay, inputEndYear, inputEndMonth, inputEndDay, 
				inputStartHour, inputStartMinute, inputEndHour, inputEndMinute, inputDescription);
		
		currentEvents = new Eventitem[TOTAL];
		
		for (int i=0; i<TOTAL; i++) {
			inputName = "School Orientation Camp " + i ;
			inputDescription = "This is a orientation event for NUS students for the 2012/2013 intake. Test " + i;
			inputStartDay = 1 + i;
			inputEndDay = 7 + i;
			inputStartHour = 10;
			inputStartMinute = 0 + i;
			inputEndHour = 23;
			inputEndMinute = 0 + i;
			
			currentEvents[i] =  new Eventitem(inputName, inputStartYear, inputStartMonth, inputStartDay, inputEndYear, inputEndMonth, inputEndDay, 
					inputStartHour, inputStartMinute, inputEndHour, inputEndMinute, inputDescription);
		}
		
		model = new ModelEvent();
		}
	
	/**
	 * Description: This function will test the storing and retrieving of event items in the database. 
	 */
	@Test
	public void StoreNewEventDatabaseTest() {
	
		Vector<Eventitem> expectedEventList =  new Vector<Eventitem>(ModelEvent.PullList());
		
		ModelEvent.CreateEvent(currentEvent);
		Vector<Eventitem> actualEventList = ModelEvent.PullList();
		
		expectedEventList.add(currentEvent);
		
		boolean sameResult = true;
		System.out.println(expectedEventList.size());
		System.out.println(actualEventList.size());
		if (expectedEventList.size() != actualEventList.size())
			sameResult = false;
		else { 
			for (int i=0; i<expectedEventList.size(); i++) {
				if (actualEventList.get(i).getName().compareTo(expectedEventList.get(i).getName()) != 0)
					sameResult = false;
			}
		}
	
	/*	boolean sameResult = true;
		if (!actualEventList.get(0).equals(currentEvent))
			sameResult = false;*/
		assertEquals(true, sameResult);
			
	}

	/**
	 * Description: This function will test the deletion of event items in the database.
	 * This test makes the following assumptions: The program stores event items into the database correctly. 
	 */
	@Test
	public void DeleteEventDatabaseTest() {
		ModelEvent.CreateEvent(currentEvent);	//adds current event into database
		Vector<Eventitem> expectedEventList =  new Vector<Eventitem>(ModelEvent.PullList());	//retreives the list of events from database
		ModelEvent.DeleteEvent(currentEvent);	//deletes event from database
		Vector<Eventitem> actualEventList = ModelEvent.PullList();	//retreives the list of events from database
		
		expectedEventList.remove(currentEvent);	//remove event from list
		
		boolean sameResult = true;
		for (int i=0; i<expectedEventList.size(); i++) {
			if (!actualEventList.get(i).equals(expectedEventList.get(i)))
				sameResult = false;
		}
		assertEquals(true, sameResult);
			
	}
	
	/**
	 * Description: This function will test the Archiving of expired events in the database. It will check whether the expired event is removed
	 * from event list in the database and stored in the expired list in the database.
	 */
	@Test
	public void StoreNewExpiredEventDatabaseTest() {
		
		//Setting the date values to the past
		currentEvent.setStartDate(2011, 12, 12);
		currentEvent.setEndDate(2011, 12, 15);
		currentEvent.setIsExpired(true);
		
		Vector<Eventitem> expectedEventList =  new Vector<Eventitem>(ModelEvent.PullList());	//retreives the list of events from database
		Vector<Eventitem> expectedExpiredEventList =  new Vector<Eventitem>(ModelEvent.PullList());	//retreives the list of expired events from database
		ModelEvent.CreateEvent(currentEvent);	//adds current event into database
		List<Eventitem> expiredSubList = new Vector<Eventitem>();	//creates an expired sublist to pass into UpdateExpiredList function
		expiredSubList.add(currentEvent);
		ModelEvent.UpdateExpiredList(expiredSubList);	//prompts the database to remove expired event from database event list to database expired event list
		
		Vector<Eventitem> actualEventList = ModelEvent.PullList();	//retreives the list of events from database
		Vector<Eventitem> actualExpiredEventList = ModelEvent.PullList();	//retreives the list of events from database
		
		boolean sameResult = true;

		for (int i=0; i<expectedEventList.size(); i++) {
			if (actualEventList.get(i).getName().compareTo(expectedEventList.get(i).getName()) !=0 ||
					actualEventList.get(i).getStartDateTime().compareTo(expectedEventList.get(i).getStartDateTime()) != 0 ||
					actualEventList.get(i).getEndDateTime().compareTo(expectedEventList.get(i).getEndDateTime()) != 0 ||
					actualEventList.get(i).getDescription().compareTo(expectedEventList.get(i).getDescription()) != 0 )

				sameResult = false;
		}
		
		if (sameResult) {
			for (int i=0; i<expectedExpiredEventList.size(); i++) {
				if (actualExpiredEventList.get(i).getName().compareTo(expectedExpiredEventList.get(i).getName()) !=0 ||
						actualExpiredEventList.get(i).getStartDateTime().compareTo(expectedExpiredEventList.get(i).getStartDateTime()) != 0 ||
						actualExpiredEventList.get(i).getEndDateTime().compareTo(expectedExpiredEventList.get(i).getEndDateTime()) != 0 ||
						actualExpiredEventList.get(i).getDescription().compareTo(expectedExpiredEventList.get(i).getDescription()) != 0 )

					sameResult = false;
			}
		}
		
		assertEquals(true, sameResult);
			
	}
	
	/**
	 * Description: This function will test the 
	 */
	@Test
	public void DeleteExpiredEventDatabaseTest() {
		
		//Setting the date values to the past
		for (int i=0; i<TOTAL; i++) {
			currentEvents[i].setStartDate(2011, currentEvents[i].getStartDateTime().getMonth(), currentEvents[i].getStartDateTime().getDay());
			currentEvent.setEndDate(2011, 12, 15);
			currentEvents[i].setIsExpired(true);
		}
		Vector<Eventitem> expectedExpiredEventList =  new Vector<Eventitem>(ModelEvent.PullList());	//retreives the list of expired events from database
		ModelEvent.CreateEvent(currentEvent);	//adds current event into database
		List<Eventitem> expiredSubList = new Vector<Eventitem>();	//creates an expired sublist to pass into UpdateExpiredList function
		expiredSubList.add(currentEvent);
		ModelEvent.UpdateExpiredList(expiredSubList);	//prompts the database to remove expired event from database event list to database expired event list
		
		
		ModelEvent.DeleteEvent(currentEvent);
		Vector<Eventitem> actualExpiredEventList = ModelEvent.PullList();	//retreives the list of events from database
		
		boolean sameResult = true;
		if (actualExpiredEventList.size() != expectedExpiredEventList.size())
			sameResult = false;
		
		else { 
			
			for (int i=0; i<expectedExpiredEventList.size(); i++) {
				if (actualExpiredEventList.get(i).getName().compareTo(expectedExpiredEventList.get(i).getName()) !=0 ||
						actualExpiredEventList.get(i).getStartDateTime().compareTo(expectedExpiredEventList.get(i).getStartDateTime()) != 0 ||
						actualExpiredEventList.get(i).getEndDateTime().compareTo(expectedExpiredEventList.get(i).getEndDateTime()) != 0 ||
						actualExpiredEventList.get(i).getDescription().compareTo(expectedExpiredEventList.get(i).getDescription()) != 0 )
	
					sameResult = false;
			}
		}
		
		assertEquals(true, sameResult);
			
	}	
}
