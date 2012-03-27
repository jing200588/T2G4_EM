import java.util.Vector;




public class EMDBII{
	
	private String	dbName = "";
	private EMDBEvent	event;
	private EMDBBudget 	budget; 
	private EMDBVenue 	venue;	
	
	
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
	
	
	
	/**
	 * Start DB System.
	 */
	public void start(){
		event	=	new EMDBEvent(dbName);
		budget 	=	new EMDBBudget(dbName);
		venue	=	new EMDBVenue(dbName);
	}	
	
	
	
	/**
	 * Set DB Name
	 * @param aName
	 */
	public void setDbName(String aName){
		this.dbName = aName;
	}

	
	
	

	/**
	 * Access event database
	 * @return
	 */
	public EMDBEvent eventDB(){
		return event;
	}
	

	/**
	 * Access budget database
	 * @return
	 */
	public EMDBBudget budgetDB(){
		return budget;
	}
	
	
	/**
	 * Access venue database
	 * @return
	 */
	public EMDBVenue venueDB(){
		return venue;
	}
	
	
	
	/**
	 * Load up / Set up the DB System.
	 */
	public void init(){
		if (EMDBSettings.DEVELOPMENT){
			System.out.println("INITIALIZING DATABASE");
		}
		this.event.setup();
		this.budget.setup();
		this.venue.setup();
	}
	
	

	
	/**
	 * Check for Database File
	 */
	public void systemCheck(){
		if (this.event.verify() 
				&& this.budget.verify()
				&& this.venue.verify()){
			
			
			if (EMDBSettings.DEVELOPMENT){
				System.out.println("SYSTEM CHECK - OK");
			}
			
			
		}else{
			
			
			if (EMDBSettings.DEVELOPMENT){
				System.out.println("SYSTEM CHECK - FAILED");
			}
			
			
			this.init();
			
			
			if (EMDBSettings.DEVELOPMENT){
				System.out.println("POPULATING HARD CODED VENUE DETAILS");
			}
			
			//temp hard code values
			this.venue.addVenue("DR1 (COM1-B-14B)", "", "", 6, 0);
			this.venue.addVenue("DR2 (COM1-B-14A)", "", "", 6, 0);
			this.venue.addVenue("DR3 (COM1-B-07)", "", "", 5, 0);
			this.venue.addVenue("DR4 (COM1-B-06)", "", "", 5, 0);
			this.venue.addVenue("DR5 (ICUBE-03-18)", "", "", 8, 0);
			this.venue.addVenue("DR6 (COM2-02-12)", "", "", 8, 0);
			this.venue.addVenue("DR7 (COM2-03-14)", "", "", 8, 0);
			this.venue.addVenue("DR8 (COM2-03-15)", "", "", 8, 0);
			this.venue.addVenue("DR9 (COM2-04-06)", "", "", 8, 0);
			this.venue.addVenue("DR10 (COM2-02-24)", "", "", 8, 0);
			this.venue.addVenue("DR11 (COM2-02-23)", "", "", 8, 0);
			this.venue.addVenue("Executive Classroom (COM2-04-02)", "", "", 25, 0);
			this.venue.addVenue("MR1 (COM1-03-19)", "", "", 7, 0);
			this.venue.addVenue("MR2 (COM1-03-28)", "", "", 7, 0);
			this.venue.addVenue("MR3 (COM2-02-26)", "", "", 7, 0);
			this.venue.addVenue("MR4 (COM1-01-22)", "", "", 7, 0);
			this.venue.addVenue("MR5 (COM1-01-18)", "", "", 7, 0);
			this.venue.addVenue("MR6 (AS6-05-10)", "", "", 8, 0);
			this.venue.addVenue("MR7 (ICUBE-03-01)", "", "", 12, 0);
			this.venue.addVenue("MR8 (ICUBE-03-48)", "", "", 12, 0);
			this.venue.addVenue("MR9 (ICUBE-03-49)", "", "", 12, 0);
			this.venue.addVenue("Video Conferencing Room (COM1-02-13)", "", "", 30, 0);
			
		}
		
		if (EMDBSettings.DEVELOPMENT){
			System.out.println("SYSTEM CHECK - END");
		}
	}
	

	public static void main(String[] args){
		EMDBII test = new EMDBII();
		test.systemCheck();
	}

	
	
}
	
