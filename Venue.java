/**
 * @author Nguyen Truong Duy (Team 31 - CS2103)
 * 
 * Class: Venue
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
	private int m_venueID;				// Its value will be assigned by database
	private Vector<TimeSlot> m_bookedTimeSlots;
	
	/************************************************************************
	 * Constructor
	 ************************************************************************/
	public Venue(){
		
	}
	
	
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
	
	public int getCost()
	{
		return m_cost;
	}
	
	public int getVenueID()
	{
		return m_venueID;
	}
	
	/**
	 * 
	 * @return a list of pieces of venue information. All pieces are of type
	 * 			String.
	 * 		+ String[0] - name of the venue
	 * 		+ String[1] - address
	 * 		+ String[2] - description
	 * 		+ String[3] - maximum capacity
	 * 		+ String[4] - cost
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
	public void updateName(String newName)
	{
		m_name = newName;
	}
	
	public void updateAddress(String newAddress)
	{
		m_address = newAddress;
	}
	
	public void updateDescription(String newDescription)
	{
		m_description = newDescription;
	}
	
	public void updateMaxCapacity(int newMaxCapacity)
	{
		m_maxCapacity = newMaxCapacity;
	}
	
	public void updateCost(int newCost)
	{
		m_cost = newCost;
	}
	
	public void updateID(int id)
	{
		m_venueID = id;
	}
	
	/**
	 * 
	 * @param wantedTimeSlot
	 * 
	 * Description: add the wantedTimeSlot into a list of booked time slots.
	 * 
	 * Assumption: wantedTimeSlot has no clash with other data in the list
	 * 			of booked time slot.
	 * 
	 * Time complexity: O(1) (Currently, the list of booked time slots is
	 * 						  unsorted)
	 */
	public void bookTimeSlot(TimeSlot wantedTimeSlot)
	{
		m_bookedTimeSlots.add(wantedTimeSlot);
	}
	
	
	public void bookTimeSlotBlock(Vector<TimeSlot>  wantedTimeSlots){
		m_bookedTimeSlots = wantedTimeSlots;	
	}
	
	
	
	/**
	 * 
	 * @param deletedTimeSlot
	 * 
	 * Description: remove the deletedTimeSlot from a list of booked time slots.
	 * 
	 * Time complexity: O(N) (Currently, the list of booked time slots is
	 * 						  unsorted)
	 */
	public void removeTimeSlot(TimeSlot deletedTimeSlot)
	{
		m_bookedTimeSlots.remove(deletedTimeSlot);
	}
	
	/**
	 * 
	 * @param wantedTimeSlot
	 * @return true if this venue is available for the specified time slot.
	 * @return false otherwise 
	 * 
	 * Methodology: Go through the whole list of booked time slots and check
	 * 		if there is any clash with existing time slots.
	 * 
	 * Time complexity: O(N)
	 */
	public boolean isAvailable(TimeSlot wantedTimeSlot)
	{
		for(int i = 0; i < m_bookedTimeSlots.size(); i++)
		{
			/* For debugging 
			System.out.println("Time Slot " + i);
			System.out.println(m_bookedTimeSlots.get(i).getStartDateHour());
			System.out.println(m_bookedTimeSlots.get(i).getEndDateHour());
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
