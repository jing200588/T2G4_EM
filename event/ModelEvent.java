package event;

import emdb.*;
import program.*;

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
			list.get(i).setItemList(db.budgetDB().getBudgetListOptimized(pid));
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
	
	/************************************************************
	 * DELETE EVENT	//Deletes expired event from DB and expired list.
	 ***********************************************************/
	/**
	 * Description: Deletes the expired event from the database and expired list
	 * @param eitem Event Item
	 */
	public static void DeleteExpiredEvent(Eventitem eitem) {

		//TODO Remove the expired item from archive DB
		expired.remove(eitem);
	}
	
	/************************************************************
	 * DELETE ALL EXPIRED EVENTS
	 ***********************************************************/
	/**
	 * Description: Removes all expired events in the DB and local expired list
	 * @param eitem Event Item
	 */
	public static void DeleteAllExpiredEvents() {

		//TODO DROP EXPIRED TABLE FROM DB
		expired.clear();
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
				EventFlowEntry.getStringRepresentation(eitem.getEventFlow())
				);
		
	}
	
	public static void UpdateExpiredList(List<Eventitem> newlyexpired) {
		System.out.println("RAN!!");
		System.out.println("size of new" + newlyexpired.size());
		db.eventDB().addAchiveEventList(newlyexpired);
		System.out.println("RAN!!");
	}
}
