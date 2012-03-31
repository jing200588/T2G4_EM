package emdb;

import java.util.Scanner;


/**
 * Tools and Test Suite for EMDB Classes and Database.
 * @author JunZhi
 *
 */
public class EMDBIITools {

	
	private static Scanner sc = new Scanner(System.in);
	
	
	
	/**
	 * Shortcut to print out strings. (DUPLICATE FROM BASE)
	 * @param msg
	 */
	private static void dMsg(String aMsg){
		System.out.println("<EMDBTools> " + aMsg);
	}

	private static void dMsgN(String aMsg){
		System.out.println(aMsg);
	}
	
	private static void dMsgS(String aMsg){
		System.out.print("<EMDBTools> " + aMsg);
	}
	
	
	
	/**
	 * Main System
	 * @param args
	 */
	public static void main(String[] args) {
		
		EMDBII db;
		String dbName = "";
		int option = 0;
		
		
		
		dMsgN("*****************************************	\n");
		dMsgN("EMDB Tools - Database Tool and Test Suite.	");
		dMsgN("EMDB Version " + EMDBSettings.EMDB_VERSION		+	"\n");
		dMsgN("(c) Gerald, Some Rights reserved	\n");
		dMsgN("*****************************************	\n");	
		
		
		dMsg("WARNING: Certain options may cause the database to be wiped out..");
		dMsg("Backup first before using on a live database.\n");
		
		
		dbName = getDbName();
		db = new EMDBII(dbName, true);
		
		if (db.eventDB().verify()  
				&& 	db.budgetDB().verify()
				&& 	db.venueDB().verify()
				&&	db.participantDB().verify() 
			)
		{
			dMsg("Database - OK");
		}else{
			dMsg("Database - Not Initialized");
			db.init();
		}
		
		
		
		
		
		
		option = getOptions();
		while (option != 9){
			
			switch (option){
				case 1:
					dMsgS("Clean Up Tables? (y/n) : ");
					String temp1 = sc.next();
					if (temp1.compareTo("Y") == 0 || temp1.compareTo("y") == 0){
						db.truncate();
						populateVenues(db);
						/*db.init();
						
						if (db.eventDB().verify()  
								&& 	db.budgetDB().verify()
								&& 	db.venueDB().verify()
								&&	db.participantDB().verify() 
							)
						{
							populateVenues(db);
							dMsg("Re-initialization - OK");
						}else{
							dMsg("Re-initialization - ERROR");
						}*/
						
						
						
					}else{
						dMsg("Cancelled");
					}
					break;
				case 2:
					dMsg("POPULATING VENUES");
					populateVenues(db);
					break;
				case 3: 
					dMsgS("Confirm TRUNCATE / Clear Database? (y/n) : ");
					String temp4 = sc.next();
					if (temp4.compareTo("Y") == 0 || temp4.compareTo("y") == 0){
						
						db.truncate();
						
						
					}else{
						dMsg("Cancelled");
					}
					break;
				case 4:
					break;
				default:
					break;
			}//end switch	
			db.clearConnections();
			
			option = getOptions();
		}//end while
		
		
		dMsgN("\n\n*****************************************\n");
		dMsgN("Thank You for using EMDB Tools");
		dMsgN("\n*****************************************\n");	
		
	}
	
	
	
	
	
	/**
	 * Setting the database name
	 */
	private static String getDbName(){
		String option = "";
		
		/*
		 * Set Database Name
		 */
		dMsgS("Set Database Name (use default: y): ");
		option = sc.next();
		dMsgN("");
		
		
		//See if use default DB Name
		if (option.compareTo("y") == 0 || option.compareTo("Y") == 0)
			return EMDBSettings.DATABASE_NAME;
		else
			return option;
	}

	
	
	/**
	 * Options List.
	 * @return
	 */
	private static int getOptions(){
		
		int option = 0;
		
		dMsgN("\n");
		dMsgN("<EMDBTools> Options");
		dMsgN("---------------------");
		dMsgN("1 : Re-Initialize Database");
		dMsgN("2 : Populate Venues");
		dMsgN("3 : Clear / Truncate Tables");
		dMsgN("4 : Run Test Suite");
		dMsgN("9 : Exit");
	
		dMsgN("");
		dMsgS(" Enter your option : ");
		option = sc.nextInt();
		dMsgN("");
		
		
		return option;
	}
	
	
	
	
	public static void populateVenues(EMDBII db){
		db.venueDB().addVenue("DR1 (COM1-B-14B)", "", "", 6, 1000);
		db.venueDB().addVenue("DR2 (COM1-B-14A)", "", "", 6, 1200);
		db.venueDB().addVenue("DR3 (COM1-B-07)", "", "", 5, 1000);
		db.venueDB().addVenue("DR4 (COM1-B-06)", "", "", 5, 1951);
		db.venueDB().addVenue("DR5 (ICUBE-03-18)", "", "", 8, 1639);
		db.venueDB().addVenue("DR6 (COM2-02-12)", "", "", 8, 4754);
		db.venueDB().addVenue("DR7 (COM2-03-14)", "", "", 8, 35);
		db.venueDB().addVenue("DR8 (COM2-03-15)", "", "", 8, 325);
		db.venueDB().addVenue("DR9 (COM2-04-06)", "", "", 8, 72);
		db.venueDB().addVenue("DR10 (COM2-02-24)", "", "", 8, 963);
		db.venueDB().addVenue("DR11 (COM2-02-23)", "", "", 8, 43);
		db.venueDB().addVenue("Executive Classroom (COM2-04-02)", "", "", 25, 10000);
		db.venueDB().addVenue("MR1 (COM1-03-19)", "", "", 7, 48);
		db.venueDB().addVenue("MR2 (COM1-03-28)", "", "", 7, 753);
		db.venueDB().addVenue("MR3 (COM2-02-26)", "", "", 7, 2625);
		db.venueDB().addVenue("MR4 (COM1-01-22)", "", "", 7, 2452);
		db.venueDB().addVenue("MR5 (COM1-01-18)", "", "", 7, 25);
		db.venueDB().addVenue("MR6 (AS6-05-10)", "", "", 8, 436);
		db.venueDB().addVenue("MR7 (ICUBE-03-01)", "", "", 12, 425);
		db.venueDB().addVenue("MR8 (ICUBE-03-48)", "", "", 12, 234);
		db.venueDB().addVenue("MR9 (ICUBE-03-49)", "", "", 12, 242);
		db.venueDB().addVenue("Video Conferencing Room (COM1-02-13)", "", "", 30, 15000);
		
		db.clearConnections();
		
		db.clearConnections();
	}
	
	
	
	
}