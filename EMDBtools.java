import java.util.Scanner;

public class EMDBtools {
	

	

	
	public static void main(String[] args) {
		
		EMDB db = new EMDB();
		db.set_debug(true);		
		
		Scanner sc = new Scanner(System.in);
		int option = 0;
		String temp = "";
		int status = 0;
		
		
		String line = "";
		
		
		
		out("\n*****************************************\n");
		out("   Database Tools	");
		out("\n*****************************************\n");	
	
		
		out("Enter Database Name: ", true);
		line = sc.next();
		db.set_name(line);
		db.connect();
		
		
		option = options();

		
		while (option != 6){
			
			switch (option){
				case 1: // initialize Database

					boolean go = true;
					if(db.testFile()){
						out("File exists, re-init? (y/n) : ");
						temp = sc.next();
						if (temp.compareTo("y") != 0){
							out("Init Cancelled");
							go = false;
						}
						
					}
					
					
					if (go){
						db.init();
						status = db.verify_table_count(false);
	
						if (status == 1){
							db.out("DB Init - OK");
						}else{
							db.out("DB Init - NOT OK - " + db.verify_table_count(true));
						}
					}
					
					break;

					
					
					
				case 2: // Populate Venues
					
					if(db.testFile()){
						db.add_prepare("venue");
						db.add_venue("DR1 (COM1-B-14B)", "", "", 8, 0, true);
						db.add_venue("DR2 (COM1-B-14A)", "", "", 8, 0, true);
						db.add_venue("DR3 (COM1-B-07)", "", "", 8, 0, true);
						db.add_venue("DR4 (COM1-B-06)", "", "", 8, 0, true);
						db.add_venue("DR5 (ICUBE-03-18)", "", "", 8, 0, true);
						db.add_venue("DR6 (COM2-02-12)", "", "", 8, 0, true);
						db.add_venue("DR7 (COM2-03-14)", "", "", 8, 0, true);
						db.add_venue("DR8 (COM2-03-15)", "", "", 8, 0, true);
						db.add_venue("DR9 (COM2-04-06)", "", "", 8, 0, true);
						db.add_venue("DR10 (COM2-02-24)", "", "", 8, 0, true);
						db.add_venue("DR11 (COM2-02-23)", "", "", 8, 0, true);
						db.add_venue("Executive Classroom (COM2-04-02)", "", "", 8, 0, true);
						db.add_venue("MR1 (COM1-03-19)", "", "", 8, 0, true);
						db.add_venue("MR2 (COM1-03-28)", "", "", 8, 0, true);
						db.add_venue("MR3 (COM2-02-26)", "", "", 8, 0, true);
						db.add_venue("MR4 (COM1-01-22)", "", "", 8, 0, true);
						db.add_venue("MR5 (COM1-01-18)", "", "", 8, 0, true);
						db.add_venue("MR6 (AS6-05-10)", "", "", 8, 0, true);
						db.add_venue("MR7 (ICUBE-03-01)", "", "", 8, 0, true);
						db.add_venue("MR8 (ICUBE-03-48)", "", "", 8, 0, true);
						db.add_venue("MR9 (ICUBE-03-49)", "", "", 8, 0, true);
						db.add_venue("Video Conferencing Room (COM1-02-13)", "", "", 8, 0, true);
						db.add_batch_commit();
	
						out("\n Done \n");
						
					}else{
						out("Database doesn't exist");
					}
					
					
					break;
					
					
					
					
					
					
					
					
				case 3: // Populate Test Tables (Event, Bookings);
		
					int eid, vid;
					vid = db.add_venue("TEST VENUE", "", "", 8, 0);
					
					eid = db.add_event("NUS Hackers Friday Hacks", "", "5-2-2012", "7-2-2012", "10:2", "18:2");
					db.add_event("NUS Symposium", "", "7-2-2012", "8-2-2012", "10:2", "18:2");
					db.add_event("Rockfest", "", "8-2-2012", "9-2-2012", "10:2", "18:2");
					db.add_event("Matriculation Fair", "", "10-2-2012", "12-2-2012", "11:2", "16:2");
					db.add_event("Coding Marathon", "", "13-2-2012", "13-2-2012", "10:2", "18:2");
					
					db.add_booking(eid, vid, "5/2/2012/10", "5/2/2012/12");
					db.add_booking(eid, vid, "5/2/2012/14", "5/2/2012/16");
					db.add_booking(eid, vid, "6/2/2012/11", "7/2/2012/18");
					
					
					out("\n Test Data Ready \n");
					break;
					
				case 4: //Clear Tables
					db.reset_clear();
					break;
				
				case 5: // Delete Table
					db.reset();
					break;
				
				
				case 6:
				default: 
					break;
			
			}
			
			
			
			
			
			
			option = options();
		}		
		

		db.disconnect();
		
		db.out("\n*****************************************\n");
		db.out("   Tool Has Ended	");
		db.out("\n*****************************************\n");	
		
		
		
	}
	
	
	private static int options(){
		
		Scanner sc = new Scanner(System.in);
		int option = 0;
		
		out("Options");
		out("---------------------");
		out("1 : Initialize Database");
		out("2 : Populate Venues Only");
		out("3 : Populate Test Data (Events and Bookings)");
		out("4 : Clear Tables");
		out("5 : Delete Tables");
		out("6 : Exit");
	
		out("Enter your option : ", true);
		option = sc.nextInt();
	
		return option;
	}
	
	
	private static void out(String line){
		System.out.println(line);
	}
	
	private static void out(String line, boolean flag){
		System.out.print(line);
	}
	
	
}


/*
DR1 (COM1-B-14B)
DR2 (COM1-B-14A)
DR3 (COM1-B-07)
DR4 (COM1-B-06)
DR5 (ICUBE-03-18)
DR6 (COM2-02-12)
DR7 (COM2-03-14)
DR8 (COM2-03-15)
DR9 (COM2-04-06)
DR10 (COM2-02-24)
DR11 (COM2-02-23)
Executive Classroom (COM2-04-02)
MR1 (COM1-03-19)
MR2 (COM1-03-28)
MR3 (COM2-02-26)
MR4 (COM1-01-22)
MR5 (COM1-01-18)
MR6 (AS6-05-10)
MR7 (ICUBE-03-01)
MR8 (ICUBE-03-48)
MR9 (ICUBE-03-49)
Video Conferencing Room (COM1-02-13)
*/