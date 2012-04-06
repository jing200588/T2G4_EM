package participant;

import event.*;
import emdb.*;


public class ModelParticipantList {
	
	private static EMDBII db;
	
	public ModelParticipantList() {
		db = new EMDBII();
	}
	
	public ModelParticipantList(String test) {
		
		db = new EMDBII(test);
		db.systemCheck();
	}
	
	public static void UpdateDB (EventItem eitem) {
		db.participantDB().deleteParticipantList(eitem.getID());
		db.participantDB().addParticipantList(eitem.getParticipantList(), eitem.getID());
	}
	
	public static void PullParticipantList (EventItem eitem) {
		eitem.setParticipantList(db.participantDB().getParticipantList(eitem.getID()));
	}
}
