import java.util.*;



public class ModelEvent {
	private static Vector<Eventitem> list;
	private static Vector<Eventitem> expired;
	private static EMDBII db;

	public ModelEvent() {
		list = new Vector<Eventitem>();
		expired = new Vector<Eventitem>();
		
		db = new EMDBII();
		
	};
	
	/************************************************************
	 * CREATE EVENT //Passes event to DB and local list.
	 ***********************************************************/
	/**
	 * Description: Passes event into database and local list.
	 * @param eitem Event Item
	 */
	public static void CreateEvent(Eventitem eitem) {
		
		
		int id = db.eventDB().addEvent(
				eitem.getName(), 
				eitem.getDescription(), 
				eitem.getBudget(),
				eitem.getStartDateTime().getDateRepresentation(), 
				eitem.getEndDateTime().getDateRepresentation(),
				eitem.getStartDateTime().getTimeRepresentation(),
				eitem.getEndDateTime().getTimeRepresentation(), 
				"");
		eitem.setID(id);
		
		list.add(eitem);
	
	}
	
	/************************************************************
	 * PULL LIST //Returns local list.
	 ***********************************************************/
	/**
	 * Description: Returns the local list of event items
	 * @return
	 */
	public static Vector<Eventitem> PullList() {
		
		list = db.eventDB().getEventList();
		int size = list.size();
		for (int i=0; i<size; i++){
			
			int pid = list.get(i).getID();
			list.get(i).addBVI(db.venueDB().getEventBookings(pid));	
			list.get(i).setitem_list(db.budgetDB().getBudgetListOptimized(pid));
			list.get(i).setParticipantList(db.participantDB().getParticipantList(pid));
		}
		return list;
	}

	/************************************************************
	 * PULL EXPIRED LIST //Returns local expired list.
	 ***********************************************************/
	/**
	 * Description: Returns the local expired list of event items
	 * @return
	 */
	public static Vector<Eventitem> PullExpiredList() {

		expired = db.eventDB().getArchiveEventList();
		
		return expired;
	}
	
	/************************************************************
	 * DELETE EVENT	//Deletes event from DB and local list.
	 ***********************************************************/
	/**
	 * Description: Deletes the event from the database and local list
	 * @param eitem Event Item
	 */
	public static void DeleteEvent(Eventitem eitem) {

		db.eventDB().deleteEvent(eitem.getID());
		db.venueDB().deleteBookingAll(eitem.getID());
		db.budgetDB().deleteBudgetList(eitem.getID());
		db.budgetDB().deleteBudgetListOptimized(eitem.getID());
		db.participantDB().deleteParticipantList(eitem.getID());
		list.remove(eitem);
	}
	
	/**********************************************************************
	 * UPDATE PARTICULARS //Updates event particulars on DB and local list.
	 **********************************************************************/
	/**
	 * Description: Updates event particulars in the database and local list
	 * @param eitem Event Item
	 * @param index 
	 */
	public static void 	UpdateParticulars(Eventitem eitem) {


		
		db.eventDB().updateEvent(
				eitem.getID(), 
				eitem.getName(), 
				eitem.getDescription(), 
				eitem.getBudget(),
				eitem.getStartDateTime().getDateRepresentation(),
				eitem.getEndDateTime().getDateRepresentation(), 
				eitem.getStartDateTime().getTimeRepresentation(), 
				eitem.getEndDateTime().getTimeRepresentation(),
				""
				);
		
	}
	
	public static void UpdateExpiredList(Vector<Eventitem> newlyexpired) {
		System.out.println("RAN!!");
		System.out.println("size of new" + newlyexpired.size());
		db.eventDB().addAchiveEventList(newlyexpired);
		System.out.println("RAN!!");
	}
}
