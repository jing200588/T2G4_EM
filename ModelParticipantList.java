
public class ModelParticipantList {
	
	private static EMDBII db = new EMDBII();;
	
	public ModelParticipantList() {
	
	}
	
	
	
	public static void UpdateDB (Eventitem eitem) {
		db.participantDB().deleteParticipantList(eitem.getID());
		db.participantDB().addParticipantList(eitem.getParticipantList(), eitem.getID());
	}
}
