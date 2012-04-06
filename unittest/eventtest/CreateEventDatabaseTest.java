package unittest.eventtest;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.junit.Test;

import event.*;

public class CreateEventDatabaseTest {
	
	private Eventitem currentEvent;
	private Eventitem[] currentEvents;
	private ModelEvent model;
	private static final int TOTAL = 5;

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
		
		//model = new ModelEvent();
		model = new ModelEvent("unit.sqlite");
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
		
		
		assertEquals(expectedEventList.size(), actualEventList.size());
		
		for (int i=0; i<expectedEventList.size(); i++) {
			assertEquals(expectedEventList.get(i).getName(), actualEventList.get(i).getName());
			assertEquals(expectedEventList.get(i).getStartDateTime().getTimeRepresentation(), actualEventList.get(i).getStartDateTime().getTimeRepresentation());
			assertEquals(expectedEventList.get(i).getStartDateTime().getDateRepresentation(), actualEventList.get(i).getStartDateTime().getDateRepresentation());
			assertEquals(expectedEventList.get(i).getEndDateTime().getTimeRepresentation(), actualEventList.get(i).getEndDateTime().getTimeRepresentation());
			assertEquals(expectedEventList.get(i).getEndDateTime().getDateRepresentation(), actualEventList.get(i).getEndDateTime().getDateRepresentation());
			assertEquals(expectedEventList.get(i).getDescription(), actualEventList.get(i).getDescription());
			assertEquals(false, actualEventList.get(i).isExpired());
		}			
	}

	/**
	 * Description: This function will test the editing of particulars of event items that have already existed in the database. 
	 */
	@Test
	public void EditEventDatabaseTest() {
		
		ModelEvent.CreateEvent(currentEvent);
		Vector<Eventitem> expectedEventList =  new Vector<Eventitem>(ModelEvent.PullList());

		
		Eventitem tempEvent = expectedEventList.get(expectedEventList.size()-1);
		tempEvent.setName("Test Edit Name");
		tempEvent.setStartDate(2012, 5, 1);
		tempEvent.setEndDate(2012, 5, 20);
		tempEvent.setStartTime(9, 30);
		tempEvent.setEndTime(22, 30);
		tempEvent.setDescription("This is a edit description test!");

		ModelEvent.UpdateParticulars(tempEvent);
		Vector<Eventitem> actualEventList = ModelEvent.PullList();
		
		assertEquals(expectedEventList.size(), actualEventList.size());
		
		for (int i=0; i<expectedEventList.size(); i++) {
			assertEquals(expectedEventList.get(i).getName(), actualEventList.get(i).getName());
			assertEquals(expectedEventList.get(i).getStartDateTime().getTimeRepresentation(), actualEventList.get(i).getStartDateTime().getTimeRepresentation());
			assertEquals(expectedEventList.get(i).getStartDateTime().getDateRepresentation(), actualEventList.get(i).getStartDateTime().getDateRepresentation());
			assertEquals(expectedEventList.get(i).getEndDateTime().getTimeRepresentation(), actualEventList.get(i).getEndDateTime().getTimeRepresentation());
			assertEquals(expectedEventList.get(i).getEndDateTime().getDateRepresentation(), actualEventList.get(i).getEndDateTime().getDateRepresentation());
			assertEquals(expectedEventList.get(i).getDescription(), actualEventList.get(i).getDescription());
			assertEquals(expectedEventList.get(i).getID(), actualEventList.get(i).getID());
		}

	}
	
	/**
	 * Description: This function will test the deletion of event items in the database.
	 * This test makes the following assumptions: The program stores event items into the database correctly. 
	 */
	@Test
	public void DeleteEventDatabaseTest() {
		ModelEvent.CreateEvent(currentEvent);	//adds current event into database
		Vector<Eventitem> expectedEventList =  new Vector<Eventitem>(ModelEvent.PullList());	//retreives the list of events from database
		currentEvent.setID(expectedEventList.get(expectedEventList.size()-1).getID());	//store the ID into current event
		
		ModelEvent.DeleteEvent(currentEvent);	//deletes event from database
		Vector<Eventitem> actualEventList = ModelEvent.PullList();	//retreives the list of events from database
		
		expectedEventList.remove(expectedEventList.size()-1);	//remove event from local list
		
		assertEquals(expectedEventList.size(), actualEventList.size());
		
		for (int i=0; i<expectedEventList.size(); i++) {
			assertEquals(expectedEventList.get(i).getName(), actualEventList.get(i).getName());
			assertEquals(expectedEventList.get(i).getStartDateTime().getTimeRepresentation(), actualEventList.get(i).getStartDateTime().getTimeRepresentation());
			assertEquals(expectedEventList.get(i).getStartDateTime().getDateRepresentation(), actualEventList.get(i).getStartDateTime().getDateRepresentation());
			assertEquals(expectedEventList.get(i).getEndDateTime().getTimeRepresentation(), actualEventList.get(i).getEndDateTime().getTimeRepresentation());
			assertEquals(expectedEventList.get(i).getEndDateTime().getDateRepresentation(), actualEventList.get(i).getEndDateTime().getDateRepresentation());
			assertEquals(expectedEventList.get(i).getDescription(), actualEventList.get(i).getDescription());
			assertEquals(expectedEventList.get(i).getID(), actualEventList.get(i).getID());
			assertEquals(false, actualEventList.get(i).isExpired());
		}

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
		Vector<Eventitem> expectedExpiredEventList =  new Vector<Eventitem>(ModelEvent.PullExpiredList());	//retreives the list of expired events from database
		ModelEvent.CreateEvent(currentEvent);	//adds current event into database
		Vector<Eventitem> eventList = new Vector<Eventitem>(ModelEvent.PullList());
		currentEvent = eventList.get(eventList.size()-1);
		
		//creates an expired sublist to pass into UpdateExpiredList function
		List<Eventitem> expiredSubList = new Vector<Eventitem>();
		expiredSubList.add(currentEvent);
		
		expectedExpiredEventList.add(currentEvent);		//adds the current event into the expected expired list
		ModelEvent.UpdateExpiredList(expiredSubList);	//prompts the database to remove expired event from database event list to database expired event list
		
		Vector<Eventitem> actualEventList = ModelEvent.PullList();	//retreives the list of events from database
		Vector<Eventitem> actualExpiredEventList = ModelEvent.PullExpiredList();	//retreives the list of events from database
		
		
		assertEquals(expectedEventList.size(), actualEventList.size());
		
		for (int i=0; i<expectedEventList.size(); i++) {
			assertEquals(expectedEventList.get(i).getName(), actualEventList.get(i).getName());
			assertEquals(expectedEventList.get(i).getStartDateTime().getTimeRepresentation(), actualEventList.get(i).getStartDateTime().getTimeRepresentation());
			assertEquals(expectedEventList.get(i).getStartDateTime().getDateRepresentation(), actualEventList.get(i).getStartDateTime().getDateRepresentation());
			assertEquals(expectedEventList.get(i).getEndDateTime().getTimeRepresentation(), actualEventList.get(i).getEndDateTime().getTimeRepresentation());
			assertEquals(expectedEventList.get(i).getEndDateTime().getDateRepresentation(), actualEventList.get(i).getEndDateTime().getDateRepresentation());
			assertEquals(expectedEventList.get(i).getDescription(), actualEventList.get(i).getDescription());
			assertEquals(expectedEventList.get(i).getID(), actualEventList.get(i).getID());
			assertEquals(false, actualEventList.get(i).isExpired());
		}		
		
		assertEquals(expectedExpiredEventList.size(), actualExpiredEventList.size());
		
		for (int i=0; i<expectedExpiredEventList.size(); i++) {
			assertEquals(expectedExpiredEventList.get(i).getName(), actualExpiredEventList.get(i).getName());
			assertEquals(expectedExpiredEventList.get(i).getStartDateTime().getTimeRepresentation(), actualExpiredEventList.get(i).getStartDateTime().getTimeRepresentation());
			assertEquals(expectedExpiredEventList.get(i).getStartDateTime().getDateRepresentation(), actualExpiredEventList.get(i).getStartDateTime().getDateRepresentation());
			assertEquals(expectedExpiredEventList.get(i).getEndDateTime().getTimeRepresentation(), actualExpiredEventList.get(i).getEndDateTime().getTimeRepresentation());
			assertEquals(expectedExpiredEventList.get(i).getEndDateTime().getDateRepresentation(), actualExpiredEventList.get(i).getEndDateTime().getDateRepresentation());
			assertEquals(expectedExpiredEventList.get(i).getDescription(), actualExpiredEventList.get(i).getDescription());
			assertEquals(expectedExpiredEventList.get(i).getID(), actualExpiredEventList.get(i).getID());
			assertEquals(true, actualExpiredEventList.get(i).isExpired());
		}
	}
	
	/**
	 * Description: This function will test the deletion of any expired event in the expired list on the database.
	 */
	@Test
	public void DeleteExpiredEventDatabaseTest() {
		
		Random random = new Random();
		int randNo = random.nextInt(TOTAL);
		Vector<Eventitem> expectedExpiredEventList =  new Vector<Eventitem>(ModelEvent.PullExpiredList());	//retreives the list of expired events from database
		List<Eventitem> expiredSubList = new Vector<Eventitem>();	//creates an expired sublist to pass into UpdateExpiredList function
		
		for (int i=0; i<TOTAL; i++) {
			//Setting the date values to the past
			currentEvents[i].setStartDate(2011, currentEvents[i].getStartDateTime().getMonth(), currentEvents[i].getStartDateTime().getDay());
			currentEvents[i].setEndDate(2011, currentEvents[i].getEndDateTime().getMonth(), currentEvents[i].getEndDateTime().getDay());
			
			currentEvents[i].setIsExpired(true);
			
			ModelEvent.CreateEvent(currentEvents[i]);	//adds events into database
		}
		
		Vector<Eventitem> eventList = new Vector<Eventitem>(ModelEvent.PullList());
		expiredSubList = eventList.subList(eventList.size() - TOTAL, eventList.size());
		
		for (int i=0; i<TOTAL; i++) {
			currentEvents[i].setID(expiredSubList.get(i).getID());
			expectedExpiredEventList.add(currentEvents[i]);	//adds events into expected list
		}
		
		ModelEvent.UpdateExpiredList(expiredSubList);	//prompts the database to remove expired event from database event list to database expired event list
		
		expectedExpiredEventList.remove(currentEvents[randNo]);	//removes the randomly chosen event from the expected list
		ModelEvent.DeleteExpiredEvent(currentEvents[randNo]);	//removes the randomly chosen event from the database expired list
		Vector<Eventitem> actualExpiredEventList = ModelEvent.PullExpiredList();	//retreives the list of events from database

		assertEquals(expectedExpiredEventList.size(), actualExpiredEventList.size());
		
		for (int i=0; i<expectedExpiredEventList.size(); i++) {
			assertEquals(expectedExpiredEventList.get(i).getName(), actualExpiredEventList.get(i).getName());
			assertEquals(expectedExpiredEventList.get(i).getStartDateTime().getTimeRepresentation(), actualExpiredEventList.get(i).getStartDateTime().getTimeRepresentation());
			assertEquals(expectedExpiredEventList.get(i).getStartDateTime().getDateRepresentation(), actualExpiredEventList.get(i).getStartDateTime().getDateRepresentation());
			assertEquals(expectedExpiredEventList.get(i).getEndDateTime().getTimeRepresentation(), actualExpiredEventList.get(i).getEndDateTime().getTimeRepresentation());
			assertEquals(expectedExpiredEventList.get(i).getEndDateTime().getDateRepresentation(), actualExpiredEventList.get(i).getEndDateTime().getDateRepresentation());
			assertEquals(expectedExpiredEventList.get(i).getDescription(), actualExpiredEventList.get(i).getDescription());
			assertEquals(expectedExpiredEventList.get(i).getID(), actualExpiredEventList.get(i).getID());
			assertEquals(true, actualExpiredEventList.get(i).isExpired());
		}
			
	}	
	
	
	/**
	 * Description: This function will test the deletion of all the expired events in the expired list on the database. 
	 */
	@Test
	public void DeleteAllExpiredEventDatabaseTest() {
		
		for (int i=0; i<TOTAL; i++) {
			//Setting the date values to the past
			currentEvents[i].setStartDate(2011, currentEvents[i].getStartDateTime().getMonth(), currentEvents[i].getStartDateTime().getDay());
			currentEvents[i].setEndDate(2011, currentEvents[i].getEndDateTime().getMonth(), currentEvents[i].getEndDateTime().getDay());
			
			currentEvents[i].setIsExpired(true);
			
			ModelEvent.CreateEvent(currentEvents[i]);	//adds events into database
		}
		
		//Sets up the scenerio where all the created expired events are now in the expired list of the database
		Vector<Eventitem> eventList = new Vector<Eventitem>(ModelEvent.PullList());		//pull event list from database
		List<Eventitem> expiredSubList = eventList.subList(eventList.size() - TOTAL, eventList.size());	//obtain sublist to pass into UpdateExpiredList
		ModelEvent.UpdateExpiredList(expiredSubList);	//prompts the database to remove expired event from database event list to database expired event list
		
		
		ModelEvent.DeleteAllExpiredEvents();	//prompts the database to delete the entire list of expired events
		Vector<Eventitem> actualExpiredEventList = ModelEvent.PullExpiredList();	//retreives the list of events from database

		assertEquals(0, actualExpiredEventList.size());
		
	}	





}
