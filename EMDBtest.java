/*
 * 
 * Unit Test Suite for EMDB SQLite wrapper.
 * 
 */



public class EMDBtest {

	public static void main(String[] args) {
		EMDB db = new EMDB();
		
		
		
		
		db.set_name("test.sqlite");
		
		//Connect to DB
		
		

		
		db.connect();
		
		
		db.init();
		
		
		db.add_event("test", "", "7-2-2012", "7-2-2012", "18:2", "18:2");
		db.add_event("test", "", "5-2-2012", "7-6-2012", "18:2", "18:2");
		db.add_event("test", "", "7-2-2012", "7-2-2012", "18:2", "18:2");
		db.add_event("test", "", "5-2-2012", "7-6-2012", "18:2", "18:2");
		db.add_event("test", "", "7-2-2012", "7-2-2012", "18:2", "18:2");
		db.add_event("test", "", "5-2-2012", "7-6-2012", "18:2", "18:2");
		
		/*
		 * BUDGET
		 */
		db.add_budget(0, "1_Apple_Ipad",  600, 7, null);
		db.add_budget(0, "2_ACER_NETBOOK",  13008, 10, null);
		db.add_budget(0, "3_Nikon_Cam",  6345, 9, null);
		db.add_budget(0, "4_Monster_Earpiece",  647, 7, null);
		db.add_budget(0, "5_OCZ_120GB_SSD",  32088, 8, null);
		db.add_budget(0, "6_Canon_DSLR", 100050, 11, null);

		//db.delete_budget_list(0);
		
		

		
		/*
		 * VENUE
		 */
		
		
		//System.out.println(db.add_venue("me", "", "", 0, 0));
		db.add_venue("COM1", "TEST1", "HAIZ", 11, 10000);
		db.add_venue("COM2", "TEST2", "HAIZ", 12, 10000);		
		db.add_venue("COM3", "TEST3", "HAIZ", 13, 10000);
		db.add_venue("COM4", "TEST4", "HAIZ", 14, 10000);
		db.add_venue("COM5", "TEST5", "HAIZ", 15, 10000);
		//db.get_venue(1);
		

		
		
		
		
		
		
		
		//Disconnect from DB
		db.disconnect();
		db.out("Test End");
	}

}
