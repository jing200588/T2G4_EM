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
		this.eventsID 			= 	this.eventsTable.addColumn("events_id", "INTEGER PRIMARY KEY AUTOINCREMENT", null);
		this.eventsName 		= 	this.eventsTable.addColumn("name", "TEXT NOT NULL", null);
		this.eventsDescription	= 	this.eventsTable.addColumn("description", "TEXT", null);
		this.eventsBudget		= 	this.eventsTable.addColumn("budget", "TEXT", null);
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
	
	public void setup(){
		String sql 	=	new CreateTableQuery(this.eventsTable, true)
						.validate().toString();
		
		if (EMDBSettings.DEVELOPMENT)
			this.dMsg("Setup Event: "+ sql);
		
		
		this.query(sql);
	}
	
	
	
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
	public void addEvent(){
		
	}
	
	
	
	
	
	/**
	 * Get details of an event
	 */
	public void getEvent(){
		
	}
	
	
	
	
	
	/**
	 * Modifies details of an event
	 */
	public void updateEvent(){
		
	}
	
	
	
	
	/**
	 * Deletes an Event
	 */
	public void deleteEvent(){
		
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