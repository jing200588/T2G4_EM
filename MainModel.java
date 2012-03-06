import java.util.*;


public class MainModel {
	private static Vector<Eventitem> list;

	public MainModel() {
		list = new Vector<Eventitem>();
		
		/*
		 * Dummy content.
		 */
		list.add(new Eventitem("test event 1", 2012, 11, 13, 2012, 11, 15, 10, 30, 23, 0));
		list.add(new Eventitem("test event 2", 2012, 11, 13, 2012, 11, 15, 10, 30, 23, 0));
		list.add(new Eventitem("test event 3", 2012, 11, 13, 2012, 11, 15, 10, 30, 23, 0));
		list.add(new Eventitem("test event 4", 2012, 11, 13, 2012, 11, 15, 10, 30, 23, 0));
		list.add(new Eventitem("test event 5", 2012, 11, 13, 2012, 11, 15, 10, 30, 23, 0));		
	};
	
	public static void CreateEvent(Eventitem eitem) {
		//Event object will be passed here to send to db
		list.add(eitem);
	}
	
	public static Vector<Eventitem> PullList() {
		//query for entire list of events
		return list;
	}
	
	public static void DeleteEvent(Eventitem eitem) {
		//delete item from db
		list.remove(eitem);
	}
	
}
