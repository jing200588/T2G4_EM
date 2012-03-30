package program;

import emdb.*;

import java.util.Vector;

/*
 * Class: ModelEventFlow
 * 
 * Interacts with database to save or retrieve data.
 */

public class ModelEventFlow {
	
	private static EMDBII db = new EMDBII();
	
	/**
	 * Stores the given list of EventFlowEntry objects of an event with eventID into the database
	 * 
	 * @param storedData - Vector<EventFlowEnrty>
	 * @param
	 * @return true if data are stored into the database successfully.
	 * @return false if there is a storing problem.
	 */
	public static boolean saveEventFlow(int eventID, Vector<EventFlowEntry> storedData)
	{
		try
		{
			String storedText = EventFlowEntry.getStringRepresentation(storedData);
			
			// DATABASE: add the string storedText into the database
			// Add code here
			
			db.eventDB().updateSchedule(eventID, storedText);
			
			
			return true;
		}
		catch(Exception exception)
		{
			return false;
		}
	}
	
	/**
	 * Retrieves information of the event flow from the database.
	 * 
	 * @return eventFlowData - Vector<EventFlowEntry>
	 */
	public static Vector<EventFlowEntry> retrieveEventFlow(int eventID)
	{
		// DATABASE: you should return a string
		// Replace the instruction below
		return db.eventDB().getEvent(eventID).getEventFlow();
		
		
		//return EventFlowEntry.constructEventFlowEntryList(stringData);
	}
}
