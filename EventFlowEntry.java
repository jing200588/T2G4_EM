/**
 * The instance of this class represents an activity in an event. It consists of start and end date time (time slot),
 * 		name of the activity, the venue, and user's notes. 
 */

/**
 * @author Nguyen Truong Duy (Team 31 - CS2103T)
 *
 */
public class EventFlowEntry {
	
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
}

