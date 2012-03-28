import java.util.Vector;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.ComboCondition;
import com.healthmarketscience.sqlbuilder.CreateTableQuery;
import com.healthmarketscience.sqlbuilder.DropQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;

class EMDBParticipant extends EMDBBase{
	
	private DbTable 	masterTable = this.schema.addTable(EMDBSettings.TABLE_MASTER);
	private DbColumn	masterName	= this.masterTable.addColumn("name");

	
    private DbTable 	participantTable;
    private DbColumn 	participantID;
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

	
	
	
	
	
	
	
	
	
}