import java.io.File;
import java.sql.*;
import java.util.Vector;

import com.healthmarketscience.sqlbuilder.dbspec.basic.*;


/**
 * 
 * The bridge and layer between the application models and the database engine/driver.
 * 
 * @author JunZhi
 * 
 */
public class EMDBBase{
	
	
	 
/*
 * 
 * *******************************************
 * NAMING CONVENTIONS
 * *******************************************
 * PRIVATE variables are in CAPS for the first word in the name
 * eg: TABLE_one, DBQUERY
 * 
 */
	 
	 
	
	
	/*
	 * ***********************************
	 * 
	 * CLASS WIDE VARIABLES
	 * 
	 * ***********************************
	 */
	protected String DBNAME = "";
	protected Connection DBCON = null;
	protected Statement DBQUERY = null;
	

	//Specifications and extended tables
   	protected DbSpec spec = new DbSpec();
   	protected DbSchema schema  = spec.addDefaultSchema();

	
	
	//Processing
   	//Batch Processing
   	Vector<String> queue = new Vector<String>();
	

	
	
	

	
	
	/**
	 * Constructor.
	 */
	public EMDBBase(){	
		this.setDefaultDB();
		this.loadLibrary();
	}

	public EMDBBase(String name){	
		this.DBNAME = name;
		this.setDefaultDB();
		this.loadLibrary();
	}

	
	
	/**
	 * JDBC SQLite loader
	 */
	private void loadLibrary(){
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {}
	}

	
	
	
	/*
	 * ***********************************
	 * 
	 * Connection Management
	 * 
	 * ***********************************
	 */
	/**
	 * Starting a connection
	 * @return
	 */
	private void connect(){
		try {
			this.DBCON = DriverManager.getConnection("jdbc:sqlite:"+DBNAME);
			this.DBQUERY = this.DBCON.createStatement();
			
		} catch (SQLException e) {}
	}
	
	
	
	
	/**
	 * Disconnecting from the database.
	 * @return
	 */
	private void disconnect(){
		try {
			this.DBCON.close();
			
		} catch (SQLException e) {}
	}
	
	
	
	
	

	/*
	 * ***********************************
	 * 
	 * Settings
	 * 
	 * ***********************************
	 */
	
	
	/**
	 * Setting database name to default if DBNAME variable is not set.
	 */
	private void setDefaultDB(){
		if (this.DBNAME.isEmpty()){
			this.set_name(EMDBSettings.DATABASE_NAME);
		}	
		this.setFile();
	}

	
	
	
	
	
	/**
	 * Setting of the database name.
	 * @param name
	 */
	public void set_name(String name){
		DBNAME = name;
	}
	

	
	
	
	/**
	 * Initialization of the database.
	 * @return
	 */
	/*
	public int init(){
		//DB Schema
		//Creating table for EVENTS
		String CT_events =	"CREATE TABLE " + this.TABLE_events + " ("+
							"'event_id' INTEGER PRIMARY KEY AUTOINCREMENT,"+
							"'name' TEXT NOT NULL," +
							"'description' TEXT," +
							"'budget' DOUBLE," +
							"'startdate' TEXT," +
							"'enddate' TEXT," +
							"'starttime' TEXT," +
							"'endtime' TEXT" +
							")";
		
		
		//Creating table for VENUEs
		String CT_venue = 	"CREATE TABLE " + this.TABLE_venue + " ("+
							"'venue_id' INTEGER PRIMARY KEY AUTOINCREMENT,"+
							"'name' TEXT NOT NULL," +
							"'address' TEXT," +
							"'description' TEXT," +
							"'capacity' INTEGER NOT NULL DEFAULT (0)," +
							"'cost' INTEGER NOT NULL DEFAULT (0)"+
							")";
	

		
		//Creating table for the Booking of Venues
		//This table saves the list of booked venue timings.
		String CT_bookings = 	"CREATE TABLE " + this.TABLE_venue_bookings + " ("+
								"'booking_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
								"'event_id' INTEGER NOT NULL," +
								"'venue_id' INTEGER NOT NULL,"+
								"'time_start' TEXT," +
								"'time_end' TEXT"+
								")";	

	
	
		//Creating table for the Budget Optimization function
		//This table saves the initial list of items
		String CT_budget = 	"CREATE TABLE " + this.TABLE_budget + " ("+
							"'budget_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
							"'event_id' INTEGER NOT NULL DEFAULT (-1),"+
							"'name' TEXT," +
							"'price' INTEGER," +
							"'satisfaction' INTEGER," +
							"'type' TEXT" +
							")";	
		
		

		
		//Creating table for the Budget Optimization function
		//This table saves the optimized list of items
		String CT_budget_optimized = 	"CREATE TABLE " + this.TABLE_budget_optimized + " ("+
										"'budget_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
										"'event_id' INTEGER NOT NULL DEFAULT (-1),"+
										"'name' TEXT," +
										"'price' INTEGER," +
										"'satisfaction' INTEGER," +
										"'type' TEXT" +
										")";	
	
		
		
		try{
			this.DBQUERY.addBatch(CT_events);
			this.DBQUERY.addBatch(CT_venue);
			this.DBQUERY.addBatch(CT_bookings);
			this.DBQUERY.addBatch(CT_budget);
			this.DBQUERY.addBatch(CT_budget_optimized);
			
			this.DBCON.setAutoCommit(false);
			this.DBQUERY.executeBatch();
			this.DBCON.setAutoCommit(true);
			
			
			return 1;
		}catch (Exception e){
			return 0;
		}
		
		
	}*/
	
	
	

	/*
	 * ***********************************
	 * 
	 * Generic Functions
	 * 
	 * ***********************************
	 */
	
	/**
	 * Generic Query. 
	 * Accepts a string, connect to DB, execute, and disconnect.
	 * @param sql
	 * @return
	 */
	protected int query(String sql){
		try {
			this.connect();
			this.DBQUERY.execute(sql);
			this.disconnect();
			
			
			return 1;
			
		} catch (SQLException e) {
			return 0;
		}
	}
	
	
	
	
	
	
	
	/* 
	 * Batch Processing
	 * **************************************
	 */
	

	/**
	 * Add a query for batch processing.
	 * @param sql
	 * @return
	 */
	protected void queue(String sql){
		this.queue.add(sql);
	}
	
	
	/**
	 * Execute batch processing.
	 * @return
	 */
	protected int commit(){
		try {

			
			if(!this.queue.isEmpty()){
				this.connect();
				
				int size = this.queue.size();
				for (int i=0; i< size; i++){
					this.DBQUERY.addBatch(this.queue.get(i));
				}
				
				this.DBCON.setAutoCommit(false);
				this.DBQUERY.executeBatch();
				this.DBCON.setAutoCommit(true);

				this.disconnect();
				this.queue.clear();
				
				return 1;
				
				
			}else{
				return 0;
			}
			
		} catch (SQLException e) {
			return 0;
		}
	}
	
	
	
	
	
	
	
	/*
	 * ***********************************
	 * 
	 * Helper Functions
	 * 
	 * ***********************************
	 */
	
	/**
	 * Set and Create file.
	 * @return
	 */
	protected boolean setFile(){
		File findFile = new File(this.DBNAME);
		return findFile.isFile();
	}
	
	
	/**
	 * Shortcut to print out strings.
	 * @param msg
	 */
	protected void dMsg(String msg){
		System.out.println(msg);
	}
	
	
		
}
