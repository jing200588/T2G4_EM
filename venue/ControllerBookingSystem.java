package venue;

import event.*;

/**
 * @author Nguyen Truong Duy (Team 31 - CS2103)
 *
 */
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class ControllerBookingSystem {
	
	public static enum SearchCriteria{COST, CAPACITY, TIME, COST_TIME,
						CAPACITY_TIME, COST_CAPACITY, ALL_THREE}
	public static enum SortCriteria{VENUEID, NAME, ADDRESS, CAPACITY, COST}
	
	public static final double THRESHOLD = Double.MIN_VALUE;
	
	private static ModelBookingSystem mbs = new ModelBookingSystem();
	
	private Vector<Venue> listAllVenue;
	private Vector<Venue> currentSearchResult;
	
	/**
	 * Constructor:
	 */
	public ControllerBookingSystem()
	{
		listAllVenue = new Vector<Venue>();
		currentSearchResult = new Vector<Venue>();
	}
	
	/**
	 * Books a venue with bookedVenueID from the list of venues (currentSearchResult) for an event with 
	 * a specified time slot. It is assumed that the venue is available at the give time slot. 
	 * Therefore, the availability checking should have been done before calling this method.
	 * The method updates the new booking information in the database of venues and in the 
	 * Eventitem object itself.
	 * 
	 * @param event - Eventitem
	 * @param bookedVenueID - int
	 * @param wantedTimeSlot - TimeSlot
	 * 
	 * @return true if the booking is successful.
	 * @return false if there is an error during the booking process.
	 * 
	 */
	public boolean bookVenue(Eventitem event, int bookedVenueID, TimeSlot wantedTimeSlot)
	{	
		/* For testing purpose
		System.out.println("Hi Inside BookVenue");
		System.out.println("This is my EVENT: " + event);
		System.out.println("This is my EVENT: " + listVenues);
		System.out.println("This is my EVENT: " + listVenues.get(0)); */
		
		
		mbs.add_booking_to_db(event.getID(), bookedVenueID, wantedTimeSlot);
		
		int index = findIndex(currentSearchResult, bookedVenueID);
	
		/* For testing purpose 
		System.out.println("Inside BookVenue" + index); */
		
		// index is in valid range!!
		BookedVenueInfo newObj = new BookedVenueInfo(currentSearchResult.get(index), wantedTimeSlot);
		event.addBVI(newObj);
		
		// Update in the 2 lists (listAllVenue and currentSearchResult)
		Venue needUpdate = currentSearchResult.get(index);
		needUpdate.bookTimeSlot(wantedTimeSlot);
		// Actually, two elements in 2 two lists with the same ID will reference
		// to the same objects
		
		// For testing purpose: System.out.println("Hi! THis is the end of bookVenue");
		// If the booking is successful
		return true;
	}
	
	/**
	 * Looks up in the database and returns the venues whose names or addresses or descriptions 
	 * contain the input string as a substring. Case-insensitivity is considered.
	 *  
	 * @param venueName - String
	 * @return list - Vector<Venue>
	 * list will be empty if there is no venue with such a name.
	 */
	public Vector<Venue> findVenueByName(String venueName)
	{	
		if(listAllVenue.isEmpty() == true)
			currentSearchResult = mbs.find_venue_by_name(venueName);
		else
			currentSearchResult = shortListByName(listAllVenue, venueName);
		
		return new Vector<Venue>(currentSearchResult);
	}
	
	/**
	 * Searches venues that satisfy the specified criteria in terms of cost, capacity and time slot.
	 * It is assumed the inputs are correct.
	 * 
	 * @param costRange - int[] (a pair of double numbers (a, b) where a <= b)
	 * @param capacityRange - int[] (a pair of integers (a, b) where a <= b)
	 * @param preferredTime - TimeSlot
	 * @param type - ControllerBokkingSystem.SearchCriteria
	 * 
	 * @return satisfiedVenueList - Vector<Venue>
	 */
	public Vector<Venue> findVenueByCriteria(int[] costRange, int[] capacityRange,
			TimeSlot preferredTime, SearchCriteria type)
	{
		Vector<Venue> returnList = new Vector<Venue>();
		
		if(listAllVenue.isEmpty() == true)
			System.out.println("Search in database");
		else
			System.out.println("Cache");
		
		switch(type)
		{
			case CAPACITY:
			case CAPACITY_TIME:
			case COST_CAPACITY:
			case ALL_THREE:
			{
				Vector<Venue> firstRoundCapacity = new Vector<Venue>();
				if(listAllVenue.isEmpty() == true)
					firstRoundCapacity = mbs.get_venue_by_capacity(capacityRange[1], capacityRange[0]); 
				else
					firstRoundCapacity = shortListByCapacity(listAllVenue, capacityRange[0], capacityRange[1]);
				
				/* For debugging
				System.out.println(firstRoundCapacity);
				if(firstRoundCapacity == null)
					System.out.println("null");
				else
					System.out.println("not null");
				System.out.println(firstRoundCapacity.get(0).getName());
				System.out.println(firstRoundCapacity.size());
				System.out.println(firstRoundCapacity.get(0).get) */
				
				// Actual code
				if(type == SearchCriteria.CAPACITY)
				{
					returnList = firstRoundCapacity;
				}
				else
				{
					if(type == SearchCriteria.COST_CAPACITY)
					{
						returnList = shortListByCost(firstRoundCapacity, costRange[0], costRange[1]);
					}
					else
					{
						// Then the type is either CAPACITY_TIME or ALL_THREE
						// In either case, we still need to short list by time.
						Vector<Venue> shortListTime = shortListByTimeSlot(
								firstRoundCapacity, preferredTime);
				
						if(type == SearchCriteria.CAPACITY_TIME)
						{
							returnList = shortListTime;
						}
						else
						{
							returnList = shortListByCost(shortListTime,
									costRange[0], costRange[1]);
						}
					}
				}
				break;
			}
			
			case COST:
			case COST_TIME:
			{
				// For debugging: System.out.println(costRange[1] + " " + costRange[0]);
				Vector<Venue> firstRoundCost = new Vector<Venue>();
				if(listAllVenue.isEmpty() == true)
					firstRoundCost = mbs.get_venue_by_cost(costRange[1], costRange[0]);
				else
					firstRoundCost = shortListByCost(listAllVenue, costRange[0], costRange[1]);	
				
				// Actual code
				if(type == SearchCriteria.COST)
				{
					returnList = firstRoundCost;
				}
				else
				{
					// The type is COST_TIME
					returnList = shortListByTimeSlot(firstRoundCost, preferredTime);
				}
				break;
			}
			
			case TIME:
			{
				// The worst case: We have to take all the venues from the
				// database and then short-list them by timeslot.
				if(listAllVenue.isEmpty() == true)
					listAllVenue = mbs.get_all_venue();
				returnList = shortListByTimeSlot(listAllVenue, preferredTime);
				
			}
		}
		
		if(returnList == null)
			returnList = new Vector<Venue>();
		
		// Update the current search result.
		currentSearchResult = returnList;
		
		return new Vector<Venue>(returnList);
	}

	/**
	 * Checks a venue with venueID from a list of venues (currentSearchResult)
	 * is available at a specified time slot.
	 * 
	 * @param venueID - int
	 * @param preferredTime - TimeSlot
	 * @return true if a venue with venueID in the list of venues is available at the given
	 * time slot. 
	 * @return false otherwise.
	 * @throws Exception If there does exist a venue with venueID in the list.
	 */
	public boolean isAvailable(int venueID, TimeSlot preferredTime) throws Exception
	{
		int index = findIndex(currentSearchResult, venueID);
		
		/* For testing purpose:
		System.out.println("I'm here. Venue ID: " + venueID + " Index: " + index + " Time: " + preferredTime);
		*/
		
		if(index < 0)		// A venue with venueID does not exist
			throw new Exception("There is no venue with such an ID in the table above!");
		
		/* For testing purpose:
		System.out.println(listVenue.get(index)); */
		
		return currentSearchResult.get(index).isAvailable(preferredTime);
	}
	
	/**
	 * Returns a string that contains all information about a venue with venueID in the list of venues (currentSearchResult).
	 * 
	 * @param venueID - int
	 * @return venueDetail - String
	 * @throws Exception if there does not exist such a venue with venueID in the list.
	 */
	public String getVenueDetail(int venueID)throws Exception
	{
		int index = findIndex(currentSearchResult, venueID);
	
		return currentSearchResult.get(index).getAllVenueInformation();
	}
	
	/**
	 * Checks if a venue with venueID is in the input list of venues (currentSearchResult).
	 * 
	 * @param venueID - int
	 * @return true if there exists a venue with venueID in the list.
	 * @return false otherwise
	 */
	public boolean isInTheList(int venueID)
	{
		if(findIndex(currentSearchResult, venueID) < 0)
			return false;
		return true;
	}
	
	/**
	 * Returns the index of a venue with venueID in a given list of venues.
	 * 
	 * @param listVenue - Vector<Venue>
	 * @param venueID - int
	 * @return the index of a venue with venueID in the list
	 * @return -1 if such an element does not exist.
	 */
	private int findIndex(Vector<Venue> listVenue, int venueID)
	{
		for(int index = 0; index < listVenue.size(); index++)
			if(listVenue.get(index).getVenueID() == venueID)
				return index;
		
		return -1;
	}
	
	/** 
	 * Selects venues in the given list of venues such that they are available at the specified
	 * time slot.
	 * 
	 * @param listVenue - Vector<Venue>
	 * @param preferTime - TimeSlot
	 * @return satisfiedList - Vector<Venue> (a subset of listVenue such that all the venues in the subset
	 * 	is available at the given time slot)
	 */
	private Vector<Venue> shortListByTimeSlot(Vector<Venue> listVenue, TimeSlot preferTime)
	{
		Vector<Venue> returnList = new Vector<Venue>();
		
		for(int index = 0; index < listVenue.size(); index++)
		{
			// For debugging: System.out.println(index);
			if(listVenue.get(index) == null)
				System.out.println("null");
			/* For debugging
			else
			{
				System.out.println("not null");
				System.out.println(listVenue.get(index).getName());
			}
			System.out.println("Venue ID: " + listVenue.get(index).getVenueID());
			System.out.println("Venue Name: " + listVenue.get(index).getName()); */
			if(listVenue.get(index).isAvailable(preferTime) == true)
			{
				// For debugging: System.out.println("Choose venue " + listVenue.get(index).getName());
				returnList.add(listVenue.get(index));
			}
			/* For debugging 
			else
				System.out.println("NOT Choose venue " + listVenue.get(index).getName()); */
			
		}
		
		return returnList;
	}
	
	/**
	 * Selects venues in the given list of venues such that their cost (in cents) is in
	 * the specified range.
	 * 
	 * @param listVenue - Vector<Venue>
	 * @param lower - int
	 * @param upper - int
	 * @return satisfiedList - Vetor<Venue> (a subset of listVenue such that all the venues in the subset 
	 * 		has cost in the range [lower upper])
	 */
	private Vector<Venue> shortListByCost(Vector<Venue> listVenue, int lower, int upper)
	{
		Vector<Venue> returnList = new Vector<Venue>();
		
		for(int index = 0; index < listVenue.size(); index++)
			if(listVenue.get(index).getCost() >= lower &&
				listVenue.get(index).getCost() <= upper)
				returnList.add(listVenue.get(index));
		
		return returnList;
	}
	
	/**
	 * Selects venues in the given list of venues such that their capacity is in the specified range.
	 * 
	 * @param listVenue - Vector<Venue>
	 * @param lower - int
	 * @param upper - int
	 * @return satisfiedList - Vetor<Venue> (a subset of listVenue such that all the venues in the subset 
	 * 		has capacity in the range [lower upper])
	 */
	private Vector<Venue> shortListByCapacity(Vector<Venue> listVenue, int lower, int upper)
	{
		Vector<Venue> returnList = new Vector<Venue>();
		
		for(int index = 0; index < listVenue.size(); index++)
			if(listVenue.get(index).getMaxCapacity() >= lower &&
				listVenue.get(index).getMaxCapacity() <= upper)
				returnList.add(listVenue.get(index));
		
		return returnList;
	}
	
	/**
	 * Selects venues in the given list of venues such that their name or address or description contains
	 * 		the given input string as a substring. Case-insensitivity is considered.
	 * 
	 * @param listVenue - Vector<Venue>
	 * @param subString - String
	 * @return satisfiedList - Vetor<Venue> 
	 */
	private Vector<Venue> shortListByName(Vector<Venue> listVenue, String subString)
	{
		subString = subString.toUpperCase();
		Vector<Venue> returnList = new Vector<Venue>();
		
		for(int index = 0; index < listVenue.size(); index++)
			// Check if subString is a substring of the name of the venue.
			if(listVenue.get(index).getName().toUpperCase().indexOf(subString) >= 0 ||
			   listVenue.get(index).getAddress().toUpperCase().indexOf(subString) >= 0 ||
			   listVenue.get(index).getDescription().toUpperCase().indexOf(subString) >= 0)
				returnList.add(listVenue.get(index));
		
		return returnList;
	}
	
	/**
	 * Sort a vector of Venue by the specified criteria.
	 * 
	 * @param inputVenueList - Vector<Venue>
	 * @param type - SortCriteria
	 */
	public static void sortVenueList(Vector<Venue> inputVenueList, SortCriteria type)
	{
		if(inputVenueList == null)
			return;
		
		Comparator<Venue> compare = null;			// Dummy value
		switch(type)
		{
			case VENUEID:
				compare = new VenueIDComparator();
				break;
			case NAME:
				compare = new NameComparator();
				break;
			case ADDRESS:
				compare = new AddressComparator();
				break;
			case CAPACITY:
				compare = new CapacityComparator();
				break;
			case COST:
				compare = new CostComparator();
		}
		
		if(compare != null)
		{
			Collections.sort(inputVenueList, compare);
		}
				
	}
}
