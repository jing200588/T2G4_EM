package event;

import emdb.*;
import program.*;

import java.util.*;



public class ModelEvent {
	private static Vector<EventItem> list;
	private static Vector<EventItem> expired;
	private static EMDBII db;

	/**
	 * Description: Constructor for ModelEvent which initializes the default database
	 */
	public ModelEvent() {
		list = new Vector<EventItem>();
		expired = new Vector<EventItem>();
		
		db = new EMDBII();
		
	};
	
	/**
	 * Description:  Constructor for ModelEvent which initializes the test database
	 * @param test - Name for the test database
	 */
	public ModelEvent(String test) {
		list = new Vector<EventItem>();
		expired = new Vector<EventItem>();
		
		db = new EMDBII(test);
		db.systemCheck();
	};
	
	/************************************************************
	 * CREATE EVENT
	 ***********************************************************/
	/**
	 * Description: Passes event into database and local list.
	 * @param eitem - Event Item
	 */
	public static void CreateEvent(EventItem eitem) {
		
		
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
	 * PULL LIST
	 ***********************************************************/
	/**
	 * Description: Returns the local list of event items
	 * @return - Vector list of event items
	 */
	public static Vector<EventItem> PullList() {
		
		list = db.eventDB().getEventList();
		int size = list.size();
		for (int i=0; i<size; i++){
			
			int pid = list.get(i).getID();
			list.get(i).addBVI(db.venueDB().getEventBookings(pid));	
			list.get(i).setItemList(db.budgetDB().getBudgetListOptimized(pid));
			list.get(i).setParticipantList(db.participantDB().getParticipantList(pid));
		}
		return list;
	}

	/************************************************************
	 * PULL EXPIRED LIST
	 ***********************************************************/
	/**
	 * Description: Returns the local expired list of event items
	 * @return - Vector list of expired event items
	 */
	public static Vector<EventItem> PullExpiredList() {

		expired = db.eventDB().getArchiveEventList();
		int size = expired.size();
		for (int i=0; i<size; i++){
			int pid = expired.get(i).getID();
			expired.get(i).addBVI(db.venueDB().getEventBookings(pid));	
			expired.get(i).setItemList(db.budgetDB().getBudgetListOptimized(pid));
			expired.get(i).setParticipantList(db.participantDB().getParticipantList(pid));
			expired.get(i).setIsExpired(true);
		}
		
		return expired;
	}
	
	/************************************************************
	 * DELETE EVENT	
	 ***********************************************************/
	/**
	 * Description: Deletes the event from the database and local list
	 * @param eItem - Event Item
	 */
	public static void DeleteEvent(EventItem eItem) {

		db.eventDB().deleteEvent(eItem.getID());
		db.venueDB().deleteBookingAll(eItem.getID());
		db.budgetDB().deleteBudgetList(eItem.getID());
		db.budgetDB().deleteBudgetListOptimized(eItem.getID());
		db.participantDB().deleteParticipantList(eItem.getID());
		list.remove(eItem);
	}
	
	/************************************************************
	 * DELETE EXPIRED EVENT	
	 ***********************************************************/
	/**
	 * Description: Deletes the expired event from the database and expired list
	 * @param eItem - Event Item
	 */
	public static void DeleteExpiredEvent(EventItem eItem) {

		//TODO Remove the expired item from archive DB
		db.eventDB().deleteArchiveEvent(eItem.getID());
		db.venueDB().deleteBookingAll(eItem.getID());
		db.budgetDB().deleteBudgetList(eItem.getID());
		db.budgetDB().deleteBudgetListOptimized(eItem.getID());
		db.participantDB().deleteParticipantList(eItem.getID());
		expired.remove(eItem);
	}
	
	/************************************************************
	 * DELETE ALL EXPIRED EVENTS
	 ***********************************************************/
	/**
	 * Description: Removes all expired events in the DB and local expired list
	 * @param eitem Event Item
	 */
	public static void DeleteAllExpiredEvents() {

		Vector<EventItem> dblist = PullExpiredList();
		db.eventDB().truncate("archive");
		for (int i=0; i<dblist.size(); i++){
			int pid = dblist.get(i).getID();
			db.venueDB().deleteBookingAll(pid);
			db.budgetDB().deleteBudgetList(pid);
			db.budgetDB().deleteBudgetListOptimized(pid);
			db.participantDB().deleteParticipantList(pid);
		}
		expired.clear();
	}
	
	/**********************************************************************
	 * UPDATE PARTICULARS 
	 **********************************************************************/
	/**
	 * Description: Updates event particulars in the database and local list
	 * @param eItem - Event Item
	 */
	public static void 	UpdateParticulars(EventItem eItem) {


		
		db.eventDB().updateEvent(
				eItem.getID(), 
				eItem.getName(), 
				eItem.getDescription(), 
				eItem.getBudget(),
				eItem.getStartDateTime().getDateRepresentation(),
				eItem.getEndDateTime().getDateRepresentation(), 
				eItem.getStartDateTime().getTimeRepresentation(), 
				eItem.getEndDateTime().getTimeRepresentation(),
				EventFlowEntry.getStringRepresentation(eItem.getEventFlow())
				);
		
	}
	
	/**
	 * Description: Updates the expired list in the database
	 * @param newlyexpired - list of recently expired event items
	 */
	public static void UpdateExpiredList(List<EventItem> newlyexpired) {
		db.eventDB().addArchiveEventList(newlyexpired);		
	}
	
	/**
	 * Description: Destroys the database (used for testing purposes)
	 */
	public static void DestroyDB() {
		db.destroy(true);
	}
}
