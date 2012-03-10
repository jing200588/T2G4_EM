import java.util.Scanner;


/**
 * Test Suite for EMDB SQLite wrapper as well as the database.
 * 
 * @author JunZhi
 *
 */
public class EMDBtools {

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
					db.out("WARNING: Database will be wiped out for the test..  Backup first before using on a live database.\n");
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
			db.set_name(EMSettings.DATABASE_NAME);
		else
			db.set_name(option);	
		
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
	 * Populate tables with sample data (fake) for testing purposes
	 */
	public static void populate_sample(){
		int eid1,
			eid2,
			vid1, 
			vid2;

		
		vid1 = db.add_venue("COM1", "", "", 10, 100000);
		db.add_venue("SCIENCE2", "", "", 20, 2041);
		vid2 = db.add_venue("BIZ1", "", "", 1, 30);
		db.add_venue("FASS1", "", "", 30, 4);
		db.add_venue("ENGINE4", "", "", 12, 400000);
		db.add_venue("STARBUCKS", "", "", 21, 5000);
		db.add_venue("UTOWN", "", "", 400, 20000);

		eid1 = db.add_event("NUS Hackers Friday Hacks", "", 0, "5-2-2012", "7-2-2012", "10:2", "18:2");
		db.add_event("NUS Symposium", "", 0, "7-2-2012", "8-2-2012", "10:2", "18:2");
		db.add_event("Rockfest", "", 0, "8-2-2012", "9-2-2012", "10:2", "18:2");
		eid2 = db.add_event("Matriculation Fair", "", 0, "10-2-2012", "12-2-2012", "11:2", "16:2");
		db.add_event("Coding Marathon", "", 0, "13-2-2012", "13-2-2012", "10:2", "18:2");
		
		db.add_booking(eid1, vid1, "5/2/2012/10", "5/2/2012/12");
		db.add_booking(eid1, vid2, "5/2/2012/14", "5/2/2012/16");
		db.add_booking(eid1, vid1, "6/2/2012/11", "7/2/2012/18");
		
		
		db.add_booking(eid2, vid1, "20/3/2013/10", "5/4/2013/12");
		db.add_booking(eid2, vid2, "21/4/2013/14", "5/5/2013/16");
		db.add_booking(eid2, vid2, "22/5/2013/11", "7/6/2013/18");	
		
		
		db.add_prepare("budget");
		db.add_budget(eid1, "Apple_Ipad", 60000, 7, null, true);
		db.add_budget(eid1, "ACER_NETBOOK", 130080, 10, null, true);
		db.add_budget(eid1, "Nikon_Cam", 6345, 9, null, true);
		db.add_budget(eid1, "Monster_Earpiece", 6470, 7, null, true);
		db.add_budget(eid1, "OCZ_120GB_SSD", 32088, 8, null, true);
		db.add_budget(eid1, "Canon_DSLR", 100050, 11, null, true);
		db.add_batch_commit();
		
		db.add_prepare("budgetOptimized");
		db.add_budget_optimized(eid1, "Apple_Ipad", 60000, 7, null, true);
		db.add_budget_optimized(eid1, "ACER_NETBOOK", 130080, 10, null, true);
		db.add_budget_optimized(eid1, "Nikon_Cam", 6345, 9, null, true);
		db.add_budget_optimized(eid1, "Monster_Earpiece", 6470, 7, null, true);
		db.add_budget_optimized(eid1, "OCZ_120GB_SSD", 32088, 8, null, true);
		db.add_batch_commit();	
		
		db.add_prepare("budget");
		db.add_budget(eid2, "Windows_7_Basic", 600000, 1, null, true);
		db.add_budget(eid2, "World_of_Warcraft", 1300, 1000, null, true);
		db.add_budget(eid2, "OCZ_120GB_SSD", 32088, 8, null, true);
		db.add_budget(eid2, "Western_Digital_HD_50GB", 18060, 5, null, true);
		db.add_batch_commit();
	
		db.add_prepare("budgetOptimized");
		db.add_budget_optimized(eid2, "Windows_7_Basic", 600000, 1, null, true);
		db.add_budget_optimized(eid2, "World_of_Warcraft", 1300, 1000, null, true);
		db.add_budget_optimized(eid2, "OCZ_120GB_SSD", 32088, 8, null, true);
		db.add_batch_commit();		
	}
	
	
	
	/**
	 * A Series of test consisting of most of the functions from EMDB. INSERTS, DELETES and UPDATES
	 */
	public static void testsuite(){

		check_tables(true);
		check_file();
		db.reset();
		
		
		
		
		/*
		 * Add Event
		 */
		db.out("******Adding Events******");
		int eid1 = db.add_event("NUS Hackers Friday Hacks", "", 0, "5-2-2012", "7-2-2012", "10:2", "18:2");
		int eid2 = db.add_event("Matriculation Fair", "", 0, "10-2-2012", "12-2-2012", "11:2", "16:2");
		db.out("Events of ID #"+eid1 +"& #"+eid2 +" are created");
		
		
		
		
		/*
		 * Deleting Event
		 */
		db.out("******Delete Event******");
		db.out("******Show Budget (Should be Empty)******");
		db.out("******Show Event (Should be Empty)******");
		db.out("******Show Venues (Should be Empty)******");

		
		
		db.reset();
		
	}
	
	
	
	
}
