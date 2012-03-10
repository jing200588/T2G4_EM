/**
 * A TimeSlot object represents a time slot which consists of the starting date and hour and
 * the ending date and hour.
 * A TimeSlot object is immutable.
 * 
 * @author Nguyen Truong Duy (Team 31 - CS2103)
 */
public class TimeSlot {
	/************************************************************************
	 * Class member variables
	 ************************************************************************/
	private int booking_id; 
	private DateHour m_startDateHour;
	private DateHour m_endDateHour;
	
	/*************************************************************************
	 * Constructor
	 *************************************************************************/
	
	/**
	 * Constructor: Creates a TimeSlot object with inputs are the starting date and hour
	 * and the ending date and hour. It is assumed the inputs are valid and not null.
	 * 
	 * @param start - DateHour
	 * @param end - DateHour
	 */
	public TimeSlot(DateHour start, DateHour end)
	{
		m_startDateHour = start;
		m_endDateHour = end;
	}

	/**
	 * Constructor: Creates a TimeSlot object with an additional information which is the 
	 * booking ID.
	 * 
	 * @param id - int
	 * @param start - DateHour
	 * @param end - DateHour
	 */
	public TimeSlot(int id, DateHour start, DateHour end)
	{
		booking_id = id;
		m_startDateHour = start;
		m_endDateHour = end;
	}
	
	/**************************************************************************
	 * Methods that support extracting information
	 **************************************************************************/
	
	/**
	 * 
	 * @return m_startDateHour - DateHour
	 */
	public DateHour getStartDateHour()
	{
		return m_startDateHour;
	}
	
	/**
	 * 
	 * @return m_endDateHour - DateHour
	 */
	public DateHour getEndDateHour()
	{
		return m_endDateHour;
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
		if(m_startDateHour.compareTo(comparedObj.getEndDateHour()) >= 0 ||
				m_endDateHour.compareTo(comparedObj.getStartDateHour()) <= 0)
			return false;
		return true;
	}
}
