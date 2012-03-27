

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.healthmarketscience.sqlbuilder.*;
import com.healthmarketscience.sqlbuilder.dbspec.basic.*;



/**
 * Database Extended Class - Events
 * @author JunZhi
 *
 */
class EMDBEvent extends EMDBBase{
    
	private DbTable 	masterTable = this.schema.addTable(EMDBSettings.TABLE_MASTER);
	private DbColumn	masterName	= this.masterTable.addColumn("name");
	
	
    private DbTable 	eventsTable;
    private DbColumn 	eventsID;
    private DbColumn 	eventsName;
    private DbColumn 	eventsDescription;
    private DbColumn 	eventsBudget;
    private DbColumn 	eventsStartDate;
    private DbColumn 	eventsEndDate;
    private DbColumn 	eventsStartTime;
    private DbColumn 	eventsEndTime;
    private DbColumn	eventsSchedule;
	

    
    /**
     * Constructor
     */
	public EMDBEvent(){
		super();
		this.setTable();
	}

	public EMDBEvent(String aName){
		super(aName);
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
		this.eventsSchedule		=	this.eventsTable.addColumn("schdeule", "TEXT", null);
		
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
		
		
		this.queue(sql);
		this.commit();
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
	
	
	
	
	/**
	 * VERIFY the database tables
	 * @return
	 */
	public boolean verify(){
		
		int tableTotal = 1;
				
		String sql = new SelectQuery()
						.addAllColumns()
						.addFromTable(this.masterTable)
						.addCondition(
									BinaryCondition.equalTo(this.masterName, EMDBSettings.TABLE_EVENTS)
						)
						.validate().toString();

		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("VERIFICATION - EVEMT TABLES");
			this.dMsg(sql);
		}
		
		this.connect();
		
		Vector<Object[]> result = this.runQueryResults(sql);
		int count = result.size();
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("VERIFIED SIZE: "+count);
		}
		
		this.disconnect();
		
		if (count == tableTotal)
			return true;
		
		return false;
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
			String aEndTime,
			String aSchedule
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
					      		.addColumn(this.eventsSchedule, aSchedule)
					      		.validate().toString();
		
		

		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("ADD AN EVENT");
			this.dMsg(sql);
		}
		
		this.connect();
		
		Vector<Object[]> result = this.runQueryResults(sql);
		int id = 0;
		
		if (result.size() > 0){
			id = Integer.parseInt(result.get(0)[0].toString());
		}
		
		this.disconnect();
		
		return id;
		

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

		
		
		this.connect();
		Vector<Object[]> result = this.runQueryResults(sql);
	
		int size = result.size();
		for (int i=0; i< size; i++){
			Object[] row = result.get(i);
			
			item = new Eventitem(
					row[1].toString(), //name
					row[4].toString(), //startdate
					row[5].toString(), //enddate
					row[6].toString(), //starttime
					row[7].toString() //endtime
					);
			item.setDescription( row[2].toString());
			item.setBudget( Double.parseDouble(row[3].toString()) );
			item.setID( Integer.parseInt(row[0].toString()) );
			
		}
					
		this.disconnect();
		
		return item;
		

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
			
		
		
		this.connect();
		Vector<Object[]> result = this.runQueryResults(sql);
		
		int size = result.size();
		for (int i=0; i< size; i++){
			Object[] row = result.get(i);
			
			Eventitem item = new Eventitem(
						row[1].toString(), //name
						row[4].toString(), //startdate
						row[5].toString(), //enddate
						row[6].toString(), //starttime
						row[7].toString() //endtime
					);
			item.setDescription( row[2].toString()); //description
			item.setBudget( Double.parseDouble(row[3].toString()) ); //budget
			item.setID( Integer.parseInt(row[0].toString()) ); //event_id
		
		
			list.add(item);
		}
			
		
		
		this.disconnect();
		return list;
			
		
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
			String aEndTime,
			String aSchedule
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
								.addSetClause(this.eventsSchedule, aSchedule)
								.addCondition(BinaryCondition.equalTo(this.eventsID, aEventID))
								.validate().toString();
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Update Event Schedule Only.
	 * @param aEventID
	 * @param aSchedule
	 * @return
	 */
	public int updateSchedule(int aEventID, String aSchedule){
		String sql	=	new UpdateQuery(this.eventsTable)
							.addSetClause(this.eventsSchedule, aSchedule)
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