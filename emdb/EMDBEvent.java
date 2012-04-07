package emdb;

import event.*;
import program.*;

import java.util.List;
import java.util.Vector;

import com.healthmarketscience.sqlbuilder.*;
import com.healthmarketscience.sqlbuilder.dbspec.basic.*;



/**
 * Database Extended Class - Events
 * @author JunZhi
 *
 */
public class EMDBEvent extends EMDBBase{
    
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
	
    private DbTable 	archiveTable;
    private DbColumn 	archiveID;
    private DbColumn	archiveEventID;
    private DbColumn 	archiveName;
    private DbColumn 	archiveDescription;
    private DbColumn 	archiveBudget;
    private DbColumn 	archiveStartDate;
    private DbColumn 	archiveEndDate;
    private DbColumn 	archiveStartTime;
    private DbColumn 	archiveEndTime;
    private DbColumn	archiveSchedule;

    
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

	public EMDBEvent(String aName, boolean aDebugState){
		super(aName, aDebugState);
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
		
		
		this.archiveTable 		= 	this.schema.addTable(EMDBSettings.TABLE_EVENTS_ARCHIVE);
		this.archiveID 			= 	this.archiveTable.addColumn("archive_id", "INTEGER PRIMARY KEY AUTOINCREMENT", null);
		this.archiveEventID 	= 	this.archiveTable.addColumn("event_id", "INTEGER", null);
		this.archiveName 		= 	this.archiveTable.addColumn("name", "TEXT NOT NULL", null);
		this.archiveDescription	= 	this.archiveTable.addColumn("description", "TEXT", null);
		this.archiveBudget		= 	this.archiveTable.addColumn("budget", "DOUBLE", null);
		this.archiveStartDate	= 	this.archiveTable.addColumn("startdate", "TEXT", null);
		this.archiveEndDate		= 	this.archiveTable.addColumn("enddate", "TEXT", null);
		this.archiveStartTime	= 	this.archiveTable.addColumn("starttime", "TEXT", null);
		this.archiveEndTime		= 	this.archiveTable.addColumn("endtime", "TEXT", null);
		this.archiveSchedule	=	this.archiveTable.addColumn("schdeule", "TEXT", null);
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
		String sql2 =	new CreateTableQuery(this.archiveTable, true)
								.validate().toString();
		
		
		if (this.dbDebug){
			this.dMsg(sql);
			this.dMsg(sql2);
		}
		
		
		this.queue(sql);
		this.queue(sql2);
		this.commit();
	}
	
	
	
	/**
	 * DROP the database tables
	 */
	public void drop(){
		String sql 	=	DropQuery.dropTable(this.eventsTable)
							.validate().toString();
		String sql2 =	DropQuery.dropTable(this.archiveTable)
							.validate().toString();
		
		
		if (this.dbDebug){
			this.dMsg(sql);
			this.dMsg(sql2);
		}
		
		this.queue(sql);
		this.queue(sql2);
		this.commit();
	}
	
	
	
	/**
	 * DROP the a specific database table
	 * @param aType
	 */
	public void drop(String aType){
		String sql = "";
		
		if (aType.compareTo("events") == 0){
			sql =	DropQuery.dropTable(this.eventsTable)
						.validate().toString();
				
		}else if (aType.compareTo("archive") == 0){
			sql =	DropQuery.dropTable(this.archiveTable)
						.validate().toString();
		}

		if (this.dbDebug){
			this.dMsg(sql);
		}
		
		if (!sql.isEmpty()){
			this.queue(sql);
			this.commit();
		}
	}
	
	
	
	
	/**
	 * "TRUNCATE" the database tables
	 */
	public void truncate(){
		String sql 	=	new DeleteQuery(this.eventsTable)
							.validate().toString();
		String sql2 =	new DeleteQuery(this.archiveTable)
						.validate().toString();
		
		
		if (this.dbDebug){
			this.dMsg(sql);
			this.dMsg(sql2);
		}
		
		this.queue(sql);
		this.queue(sql2);
		this.commit();
	}
	
	
	/**
	 * "TRUNCATE" a specific database table
	 * @param aType
	 */
	public void truncate(String aType){
		String sql = "";
		
		if (aType.compareTo("events") == 0){
			sql 	=	new DeleteQuery(this.eventsTable)
							.validate().toString();
				
		}else if (aType.compareTo("archive") == 0){
			sql =	new DeleteQuery(this.archiveTable)
							.validate().toString();
		}
		
		if (this.dbDebug){
			this.dMsg(sql);
		}
		
		if (!sql.isEmpty()){
			this.queue(sql);
			this.commit();
		}
	}
	
	/**
	 * VERIFY the database tables
	 * @return
	 */
	public boolean verify(){
		
		if (this.dbDebug){
			this.dMsg(EMDBSettings.TABLE_EVENTS);
			this.dMsg(EMDBSettings.TABLE_EVENTS_ARCHIVE);
		}
		
		
		String sql = new SelectQuery()
						.addAllColumns()
						.addFromTable(this.masterTable)
						.addCondition(
								ComboCondition.or(
									BinaryCondition.equalTo(this.masterName, EMDBSettings.TABLE_EVENTS),
									BinaryCondition.equalTo(this.masterName, EMDBSettings.TABLE_EVENTS_ARCHIVE)
								)
						)
						.validate().toString();

		return this.verification(sql, 2);
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
					      		.addColumn(this.eventsName, aName.replaceAll("[\']", ""))
					      		.addColumn(this.eventsDescription, aDescription.replaceAll("[\']", ""))
					      		.addColumn(this.eventsBudget, aBudget)
					      		.addColumn(this.eventsStartDate, aStartDate)
					      		.addColumn(this.eventsEndDate, aEndDate)
					      		.addColumn(this.eventsStartTime, aStartTime)
					      		.addColumn(this.eventsEndTime, aEndTime)
					      		.addColumn(this.eventsSchedule, aSchedule.replaceAll("[\']", ""))
					      		.validate().toString();
		
		

		if (this.dbDebug){
			this.dMsg("ADD AN EVENT");
			this.dMsg(sql);
		}
		
		//Constraints
		if (aName.isEmpty())
			return 0;
		
		this.connect();
		
		int id = this.runQueryKey(sql);
		
		this.disconnect();
		
		return id;
		

	}
	

	
	/**
	 * Creates an Event.
	 * @param aItem
	 * @return
	 */
	public int addEvent(EventItem aItem){
		return this.addEvent(
						aItem.getName(), 
						aItem.getDescription(),  
						aItem.getBudget(), 
						aItem.getStartDateTime().getDateRepresentation(), 
						aItem.getEndDateTime().getDateRepresentation(), 
						aItem.getStartDateTime().getTimeRepresentation(), 
						aItem.getEndDateTime().getTimeRepresentation(), 
						EventFlowEntry.getStringRepresentation(aItem.getEventFlow())
					);
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Get details of an event
	 * @param aEventID
	 * @return
	 */
	public EventItem getEvent(int aEventID){
		
		
		String sql = new SelectQuery()
							.addAllColumns()
							.addFromTable(this.eventsTable)
							.addCondition(BinaryCondition.equalTo(this.eventsID, aEventID))
							.validate().toString();
		
		EventItem item = null;
		

		if (this.dbDebug){
			this.dMsg("GET AN EVENT #"+aEventID);
			this.dMsg(sql);
		}

		
		
		this.connect();
		Vector<Object[]> result = this.runQueryResults(sql);
	
		int size = result.size();
		for (int i=0; i< size; i++){
			Object[] row = result.get(i);
			
			item = new EventItem(
					row[1].toString(), //name
					row[4].toString(), //startdate
					row[5].toString(), //enddate
					row[6].toString(), //starttime
					row[7].toString() //endtime
					);
			item.setDescription( row[2].toString());
			item.setBudget( Double.parseDouble(row[3].toString()) );
			item.setID( Integer.parseInt(row[0].toString()) );
			item.setEventFlow(EventFlowEntry.constructEventFlowEntryList(row[8].toString()));
			
		}
					
		this.disconnect();
		
		return item;
		

	}
	
	
	
	
	
	
	
	/**
	 * Wrapper for getting list of events
	 * @return
	 */
	public Vector<EventItem> getEventList(){
		if (this.dbDebug){
			this.dMsg("GET AN EVENT LIST");
		}	
		
		return this.getEventListAll();

	}
	
	
	
	
	
	/**
	 * Gets a list of all events in the Database.
	 * @return
	 */
	private Vector<EventItem> getEventListAll(){

		Vector<EventItem> list = new Vector<EventItem>();
		
		String sql 	= new SelectQuery()
						.addAllColumns()
						.addFromTable(this.eventsTable)
						.validate().toString();	
		
		
		if (this.dbDebug){
			this.dMsg("GET ALL EVENTS LIST");
			this.dMsg(sql);
		}
			
		
		
		this.connect();
		Vector<Object[]> result = this.runQueryResults(sql);
		
		int size = result.size();
		for (int i=0; i< size; i++){
			Object[] row = result.get(i);
			
			EventItem item = new EventItem(
						row[1].toString(), //name
						row[4].toString(), //startdate
						row[5].toString(), //enddate
						row[6].toString(), //starttime
						row[7].toString() //endtime
					);
			item.setDescription( row[2].toString()); //description
			item.setBudget( Double.parseDouble(row[3].toString()) ); //budget
			item.setID( Integer.parseInt(row[0].toString()) ); //event_id
			item.setEventFlow(EventFlowEntry.constructEventFlowEntryList(row[8].toString()));
		
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
								.addSetClause(this.eventsName, aName.replaceAll("[\']", ""))
								.addSetClause(this.eventsDescription, aDescription.replaceAll("[\']", ""))
								.addSetClause(this.eventsBudget, aBudget)
								.addSetClause(this.eventsStartDate, aStartDate)
								.addSetClause(this.eventsEndDate, aEndDate)
								.addSetClause(this.eventsStartTime, aStartTime)
								.addSetClause(this.eventsEndTime, aEndTime)	
								.addSetClause(this.eventsSchedule, aSchedule.replaceAll("[\']", ""))
								.addCondition(BinaryCondition.equalTo(this.eventsID, aEventID))
								.validate().toString();
		
		if (this.dbDebug){
			this.dMsg("UPDATE EVENT #"+aEventID);
			this.dMsg(sql);
		}
		
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;
	}
	
	
	
	/**
	 * Modifies details of an event.
	 * @param aItem
	 * @return
	 */
	public int updateEvent(EventItem aItem){
		return this.updateEvent(
							aItem.getID(), 
							aItem.getName(), 
							aItem.getDescription(), 
							aItem.getBudget(), 
							aItem.getStartDateTime().getDateRepresentation(), 
							aItem.getEndDateTime().getDateRepresentation(), 
							aItem.getStartDateTime().getTimeRepresentation(), 
							aItem.getEndDateTime().getTimeRepresentation(), 
							EventFlowEntry.getStringRepresentation(aItem.getEventFlow())
						);
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
		
		
		if (this.dbDebug){
			this.dMsg("UDPATE SCHEDULE FOR #"+aEventID);
			this.dMsg(sql);
		}
		
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

		return this.deleteEventGateway(sql, aEventID);
	}
	
	
	
	
	/**
	 * Deletes an Archived Event.
	 * @param aEventID
	 * @return
	 */
	public int deleteArchiveEvent(int aEventID){
		String sql 	=	new DeleteQuery(this.archiveTable)
						.addCondition(BinaryCondition.equalTo(this.archiveEventID, aEventID))
						.validate().toString();
		
		return this.deleteEventGateway(sql, aEventID);
	}
	
	
	
	
	/**
	 * Delete Gateway
	 * @param aSql
	 * @param aEventID
	 * @return
	 */
	private int deleteEventGateway(String aSql, int aEventID){

		if (this.dbDebug){
			this.dMsg("DELETE EVENT/ARCHIVE EVENT #"+aEventID);
			this.dMsg(aSql);
		}
		
		
		this.connect();
		int result = this.runQuery(aSql);
		this.disconnect();
		
		return result;
	}

	
	
	
	
	
	/*
	 * ******************************************************
	 * 
	 * CRUD Options (ARCHIVE)
	 * 
	 * ******************************************************
	 */
	
	
	
	/**
	 * Generate the event query
	 * @param aItem
	 * @return
	 */
	private String generateArchiveQuery(EventItem aItem){
		String sql	=	new InsertQuery(this.archiveTable)
								.addColumn(this.archiveEventID, aItem.getID())
					      		.addColumn(this.archiveName, aItem.getName().replaceAll("[\']", ""))
					      		.addColumn(this.archiveDescription, aItem.getDescription().replaceAll("[\']", ""))
					      		.addColumn(this.archiveBudget, aItem.getBudget())
					      		.addColumn(this.archiveStartDate, aItem.getStartDateTime().getDateRepresentation())
					      		.addColumn(this.archiveEndDate, aItem.getEndDateTime().getDateRepresentation())
					      		.addColumn(this.archiveStartTime, aItem.getStartDateTime().getTimeRepresentation())
					      		.addColumn(this.archiveEndTime, aItem.getEndDateTime().getTimeRepresentation())
					      		.addColumn(this.archiveSchedule, EventFlowEntry.getStringRepresentation( aItem.getEventFlow() ).replaceAll("[\']", ""))
					      		.validate().toString();
		
		return sql;

	}
	
	
	
	/**
	 * Transfer an event to an archive.
	 * @param aEventID
	 * @return
	 */
	public int addArchiveEvent(int aEventID){
		
		
		if (this.dbDebug){
			this.dMsg("STARTING PROCESS TO ARCHIVE EVENT #"+aEventID);
		}
		
		EventItem item = this.getEvent(aEventID);

		this.deleteEvent(aEventID);
		String sql = this.generateArchiveQuery(item);
		
		if (this.dbDebug){
			this.dMsg("ADDING TO ARCHIVE LIST");
			this.dMsg(sql);
		}	
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
	
		return result;
	}
	
	

	
	
	
	
	/**
	 * Transfer a list of events to archive.
	 * @param aNewlyExpired
	 * @return
	 */
	public int addArchiveEventList(List<EventItem> aNewlyExpired){
		
		int size = aNewlyExpired.size();
		
		if (this.dbDebug){
			this.dMsg("STARTING PROCESS TO ARCHIVE LIST OF SIZE " + size);
		}	
		
		
		if (!aNewlyExpired.isEmpty()){
			for (int i=0; i<size; i++){
				EventItem current = aNewlyExpired.get(i);
				this.deleteEvent(current.getID());
				
				String sql = this.generateArchiveQuery(current);
				this.queue(sql);
				if (this.dbDebug){
					this.dMsg("ADD TO ARCHIVE LIST QUEUE");
					this.dMsg(sql);
				}	
			}
			
			if (this.dbDebug){
				this.dMsg("ADD TO ARCHIVE LIST");
			}	
			
			
			int result = this.commit();
		
			
			return result;
		}else{
			return 0;
		}
		 
	}
	
	
	/**
	 * Wrapper for getting list of events
	 * @return
	 */
	public Vector<EventItem> getArchiveEventList(){
			
		if (this.dbDebug){
			this.dMsg("GET AN ARCHIVE EVENT LIST");
		}	
		
		return this.getArchiveEventListAll();

	}
	
	private Vector<EventItem> getArchiveEventListAll(){
		
		Vector<EventItem> list = new Vector<EventItem>();
		
		String sql 	= new SelectQuery()
						.addAllColumns()
						.addFromTable(this.archiveTable)
						.validate().toString();	
		
		
		if (this.dbDebug){
			this.dMsg("GET ALL ARCHIVE EVENTS LIST");
			this.dMsg(sql);
		}
			
		
		
		this.connect();
		Vector<Object[]> result = this.runQueryResults(sql);
		
		if (!result.isEmpty()){
			int size = result.size();
			for (int i=0; i< size; i++){
				Object[] row = result.get(i);
				
				EventItem item = new EventItem(
							row[2].toString(), //name
							row[5].toString(), //startdate
							row[6].toString(), //enddate
							row[7].toString(), //starttime
							row[8].toString() //endtime
						);
				item.setDescription( row[3].toString()); //description
				item.setBudget( Double.parseDouble(row[4].toString()) ); //budget
				item.setID( Integer.parseInt(row[1].toString()) ); //event_id
				item.setEventFlow(EventFlowEntry.constructEventFlowEntryList(row[9].toString()));
			
				list.add(item);
			}
		}
		
		this.disconnect();
		return list;
			
		
	}

	
	
}