

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.healthmarketscience.sqlbuilder.*;
import com.healthmarketscience.sqlbuilder.dbspec.basic.*;



/**
 * Database Extended Class - Events
 * 
 * @author JunZhi
 * @version 1.0
 * @param <Eventitem>
 */
class EMDBEvent extends EMDBBase{
    
    private DbTable 	eventsTable;
    private DbColumn 	eventsID;
    private DbColumn 	eventsName;
    private DbColumn 	eventsDescription;
    private DbColumn 	eventsBudget;
    private DbColumn 	eventsStartDate;
    private DbColumn 	eventsEndDate;
    private DbColumn 	eventsStartTime;
    private DbColumn 	eventsEndTime;
	

    
    /**
     * Constructor
     */
	public EMDBEvent(){
		super();
		this.setTable();
	}

	
	
	
	/**
	 * Set up the table structure.
	 */
	private void setTable(){
		
		this.eventsTable 		= 	this.schema.addTable(EMDBSettings.TABLE_EVENTS);
		this.eventsID 			= 	this.eventsTable.addColumn("event_id", "INTEGER PRIMARY KEY AUTOINCREMENT", null);
		this.eventsName 		= 	this.eventsTable.addColumn("name", "TEXT NOT NULL", null);
		this.eventsDescription	= 	this.eventsTable.addColumn("description", "TEXT", null);
		this.eventsBudget		= 	this.eventsTable.addColumn("budget", "DOUBLE", null);
		this.eventsStartDate	= 	this.eventsTable.addColumn("startdate", "TEXT", null);
		this.eventsEndDate		= 	this.eventsTable.addColumn("enddate", "TEXT", null);
		this.eventsStartTime	= 	this.eventsTable.addColumn("starttime", "TEXT", null);
		this.eventsEndTime		= 	this.eventsTable.addColumn("endtime", "TEXT", null);
		
	}
	
	
	
	
	
	
	/*
	 * ******************************************************
	 * 
	 * Setup and Management
	 * 
	 * ******************************************************
	 */
	
	
	/**
	 * CREATE the database tables
	 */
	public void setup(){
		String sql 	=	new CreateTableQuery(this.eventsTable, true)
						.validate().toString();
		
		if (EMDBSettings.DEVELOPMENT)
			this.dMsg("Setup Event: "+ sql);
		
		
		this.runQuery(sql);
	}
	
	
	
	/**
	 * DROP the database tables
	 */
	public void cleanup(){
		String sql 	=	DropQuery.dropTable(this.eventsTable)
						.validate().toString();
		
		if (EMDBSettings.DEVELOPMENT)
			this.dMsg("Cleanup Event: "+ sql);
		
		this.runQuery(sql);
	}
	
	
	
	
	
	
	
	
	/*
	 * ******************************************************
	 * 
	 * CRUD Options
	 * 
	 * ******************************************************
	 */
	
	
	
	
	

	
	/**
	 * Creates an Event.
	 * @param aName
	 * @param aDescription
	 * @param aBudget
	 * @param aStartDate
	 * @param aEndDate
	 * @param aStartTime
	 * @param aEndTime
	 * @return
	 */
	public int addEvent(
			String aName, 
			String aDescription, 
			double aBudget, 
			String aStartDate, 
			String aEndDate,
			String aStartTime, 
			String aEndTime
			)
	{
		String sql	=	new InsertQuery(this.eventsTable)
			      		.addColumn(this.eventsName, aName)
			      		.addColumn(this.eventsDescription, aDescription)
			      		.addColumn(this.eventsBudget, aBudget)
			      		.addColumn(this.eventsStartDate, aStartDate)
			      		.addColumn(this.eventsEndDate, aEndDate)
			      		.addColumn(this.eventsStartTime, aStartTime)
			      		.addColumn(this.eventsEndTime, aEndTime)
			      		.validate().toString();
		
		

		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("ADD AN EVENT");
			this.dMsg(sql);
		}
		
		try {

			this.connect();
			ResultSet result = this.runQueryResults(sql);
			int id = 0;
			
			if (result.next()){
				id = result.getInt("event_id");
			}
			
			result.close();
			this.disconnect();
			return id;
			
			
		} catch (SQLException e) {
			return 0;
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Get details of an event
	 * @param aEventID
	 * @return
	 */
	public Eventitem getEvent(int aEventID){
		
		
		String sql = new SelectQuery()
						.addAllColumns()
						.addFromTable(this.eventsTable)
						.addCondition(BinaryCondition.equalTo(this.eventsID, aEventID))
						.validate().toString();
		
		Eventitem item = null;
		

		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("GET AN EVENT #"+aEventID);
			this.dMsg(sql);
		}
		
				
		
		try {
			this.connect();
			ResultSet result = this.runQueryResults(sql);
	
			while(result.next()){
				item = new Eventitem(
						result.getString("name"),
						result.getString("startdate"),
						result.getString("enddate"),
						result.getString("starttime"),
						result.getString("endtime")
						);
				item.setDescription(result.getString("description"));
				item.setBudget(result.getDouble("budget"));
				item.setID(result.getInt("event_id"));
			}
		
			result.close();
			this.disconnect();
			
	
			
			return item;
			
		} catch (SQLException e) {
			return null;
		}

	}
	
	
	
	
	
	
	
	/**
	 * Wrapper for getting list of events
	 * @return
	 */
	public Vector<Eventitem> getEventList(){
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("GET AN EVENT LIST");
		}	
		
		return this.getEventListAll();

	}
	
	
	
	
	
	/**
	 * Gets a list of all events in the Database.
	 * @return
	 */
	private Vector<Eventitem> getEventListAll(){

		Vector<Eventitem> list = new Vector<Eventitem>();
		
		String sql 	= new SelectQuery()
						.addAllColumns()
						.addFromTable(this.eventsTable)
						.validate().toString();	
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("GET ALL EVENTS LIST");
			this.dMsg(sql);
		}
			
		
		try {
			this.connect();
			ResultSet result = this.runQueryResults(sql);
			
			while(result.next()){
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
			this.disconnect();
			return list;
			
			
		} catch (SQLException e) {
			return  null;
		}
			
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Modifies details of an event.
	 * @param aEventID
	 * @param aName
	 * @param aDescription
	 * @param aBudget
	 * @param aStartDate
	 * @param aEndDate
	 * @param aStartTime
	 * @param aEndTime
	 * @return
	 */
	public int updateEvent(
			int aEventID,
			String aName, 
			String aDescription, 
			double aBudget, 
			String aStartDate, 
			String aEndDate,
			String aStartTime, 
			String aEndTime
			)
	{
		String sql	=	new UpdateQuery(this.eventsTable)
						.addSetClause(this.eventsName, aName)
						.addSetClause(this.eventsDescription, aDescription)
						.addSetClause(this.eventsBudget, aBudget)
						.addSetClause(this.eventsStartDate, aStartDate)
						.addSetClause(this.eventsEndDate, aEndDate)
						.addSetClause(this.eventsStartTime, aStartTime)
						.addSetClause(this.eventsEndTime, aEndTime)	
						.addCondition(BinaryCondition.equalTo(this.eventsID, aEventID))
						.validate().toString();
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Deletes an Event.
	 * @param aEventID
	 * @return
	 */
	public int deleteEvent(int aEventID){
		String sql 	=	new DeleteQuery(this.eventsTable)
						.addCondition(BinaryCondition.equalTo(this.eventsID, aEventID))
						.validate().toString();


		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("DELETE EVENT #"+aEventID);
			this.dMsg(sql);
		}
		
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;
	}
	
	
	
	
	
	

	
	
}