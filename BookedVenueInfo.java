/**
 * An object of type BookedVenueInfo contains all information of a venue (like name, address, etc.)
 * 		and a list of time slots that a particular event books for this venue.
 * 
 * @author Nguyen Truong Duy (Team 31 - CS2103T)
 *
 */
public class BookedVenueInfo {
	
	/***********************************************************************************************
	 * Class member variables
	 **********************************************************************************************/
	
	private String m_name;
	private String m_address;
	private String m_description;
	private int m_maxCapacity;
	private int m_cost;
	private int m_venueID;					// Its value will be assigned by database
	private TimeSlot m_bookedTimeSlot;		// A single time slot booked by ONE particular event
	
	/************************************************************************
	 * Constructor
	 ************************************************************************/
	public BookedVenueInfo(Venue obj, TimeSlot bookedTimeSlot)
	{
		m_name = obj.getName();
		m_address = obj.getAddress();
		m_description = obj.getDescription();
		m_maxCapacity = obj.getMaxCapacity();
		m_cost = obj.getCost();
		m_bookedTimeSlot = bookedTimeSlot; 		// Note that TimeSlot object is immutable!
		m_venueID = obj.getVenueID();
	}

	
	public BookedVenueInfo(String name, int venueID, String address, String description, int capacity, int cost, TimeSlot bookedTimeSlot)
	{
		m_name = name;
		m_address = address;
		m_description = description;
		m_maxCapacity = capacity;
		m_cost = cost;
		m_bookedTimeSlot = bookedTimeSlot; 		// Note that TimeSlot object is immutable!
	}	
	
	
	/************************************************************************
	 * Methods that support extracting information
	 ***********************************************************************/
	
	/**
	 * Returns the name of the BookedVenueInfo object
	 * 
	 * @return m_name - String
	 */
	public String getName()
	{
		return m_name;
	}
	
	/**
	 * Returns the address of the BookedVenueInfo object
	 * 
	 * @return m_address - String
	 */
	public String getAddress()
	{
		return m_address;
	}
	
	/**
	 * Returns the description of the BookedVenueInfo object
	 * 
	 * @return m_description - String
	 */
	public String getDescription()
	{
		return m_description;
	}
	
	/**
	 * Returns the maximum capacity of the BookedVenueInfo object
	 * 
	 * @return m_maxCapacity - int
	 */
	public int getMaxCapacity()
	{
		return m_maxCapacity;
	}
	
	/**
	 * Returns the maximum capacity of the BookedVenueInfo object
	 * 
	 * @return maxCapacity - String
	 */
	public String getMaxCapacityString()
	{
		return Integer.toString(m_maxCapacity);
	}
	
	/**
	 * Returns the price / cost (in cents) of the BookedVenueInfo object
	 * 
	 * @return m_cost - int
	 */
	public int getCostInCent()
	{
		return m_cost;
	}
	
	/**
	 * Returns the price / cost (in cents) of the BookedVenueInfo object
	 * 
	 * @return cost - String
	 */
	public String getCostInCentString()
	{
		return Integer.toString(m_cost);
	}
	
	/**
	 * Returns the price / cost (in dollars) of the BookedVenueInfo object.
	 * The cost has at most two decimal digits
	 * 
	 * @return cost - double
	 */
	public double getCostInDollar()
	{
		return m_cost / 100.0;
	}
	
	/**
	 * Returns the price / cost (in dollars) of the BookedVenueInfo object.
	 * The cost has at most two decimal digits
	 * 
	 * @return cost - String
	 */
	public String getCostInDollarString()
	{
		return Double.toString(m_cost / 100.0);
	}
	
	/**
	 * Returns the identification number (ID) of the BookedVenueInfo object.
	 * 
	 * @return m_venueID - int
	 */
	public int getVenueID()
	{
		return m_venueID;
	}
	
	/**
	 * Returns the identification number (ID) of the BookedVenueInfo object.
	 * 
	 * @return m_venueID - String
	 */
	public String getVenueIDString()
	{
		return Integer.toString(m_venueID);
	}
	
	/**
	 * Returns the starting date of the booked time slot of the BookedVenueInfo object. The
	 * date is in the format dd/mm/yyyy.
	 * 
	 * @return startDate - String
	 */
	public String getStartDateString()
	{
		return m_bookedTimeSlot.getStartDateTime().getDateRepresentation();
	}
	
	/**
	 * Returns the ending date of the booked time slot of the BookedVenueInfo object. The
	 * date is in the format dd/mm/yyyy.
	 * 
	 * @return endDate - String
	 */
	public String getEndDateString()
	{
		return m_bookedTimeSlot.getEndDateTime().getDateRepresentation();
	}
	
	/**
	 * Returns the starting hour of the booked time slot of the BookedVenueInfo object. The
	 * date is in the format h:00 (e.g. 9:00, 17:00, etc).
	 * 
	 * @return startHour - String
	 */
	public String getStartTimeString()
	{
		return m_bookedTimeSlot.getStartDateTime().getTimeRepresentation();
	}
	
	/**
	 * Returns the ending hour of the booked time slot of the BookedVenueInfo object. The
	 * date is in the format h:00 (e.g. 9:00, 17:00, etc).
	 * 
	 * @return endHour - String
	 */
	public String getEndTimeString()
	{
		return m_bookedTimeSlot.getEndDateTime().getTimeRepresentation();
	}
}
