import java.util.Vector;



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
	private int m_venueID;				// Its value will be assigned by database
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
	}

	
	public BookedVenueInfo(String name, String address, String description, int capacity, int cost, TimeSlot bookedTimeSlot)
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
	 * Returns the address of the BookedVenueInfo object
	 * 
	 * @return m_address - String
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
	 * @return m_maxCapacity - String
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
	 * @return m_cost - String
	 */
	public String getCostInCentString()
	{
		return Integer.toString(m_cost);
	}
	
	public double getCostInDollar()
	{
		return m_cost / 100.0;
	}
	
	public String getCostInDollarString()
	{
		return Double.toString(m_cost / 100.0);
	}
	
	public int getVenueID()
	{
		return m_venueID;
	}
	
	public String getVenueIDString()
	{
		return Integer.toString(m_venueID);
	}
	
	public String getStartDateString()
	{
		return m_bookedTimeSlot.getStartDateHour().getDateRepresentation();
	}
	
	public String getEndDateString()
	{
		return m_bookedTimeSlot.getEndDateHour().getDateRepresentation();
	}
	
	public String getStartHourString()
	{
		return Integer.toString(m_bookedTimeSlot.getStartDateHour().getHour()) + ":00";
	}
	
	public String getEndHourString()
	{
		return Integer.toString(m_bookedTimeSlot.getEndDateHour().getHour()) + ":00";
	}
}
