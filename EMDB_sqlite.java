import java.sql.*;
import java.util.Vector;


/**
 * 
 * 
 * @author JunZhi
 * 
 * Description: Layer/Bridge between application and SQLite database.
 * 
 * *******************************************
 * NAMING CONVENTIONS
 * *******************************************
 * PRIVATE variables are in CAPS for the first word in the name
 * eg: TABLE_one, DBQUERY
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
	protected String DBNAME = "";
	private Connection DBCON = null;
	private Statement DBQUERY = null;
	private PreparedStatement PREPSTATEM = null;
	
	
	protected boolean EMDB_DEBUGGING = false;
	
	//Table names
	private final String TABLE_venue 					= "EM_VENUE";
	private final String TABLE_venue_bookings 			= "EM_VENUE_BOOKINGS";
	private final String TABLE_events 					= "EM_EVENTS";
	private final String TABLE_budget 					= "EM_BUDGET";
	private final String TABLE_budget_optimized			= "EM_BUDGET_OPTIMIZED";	
	
	
	
	
	
	
	/**
	 * Description: Constructor.
	 */
	public EMDB_sqlite(){	
		try {
			Class.forName("org.sqlite.JDBC");
			
			this.set_default_db();
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}

	}
	
	

	
	
	
	
	
	
	/**
	 * Description: Setting database name to default if DBNAME variable is not set.
	 */
	private void set_default_db(){
		if (this.DBNAME.isEmpty()){
			this.set_name(EMSettings.DATABASE_NAME);
		}
			
	}

	
	
	
	
	
	
	
	
	/**
	 * Description: Initialization and creation of Tables on the database.
	 * @return
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

	
	
	
	
	
	
	
	/**
	 * Description: Drops all current database tables.
	 * @return
	 */
	private int drop_all_table(){
		int state = 0;
			
		state += this.drop_table(this.TABLE_venue);
		state += this.drop_table(this.TABLE_venue_bookings);
		state += this.drop_table(this.TABLE_events);
		state += this.drop_table(this.TABLE_budget);
		state += this.drop_table(this.TABLE_budget_optimized);
		
		return state;
	}
	
	
	
	
	
	
	
	
	/**
	 * Description: Creates all required tables on the database for the program.
	 * @return
	 */
	private int create_all_table(){
		
		try {

			//Creating table for EVENTS
			this.DBQUERY.execute("CREATE TABLE " + this.TABLE_events + " ("+
					"'event_id' INTEGER PRIMARY KEY AUTOINCREMENT,"+
					"'name' TEXT NOT NULL," +
					"'description' TEXT," +
					"'budget' DOUBLE," +
					"'startdate' TEXT," +
					"'enddate' TEXT," +
					"'starttime' TEXT," +
					"'endtime' TEXT" +
					")");
			
			
			//Creating table for VENUEs
			this.DBQUERY.execute("CREATE TABLE " + this.TABLE_venue + " ("+
					"'venue_id' INTEGER PRIMARY KEY AUTOINCREMENT,"+
					"'name' TEXT NOT NULL," +
					"'address' TEXT," +
					"'description' TEXT," +
					"'capacity' INTEGER NOT NULL DEFAULT (0)," +
					"'cost' INTEGER NOT NULL DEFAULT (0)"+
					")");
		

			
			//Creating table for the Booking of Venues
			//This table saves the list of booked venue timings.
			this.DBQUERY.execute("CREATE TABLE " + this.TABLE_venue_bookings + " ("+
					"'booking_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
					"'event_id' INTEGER NOT NULL," +
					"'venue_id' INTEGER NOT NULL,"+
					"'time_start' TEXT," +
					"'time_end' TEXT"+
					")");	

		
		
			//Creating table for the Budget Optimization function
			//This table saves the initial list of items
			this.DBQUERY.execute("CREATE TABLE " + this.TABLE_budget + " ("+
					"'budget_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
					"'event_id' INTEGER NOT NULL DEFAULT (-1),"+
					"'name' TEXT," +
					"'price' INTEGER," +
					"'satisfaction' INTEGER," +
					"'type' TEXT" +
					")");	
			
			

			
			//Creating table for the Budget Optimization function
			//This table saves the optimized list of items
			this.DBQUERY.execute("CREATE TABLE " + this.TABLE_budget_optimized + " ("+
					"'budget_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
					"'event_id' INTEGER NOT NULL DEFAULT (-1),"+
					"'name' TEXT," +
					"'price' INTEGER," +
					"'satisfaction' INTEGER," +
					"'type' TEXT" +
					")");			

			return 1;
			
			
		} catch (SQLException e) {

			return 0;
		}
		
	}
	
	
	
	
	
	
	
	
	/**
	 * Description: Gets the number of tables within the database.
	 * @param num
	 * @return
	 */
	public int verify_table_count(boolean num){
		int count = 0;
		try {
			
	         PreparedStatement pstm = this.DBCON.prepareStatement(
	        		 "SELECT name FROM " +
                     " sqlite_master " +
                     " WHERE "+ 
                     "	  name='" + this.TABLE_events + "'" +
                     " OR name='" + this.TABLE_budget + "'" +
                     " OR name='" + this.TABLE_budget_optimized + "'" +
                     " OR name='" + this.TABLE_venue + "'" +
                     " OR name='" + this.TABLE_venue_bookings + "'"
                     );
			
			ResultSet result = pstm.executeQuery();
			
			while (result.next()){
				count++;
			}
			
			
			if (num)
				return count;
			
			if (count == 5)
				return 1;
			else
				return 0;
			
		} catch (SQLException e) {
			return 0;
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * Description: Dropping the named table.
	 * @param name
	 * @return
	 */
	private int drop_table(String name){
		
		try {
			this.DBQUERY.executeUpdate("DROP TABLE IF EXISTS " + name + ";");
			
			return 1;
			
		} catch (SQLException e) {
			
			return 0;
		}	
	}
	
	
	
	
	
	
	
	/**
	 * Description: Clears the named table.
	 * @param name
	 * @return
	 */
	private int truncate_table(String name){
		
		try {
			this.DBQUERY.executeUpdate("TRUNCATE TABLE IF EXISTS " + name + ";");
			
			return 1;
			
		} catch (SQLException e) {
			
			return 0;
		}	
	}
	
	
	
	

	/**
	 * Description: Resets variables and the database to default.
	 */
	public void reset(){
		this.DBCON = null;
		this.DBQUERY = null;	
		this.PREPSTATEM = null;
		
		
		this.init();
		
	}
	
	
	
	
	/**
	 * Description: Resets database to default.
	 */
	public void reset_clear(){
		this.truncate_table(TABLE_venue);
		this.truncate_table(TABLE_events);
		this.truncate_table(TABLE_venue_bookings);
		this.truncate_table(TABLE_budget);
		this.truncate_table(TABLE_budget_optimized);
		
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
					this.PREPSTATEM = this.DBCON.prepareStatement("INSERT INTO " + this.TABLE_events + " (name, description, startdate, enddate, starttime, endtime, budget) VALUES (?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
				
				else if (type.compareTo("venue") == 0)
					this.PREPSTATEM = this.DBCON.prepareStatement("INSERT INTO " + this.TABLE_venue + " (name, address, description, capacity, cost) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
					
				else if (type.compareTo("bookings") == 0)
					this.PREPSTATEM = this.DBCON.prepareStatement("INSERT INTO " + this.TABLE_venue_bookings + " (event_id, venue_id,  time_start, time_end) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
					
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
	public int add_event(String name, String description, double budget, String startdate, String enddate, String starttime, String endtime){
		
		
		if (this.EMDB_DEBUGGING){
			this.out("INSERTING EVENT:" + 
					" \n + NAME: " + name +
					" \n + DESCRIPTION: " + description +
					" \n + BUDGET: " + budget +
					" \n + START DATE: " + startdate +
					" \n + END DATE: " + enddate +
					" \n + START TIME: " + starttime +
					" \n + END TIME: " + endtime
					);
		}
		
		
		
		this.add_prepare("event");
		
		try {
			ResultSet result;
			
			this.PREPSTATEM.setString(1, name);
			this.PREPSTATEM.setString(2, description);
			this.PREPSTATEM.setString(3, startdate);
			this.PREPSTATEM.setString(4, enddate);
			this.PREPSTATEM.setString(5, starttime);
			this.PREPSTATEM.setString(6, endtime);
			this.PREPSTATEM.setDouble(7, budget);
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
		if (this.EMDB_DEBUGGING){
			this.out("INSERTING VENUE:" + 
					" \n + NAME: " + name  +
					" \n + ADDRESS: " + address +
					" \n + DESCRIPTION: " + description +
					" \n + CAPACITY: " + capacity +
					" \n + COST: " + cost
					);
		}
		
		
		
		
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
	public int add_booking(int event_id, int venue_id, String time_start , String time_end){
		
		if (this.EMDB_DEBUGGING){
			this.out("INSERTING BOOKING:" + 
					" \n + EVENT ID:" + event_id  +
					" \n + VENUE ID: " + venue_id +
					" \n + START DTG: " + time_start +
					" \n + END DTG: " + time_end
					);
		}
		
		
		
		
		this.add_prepare("bookings");
		try {
			ResultSet result;
			
			this.PREPSTATEM.setInt(1, event_id);
			this.PREPSTATEM.setInt(2, venue_id);
			this.PREPSTATEM.setString(3, time_start);
			this.PREPSTATEM.setString(4, time_end);
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
		
		if (this.EMDB_DEBUGGING){
			this.out("INSERTING BUDGET:" + 
					" \n + EVENT ID:" + event_id  +
					" \n + NAME: " + name +
					" \n + PRICE: " + price +
					" \n + SATISFACTION: " + satisfaction +
					" \n + TYPE: " + type
					);
		}
		
		
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
	public void add_event(String name, String description, double budget, String startdate, String enddate, String starttime, String endtime, boolean batch){
		
	   	try {
			this.PREPSTATEM.setString(1, name);
			this.PREPSTATEM.setString(2, description);
			this.PREPSTATEM.setString(3, startdate);
			this.PREPSTATEM.setString(4, enddate);
			this.PREPSTATEM.setString(5, starttime);
			this.PREPSTATEM.setString(6, endtime);
			this.PREPSTATEM.setDouble(7, budget);
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
	public Eventitem get_event(int id){
		
		if (this.EMDB_DEBUGGING){
			this.out("GET EVENT:" + 
					" #" + id 
					);
		}
		
		
		//create empty event container here
		Eventitem item = null;

		try {
			 
			String query = this.select_all(
					this.TABLE_events, 
					"event_id="+ id , 
					1);
			
			ResultSet result = this.DBQUERY.executeQuery(query);
			while (result.next()) {
			
				
				item = new Eventitem(
						result.getString("name"),
						result.getString("startdate"),
						result.getString("enddate"),
						result.getString("starttime"),
						result.getString("endtime")
						);
				item.setDescription(result.getString("description"));
				item.addBVI(this.get_booking_info(result.getInt("event_id")));
				item.setBudget(result.getDouble("budget"));
			}
			
			return item;
			
		} catch (SQLException e) {
			
			return null;
			
		}
		
		
	}
	
	
	
	public Vector<Eventitem> get_event_list(){

		if (this.EMDB_DEBUGGING){
			this.out("GET EVENT LIST");
		}
		
		
		Vector<Eventitem> list = new Vector<Eventitem>();
		
		try {
			String query = this.select_all(
					this.TABLE_events, 
					"", 
					0);	
			
			ResultSet result;
			result = this.DBQUERY.executeQuery(query);
			
			
			while (result.next()) {
	
				 
				Eventitem item = new Eventitem(
						result.getString("name"),
						result.getString("startdate"),
						result.getString("enddate"),
						result.getString("starttime"),
						result.getString("endtime")
						);
				item.setDescription(result.getString("description"));
				item.setID(result.getInt("event_id"));
				item.setBudget(result.getDouble("budget"));
				list.add(item);
				
			}
	
			result.close();
			return list;
			
			
		} catch (SQLException e) {
			return null;
		}
	}
	
	
	
	
	
	
	/*
	 * Getting a venue by the venue id as set in the database
	 */
	public Venue get_venue(int id){
		
		
		if (this.EMDB_DEBUGGING){
			this.out("GET VENUE:" + 
					" #" + id 
					);
		}
		
		
		Venue place = new Venue();
		
		 try {
			 
			 
			String query = this.select_all(
					this.TABLE_venue, 
					"venue_id="+ id, 
					1);	
	
			
			ResultSet result = this.DBQUERY.executeQuery(query);
			
			
			
			while (result.next()) {
				place.updateName(result.getString("name"));
				place.updateAddress(result.getString("address"));
				place.updateDescription(result.getString("description"));
				place.updateCost(result.getInt("cost"));
				place.updateMaxCapacity(result.getInt("capacity"));
			}

			
			result.close();
			
			return place;
			
		} catch (SQLException e) {
			
			return null;
		}
		
		
		
	}
	
	
	
	
	
	/* 
	 * Get a list of venues. 
	 * The clause is optional
	 */
	public Vector<Venue> get_venue_list(String clause, int upper, int lower){

		if (this.EMDB_DEBUGGING){
			this.out("GET VENUE LIST"
					);
		}
		
		
		String query = "";
		Vector<Venue> list = new Vector<Venue>();
		
		
		 try {
			
			query = this.select_all(
						this.TABLE_venue, 
						"",
						0);	
		
			
			ResultSet result = this.DBQUERY.executeQuery(query);
			
			while (result.next()) {
				
				int cost = result.getInt("cost");
				int capacity = result.getInt("capacity");
				
				if ( (upper == 0 && lower == 0) 
						|| (clause.compareTo("cost") == 0 && cost <= upper && cost >= lower)
						|| (clause.compareTo("capacity") == 0 && capacity <= upper && capacity >= lower )
						)
				{	
					
					Venue place = new Venue();
					place.updateID(result.getInt("venue_id"));
					place.updateName(result.getString("name"));
					place.updateAddress(result.getString("address"));
					place.updateDescription(result.getString("description"));
					place.updateCost(cost);
					place.updateMaxCapacity(capacity);
					
					list.add(place);
				}
			}
			
			result.close();
			return list;
			
		} catch (SQLException e) {
			
			return null;
			
		}
		
	}
	
	
	
	
	
	
	
	
	/*
	 * Getting a list of bookings from db
	 */
	public Vector<TimeSlot> get_bookings(int id, String type){
		
		
		if (this.EMDB_DEBUGGING){
			this.out("GET BOOKINGS OF: " + 
					" #" + id +
					" ("+ type +")"
					);
		}
		
		
		//create empty venue container here
		String query = "";
		
		Vector<TimeSlot> list = new Vector<TimeSlot>();
		
		 try {
			 
			
			if (type.compareTo("event") == 0){
				query = this.select_all(
							this.TABLE_venue_bookings, 
							"event_id="+ id , 
							0);	
			}else if (type.compareTo("venue") == 0){
			
				query = this.select_all(
							this.TABLE_venue_bookings, 
							"venue_id="+ id , 
							0);	
			} else{
				query = this.select_all(
							this.TABLE_venue_bookings, 
							"venue_id="+ id , 
							0);	
			}
			 

			ResultSet result = this.DBQUERY.executeQuery(query);
			
			while (result.next()) {
			
				list.add(
					new TimeSlot(
						result.getInt("booking_id"),
						new DateHour(result.getString("time_start")),
						new DateHour(result.getString("time_end"))
						)
					);
			}
			
			
			result.close();
			
			
			return list;
			
		} catch (SQLException e) {
			
			return null;
			
		}
		
		
	}
	
	
	/*
	 * Getting a list of bookings from db
	 */
	public Vector<BookedVenueInfo> get_booking_info(int id){
		
		
		if (this.EMDB_DEBUGGING){
			this.out("GET BOOKINGS OF: " + 
					" #" + id
					);
		}
		
		
		
		Vector<BookedVenueInfo> list = new Vector<BookedVenueInfo>();
		
		 try {
			 
			this.PREPSTATEM = this.DBCON.prepareStatement("SELECT * FROM "+this.TABLE_venue_bookings + " WHERE event_id=?;", Statement.RETURN_GENERATED_KEYS);
			this.PREPSTATEM.setInt(1, id);	
			 
			ResultSet result = this.PREPSTATEM.executeQuery();
			System.out.println(result);

			while (result.next()) {
				TimeSlot timing = new TimeSlot(new DateHour(result.getString("time_start")),new DateHour(result.getString("time_end")));
				Venue venue = this.get_venue(result.getInt("venue_id"));
				BookedVenueInfo info = new BookedVenueInfo(venue, timing);
				list.add(info);
			}
	
			result.close();
			
			return list;
			
		} catch (SQLException e) {
			
			return null;
			
		}
		
	}
	
	
	
	
	
	/*
	 * Get a budget list attached to the event by it's id
	 */
	public Vector<Item> get_budget_list(int id, boolean optimized){


		
		if (this.EMDB_DEBUGGING){
			this.out("GET BUDGET LIST: " + 
					" #" + id +
					" ("+ optimized +")"
					);
		}
		
	
		Vector<Item> list = new Vector<Item>();
		
		try {
			String query = "";
			
			
			if (optimized){
				query = this.select_all(
						this.TABLE_budget_optimized, 
						"event_id="+ id, 
						0);
			}else{
				query = this.select_all(
						this.TABLE_budget, 
						"event_id="+ id, 
						0);
			}
			
			
			ResultSet result = this.DBQUERY.executeQuery(query);
	
		    while (result.next()) {
			      Item current = new Item(result.getInt("event_id"), result.getString("name"), result.getInt("price"), result.getInt("satisfaction"), result.getString("type"));
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
		
		
		if (this.EMDB_DEBUGGING){
			this.out("DEL BUDGET LIST: " + 
					" #" + id
					);
		}
		
		
		String sql ="";
		
		
		try {
			sql = "DELETE FROM " + this.TABLE_budget + " WHERE event_id="+id;
			this.DBQUERY.execute(sql);
			sql = "DELETE FROM "+this.TABLE_budget_optimized +" WHERE event_id="+id;
			this.DBQUERY.execute(sql);
			
			
		} catch (SQLException e) {
		
		}
			
			
	}
	
	
	
	
	
	
	public void delete_event(int id){
		
		
		if (this.EMDB_DEBUGGING){
			this.out("DEL EVENT: " + 
					" #" + id
					);
		}
		
		
		String sql = "";
		
		try {
			sql = "DELETE FROM " + this.TABLE_events + " WHERE event_id="+id;
			
			this.DBQUERY.execute(sql);
			
			this.delete_budget_list(id);
			this.delete_all_bookings(id, "event");
			
		} catch (SQLException e) {
		
		}
		
		
	}
	
	

	
	
	public void delete_venue(int id){
		
		
		if (this.EMDB_DEBUGGING){
			this.out("DEL VENUE: " + 
					" #" + id
					);
		}
		
		
		String sql = "";
		try {
			sql = "DELETE FROM " + this.TABLE_venue + " WHERE venue_id="+id;
			this.DBQUERY.execute(sql);

			this.delete_all_bookings(id, "venue");
			
		} catch (SQLException e) {
		
		}
	}

	
	
	
	public void delete_booking(int id){
		
		if (this.EMDB_DEBUGGING){
			this.out("DEL BOOKING: " + 
					" #" + id
					);
		}
		
		
		String sql = "";
		try {
			
			sql = "DELETE FROM " + this.TABLE_venue_bookings + " WHERE booking_id="+id;

			this.DBQUERY.execute(sql);

		} catch (SQLException e) {
		
		}
	}
	
	
	public void delete_all_bookings(int id, String type){
		
		if (this.EMDB_DEBUGGING){
			this.out("DEL BOOKING: ALL" + 
					" #" + id +
					" ("+type+")"
					);
		}
		
		
		String sql = "";
		try {
			
			if (type.compareTo("event") == 0)
				sql = "DELETE FROM " + this.TABLE_venue_bookings + " WHERE event_id="+id;
			else
				sql = "DELETE FROM " + this.TABLE_venue_bookings + " WHERE venue_id="+id;
			
			this.DBQUERY.execute(sql);

		} catch (SQLException e) {
		
		}
	}

	
	
	/*
	 * ***********************************
	 * 
	 * SETUP
	 * -UPDATE REQUEST
	 * 
	 * ***********************************
	 */
	
	
	/**
	 * Description: Updating details of event to database.
	 * @param id
	 * @param name
	 * @param description
	 * @param budget
	 * @param startdate
	 * @param enddate
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public int update_event(int id, String name, String description, double budget, String startdate, String enddate, String starttime, String endtime){
		
		
		if (this.EMDB_DEBUGGING){
			this.out("UPDATE EVENT: " + 
					" #"+ id +
					" \n + NAME: " + name +
					" \n + DESCRIPTION: " + description +
					" \n + BUDGET: " + budget +
					" \n + START DATE: " + startdate +
					" \n + END DATE: " + enddate +
					" \n + START TIME: " + starttime +
					" \n + END TIME: " + endtime
					);
		}
		
		
		String query = "";
		try{
			
			query = "UPDATE " + this.TABLE_events +
					" SET name='" + name + "'" 
					+ ", description='" + description + "'"
					+ ", budget='" + budget + "'"
					+ ", startdate='" + startdate + "'"
					+ ", enddate='" + enddate + "'"
					+ ", starttime='" + starttime + "'"
					+ ", endtime='" + endtime + "'"
					+ "  WHERE event_id=" + id + ";";
			
			
			this.DBQUERY.execute(query);
			
		
	
			return 1;
		
			
		}catch (SQLException e){
			return 0;
		}
	}
	
	
	
	
	/*
	 * ***********************************
	 * 
	 * SETUP
	 * - SEARCH
	 * 
	 * ***********************************
	 */
	
	
	/**
	 * Description: Search venue database by name.
	 * @param name
	 * @return
	 */
	public Vector<Venue> find_venue_by_name(String name){
		
		
		if (this.EMDB_DEBUGGING){
			this.out("FIND VENUE: " + 
					" NAME = " + name
					);
		}
		
		
		String query = "SELECT * FROM " + this.TABLE_venue + " WHERE name LIKE '%%%s%%'";
		name = name.replaceAll("[^\\w]", " ");
		query = String.format(query, name);
		Vector<Venue> list = new Vector<Venue>();
		
		try {

			this.out(query);
			ResultSet result = this.DBQUERY.executeQuery(query);
			
			
			while (result.next()) {
			    	Venue place = new Venue();
					place.updateID(result.getInt("venue_id"));
					place.updateName(result.getString("name"));
					place.updateAddress(result.getString("address"));
					place.updateDescription(result.getString("description"));
					place.updateCost(result.getInt("cost"));
					place.updateMaxCapacity(result.getInt("capacity"));
					
					list.add(place);
			}
			result.close();

			return list;
			
		} catch (SQLException e) {
			return null;
		}
		
		
	}
	
	
	
	
	
	
	
	/*
	 * ***********************************
	 * 
	 * QUERY HELPERS
	 * 
	 * ***********************************
	 */
	
	/**
	 * Description: SQLite "SELECT ALL" query helper.
	 * @param name
	 * @param where
	 * @param limit
	 * @return
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
	
	
	/**
	 * Description: Println helper.
	 * @param msg
	 */
	public void out(String msg){
		System.out.println(msg);
	}
	
	
	/**
	 * Description: Test for the running mode of the SQLite library.
	 * @return
	 */
	public String testNative(){
		return String.format("SQLite running in %s mode", org.sqlite.SQLiteJDBCLoader.isNativeMode() ? "native" : "pure-java");
	}

	
	/**
	 * Description: Determines if debug messages shown and other debug helpers are executed.
	 * @param state
	 */
	public void set_debug(boolean state){
		this.EMDB_DEBUGGING = state;
	}

	

	

	
	
}
