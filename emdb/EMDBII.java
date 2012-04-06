package emdb;


import java.io.File;
import java.util.Vector;

public class EMDBII{
	
	private String				dbName = "";
	private EMDBEvent			event;
	private EMDBBudget 			budget; 
	private EMDBVenue 			venue;	
	private EMDBParticipant 	participant;
	
	private boolean				debugState = EMDBSettings.DEVELOPMENT;
	
	/**
	 * Constructor
	 */
	public EMDBII(){
		this.start();
	}

	public EMDBII(String aName){
		this.setDbName(aName);
		this.start();
	}

	public EMDBII(String aName, boolean aDebugState){
		this.setDbName(aName);
		this.setDebugState(aDebugState);
		this.start();
	}
	
	
	
	
	
	
	/**
	 * Set the debug status.
	 * @param aDebugState
	 */
	public void setDebugState(boolean aDebugState){
		this.debugState = aDebugState;
	}
	
	
	
	
	
	
	
	/**
	 * Set DB Name
	 * @param aName
	 */
	public void setDbName(String aName){
		this.dbName = aName;
	}

	
	
	
	
	
	
	/**
	 * Start DB System.
	 */
	private void start(){
		if (this.debugState){
			EMDBSettings.dMsg("EMDB - STARTING UP");
		}
		
		this.event			=	new EMDBEvent(dbName, this.debugState);
		this.budget			=	new EMDBBudget(dbName, false);
		this.venue			=	new EMDBVenue(dbName, false);
		this.participant	=	new EMDBParticipant(dbName, false);
		
		
		this.budget.setDebug(this.debugState);
		this.venue.setDebug(this.debugState);
		this.participant.setDebug(this.debugState);
	}	
	


	
	/**
	 * Clear up all database tables.
	 */
	public void truncate(){
		if (this.debugState){
			EMDBSettings.dMsg("EMDB - Clearing Tables");
		}
		
		this.event.truncate();
		this.budget.truncate();
		this.venue.truncate();
		this.participant.truncate();
		
		
	}
	
	
	
	
	
	
	
	/**
	 * Load up / Set up the DB System.
	 */
	public void init(){
		if (this.debugState){
			EMDBSettings.dMsg("EMDB - INITIALIZING DATABASE");
		}
		this.event.setup();
		this.budget.setup();
		this.venue.setup();
		this.participant.setup();
		
		this.clearConnections();
		
	}
	
	
	
	
	
	
	
	/**
	 * Clears and ensures all connections are closed.
	 */
	public void clearConnections(){
		try{
			this.event.disconnect();
		}catch(Exception e){}
		
		try{
			this.budget.disconnect();
		}catch(Exception e){}
		
		try{
			this.venue.disconnect();
		}catch(Exception e){}
		
		try{
			this.budget.disconnect();
		}catch(Exception e){}
		
		try{
			this.participant.disconnect();
		}catch(Exception e){}
		
	}
	
	
	
	/**
	 * Destroys the database system
	 */
	public void deinit(){
		if (this.debugState){
			EMDBSettings.dMsg("EMDB - Destructing Tables");
		}
		
		
		/*
		 * Remove event tables
		 */
		this.event.drop();
		if(this.event.verify() && this.debugState){
			EMDBSettings.dMsg("EMDB - EVENT TABLE CLEARED");
		}
		
		
		/*
		 * Remove budget tables
		 */
		this.budget.drop();
		if(this.budget.verify() && this.debugState){
			EMDBSettings.dMsg("EMDB - BUDGET TABLE CLEARED");
		}
		
		/*
		 * Remove venue tables
		 */
		this.venue.drop();
		if(this.venue.verify() && this.debugState){
			EMDBSettings.dMsg("EMDB - VENUE TABLE CLEARED");
		}
		
		/*
		 * Remove participant tables
		 */
		this.participant.drop();
		if(this.participant.verify() && this.debugState){
			EMDBSettings.dMsg("EMDB - PARTICIPANT TABLE CLEARED");
		}
		
		
		
	}
	
	
	
	

	/**
	 * Access event database
	 * @return
	 */
	public EMDBEvent eventDB(){
		return this.event;
	}
	

	/**
	 * Access budget database
	 * @return
	 */
	public EMDBBudget budgetDB(){
		return this.budget;
	}
	
	
	/**
	 * Access venue database
	 * @return
	 */
	public EMDBVenue venueDB(){
		return this.venue;
	}
	
	
	
	/**
	 * Access participant database
	 * @return
	 */
	public EMDBParticipant participantDB(){
		return this.participant;
	}
	
	
	
	
	
	
	
	
	


	/**
	 * Remove Database completely
	 */
	public boolean destroy(boolean force){
		File dbFile= new File(this.dbName);

		
		if (!dbFile.exists() && this.debugState){
			EMDBSettings.dMsg("EMDB -DATBASE DOES NOT EXIST - " + this.dbName);
			return false;
		}
		 if (!dbFile.canWrite()){
			EMDBSettings.dMsg("EMDB -DATBASE LOCKED - " + this.dbName);
			return false;
		 }
	
		 if(force){
			 dbFile.deleteOnExit();
			 return true;
		 }else{
			 return dbFile.delete();
		 }
		
		
	}
	
	
	/**
	 * Check for Database File
	 */
	public void systemCheck(){
		
		
		//check if all tables are present and initialized.
		if (		this.event.verify() 
				&& 	this.budget.verify()
				&& 	this.venue.verify()
				&&	this.participant.verify()
			)
	{
			
			
			if (this.debugState){
				EMDBSettings.dMsg("EMDB - SYSTEM CHECK - OK");
			}
			
			
		}else{
			
			
			if (this.debugState){
				EMDBSettings.dMsg("EMDB - SYSTEM CHECK - FAILED");
			}
			
			
			//Initialize the system
			this.init();
			
			
			if (this.debugState){
				EMDBSettings.dMsg("EMDB - POPULATING HARD CODED VENUE DETAILS");
			}
			
			//Use the tool 
			EMDBIITools.populateVenues(this);
			
		}
		
		if (this.debugState){
			EMDBSettings.dMsg("EMDB - SYSTEM CHECK - END");
		}
	}
	


	
	
	
	
	
	public static void main(String[] args){
		/*EMDBII test = new EMDBII();
		test.systemCheck();*/
	}

	
	
}
	
