
import java.io.File;
import java.sql.*;
import java.util.Vector;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.ComboCondition;
import com.healthmarketscience.sqlbuilder.CreateTableQuery;
import com.healthmarketscience.sqlbuilder.DropQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
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
	protected String dbName = "";
	protected Connection dbCon = null;
	protected Statement dbQuery = null;
	
	
	protected boolean dbDebug = false;

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
		this.setDebug(EMDBSettings.DEVELOPMENT);
		
		this.setDefaultDB();
		this.loadLibrary();
	}

	public EMDBBase(String aName){	
		this.setDebug(EMDBSettings.DEVELOPMENT);
		
		this.dbName = aName;
		this.setDefaultDB();
		this.loadLibrary();
	}

	
	public EMDBBase(String aName, boolean aDebugState){
		this.setDebug(aDebugState);
		
		this.dbName = aName;
		this.setDefaultDB();
		this.loadLibrary();
	}
	
	
	/**
	 * Debug state override
	 * @param state
	 */
	public void setDebug(boolean aDebugState){
		this.dbDebug = aDebugState;
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
	public void connect(){
		try {
			this.dbCon = DriverManager.getConnection("jdbc:sqlite:"+dbName);
			this.dbQuery = this.dbCon.createStatement();
			
		} catch (SQLException e) {}
	}
	
	
	
	
	/**
	 * Disconnecting from the database.
	 * @return
	 */
	public void disconnect(){
		try {
			this.dbCon.close();
			
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

		
		if (this.dbName.isEmpty()){
			this.setName(EMDBSettings.DATABASE_NAME);
		}	
		
		
		if (EMDBSettings.DEVELOPMENT || this.dbDebug){
			this.dMsg("EMDB - SETTING DEFAULT DB - " + this.dbName);
		}
		
		this.setFile();
	}

	
	
	
	
	
	/**
	 * Setting of the database name.
	 * @param name
	 */
	public void setName(String aName){
		dbName = aName;
	}
	

	
	

	
	
	

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
	protected int runQuery(String aSql){
		
		if (this.dbDebug){
			this.dMsg("EMDB - RUNNING QUERY (NORMAL)");
		}
		
		try {	
			Statement query = this.dbCon.createStatement();
			query.executeQuery(aSql);
			
			return 1;
			
		} catch (SQLException e) {
			return 0;
		}
	}
	


	
	
	
	protected Vector<Object[]> runQueryResults(String aSql){
		
		
		if (this.dbDebug){
			this.dMsg("EMDB - RUNNING QUERY (RESULTS)");
		}
		
		try {	
	
			PreparedStatement query = this.dbCon.prepareStatement(aSql, Statement.RETURN_GENERATED_KEYS);
			query.execute();
			ResultSet rs = query.getResultSet();
			
			Vector<Object[]> result = new Vector<Object[]>(); 
			
			while (rs.next()){
				int cols = rs.getMetaData().getColumnCount();
				Object[] row = new Object[cols];
				
				for (int i=0; i<cols; i++){
					row[i] = rs.getObject(i+1);
				}
				result.add(row);
			}

			return result;
			
		} catch (SQLException e) {
			
			return new Vector<Object[]>();
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
	protected void queue(String aSql){
		
		if (this.dbDebug){
			this.dMsg("EMDB - ADDING TO QUEUE");
		}
		
		this.queue.add(aSql);
	}
	
	
	
	
	/**
	 * Execute batch processing.
	 * @return
	 */
	protected int commit(){
		
		
		if (this.dbDebug){
			this.dMsg("EMDB - COMMIT ALL QUERIES");
		}
		
		
		try {

			if(!this.queue.isEmpty()){
							
				this.connect();
							
				//insert all the batch queries.
				int size = this.queue.size();
				for (int i=0; i< size; i++){
					this.dbQuery.addBatch(this.queue.get(i));
				}
				
				//run the batch queries
				this.dbCon.setAutoCommit(false);
				this.dbQuery.executeBatch();
				this.dbCon.setAutoCommit(true);

				//DC and clear the temp vector queue.
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
		
		
		if (this.dbDebug){
			this.dMsg("EMDB - CREATE/CHECK FILE");
		}
		
		File findFile = new File(this.dbName);
		return findFile.isFile();
	}
	


	/**
	 * Shortcut to print out strings.
	 * @param msg
	 */
	protected void dMsg(String aMsg){
		System.out.println(aMsg);
	}
	
	
		
	

	
	
	/**
	 * Generic verfication process
	 * @return
	 */
	protected boolean verification(String aSql, int aTableTotal){
		
		if (this.dbDebug){
			this.dMsg("EMDB - VERIFICATION");
			this.dMsg("EMDB - " + aSql);
		}
		
		this.connect();
		
		Vector<Object[]> result = this.runQueryResults(aSql);
		int count = result.size();
		
		if (this.dbDebug){
			this.dMsg("EMDB - VERIFIED SIZE: "+count);
		}
		
		this.disconnect();
		
		if (count == aTableTotal)
			return true;
		
		return false;
	}
	
	
	
	
	
	
	
}
