import java.util.*;


public class ModelEvent {
	private static Vector<Eventitem> list;
	private static EMDB db;

	public ModelEvent() {
		list = new Vector<Eventitem>();


		db = new EMDB();
		
	
		db.set_name(EMSettings.DATABASE_NAME);
		
		
		
		
		/*
		 * Dummy content.
		 */
		db.connect();
		list = db.get_event_list();
		int size = list.size();
		
		for (int i=0; i<size; i++){
			list.get(i).addBVI(db.get_booking_info(list.get(i).getID()));	
			list.get(i).setitem_list(db.get_budget_list(list.get(i).getID(), true));
		}
		
		
		db.disconnect();
		
		/*
		//Test Data.
		list.add(new Eventitem("test event 1", 2012, 11, 13, 2012, 11, 15, 10, 30, 23, 0));
		list.add(new Eventitem("test event 2", 2012, 11, 13, 2012, 11, 15, 10, 30, 23, 0));
		list.add(new Eventitem("test event 3", 2012, 11, 13, 2012, 11, 15, 10, 30, 23, 0));
		list.add(new Eventitem("test event 4", 2012, 11, 13, 2012, 11, 15, 10, 30, 23, 0));
		list.add(new Eventitem("test event 5", 2012, 11, 13, 2012, 11, 15, 10, 30, 23, 0));	
		*/
		
		
	};
	
	/************************************************************
	 * CREATE EVENT //Passes event to DB and local list.
	 ***********************************************************/
	/**
	 * Description: Passes event into database and local list.
	 * @param eitem Event Item
	 */
	public static void CreateEvent(Eventitem eitem) {
		
		
		db.out(eitem.getName());
		db.out(eitem.getDescription());
		db.out(eitem.getStartDate()); 
		db.out(eitem.getEndDate());
		db.out(eitem.getStartTime());
		db.out(eitem.getEndTime());
		
		db.connect();
		int id = db.add_event(eitem.getName(), 
				"", 
				eitem.getBudget(),
				eitem.getStartDate(), 
				eitem.getEndDate(),
				eitem.getStartTime(),
				eitem.getEndTime());
		//db.out("Create: " + id);
		eitem.setID(id);
		list.add(eitem);
		
		db.disconnect();
		
		//db.out("Returned: " + eitem.getID());
	
	}
	
	/************************************************************
	 * PULL LIST //Returns local list.
	 ***********************************************************/
	/**
	 * Description: Returns the local list of event items
	 * @return
	 */
	public static Vector<Eventitem> PullList() {
		db.connect();
		list = db.get_event_list();
		
		int size = list.size();
		for (int i=0; i<size; i++){
			list.get(i).addBVI(db.get_booking_info(list.get(i).getID()));	
			list.get(i).setitem_list(db.get_budget_list(list.get(i).getID(), true));
		}
		
		
		
		db.disconnect();
		return list;
	}
	
	/************************************************************
	 * DELETE EVENT	//Deletes event from DB and local list.
	 ***********************************************************/
	/**
	 * Description: Deletes the event from the database and local list
	 * @param eitem Event Item
	 */
	public static void DeleteEvent(Eventitem eitem) {
		
		db.connect();
		//db.out("Passing: " + eitem.getID() + "-" + eitem.getName());
		
		db.delete_event(eitem.getID());
		db.disconnect();
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
	public static void 	UpdateParticulars(Eventitem eitem, int index) {
		//update particulars

		list.remove(index);
		list.add(index, eitem);
		
		
		//db.out("testing start");
		db.connect();
		db.update_event(
				eitem.getID(), 
				eitem.getName(), 
				eitem.getDescription(), 
				eitem.getBudget(),
				eitem.getStartDate(),
				eitem.getEndDate(), 
				eitem.getStartTime(), 
				eitem.getEndTime());
		
		db.disconnect();
	//	db.out("index:" + index);
	//	db.out("testing end");
		
		
	}
}
