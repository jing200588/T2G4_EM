import java.sql.ResultSet;
import java.sql.SQLException;

import com.healthmarketscience.sqlbuilder.*;
import com.healthmarketscience.sqlbuilder.dbspec.basic.*;



/**
 * Database Extended Class - Events
 * 
 * @author JunZhi
 * @version 1.0
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
	}
	
	
	
	
	
	
	
	
	/*
	 * ******************************************************
	 * 
	 * CRUD for events (SINGLE)
	 * 
	 * ******************************************************
	 */
	

	
	/**
	 * Creates an Event.
	 */
	public void addEvent(
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
		
		this.runQuery(sql);
	}
	
	
	
	
	
	/**
	 * Get details of an event
	 */
	public Eventitem getEvent(int aEventID){
		String sql = new SelectQuery()
						.addAllColumns()
						.addFromTable(this.eventsTable)
						.addCondition(BinaryCondition.equalTo(this.eventsID, aEventID))
						.validate().toString();
		
		Eventitem item = null;
		
		try {
			this.connect();
			ResultSet result = this.runQueryResults(sql);
	
			while(result.next()){
				//item = new Eventitem();
			}
		

			this.disconnect();
			
			return null;
			
		} catch (SQLException e) {
			return null;
		}

	}
	
	
	
	
	
	/**
	 * Modifies details of an event
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
		
		return this.runQuery(sql);
	}
	
	
	
	
	/**
	 * Deletes an Event
	 */
	public void deleteEvent(int aEventID){
		
	}
	
	

	/*
	 * ******************************************************
	 * 
	 * CRUD for events (multiple)
	 * 
	 * ******************************************************
	 */
	
	
	/**
	 * Wrapper for getting list of events
	 */
	public void getEventList(){
		
	}
	
	
	/**
	 * Gets a list of all events in the Database.
	 */
	private void getEventListAll(){
		
	}
	
	
}