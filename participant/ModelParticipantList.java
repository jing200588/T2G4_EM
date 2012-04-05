package participant;

import event.*;
import emdb.*;


public class ModelParticipantList {
	
	private static EMDBII db = new EMDBII();;
	
	public ModelParticipantList() {
	
	}
	
	
	
	public static void UpdateDB (Eventitem eitem) {
		db.participantDB().deleteParticipantList(eitem.getID());
		db.participantDB().addParticipantList(eitem.getParticipantList(), eitem.getID());
	}
	
	public static void PullParticipantList (Eventitem eitem) {
		eitem.setParticipantList(db.participantDB().getParticipantList(eitem.getID()));
	}
}