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
	 * Load up / Set up the DB System.
	 */
	public void init(){
		
	}
	
	

	
	/**
	 * Check for Database File
	 */
	public void system_check(){
		
	}
	

	public static void main(String[] args){
		String test ="";
		
		if(test.isEmpty())
			System.out.println("Empty");
		else
			System.out.println("Not!");
		
	}

	
	
}
	
