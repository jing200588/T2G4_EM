package venue;


/**
 * A TimeSlot object represents a time slot which consists of the starting date and hour and
 * the ending date and hour.
 * A TimeSlot object is immutable.
 * 
 * @author Nguyen Truong Duy (Team 31 - CS2103)
 */
public class TimeSlot implements Comparable<TimeSlot> {
	/************************************************************************
	 * Class member variables
	 ************************************************************************/
	private int booking_id; 
	private MyDateTime m_startDateTime;
	private MyDateTime m_endDateTime;
	
	/*************************************************************************
	 * Constructor
	 *************************************************************************/
	
	/**
	 * Constructor: Creates a TimeSlot object with inputs are the starting date and hour
	 * and the ending date and hour. It is assumed the inputs are valid and not null.
	 * 
	 * @param start - MyDateTime
	 * @param end - MyDateTime
	 */
	public TimeSlot(MyDateTime start, MyDateTime end)
	{
		m_startDateTime = start;
		m_endDateTime = end;
	}

	/**
	 * Constructor: Creates a TimeSlot object with an additional information which is the 
	 * booking ID.
	 * 
	 * @param id - int
	 * @param start - MyDateTime
	 * @param end - MyDateTime
	 */
	public TimeSlot(int id, MyDateTime start, MyDateTime end)
	{
		booking_id = id;
		m_startDateTime = start;
		m_endDateTime = end;
	}
	
	/**************************************************************************
	 * Methods that support extracting information
	 **************************************************************************/
	
	/**
	 * 
	 * @return m_startDateTime - MyDateTime
	 */
	public MyDateTime getStartDateTime()
	{
		return m_startDateTime;
	}
	
	/**
	 * 
	 * @return m_endDateTime - MyDateTime
	 */
	public MyDateTime getEndDateTime()
	{
		return m_endDateTime;
	}
	
	
	/**
	 * 
	 * @return booking_id - int
	 */
	public int getBookingID(){
		return booking_id;
	}
	
	/**
	 * Checks if the two time slots represented by two TimeSlot objects are overlapped.
	 * 
	 * @param comparedObj - TimeSlot
	 * @return true if this TimeSlot object and the object comparedObj are
	 * 		overlapping. It returns false otherwise
	 */
	public boolean isOverlapping(TimeSlot comparedObj)
	{
		if(m_startDateTime.compareTo(comparedObj.getEndDateTime()) >= 0 ||
				m_endDateTime.compareTo(comparedObj.getStartDateTime()) <= 0)
			return false;
		return true;
	}
	
	/**
	 * Checks if this time slot precedes the specified point of time (inputDateTime) according to
	 * chronological order.
	 * 
	 * @param inputDateTime
	 * @return true if this time slot precedes inputDateTime in chronological order.
	 * @return false otherwise
	 */
	public boolean happenBefore(MyDateTime inputDateTime)
	{
		if(m_startDateTime.compareTo(inputDateTime) >= 0)
			return false;
		return true;
	}
	
	/**
	 * Checks if this time slot is contained in the input TimeSlot object (anotherObj)
	 *  
	 * @param anotherObj
	 * @return true of this time slot is contained in anotherObj
	 * @return false otherwise.
	 */
	public boolean isContained(TimeSlot anotherObj)
	{
		if(anotherObj.getStartDateTime().compareTo(m_startDateTime) <= 0 &&
				anotherObj.getEndDateTime().compareTo(m_endDateTime) >= 0)
			return true;
		return false;
	}

	/**
	 * Compares two TimeSlot objects.
	 * 
	 * @param anotherObj - TimeSlot
	 * @return 1 if this TimeSlot object is 'larger' than anotherObj.
	 * @return -1 if this TimeSlot object is 'smaller' than anotherObj.
	 * @return 0 if this TimeSlot object is 'equal' to anotherObj.
	 * 
	 * Comparison standard:
	 * 	+ If this.startDateTime '>' anotherObj.startDateTime, then this '>' anotherObj.
	 *  + If this.startDateTime '<' anotherObj.startDateTime, then this '<' anotherObj.
	 *  + If this.startDateTime '=' anotherObj.startDateTime,
	 *  	- If this.EndDateTime '>' anotherObj.EndDateTime, then this '>' anotherObj.
	 *  	- If this.EndDateTime '<' anotherObj.EndDateTime, then this '<' anotherObj.
	 *  	- If this.EndDateTime '=' anotherObj.EndDateTime, then this '=' anotherObj.
	 */
	//@Override
	public int compareTo(TimeSlot anotherObj) {
		// TODO Auto-generated method stub
		if(m_startDateTime.compareTo(anotherObj.getStartDateTime()) > 0)
			return 1;
		if(m_startDateTime.compareTo(anotherObj.getStartDateTime()) < 0)
			return -1;
		
		// At this point this.startDateTime '=' anotherObj.startDateTime
		// Compare the end date time.
		if(m_endDateTime.compareTo(anotherObj.getEndDateTime()) > 0)
			return 1;
		if(m_endDateTime.compareTo(anotherObj.getEndDateTime()) < 0)
			return -1;
		return 0;
	}
}

