package participant;

import event.*;
import emdb.*;


public class ModelParticipantList {
	
	private static EMDBII db;
	
	/**
	 * Description: Construct the ModelParticipantList which initializes the default database
	 */
	public ModelParticipantList() {
		db = new EMDBII();
	}
	
	/**
	 * Description: Construct the ModelParticipantList which initializes the test database
	 * @param test	- Name for test database
	 */
	public ModelParticipantList(String test) {
		
		db = new EMDBII(test);
		db.systemCheck();
	}
	
	/**
	 * Description: Updates the database with the latest participant list in the event item
	 * @param eItem	- event item with updated participant list
	 */
	public static void UpdateDB (EventItem eItem) {
		db.participantDB().deleteParticipantList(eItem.getID());
		db.participantDB().addParticipantList(eItem.getParticipantList(), eItem.getID());
	}
	
	/**
	 * Description: Pulls and stores the participant list of the event item that is stored in the database to the event item
	 * @param eItem = event item that is to be updated with the participant list from database
	 */
	public static void PullParticipantList (EventItem eItem) {
		eItem.setParticipantList(db.participantDB().getParticipantList(eItem.getID()));
	}
}
