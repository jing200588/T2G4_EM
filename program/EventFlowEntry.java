package program;

import venue.*;

import java.util.Scanner;
import java.util.Vector;

/**
 * The instance of this class represents an activity in an event. It consists of start and end date time (time slot),
 * 		name of the activity, the venue, and user's notes. 
 * This object is mutable.
 */

/**
 * @author Nguyen Truong Duy (Team 31 - CS2103T)
 *
 */
public class EventFlowEntry implements Comparable<EventFlowEntry> {
	
	/**************************************************************************************************
	 * Class member variables (Attributes)
	 *************************************************************************************************/
	private TimeSlot m_duration;
	private String m_activityName;
	private String m_venueName;
	private int m_venueID;
	private String m_userNote;
	
	/**
	 * Constructor: creates an event flow entry with the input information, such as duration, 
	 * 		activity name, venue name, venueID, and user's note.
	 * @param duration
	 * @param activityName
	 * @param venueName
	 * @param venueID
	 * @param note
	 */
	public EventFlowEntry(TimeSlot duration, String activityName, String venueName, int venueID, String note)
	{
		m_duration = duration;
		m_activityName = activityName;
		m_venueName = venueName;
		m_venueID = venueID;
		m_userNote = note;
	}

	/**
	 * Copy Constructor:
	 * 
	 * @param anotherObj - EventFlowEntry
	 */
	public EventFlowEntry(EventFlowEntry anotherObj)
	{
		m_duration = anotherObj.getDuration();
		m_activityName = anotherObj.getActivityName();
		m_venueName = anotherObj.getVenueName();
		m_venueID = anotherObj.getVenueID();
		m_userNote = anotherObj.getUserNote();
	}
	
	/****************************************************************************************************
	 * Group of methods that retrives information.
	 ***************************************************************************************************/
	public TimeSlot getDuration() 
	{
		return m_duration;
	}
	
	public String getActivityName() 
	{
		return m_activityName;
	}
	
	public String getVenueName() 
	{
		return m_venueName;
	}

	public int getVenueID() 
	{
		return m_venueID;
	}
	
	public String getUserNote() 
	{
		return m_userNote;
	}
	
	/*****************************************************************************************************
	 * Group of methods that update information
	 *****************************************************************************************************/
	public void updateDuration(TimeSlot duration) 
	{
		m_duration = duration;
	}

	public void updateActivityName(String activityName) 
	{
		m_activityName = activityName;
	}

	public void updateVenue(String venueName, int venueID) 
	{
		m_venueName = venueName;
		m_venueID = venueID;
	}

	public void updateUserNote(String userNote) 
	{
		m_userNote = userNote;
	}
	
	/**
	 * 
	 * @return a string that contains all information regarding the event flow entry
	 */
	public String getStringRepresentation()
	{
		String output = m_duration.getStartDateTime().getDateRepresentation() + "\n";
		output += m_duration.getStartDateTime().getTimeRepresentation() + "\n";
		output += m_duration.getEndDateTime().getDateRepresentation() + "\n";
		output += m_duration.getEndDateTime().getTimeRepresentation() + "\n";
		output += m_activityName + "\n";
		output += m_venueName + "\n" + Integer.toString(m_venueID) + "\n";
		output += m_userNote + "\n";
		return output;
		
	}
	
	/**
	 * Construct a list of EventFlowEntry objects from the given String inputText. The String inputText
	 * should be in the correct format. That is, it must be a concatenation of the strings generated by
	 * the method getStringRepresentation.
	 * 
	 * @param inputText - String
	 * @return listEventFlowEntry - Vector<EventFlowEntr>
	 * 
	 * Note that listEventFlowEntry will be empty if the string is not in the correct format.
	 */
	public static Vector<EventFlowEntry> constructEventFlowEntryList(String inputText)
	{
		try
		{
			Vector<EventFlowEntry> listEventFlowEntry = new Vector<EventFlowEntry>();
			Scanner scan = new Scanner(inputText);
			while(scan.hasNext() == true)
			{
				TimeSlot inputTimeSlot = new TimeSlot(new MyDateTime(scan.nextLine(), scan.nextLine()),
						new MyDateTime(scan.nextLine(), scan.nextLine()));
				String activityName = scan.nextLine();
				String venueName = scan.nextLine();
				int venueID = Integer.parseInt(scan.nextLine());
				String note = scan.nextLine();
				
				listEventFlowEntry.add(new EventFlowEntry(inputTimeSlot, activityName, venueName, venueID,
						note));
			}
			return listEventFlowEntry;
		}
		catch(Exception exception)
		{
			return new Vector<EventFlowEntry>();
		}
	}
	
	/**
	 * Return a string that represents a list of event flow entries. This string is a concatenation of
	 * 		the string representation of each event flow entry in the list.
	 * 
	 * @param listEventFlowEntry - Vector<EventFlowEntry>
	 * @return stringEventFlow - String
	 * 
	 * Remark: This method uses StringBuffer to speed up the process of concatenate a list of strings 
	 */
	public static String getStringRepresentation(Vector<EventFlowEntry> listEventFlowEntry)
	{
		if(listEventFlowEntry == null)
			return new String();				// Return empty string
		
		StringBuffer stringBuff = new StringBuffer();
		
		for(int index = 0; index < listEventFlowEntry.size(); index++)
			stringBuff.append(listEventFlowEntry.get(index).getStringRepresentation());
		
		return stringBuff.toString();
	}
	
	/**
	 * Compare two EventFlowEntry objects based on their durations and activity names.
	 * 
	 * @param compareObj
	 * @return 1 if this object is 'larger' than comparedObj
	 * @return -1 if this object is 'smaller' than comparedObj
	 * @return 0 if this object is 'equal' to comparedObj
	 * 
	 * Methodology:
	 * 	+ If this.duration '>' comparedObj.duration, then this '>' comparedObj
	 * 	+ If this.duration '<' comparedObj.duration, then this '<' comparedObj
	 * 	+ If this.duration '=' comparedObj.duration, 
	 * 		- If this.activityName '>' comparedObj.activityname, then this '>' comparedObj
	 * 		- If this.activityName '<' comparedObj.activityname, then this '<' comparedObj
	 * 		- If this.activityName '=' comparedObj.activityname, then this '=' comparedObj
	 */
	@Override
	public int compareTo(EventFlowEntry comparedObj) {
		// TODO Auto-generated method stub
		if(m_duration.compareTo(comparedObj.getDuration()) > 0)
			return 1;
		
		if(m_duration.compareTo(comparedObj.getDuration()) < 0)
			return -1;
		
		if(m_activityName.compareToIgnoreCase(comparedObj.getActivityName()) > 0)
			return 1;
		
		if(m_activityName.compareToIgnoreCase(comparedObj.getActivityName()) < 0)
			return -1;
		
		return 0;
	}
}
