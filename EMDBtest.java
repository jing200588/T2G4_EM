
public class EMDBtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EMDB_sqlite db = new EMDB_sqlite();
		db.set_name("me.db");
		
		db.test();
	}

}
