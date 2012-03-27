

import java.util.Scanner;
import java.util.Vector;




/**
 * Test Suite for EMDB SQLite wrapper as well as the database.
 * 
 * @author JunZhi
 *
 */
public class EMDBTools {

	public static EMDB db = new EMDB();
	public static Scanner sc = new Scanner(System.in);

	
	
	public static void main(String[] args) {
		

		boolean cont;
		int option;
		
		db.out("*****************************************\n");
		db.out("Database Test Suite and Management Tool	");
		db.out(" +EMDB Version 0.1");
		db.out("\n*****************************************\n");	
		
		
		db.out("WARNING: Database may be wiped out..  Backup first before using on a live database.\n");
		
		set_dbname();
		
		db.connect();
		
		cont = check_file();
		
		check_tables(cont);
		
		option = show_options();
		
		while (option != 6){
			
			switch(option){
				case 1:
					System.out.print("Confirm Re-initialization? (y/n) : ");
					String temp1 = sc.next();
					if (temp1.compareTo("Y") == 0 || temp1.compareTo("y") == 0){
						
						db.out("***Re-Init Started***");
						db.init();
						check_tables(true);
	
					}else{
						db.out("***Cancelled***");
					}
					
					break;
					
					
				case 2:
					populate_venue();
					break;
					
					
				case 3:
					populate_sample();
					break;	
					
					
				case 4:
					System.out.print("Confirm Reset? (y/n) : ");
					String temp5 = sc.next();
					
					if (temp5.compareTo("Y") == 0 || temp5.compareTo("y") == 0){
						db.out("***Deleting All Tables***");
						db.reset();
						
					}else{
						db.out("***Cancelled***");
					}
					
					break;
					
					
				case 5:
					db.out("WARNING: Database will be replaced with test data..  Backup first before using on a live database.\n");
					System.out.print("Confirm Running of Test? (y/n) : ");
					String temp6 = sc.next();
					
					if (temp6.compareTo("Y") == 0 || temp6.compareTo("y") == 0){
						db.out("***STARTING Test Suite***");
						testsuite();
						db.out("***ENDING Test Suite***");
						
					}else{
						db.out("***Cancelled***");
					}
					

					break;
					
					
				case 6:	
				default: break;	
			}
		
			db.out("***DONE***");
			option = show_options();
		}
		
		
		db.disconnect();
		db.out("\n\n\n*****************************************\n");
		db.out("Thank You for Using the Test Suite");
		db.out("End");
		db.out("\n*****************************************\n");	
		
		
		
	}



	
	
	
	public static int show_options(){
		
		int option = 0;
		
		db.out("\n\nOptions");
		db.out("---------------------");
		db.out("1 : Re-Initialize Database");
		db.out("2 : Populate Venues Only");
		db.out("3 : Populate Sample Test Data for GUI (Events, Bookings, Budget only)");
		db.out("4 : Drop Tables");
		db.out("5 : Run Test Suite");
		db.out("6 : Exit");
	
		System.out.print("Enter your option : ");
		option = sc.nextInt();
		db.out("");
		
		
		return option;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Setting the database name
	 */
	public static void set_dbname(){
		String option;
		
		/*
		 * Set Database Name
		 */
		System.out.print("Set Database Name (use default: y): ");
		option = sc.next();
		
		//See if use default DB Name
		if (option.compareTo("y") == 0 || option.compareTo("Y") == 0)
			db.set_name(EMDBSettings.DATABASE_NAME);
		else
			db.set_name(option);	
		
		db.out(db.DBNAME);
	}

	
	/**
	 * Connect to Database
	 * Print current System State
	 */
	public static boolean check_file(){

		System.out.print(" + Creating/Retrieving Database: ");
		if (db.testFile()){
			db.out("- DONE");


			db.out("\nCurrent System State...");
			db.out(" +" + db.testNative());
			db.set_debug(true);					//TO DO: Check debug mode
			db.out(" +Debug Mode - On");
			
			return true;
		}else{
			db.out("- ERROR");

			return false;

		}

	}
	
	
	
	/**
	 * Checking the database for tables.
	 */
	public static void check_tables(boolean cont){
		if (cont){
			if(db.verify_table_count(false) == 1){
				db.out(" +Check Tables: - OK");
			}else{
				db.out(" +Check Tables: - ERROR");
				db.out("***Initializing Tables***");
				db.init();
				check_tables(cont);
			}
		} // end no file
	}
	
	
	
	
	
	/**
	 * Populating venue data (Real Locations, Fake capacity and Cost)
	 */
	public static void populate_venue(){
		db.add_prepare("venue");
		db.add_venue("DR1 (COM1-B-14B)", "", "", 6, 1000, true);
		db.add_venue("DR2 (COM1-B-14A)", "", "", 6, 1200, true);
		db.add_venue("DR3 (COM1-B-07)", "", "", 5, 1000, true);
		db.add_venue("DR4 (COM1-B-06)", "", "", 5, 1951, true);
		db.add_venue("DR5 (ICUBE-03-18)", "", "", 8, 1639, true);
		db.add_venue("DR6 (COM2-02-12)", "", "", 8, 4754, true);
		db.add_venue("DR7 (COM2-03-14)", "", "", 8, 35, true);
		db.add_venue("DR8 (COM2-03-15)", "", "", 8, 325, true);
		db.add_venue("DR9 (COM2-04-06)", "", "", 8, 72, true);
		db.add_venue("DR10 (COM2-02-24)", "", "", 8, 963, true);
		db.add_venue("DR11 (COM2-02-23)", "", "", 8, 43, true);
		db.add_venue("Executive Classroom (COM2-04-02)", "", "", 25, 10000, true);
		db.add_venue("MR1 (COM1-03-19)", "", "", 7, 48, true);
		db.add_venue("MR2 (COM1-03-28)", "", "", 7, 753, true);
		db.add_venue("MR3 (COM2-02-26)", "", "", 7, 2625, true);
		db.add_venue("MR4 (COM1-01-22)", "", "", 7, 2452, true);
		db.add_venue("MR5 (COM1-01-18)", "", "", 7, 25, true);
		db.add_venue("MR6 (AS6-05-10)", "", "", 8, 436, true);
		db.add_venue("MR7 (ICUBE-03-01)", "", "", 12, 425, true);
		db.add_venue("MR8 (ICUBE-03-48)", "", "", 12, 234, true);
		db.add_venue("MR9 (ICUBE-03-49)", "", "", 12, 242, true);
		db.add_venue("Video Conferencing Room (COM1-02-13)", "", "", 30, 15000, true);
		db.add_batch_commit();
	}
	
	
	
	
	/**
	 * Populate tables with sample data (fake) for testing purposes. Returns the first event_id;
	 */
	public static int populate_sample(){
		int eid1,
			eid2,
			vid1, 
			vid2;

		
		db.out("\n******Adding Venue (Single)******");
		vid1 = db.add_venue("COM1", "", "", 10, 100000);
		db.add_venue("SCIENCE2", "", "", 20, 2041);
		vid2 = db.add_venue("BIZ1", "", "", 1, 30);
		db.add_venue("FASS1", "", "", 30, 4);
		db.add_venue("ENGINE4", "", "", 12, 400000);
		db.add_venue("STARBUCKS", "", "", 21, 5000);
		db.add_venue("UTOWN", "", "", 400, 20000);

		
		db.out("\n******Adding Events (Single)******");
		eid1 = db.add_event("NUS Hackers Friday Hacks", "", 0, "05-02-2012", "06-02-2012", "10:02", "12:02");
		db.add_event("NUS Symposium", "", 0, "07-02-2012", "08-02-2012", "10:02", "18:02");
		db.add_event("Rockfest", "", 0, "09-02-2012", "09-02-2012", "10:02", "18:02");
		eid2 = db.add_event("Matriculation Fair", "", 0, "10-02-2012", "12-02-2012", "11:02", "16:02");
		db.add_event("Coding Marathon", "", 0, "13-02-2012", "13-02-2012", "10:02", "18:02");
		
		
		
		db.out("\n******Adding Booking******");
		db.add_booking(eid1, vid1, "05/02/2012/10", "05/02/2012/12");
		db.add_booking(eid1, vid2, "05/02/2012/14", "05/02/2012/16");
		db.add_booking(eid1, vid1, "06/02/2012/11", "07/02/2012/18");
		
		
		db.add_booking(eid2, vid1, "20/03/2013/10", "05/04/2013/12");
		db.add_booking(eid2, vid2, "21/04/2013/14", "05/05/2013/16");
		db.add_booking(eid2, vid2, "22/05/2013/11", "07/06/2013/18");	
		
		
		
		db.out("\n******Adding Budget (Batch)******");
		db.add_prepare("budget");
		db.add_budget(eid1, "Apple_Ipad", 60000, 7, null, true);
		db.add_budget(eid1, "ACER_NETBOOK", 130080, 10, null, true);
		db.add_budget(eid1, "Nikon_Cam", 6345, 9, null, true);
		db.add_budget(eid1, "Monster_Earpiece", 6470, 7, null, true);
		db.add_budget(eid1, "OCZ_120GB_SSD", 32088, 8, null, true);
		db.add_budget(eid1, "Canon_DSLR", 100050, 11, null, true);
		db.add_batch_commit();
		
		
		db.add_prepare("budget");
		db.add_budget(eid2, "Windows_7_Basic", 600000, 1, null, true);
		db.add_budget(eid2, "World_of_Warcraft", 1300, 1000, null, true);
		db.add_budget(eid2, "OCZ_120GB_SSD", 32088, 8, null, true);
		db.add_budget(eid2, "Western_Digital_HD_50GB", 18060, 5, null, true);
		db.add_batch_commit();
		
		
		
		
		db.out("\n******Adding Budget Optimized (Batch)******");
		db.add_prepare("budgetOptimized");
		db.add_budget_optimized(eid1, "Apple_Ipad", 60000, 7, null, true);
		db.add_budget_optimized(eid1, "ACER_NETBOOK", 130080, 10, null, true);
		db.add_budget_optimized(eid1, "Nikon_Cam", 6345, 9, null, true);
		db.add_budget_optimized(eid1, "Monster_Earpiece", 6470, 7, null, true);
		db.add_budget_optimized(eid1, "OCZ_120GB_SSD", 32088, 8, null, true);
		db.add_batch_commit();	
		

	
		db.add_prepare("budgetOptimized");
		db.add_budget_optimized(eid2, "Windows_7_Basic", 600000, 1, null, true);
		db.add_budget_optimized(eid2, "World_of_Warcraft", 1300, 1000, null, true);
		db.add_budget_optimized(eid2, "OCZ_120GB_SSD", 32088, 8, null, true);
		db.add_batch_commit();		
		
		
		return eid1;
	}
	
	
	
	/**
	 * A Series of test consisting of most of the functions from EMDB. INSERTS, DELETES and UPDATES
	 */
	public static void testsuite(){

		check_tables(true);
		check_file();
		db.reset();
		
		
		int eid = populate_sample();
		
	
		/*
		 * Displaying an event
		 */
		db.out("\n******Get Event #"+eid+"******");
		Eventitem item = db.get_event(eid);
		
		if (item != null){
			try{
				item.addBVI(db.get_booking_info(eid));	
			}catch(Exception e){
				db.out(e.getMessage());
			}
			
			try{
				item.setitem_list(db.get_budget_list(eid, true));
			}catch(Exception e){
				db.out(e.getMessage());
			}
			
			db.out("\n******Display Event #"+eid+" Details******");
			db.out(" +Name : "+item.getName());
			db.out(" +Time : "+item.getStartTime() + "-" + item.getEndTime());
			db.out(" +Date : "+item.getStartDate() + "-" + item.getEndDate());
			
			
			db.out(" +Booked Venue List :");
			
				Vector<BookedVenueInfo> info = item.getBVI_list();
				for (int i=0; i<info.size(); i++){
					db.out("++venue : " + 
							info.get(i).getName() + 
							"(" + 
								"Cost - " + info.get(i).getCostInDollar()  +
								"Capacity - " +info.get(i).getMaxCapacity() +
							")");
				}
			db.out(" +Budget List :");
				Vector<Item> bitem = item.getitem_list();
				for (int i=0; i<info.size(); i++){
					db.out("++item : " +
							bitem.get(i).getItem() + 
							" ( $" + bitem.get(i).getPrice() 
							+ ")");
				}
			
		}	
			
			
		
		/*
		 * Deleting Event
		 */
			
			
			
			
			
		db.out("******Delete Event******");
		db.delete_event(eid);

		db.out("******Show Event (Exception / Should be Empty)******");
		try{
			item = db.get_event(eid);
			db.out(item.toString());
		}catch (Exception e){
			db.out(e.getMessage());
		}
		
		db.out("******Show Budget (Exception / Should be Empty)******");
		try{
			Vector<BookedVenueInfo> list1 = db.get_booking_info(eid);
			db.out(list1.toString());
		}catch (Exception e){
			db.out(e.getMessage());
		}
		
		
		db.out("******Show Venues (Exception / Should be Empty)******");
		try{
			Vector<Item> list2 = db.get_budget_list(eid, false);
			db.out(list2.toString());
		}catch (Exception e){
			db.out(e.getMessage());
		}
		
		db.reset();
		
		
		
		db.out("******Display Event List******");
		Vector<Eventitem> elist = db.get_event_list();
		
		try{
			db.out(elist.toString());
		}catch(Exception e){
			db.out(e.getMessage());
		}
		
		db.init();
	
		
	} //end test suite
	
	
	
	
}
