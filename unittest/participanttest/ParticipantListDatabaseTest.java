package unittest.participanttest;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.junit.Test;

import event.Eventitem;
import event.ModelEvent;

import participant.ModelParticipantList;
import participant.Participant;

public class ParticipantListDatabaseTest {
	
	List<Participant> participantList;
	Eventitem currentEvent;
	ModelEvent model;
	ModelParticipantList modelPL;
	
	public ParticipantListDatabaseTest() {
		model = new ModelEvent();
		modelPL = new ModelParticipantList();
		//ModelParticipantList.startTestDatabase("unit.sqlite");
		currentEvent = CreateCurrentEvent();
	}
	
	/**
	 * Description: This function test the updating and retrievng of participant list in the database. It updates the list in the event item, passes it into 
	 * the database and retrieves it to compare with the list on hand.
	 */
	@Test
	public void UpdateAndRetrieveParticipantListTest() {
		//Setting up the scenerio  
		participantList = CreateParticipantList(50);
		currentEvent.setParticipantList(participantList);
		ModelEvent.CreateEvent(currentEvent);
		
		List<Participant> expectedParticipantList = CreateParticipantList(100);
		Vector<Eventitem> eventList = ModelEvent.PullList();
		currentEvent = eventList.get(eventList.size() - 1);	//this is done to obtain the id for the event which was assigned by the database
		
		//set new participantList for currentEvent
		currentEvent.setParticipantList(expectedParticipantList);
		ModelParticipantList.UpdateDB(currentEvent);	//updates the list into the database
		
		currentEvent.setParticipantList(participantList);	//set currentEvent's participant list back to the old one.
		
		ModelParticipantList.PullParticipantList(currentEvent);	//pulls the event's participant list from the database
		List<Participant> actualParticipantList = currentEvent.getParticipantList();
		
		
		
		assertEquals(expectedParticipantList.size(), actualParticipantList.size());
		
		for (int i=0; i<expectedParticipantList.size(); i++) {
			assertEquals(expectedParticipantList.get(i).getName(), actualParticipantList.get(i).getName());
			assertEquals(expectedParticipantList.get(i).getMatric(), actualParticipantList.get(i).getMatric());
			assertEquals(expectedParticipantList.get(i).getContact(), actualParticipantList.get(i).getContact());
			assertEquals(expectedParticipantList.get(i).getEmail(), actualParticipantList.get(i).getEmail());
			assertEquals(expectedParticipantList.get(i).getEmail(), actualParticipantList.get(i).getEmail());
			assertEquals(expectedParticipantList.get(i).getAddress(), actualParticipantList.get(i).getAddress());
			assertEquals(expectedParticipantList.get(i).getRemark(), actualParticipantList.get(i).getRemark());
		}
		
	}

	public List<Participant> CreateParticipantList(int quantity) {
		List<Participant> participantList = new Vector<Participant>();
		
		for (int i=0; i<quantity; i++) {
			Random random = new Random();
			int randSevenDigits = random.nextInt(9000000) + 1000000;
			int randEightDigits = random.nextInt(90000000) + 10000000;
			int randSixDigits = random.nextInt(900000) + 100000;
			
			int randEmail = random.nextInt(4);
			String email = "";
			String address = "";
			
			switch (randEmail) {
				case 0:
					address = "Pasir Ris Street";
					email = "google.com";
					break;
				case 1:
					email = "hotmail.com";
					address = "Prince George Park Street";
					break;
				case 2:
					email = "nus.edu.sg";
					address = "Tampines Street";
					break;
				case 3:
					email = "facebook.com";
					address = "Ang Mo Kio Street";
					break;
			}
			
			String name = "Test fellow" + i;
			String matricNo = "A" + randSevenDigits + "Z";
			String contactNo = "" + randEightDigits;
			String emailAdd = "" + "@" + email;
			String homeAddress = "Block " + (randSevenDigits%4000) + " " + address + " " + (randEightDigits%600000) + " S(" + randSixDigits + ")";  
			String remark = "This is a test remark" + i;
			
			Participant fellow = new Participant(name, matricNo, contactNo, emailAdd, homeAddress, remark);
			
			participantList.add(fellow);
		}
		
		return participantList; 
	}
	
	
	public Eventitem CreateCurrentEvent() {
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
		
		Eventitem event = new Eventitem(inputName, inputStartYear, inputStartMonth, inputStartDay, inputEndYear, inputEndMonth, inputEndDay, 
				inputStartHour, inputStartMinute, inputEndHour, inputEndMinute, inputDescription);
		
		return event;
	}
}
