


public class EMDBII{
	
	static EMDBEvent test = new EMDBEvent();

	public static void main(String[] args){
		
		System.out.println("start");
		
		//test.updateEvent(1, "aName", "aDescription", 10, "aStartDate", "aEndDate", "aStartTime", "aEndTime");
		//test.addEvent("aName", "aDescription", 10, "aStartDate", "aEndDate", "aStartTime", "aEndTime");
		test.getEvent(1);
		//test.getEvent(1);
		//test.runQueryResults("");
		
		System.out.println("end");
		
	}
	
	
	
	/**
	 * Check for Database File
	 */
	public void system_check(){
		
	}
	
	
}
	
