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
	private int m_maxCapacity;
	private double m_cost;
	private Vector<TimeSlot> m_bookedTimeSlots;
	
	/************************************************************************
	 * Constructor
	 ************************************************************************/
	public Venue(String name, String address, int maxCapacity, double cost)
	{
		m_name = name;
		m_address = address;
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
	
	public int getMaxCapacity()
	{
		return m_maxCapacity;
	}
	
	public double getCost()
	{
		return m_cost;
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
	
	public void updateMaxCapacity(int newMaxCapacity)
	{
		m_maxCapacity = newMaxCapacity;
	}
	
	public void updateCost(double newCost)
	{
		m_cost = newCost;
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
			if(m_bookedTimeSlots.get(i).isOverlapping(wantedTimeSlot) == true)
				return false;
		}
		return true;
	}
	
}
