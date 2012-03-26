
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
		try {	
			PreparedStatement pstm = this.dbCon.prepareStatement(sql);
			pstm.executeQuery();
			return 1;
			
		} catch (SQLException e) {
			return 0;
		}
	}
	


	
	
	
	protected ResultSet runQueryResults(String sql){
		try {	

			PreparedStatement pstm = this.dbCon.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet result = pstm.executeQuery();
			return result;
			
		} catch (SQLException e) {
			return null;
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
