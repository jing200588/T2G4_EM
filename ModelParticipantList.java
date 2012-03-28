
public class ModelParticipantList {
	
	private static EMDBII db;
	
	public ModelParticipantList() {
		db = new EMDBII();
	}
	
	
	
	public static void UpdateDB (Eventitem eitem) {
		db.participantDB().deleteParticipantList(eitem.getID());
		db.participantDB().addParticipantList(eitem.getParticipantList(), eitem.getID());
	}
}
