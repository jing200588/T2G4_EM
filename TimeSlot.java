/**
 * @author Nguyen Truong Duy (Team 31 - CS2103)
 *
 * Class: TimeSlot
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
	public TimeSlot(DateHour start, DateHour end)
	{
		m_startDateHour = new DateHour(start);
		m_endDateHour = new DateHour(end);
	}

	public TimeSlot(int id, DateHour start, DateHour end)
	{
		booking_id = id;
		m_startDateHour = new DateHour(start);
		m_endDateHour = new DateHour(end);
	}
	
	
	/**************************************************************************
	 * Methods that support extracting information
	 **************************************************************************/
	public DateHour getStartDateHour()
	{
		return m_startDateHour;
	}
	
	public DateHour getEndDateHour()
	{
		return m_endDateHour;
	}
	
	
	public int getBookingID(){
		return booking_id;
	}
	
	/**
	 * 
	 * @param comparedObj
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
