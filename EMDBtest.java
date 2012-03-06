public class EMDBtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EMDB db = new EMDB();
		db.set_name("test.sqlite");
		db.connect();
		
		//db.init();
		//System.out.println(db.add_venue("me", "", "", 0, 0));
		//db.add_budget(1, "something", 567890, 423, "name");
		//db.get_venue(1);
		//db.delete_budget_list(0);
		db.out("Test End");
	}

}
