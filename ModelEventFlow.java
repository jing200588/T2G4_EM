import java.util.Vector;

/*
 * Class: ModelEventFlow
 * 
 * Interacts with database to save or retrieve data.
 */

public class ModelEventFlow {
	
	/**
	 * Stores the given list of EventFlowEntry objects into the database
	 * 
	 * @param storedData - Vector<EventFlowEnrty>
	 * @return true if data are stored into the database successfully.
	 * @return false if there is a storing problem.
	 */
	public static boolean saveEventFlow(Vector<EventFlowEntry> storedData)
	{
		try
		{
			String storedText = EventFlowEntry.getStringRepresentation(storedData);
			
			// DATABASE: add the string storedText into the database
			// Add code here
			
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
	public static Vector<EventFlowEntry> retrieveEventFlow()
	{
		// DATABASE: you should return a string
		// Replace the instruction below
		String stringData = new String();
		
		return EventFlowEntry.constructEventFlowEntryList(stringData);
	}
}
