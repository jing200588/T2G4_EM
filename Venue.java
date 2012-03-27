/**
 * A Venue object is an abstraction of a venue. It keeps track of the name, address, further
 * description, maximum capacity, cost, venue identification number and all the booked time slots
 * of a venue.
 * 
 * @author Nguyen Truong Duy (Team 31 - CS2103)
 */

import java.util.Vector;

public class Venue {
	/***********************************************************************
	 * Class member variables
	 ***********************************************************************/
	
	private String m_name;
	private String m_address;
	private String m_description;
	private int m_maxCapacity;
	private int m_cost;
	private int m_venueID;					// Its value will be assigned by database
	private Vector<TimeSlot> m_bookedTimeSlots;
	
	// With a venue supported by the system, you can request the system to book it for you online
	// with your valid specified time slot. Its information is automatically updated by the system.
	// On the other hand, with a venue that is not supported by the system, the system just keeps 
	// track of it for you. You have to do the booking and updating by yourself.
	private boolean isSupportedBySystem;	 	
	
	/************************************************************************
	 * Constructor
	 ************************************************************************/
	
	/**
	 * Default Constructor:
	 */
	public Venue(){
		
	}
	
	
	/**
	 * Constructor: Creates a Venue object with parameters name, address, description, maximum capacity
	 * and cost.
	 * 
	 * @param name - String
	 * @param address - String
	 * @param description - String
	 * @param maxCapacity - int
	 * @param cost - int
	 */
	public Venue(String name, String address, String description,
					int maxCapacity, int cost)
	{
		m_name = name;
		m_address = address;
		m_description = description;
		m_maxCapacity = maxCapacity;
		m_cost = cost;
		m_bookedTimeSlots = new Vector<TimeSlot>();
	}
	
	/************************************************************************
	 * Methods that support extracting information
	 ***********************************************************************/
	
	/**
	 * 
	 * @return m_name - String
	 */
	public String getName()
	{
		return m_name;
	}
	
	/**
	 * 
	 * @return m_address - String
	 */
	public String getAddress()
	{
		return m_address;
	}
	
	/**
	 * 
	 * @return m_description - String
	 */
	public String getDescription()
	{
		return m_description;
	}
	
	/**
	 * 
	 * @return m_maxCapacity - int
	 */
	public int getMaxCapacity()
	{
		return m_maxCapacity;
	}
	
	/**
	 * Returns the cost of a venue (in cents).
	 * 
	 * @return m_cost - int
	 */
	public int getCost()
	{
		return m_cost;
	}
	
	/**
	 * 
	 * @return m_venueID - int
	 */
	public int getVenueID()
	{
		return m_venueID;
	}
	
	/**
	 * Returns a list of pieces of venue information. All pieces are of type String.
	 * 
	 * @return an array of String objects where
	 * 		+ String[0] - name of the venue
	 * 		+ String[1] - address
	 * 		+ String[2] - description
	 * 		+ String[3] - maximum capacity
	 * 		+ String[4] - cost (in cents)
	 * 	
	 */
	public String[] getAllVenueInformation()
	{
		String[] allInfo = new String[5];
		allInfo[0] = m_name;
		allInfo[1] = m_address;
		allInfo[2] = m_description;
		allInfo[3] = Integer.toString(m_maxCapacity);
		allInfo[4] = Double.toString(m_cost / 100.0);
		
		return allInfo;
 	}
	
	/*************************************************************************
	 * Methods that support updating information
	 *************************************************************************/
	
	/**
	 * 
	 * @param newName - String
	 */
	public void updateName(String newName)
	{
		m_name = newName;
	}
	
	/**
	 * 
	 * @param newAddress - String
	 */
	public void updateAddress(String newAddress)
	{
		m_address = newAddress;
	}
	
	/**
	 * 
	 * @param newDescription - String
	 */
	public void updateDescription(String newDescription)
	{
		m_description = newDescription;
	}
	
	/**
	 * 
	 * @param newMaxCapacity - int
	 */
	public void updateMaxCapacity(int newMaxCapacity)
	{
		m_maxCapacity = newMaxCapacity;
	}
	
	/**
	 * 
	 * @param newCost - int
	 */
	public void updateCost(int newCost)
	{
		m_cost = newCost;
	}
	
	/**
	 * 
	 * @param id - int
	 */
	public void updateID(int id)
	{
		m_venueID = id;
	}
	
	/**
	 * Adds a TimeSlot object into a list of booked time slots. 
	 * It is assumed the new object has no clash with other data in the list of booked time slots.
	 * Time complexity: O(1) (Currently, the list of booked time slots is unsorted)
	 * 
	 * @param wantedTimeSlot - TimeSlot
	 */
	public void bookTimeSlot(TimeSlot wantedTimeSlot)
	{
		m_bookedTimeSlots.add(wantedTimeSlot);
	}
	
	/**
	 * 
	 * @param wantedTimeSlots - Vector<TimeSlot>
	 */
	public void bookTimeSlotBlock(Vector<TimeSlot>  wantedTimeSlots){
		m_bookedTimeSlots = wantedTimeSlots;	
	}
	
	
	
	/**
	 * Removes a TimeSlot object from a list of booked time slots.
	 * Time complexity: O(N) (Currently, the list of booked time slots is unsorted)
	 * 
	 * @param deletedTimeSlot - TimeSlot
	 */
	public void removeTimeSlot(TimeSlot deletedTimeSlot)
	{
		m_bookedTimeSlots.remove(deletedTimeSlot);
	}
	
	/**
	 * Checks if this venue is available at the input time slot. 
	 * The method goes through the whole list of booked time slots and checks if there is any clash with 
	 * existing time slots.
	 * Time complexity: O(N).
	 * 
	 * @param wantedTimeSlot
	 * @return true if this venue is available for the specified time slot.
	 * @return false otherwise 
	 */
	public boolean isAvailable(TimeSlot wantedTimeSlot)
	{
		for(int i = 0; i < m_bookedTimeSlots.size(); i++)
		{
			/* For debugging 
			System.out.println("Time Slot " + i);
			System.out.println(m_bookedTimeSlots.get(i).getStartDateTime());
			System.out.println(m_bookedTimeSlots.get(i).getEndDateTime());
			*/
			if(m_bookedTimeSlots.get(i).isOverlapping(wantedTimeSlot) == true)
			{
				// For debugging: System.out.println("Clash!");
				return false;
			}
		}
		return true;
	}
	 
}
