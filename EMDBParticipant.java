import java.util.List;
import java.util.Vector;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.ComboCondition;
import com.healthmarketscience.sqlbuilder.CreateTableQuery;
import com.healthmarketscience.sqlbuilder.DeleteQuery;
import com.healthmarketscience.sqlbuilder.DropQuery;
import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;

class EMDBParticipant extends EMDBBase{
	
	private DbTable 	masterTable = this.schema.addTable(EMDBSettings.TABLE_MASTER);
	private DbColumn	masterName	= this.masterTable.addColumn("name");

	
    private DbTable 	participantTable;
    private DbColumn 	participantID;
    private DbColumn	participantEventID;
    private DbColumn 	participantName;
    private DbColumn 	participantContact;
    private DbColumn 	participantEmail;
    private DbColumn 	participantAddress;
    private DbColumn 	participantMatric;
    private DbColumn	participantRemarks;
    
    
    
    /**
     * Constructor
     */
	public EMDBParticipant(){
		super();
		this.setTable();
	}

	public EMDBParticipant(String aName){
		super(aName);
		this.setTable();
	}	


	/**
	 * Set up the table structure.
	 */
	private void setTable(){
		this.participantTable	=	this.schema.addTable(EMDBSettings.TABLE_PARTICIPANTS);
		this.participantID		=	this.participantTable.addColumn("participant_id", "INTEGER PRIMARY KEY AUTOINCREMENT", null);
		this.participantEventID	=	this.participantTable.addColumn("event_id", "INTEGER NOT NULL DEFAULT (-1)", null);
		this.participantName	=	this.participantTable.addColumn("name", "TEXT NOT NULL", null);
		this.participantContact	=	this.participantTable.addColumn("contact", "TEXT", null);
		this.participantEmail	=	this.participantTable.addColumn("email", "TEXT", null);
		this.participantAddress	=	this.participantTable.addColumn("address", "TEXT", null);
		this.participantMatric	=	this.participantTable.addColumn("matric", "TEXT", null);
		this.participantRemarks	=	this.participantTable.addColumn("remarks", "TEXT", null);
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
		String sql 	=	new CreateTableQuery(this.participantTable, true)
								.validate().toString();
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("EMDB - Setup Participants: "+ sql);
		}
		
		
		this.queue(sql);
		this.commit();
	}
	
	
	
	
	
	/**
	 * DROP the database tables
	 */
	public void cleanup(){
		String sql 	=	DropQuery.dropTable(this.participantTable)
							.validate().toString();

		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("EMDB - Cleanup Participants: "+ sql);
		}
		
		this.queue(sql);
		this.commit();
	}
	
	
	
	
	/**
	 * VERIFY the database tables
	 * @return
	 */
	public boolean verify(){
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("EMDB - " + EMDBSettings.TABLE_PARTICIPANTS);
		}
		
		
		String sql = new SelectQuery()
						.addAllColumns()
						.addFromTable(this.masterTable)
						.addCondition(BinaryCondition.equalTo(this.masterName, EMDBSettings.TABLE_PARTICIPANTS))
						.validate().toString();
		
		return this.verification(sql, 1);
		
	}
	
	
	
	
	
	
	
	
	
	/*
	 * ******************************************************
	 * 
	 * CRUD Options
	 * 
	 * ******************************************************
	 */

	/**
	 * Add a list of participants to the event
	 * @param list
	 * @param aEventID
	 * @return
	 */
	public int addParticipantList(List<Participant> list, int aEventID){
		
		String sql = "";
		
		int size = list.size();
		for (int i=0; i< size; i++){
			
			Participant row = list.get(i);
			
			sql = new InsertQuery(this.participantTable)
						.addColumn(this.participantEventID, aEventID)
						.addColumn(this.participantName, row.getName())
						.addColumn(this.participantContact, row.getContact())
						.addColumn(this.participantEmail, row.getEmail())
						.addColumn(this.participantAddress, row.getAddress())
						.addColumn(this.participantMatric, row.getMatric())
						.addColumn(this.participantRemarks, row.getRemark())
						.validate().toString();
			this.queue(sql);
			
		}
		
		int result = 0;
		
		if (size > 0){
			this.connect();
			result = this.commit();
			this.disconnect();
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * Get a list of participants who signed up for the event
	 * @param aEventID
	 * @return
	 */
	public List<Participant> getParticipantList(int aEventID){
		String sql = new SelectQuery()
							.addAllColumns()
							.addFromTable(this.participantTable)
							.addCondition(BinaryCondition.equalTo(this.participantEventID, aEventID))
							.validate().toString();
		
		List<Participant> list = new Vector<Participant>();
		
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("EMDB - GET ALL EVENTS PARTICIPANTS");
			this.dMsg(sql);
		}
			
		
		
		this.connect();
		Vector<Object[]> result = this.runQueryResults(sql);
		
		int size = result.size();
		for (int i=0; i< size; i++){
			Object[] row = result.get(i);
			
			Participant person = new Participant(
									row[1].toString(),
									row[2].toString(),
									row[3].toString(),
									row[4].toString(),
									row[5].toString(),
									row[6].toString()
								);
			person.setID(Integer.parseInt(row[0].toString()));
			list.add(person);
		}
			

		this.disconnect();
		return list;
			

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Delete an Event's participant's list
	 * @param aEventID
	 * @return
	 */
	public int deleteParticipantList(int aEventID){
		String sql	=	new DeleteQuery(this.participantTable)
							.addCondition(BinaryCondition.equalTo(this.participantEventID, aEventID))
							.validate().toString();
		
		if (EMDBSettings.DEVELOPMENT){
			this.dMsg("EMDB - DELETE/CLEAR PARTICIPANT LIST #"+aEventID);
			this.dMsg(sql);
		}
		
		
		this.connect();
		int result = this.runQuery(sql);
		this.disconnect();
		
		return result;
	}
	
	
	
	
	
}