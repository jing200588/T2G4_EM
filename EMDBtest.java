import java.util.Scanner;
import java.util.Vector;

/*
 * 
 * Unit Test Suite for EMDB SQLite wrapper.
 * 
 */



public class EMDBtest {

	public static void main(String[] args) {
		EMDB db = new EMDB();

		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter DB name (d for default):  ");
		String option = sc.next();
		
		if (option.compareTo("d") == 0)
			db.set_name("test.sqlite");
		else
			db.set_name(option);
		
		//Connect to DB
		
		db.out("\n\n\n\n*****************************************\n*\n* Test Start\n*\n*****************************************");
		

		
		db.out("\n\nConnect DB\n--------------------------------------------");
		db.connect();
		
		db.out("\n\nInit DB\n--------------------------------------------");
		int status = 0;
		db.init();
		db.out(""+ db.verify_table_count());
		status = db.verify_table_count();
		if (status == 1)
			db.out("Database init - OK");
		else
			db.out("Database init - NOT OK");
		
		
		if (status == 1){
			/*
			 * EVENT VARS
			 */
			int id;
			int eid;
			Eventitem item;
			Vector<Item> list;
			
			
			db.out("\n\nAdding Events\n--------------------------------------------");
			id = db.add_event("test", "", "7-2-2012", "7-2-2012", "18:2", "18:2");
			eid = db.add_event("test", "", "5-2-2012", "7-6-2012", "18:2", "18:2");
			db.add_event("test", "", "7-2-2012", "7-2-2012", "18:2", "18:2");
			db.add_event("test", "", "5-2-2012", "7-6-2012", "18:2", "18:2");
			db.add_event("test", "", "7-2-2012", "7-2-2012", "18:2", "18:2");
			db.add_event("test", "", "5-2-2012", "7-6-2012", "18:2", "18:2");
			
			db.out("\n\nGetting Events\n--------------------------------------------");
			item = db.get_event(id);
			db.out(item.toString());
			
			
			/*
			 * BUDGET
			 */
			db.out("\n\nAdding Budget\n--------------------------------------------");
			db.add_budget(id, "1_Apple_Ipad",  600, 7, null);
			db.add_budget(id, "2_ACER_NETBOOK",  13008, 10, null);
			db.add_budget(id, "3_Nikon_Cam",  6345, 9, null);
	
	
			db.out("\n\nRetrieving Budget (Normal)\n--------------------------------------------");
			list = db.get_budget_list(id, false);
			db.out(list.toString());
			
			db.out("\n\nRetrieving Budget (Optimized)\n--------------------------------------------");
			list = db.get_budget_list(id, true);
			db.out(list.toString());
			
			db.out("\n\nDeleting list of budget for " + id + "\n--------------------------------------------");
			db.delete_budget_list(id);
			
	
			db.out("\n\nRetrieving Budget\n--------------------------------------------");
			list = db.get_budget_list(id, false);
			db.out(list.toString());
			
			
			db.out("\n\nAdding NEW Budget\n--------------------------------------------");
			db.add_budget(id, "4_Monster_Earpiece",  647, 7, null);
			db.add_budget(id, "5_OCZ_120GB_SSD",  32088, 8, null);
			db.add_budget(id, "6_Canon_DSLR", 100050, 11, null);
			
			
			db.out("Delete Event "+id+"\n--------------------------------------------");
			db.delete_event(id);
			
			db.out("\n\nRetrieving Budget after deleting event (Empty Case)\n--------------------------------------------");
			list = db.get_budget_list(id, false);
			
			try {
				db.out(list.toString());
			}catch (Exception e){
				e.printStackTrace();
			}
			
			
			db.out("\n\nGet Deleted Event "+id+" (Empty Case)\n--------------------------------------------");
			item = db.get_event(id);
	
			try{
				db.out(item.toString());
			}catch (Exception e){
				e.printStackTrace();
			}
	
			if (item == null){
				db.out("event object is null");
			}
			
			
			
			
			/*
			 * VENUE VARS
			 */
			
			int vid;
			Venue place;
			
			db.out("\n\nAdd Venue\n--------------------------------------------");
			vid = db.add_venue("COM1", "TEST1", "HAIZ", 11, 10000);
			db.add_venue("COM2", "TEST2", "HAIZ", 12, 10000);		
			db.add_venue("COM3", "TEST3", "HAIZ", 13, 10000);
			db.add_venue("COM4", "TEST4", "HAIZ", 14, 10000);
			db.add_venue("COM5", "TEST5", "HAIZ", 15, 10000);
			
			
			
			db.out("\n\nGet Venue "+vid+"\n--------------------------------------------");
			place = db.get_venue(vid);
			db.out(place.getName());
	
			
			
			
			db.out("\n\nAdd Bookings "+vid+"\n--------------------------------------------");
			int bid; 
			Vector<TimeSlot> bookings; 
			
			
			
			bid = db.add_booking(eid, vid, "10/12/2100/11", "10/12/2100/12");
			db.out("bid:" + bid);
			db.out("bid:" + db.add_booking(eid, vid, "10/12/2100/12", "10/12/2100/13"));
			db.out("bid:" + db.add_booking(eid, vid, "10/12/2100/13", "10/12/2100/14"));
			db.out("bid:" + db.add_booking(eid, vid, "10/12/2100/14", "10/12/2100/15"));
			db.out("bid:" + db.add_booking(eid, vid, "10/12/2100/15", "10/12/2100/16"));
			
			
			
			db.out("\n\nGet Bookings "+vid+"\n--------------------------------------------");
			bookings = db.get_bookings(vid, "venue");
			for (int i = 0; i < bookings.size(); i++){
				TimeSlot timing = bookings.get(i);
				db.out( timing.toString()
						+ timing.getBookingID()
						+ ":" 
						+ timing.getStartDateHour().getDateHourRepresentation()
						+ "-" 
						+ timing.getEndDateHour().getDateHourRepresentation()
						);
			}
			
			
			db.out("\n\nDelete one Booking "+bid+"\n--------------------------------------------");
			db.delete_booking(bid);
			
			db.out("\n\nGet Bookings Again"+vid+"\n--------------------------------------------");
			bookings = db.get_bookings(vid, "venue");
			for (int i = 0; i < bookings.size(); i++){
				TimeSlot timing = bookings.get(i);
				db.out( timing.toString()
						+ timing.getBookingID()
						+ ":" 
						+ timing.getStartDateHour().getDateHourRepresentation() 
						+ "-" 
						+ timing.getEndDateHour().getDateHourRepresentation()
						);
			}	
			
		
		
		
		} //end section for DB OK
		
		//Disconnect from DB
		db.disconnect();
		
		db.out("\n\n\n\n*****************************************\n*\nTest End\n*\n*****************************************");
	}

}
