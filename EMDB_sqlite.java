import java.sql.*;
import java.util.Vector;

//import org.sqlite.SQLiteJDBCLoader;




/*
 * 
 * CLASS NAME: 				EMDB
 * CLASS DESCRIPTION: 		This is the database class for EMan. It provides the set,get,update and delete abilities of the program.
 * 
 * 
 * 
 * 
 * 
 * 
 * *******************************************
 * 
 * PROVIDED API (list)
 * 
 * *******************************************
 * 
 * 
 * set_name ()
 * add_venue()
 * add_budget()
 * add_event()
 * 
 * 
 * 
 * 
 * *******************************************
 * 
 * GENERAL ERROR VALUES
 * 
 * *******************************************
 * 
 * 0 = failed
 * 1 = OK
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * *******************************************
 * 
 * NAMING CONVENTIONS
 * 
 * *******************************************
 * 
 * 
 * PRIVATE variables are in CAPS for the first word in the name
 * eg: TABLE_one, DBQUERY
 * 
 * DB related functions are prefixed db_
 * Subactions will be appended after that.
 * 
 * 
 */



public class EMDB_sqlite{
	
	/*
	 * ***********************************
	 * 
	 * CLASS WIDE VARIABLES
	 * 
	 * ***********************************
	 */
	private String DBNAME = "";
	private Connection DBCON = null;
	private Statement DBQUERY = null;
	private PreparedStatement PREPSTATEM = null;
	
	
	
	//Table names
	private final String TABLE_venue 					= "EM_VENUE";
	private final String TABLE_venue_bookings 			= "EM_VENUE_BOOKINGS";
	private final String TABLE_events 					= "EM_EVENTS";
	private final String TABLE_budget 					= "EM_BUDGET";
	private final String TABLE_budget_optimized			= "EM_BUDGET_OPTIMIZED";	
	
	
	
	
	
	
	/*
	 * ***********************************
	 * 
	 * CONSTRUCTOR
	 * 
	 * ***********************************
	 */

	public EMDB_sqlite(){	
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	
	
	
	
	
	
	/*
	 * ***********************************
	 * 
	 * SETUP
	 * 
	 * ***********************************
	 */
	public int init(){
		
		
		//for status reply
		int state = 0;
			
		
		
		//DB Drops
		state = this.drop_all_table();
		
		
		//check if some drop table command failed.
		if (state < 3){
			return 0;

			
		}else{	
			//DB Creation
			state = create_all_table();
		}
		
	
		
		//final state reply
		if (state == 0)
			return 0;
		else 
			return 1;
		
			
		
	}

	
	
	
	
	/*
	 * Dropping of all Tables
	 */
	private int drop_all_table(){
		int state = 0;
			
		state += this.drop_table(this.TABLE_venue);
		state += this.drop_table(this.TABLE_events);
		state += this.drop_table(this.TABLE_budget);
		
		return state;
	}
	
	
	
	
	/*
	 * Creating the DB tables
	 */
	private int create_all_table(){
		
		try {
				
			
			this.DBQUERY.executeUpdate("CREATE TABLE " + this.TABLE_venue + " ("+
					"'venue_id' INTEGER PRIMARY KEY AUTOINCREMENT,"+
					"'name' TEXT NOT NULL," +
					"'address' TEXT," +
					"'description' TEXT," +
					"'capacity' INTEGER NOT NULL DEFAULT (0)," +
					"'cost' INTEGER NOT NULL DEFAULT (0)"+
					")");
		
		
		
			
			
			this.DBQUERY.executeUpdate("CREATE TABLE " + this.TABLE_events + " ("+
					"'event_id' INTEGER PRIMARY KEY AUTOINCREMENT,"+
					"'name' TEXT NOT NULL," +
					"'description' TEXT," +
					"'venue_booked' INTEGER"+
					")");
		
		
		
		
		
			this.DBQUERY.executeUpdate("CREATE TABLE " + this.TABLE_budget + " ("+
					"'budget_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
					"'event_id' INTEGER NOT NULL DEFAULT (-1),"+
					"'name' TEXT," +
					"'price' INTEGER," +
					"'satisfaction' INTEGER," +
					"'type' TEXT" +
					")");
			
			

			this.DBQUERY.executeUpdate("CREATE TABLE " + this.TABLE_budget_optimized + " ("+
					"'budget_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
					"'event_id' INTEGER NOT NULL DEFAULT (-1),"+
					"'name' TEXT," +
					"'price' INTEGER," +
					"'satisfaction' INTEGER," +
					"'type' TEXT" +
					")");			
			
			
			this.DBQUERY.executeUpdate("CREATE TABLE " + this.TABLE_venue_bookings + " ("+
					"'booking_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
					"'event_id' INTEGER NOT NULL," +
					"'venue_id' INTEGER NOT NULL,"+
					"'date' TEXT NOT NULL," +
					"'time_hour' INTEGER NOT NULL," +
					"'time_min' INTEGER"+
					")");	
			
			
			
	
			
			return 1;
			
			
		} catch (SQLException e) {

			return 0;
		}
		
	}
	
	
	
	/* 
	 * Dropping a single table
	 */
	private int drop_table(String name){
		
		try {
			this.DBQUERY.executeUpdate("DROP TABLE IF EXISTS " + name + ";");
			
			return 1;
			
		} catch (SQLException e) {
			
			return 0;
		}	
	}
	
	
	
	
	
	
	/*
	 * - reset all variables
	 * - 
	 */
	public void reset(){
		this.DBNAME = "";
		this.DBCON = null;
		this.DBQUERY = null;	
		this.PREPSTATEM = null;
		
		
		this.init();
		
	}
	
	
	
	
	
	


	
	
	
	/*
	 * ***********************************
	 * 
	 * CONNECTION MANIPULATION
	 * - Connect
	 * - Disconnect
	 * 
	 * ***********************************
	 */
	
	public int connect(){
		
		try {
			this.DBCON = DriverManager.getConnection("jdbc:sqlite:"+DBNAME);
			this.DBQUERY = this.DBCON.createStatement();
			
			return 1;
		} catch (SQLException e) {
			
			
			return 0;
		}
		
	}
	
	
	
	
	
	public int disconnect(){

		try {
			this.DBCON.close();
			return 1;
			
			
		} catch (SQLException e) {

			return 0;
		}
		

	}
	
	
	

	
	
	
	
	
	
	
	
	
	/*
	 * ***********************************
	 * 
	 * SETUP
	 * - SETTING of VARS
	 * 
	 * ***********************************
	 */
	

	public void set_name(String name){
		DBNAME = name;
	}
	
	
	
	
	
	
	
	
	/*
	 * ***********************************
	 * 
	 * ADDING to Database
	 * 
	 * PROCESS
	 * - add_preapre > add_?? > add_commit
	 * ***********************************
	 */


	/*
	 * Single and Batch helpers
	 */
	public void add_prepare(String type){
		try {
				if (type.compareTo("event") == 0)		
					this.PREPSTATEM = this.DBCON.prepareStatement("INSERT INTO " + this.TABLE_events + " (name, description) VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
				
				else if (type.compareTo("venue") == 0)
					this.PREPSTATEM = this.DBCON.prepareStatement("INSERT INTO " + this.TABLE_venue + " (name, address, description, capacity, cost) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
					
				else if (type.compareTo("bookings") == 0)
					this.PREPSTATEM = this.DBCON.prepareStatement("INSERT INTO " + this.TABLE_venue_bookings + " (event_id, venue_id, date, time_hour, time_min) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
					
				else if (type.compareTo("budget") == 0)
					this.PREPSTATEM = this.DBCON.prepareStatement("INSERT INTO " + this.TABLE_budget + " (event_id, name, price, satisfaction, type) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
					
				else if (type.compareTo("budgetOptimized") == 0)
					this.PREPSTATEM = this.DBCON.prepareStatement("INSERT INTO " + this.TABLE_budget_optimized + " (event_id, name, price, satisfaction, type) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
				
			
			
			
		} catch (SQLException e) {
			
		}
		
	}
	
	
	
	/*
	 * Single Processing functions
	 */
	
	
	
	// There is another method for batch
	public int add_event(String name, String description){
		this.add_prepare("event");
		
		try {
			ResultSet result;
			
			this.PREPSTATEM.setString(1, name);
			this.PREPSTATEM.setString(2, description);
			this.PREPSTATEM.executeUpdate();
			
			result = this.PREPSTATEM.getGeneratedKeys();
			
			if (result.next()){
				return result.getInt(1);
			}
			return 0;
			
			
		} catch (SQLException e) {
			return 0;
		}
	}
	
	
	
	
	
	
	
	
	
	// There is another method for batch
	public int add_venue(String name, String address, String description, int capacity, int cost){
		this.add_prepare("venue");
		
		try {
			ResultSet result;
			
			this.PREPSTATEM.setString(1, name);
			this.PREPSTATEM.setString(2, address);
			this.PREPSTATEM.setString(3, description);
			this.PREPSTATEM.setInt(4, capacity);
			this.PREPSTATEM.setInt(5, cost);
			this.PREPSTATEM.executeUpdate();
			
			result = this.PREPSTATEM.getGeneratedKeys();
			
			if (result.next()){
				return result.getInt(1);
			}
			return 0;
			
			
		} catch (SQLException e) {
			return 0;
		}  	
	}
	
	
	
	
	// There is another method for batch
	public int add_booking(int event_id, int venue_id, String date, int time_hour, int time_min){
		this.add_prepare("bookings");
		try {
			ResultSet result;
			
			this.PREPSTATEM.setInt(1, event_id);
			this.PREPSTATEM.setInt(2, venue_id);
			this.PREPSTATEM.setString(3, date);
			this.PREPSTATEM.setInt(4, time_hour);
			this.PREPSTATEM.setInt(5, time_min);
			this.PREPSTATEM.executeUpdate();
			
			result = this.PREPSTATEM.getGeneratedKeys();
			
			if (result.next()){
				return result.getInt(1);
			}
			return 0;
			
			
		} catch (SQLException e) {
			return 0;
		}  	
	}
	
	
	
	
	
	// There is another method for batch
	public int add_budget(int event_id, String name, int price, int satisfaction, String type){
		System.out.print(event_id + name + price + satisfaction + type);
		this.add_prepare("budget");
		try {
			ResultSet result;
			
			
			this.PREPSTATEM.setInt(1, event_id);
			this.PREPSTATEM.setString(2, name);
			this.PREPSTATEM.setInt(3, price);
			this.PREPSTATEM.setInt(4, satisfaction);
			this.PREPSTATEM.setString(5, type);
			this.PREPSTATEM.executeUpdate();
			
			result = this.PREPSTATEM.getGeneratedKeys();
			
			if (result.next()){
				return result.getInt(1);
			}
			return 0;

			
		} catch (SQLException e) {
			return 0;
		}  

		

	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * Batch Processing functions
	 */

	
	//Overloaded Function
	public void add_event(String name, String description, boolean batch){
		
	   	try {
			this.PREPSTATEM.setString(1, name);
			this.PREPSTATEM.setString(2, description);
			this.PREPSTATEM.addBatch();
			
			
	   	} catch (SQLException e) {
			
		}
	   	
	}
	
	//Overloaded Function
	public void add_venue(String name, String address, String description, int capacity, int cost, boolean batch){
	   	try {
			this.PREPSTATEM.setString(1, name);
			this.PREPSTATEM.setString(2, address);
			this.PREPSTATEM.setString(3, description);
			this.PREPSTATEM.setInt(4, capacity);
			this.PREPSTATEM.setInt(5, cost);
			this.PREPSTATEM.addBatch();
			
			
	   	} catch (SQLException e) {
			
		}
	   	
	}
	
	//Overloaded Function
	//public void add_bookings(){
	//}
	public void add_budget(int event_id, String name, int price, int satisfaction, String type, boolean batch){
	   	try {
			this.PREPSTATEM.setInt(1, event_id);
			this.PREPSTATEM.setString(2, name);
			this.PREPSTATEM.setInt(3, price);
			this.PREPSTATEM.setInt(4, satisfaction);
			this.PREPSTATEM.setString(5, type);
			this.PREPSTATEM.addBatch();
			
			
	   	} catch (SQLException e) {
			
		}
	}
	
	public void add_budget_optimized(int event_id, String name, int price, int satisfaction, String type, boolean batch){
	   	try {
			this.PREPSTATEM.setInt(1, event_id);
			this.PREPSTATEM.setString(2, name);
			this.PREPSTATEM.setInt(3, price);
			this.PREPSTATEM.setInt(4, satisfaction);
			this.PREPSTATEM.setString(5, type);
			this.PREPSTATEM.addBatch();
			
			
	   	} catch (SQLException e) {
			
		}
	}

	
	public void add_batch_commit(){
		
	    try {
			this.DBCON.setAutoCommit(false);
			this.PREPSTATEM.executeBatch();
			this.DBCON.setAutoCommit(true);
			
			
			
			
	    } catch (SQLException e) {
		
		}
	}
	
	
	
	
	/*
	 * ***********************************
	 * 
	 * SETUP
	 * - GET Values
	 * 
	 * ***********************************
	 */
	

	/*
	 * Getting an event by the event id as set in the database
	 */
	public void get_event(int id){
		
		//create empty event container here
		
		 try {
			 
			String query = this.select_all(
					this.TABLE_events, 
					"event_id="+ id , 
					1);
			
			ResultSet result = this.DBQUERY.executeQuery(query);
			while (result.next()) {
				System.out.println(1);
			}
			
		} catch (SQLException e) {
			
			//return empty event container here.
			
		}
		
		
	}
	
	
	
	/*
	 * Getting a venue by the venue id as set in the database
	 */
	public void get_venue(int id){
		
		//create empty venue container here
		
		 try {
			 
			String query = this.select_all(
					this.TABLE_venue, 
					"venue_id="+ id , 
					1);	
			ResultSet result = this.DBQUERY.executeQuery(query);
			
			while (result.next()) {
				System.out.println(result.getInt("venue_id"));
				System.out.println(result.getString("name"));
			}
			
			
			
			result.close();
			
		} catch (SQLException e) {
			
			//return empty venue container here.
			
		}
		
		
	}
	
	
	
	
	/*
	 * Getting a list of bookings from db
	 */
	public void get_bookings(int id, String type){
		
		//create empty venue container here
		String query = "";
		 try {
			 
			
			if (type.compareTo("event") == 0){
				query = this.select_all(
							this.TABLE_venue_bookings, 
							"event_id="+ id , 
							1);	
			}else if (type.compareTo("venue") == 0){
			
				query = this.select_all(
							this.TABLE_venue_bookings, 
							"venue_id="+ id , 
							1);	
			} else{
				query = this.select_all(
							this.TABLE_venue_bookings, 
							"venue_id="+ id , 
							1);	
			}
			 

			ResultSet result = this.DBQUERY.executeQuery(query);
			

			
			
			result.close();
			
		} catch (SQLException e) {
			
			//return empty venue container here.
			
		}
		
		
	}
	
	
	
	
	/*
	 * Get a budget list attached to the event by it's id
	 */
	public Vector<Item> get_budget_list(int id, boolean optimized){
		
		//create empty budget list vector here.
		
		Vector<Item> list = new Vector<Item>();
		
		try {
			String query = "";
			
			
			if (optimized){
				query = this.select_all(
						this.TABLE_budget_optimized, 
						"event_id="+ id , 
						0);
			}else{
				query = this.select_all(
						this.TABLE_budget, 
						"event_id="+ id , 
						0);
			}
			
			
			ResultSet result = this.DBQUERY.executeQuery(query);
		    while (result.next()) {
			      Item current = new Item(result.getString("name"), result.getInt("price"), result.getInt("satisfaction"), result.getString("type"));
			      list.add(current);
			}
			result.close();

			return list;
			
			
		} catch (SQLException e) {
			
			//return empty budget list here.
		
		}
		return null;
		
		
	}
	
	
	
	
	
	
	
	
	/*
	 * ***********************************
	 * 
	 * SETUP
	 * -DELETE REQUEST
	 * 
	 * ***********************************
	 */


	public void delete_budget_list(int id){
		String $sql ="";
		
		
		try {
			$sql = "DELETE FROM " + this.TABLE_budget + " WHERE event_id="+id;
			this.DBQUERY.execute($sql);
			$sql = "DELETE FROM "+this.TABLE_budget_optimized +" WHERE event_id="+id;
			this.DBQUERY.execute($sql);
			
			
		} catch (SQLException e) {
		
		}
			
			
	}
	
	
	
	
	
	
	public void delete_event(int id){
		
		this.delete_budget_list(id);
	}
	
	
	
	
	
	
	public void delete_venue(){
		
		
	}

	
	
	
	
	
	
	
	
	
	/*
	 * ***********************************
	 * 
	 * QUERY HELPERS
	 * 
	 * ***********************************
	 */
	
	private String select_all(String name, String where, int limit){
		
		
		String query = "SELECT * FROM " + name; 
		
		if (where != ""){
			query += " WHERE " + where;
		}
		
		if (limit != 0){
			query += " LIMIT "+limit;
		}
		
		
		return query;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * ***********************************
	 * 
	 * FOR TESTING
	 * 
	 * ***********************************
	 */
	public void out(String msg){
		System.out.println(msg);
	}
	
	public void test(){
		 // out(String.format("running in %s mode", SQLiteJDBCLoader.isNativeMode() ? "native" : "pure-java"));
		out("start");
		
		
		try {
		
		    this.DBQUERY.executeUpdate("drop table if exists people;");
		    this.DBQUERY.executeUpdate("create table people (name, occupation);");
		    PreparedStatement prep = this.DBCON.prepareStatement(
		      "insert into people values (?, ?);");


		    prep.setString(1, "Gandhi");
		    prep.setString(2, "politics");
		    prep.addBatch();
		    prep.setString(1, "Turing");
		    prep.setString(2, "computers");
		    prep.addBatch();
		    prep.setString(1, "Wittgenstein");
		    prep.setString(2, "smartypants");
		    prep.addBatch();


		    this.DBCON.setAutoCommit(false);
		    prep.executeBatch();
		    this.DBCON.setAutoCommit(true);

		    ResultSet rs = this.DBQUERY.executeQuery("select * from people;");
		    while (rs.next()) {
		      System.out.println("name = " + rs.getString("name"));
		      System.out.println("job = " + rs.getString("occupation"));
		    }
		    rs.close();
		    this.DBCON.close();
		    
		    

		    
		    
		    
		} catch (SQLException e) {
			
		}
		
		
		out("end");
	}
	
	
	
	


	

	

	
	
}
