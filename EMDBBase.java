
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
	protected String dbName = "";
	protected Connection dbCon = null;
	protected Statement dbQuery = null;
	

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
		this.dbName = name;
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
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("SETTING DEFAULT DB - " + this.dbName);
		}
		
		this.setFile();
	}

	
	
	
	
	
	/**
	 * Setting of the database name.
	 * @param name
	 */
	public void setName(String name){
		dbName = name;
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
	protected int runQuery(String sql){
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("RUNNING QUERY (NORMAL)");
		}
		
		try {	
			Statement query = this.dbCon.createStatement();
			query.executeQuery(sql);
			
			return 1;
			
		} catch (SQLException e) {
			return 0;
		}
	}
	


	
	
	
	protected Vector<Object[]> runQueryResults(String sql){
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("RUNNING QUERY (RESULTS)");
		}
		
		try {	
	
			PreparedStatement query = this.dbCon.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
	protected void queue(String sql){
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("ADDING TO QUEUE");
		}
		
		this.queue.add(sql);
	}
	
	
	
	
	/**
	 * Execute batch processing.
	 * @return
	 */
	protected int commit(){
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("COMMIT ALL QUERIES");
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
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("CREATE/CHECK FILE");
		}
		
		File findFile = new File(this.dbName);
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
