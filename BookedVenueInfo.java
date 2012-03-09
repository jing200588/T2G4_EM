import java.util.Vector;



/**
 * @author Nguyen Truong Duy (Team 31 - CS2103T)
 * 
 * An object of type BookedVenueInfo contains all information of a venue (like name, address, etc.)
 * 		and a list of time slots that a particular event books for this venue.
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
	
	/************************************************************************
	 * Methods that support extracting information
	 ***********************************************************************/
	public String getName()
	{
		return m_name;
	}
	
	public String getAddress()
	{
		return m_address;
	}
	
	public String getDescription()
	{
		return m_description;
	}
	
	public int getMaxCapacity()
	{
		return m_maxCapacity;
	}
	
	public String getMaxCapacityString()
	{
		return Integer.toString(m_maxCapacity);
	}
	
	public int getCostInCent()
	{
		return m_cost;
	}
	
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
		return m_bookedTimeSlot.getEndDateHour().getDateHourRepresentation();
	}
	
	public String getStartHourString()
	{
		return Integer.toString(m_bookedTimeSlot.getStartDateHour().getHour());
	}
	
	public String getEndHourString()
	{
		return Integer.toString(m_bookedTimeSlot.getEndDateHour().getHour());
	}
}
