import java.util.*;


public class MainModel {
	private static Vector<Eventitem> list;

	private static EMDB db;

	public MainModel() {
		list = new Vector<Eventitem>();


		db = new EMDB();
		db.set_name("test.sqlite");
		
		
		
		
		/*
		 * Dummy content.
		 */
		db.connect();
		//this.PullList();
		db.disconnect();
		
		
		list.add(new Eventitem("test event 1", 2012, 11, 13, 2012, 11, 15, 10, 30, 23, 0));
		list.add(new Eventitem("test event 2", 2012, 11, 13, 2012, 11, 15, 10, 30, 23, 0));
		list.add(new Eventitem("test event 3", 2012, 11, 13, 2012, 11, 15, 10, 30, 23, 0));
		list.add(new Eventitem("test event 4", 2012, 11, 13, 2012, 11, 15, 10, 30, 23, 0));
		list.add(new Eventitem("test event 5", 2012, 11, 13, 2012, 11, 15, 10, 30, 23, 0));	
		
		
		
	};
	
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
				eitem.getStartDate(), 
				eitem.getEndDate(),
				eitem.getStartTime(),
				eitem.getEndTime());
		
		
		if (id > 0){
			eitem.setID(id);
			list.add(eitem);
		}
		db.disconnect();
		
	
	}
	
	public static Vector<Eventitem> PullList() {
		db.connect();
		//list = db.get_event_list();
		db.disconnect();
		return list;
	}
	
	public static void DeleteEvent(Eventitem eitem) {
		db.connect();
		db.delete_event(eitem.getID());
		db.disconnect();
		
		list.remove(eitem);
	}
	
	public static void 	UpdateParticulars(Eventitem eitem, int index) {
		//update particulars
		list.remove(index);
		list.add(index, eitem);
		
	}
}
