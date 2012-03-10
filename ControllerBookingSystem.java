/**
 * @author Nguyen Truong Duy (Team 31 - CS2103)
 *
 */
import java.util.Vector;

public class ControllerBookingSystem {
	
	public static enum SearchCriteria{COST, CAPACITY, TIME, COST_TIME,
						CAPACITY_TIME, COST_CAPACITY, ALL_THREE}
	public static final double THRESHOLD = Double.MIN_VALUE;
	
	private static ModelBookingSystem mbs = new ModelBookingSystem();
	
	/**
	 * Books a venue with bookedVenueID from a list of venues for an event with a specified time
	 * slot. It is assumed that the venue is available at the give time slot. Therefore, The
	 * availability checking should have been done before calling this method.
	 * The method update the new booking information in the database of venues and in the 
	 * 		Eventitem object itself.
	 * 
	 * @param event - Eventitem
	 * @param listVenues - Vector<Venue>
	 * @param bookedVenueID - int
	 * @param wantedTimeSlot - TimeSlot
	 * 
	 * @return true if the booking is successful.
	 * @return false if there is an error during the booking process.
	 * 
	 */
	public static boolean bookVenue(Eventitem event, Vector<Venue> listVenues,
			int bookedVenueID, TimeSlot wantedTimeSlot)
	{	
		System.out.println("Hi Inside BookVenue");
		System.out.println("This is my EVENT: " + event);
		System.out.println("This is my EVENT: " + listVenues);
		System.out.println("This is my EVENT: " + listVenues.get(0));
		mbs.add_booking_to_db(event.getID(), bookedVenueID, wantedTimeSlot);
		
		int index = findIndex(listVenues, bookedVenueID);
		System.out.println("Inside BookVenue" + index);
		// index is in valid range!!
		BookedVenueInfo newObj = new BookedVenueInfo(listVenues.get(index), wantedTimeSlot);
		event.addBVI(newObj);
		// If the booking is successful
		System.out.println("Hi! THis is the end of bookVenue");
		return true;
	}
	
	/**
	 * Adds a venue which is not in the database of venues. It is provided by the user. However, such
	 * a venue is not supported by the system. That is, the system will not book the venue. Event 
	 * manager has to do the booking and time checking on his / her own.
	 * The method updates the information about the outside venue in the record of an event specified by eventID.
	 * Currently, this method is under heavy construction.
	 * 
	 * @param eventID - int
	 * @param outside - Venue
	 * @param wanted - TimeSlot
	 * @return true if the information is updated in the database successfully.
	 * @return false if the updating cannot be done.
	 */
	public static boolean chooseOutsideVenue(int eventID, Venue outside, TimeSlot wanted)
	{
		// Update in the database of event
		
		return true;
	}
	
	/**
	 * Looks up in the database and returns the venues whose names contain the input string as a
	 * substring.
	 *  
	 * @param venueName - String
	 * @return list - Vector<Venue>
	 * list will be empty if there is no venue with such a name.
	 */
	public static Vector<Venue> findVenueByName(String venueName)
	{
		Vector<Venue> list = new Vector<Venue>();
		list = mbs.find_venue_by_name(venueName);
		
		return list;
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
	public static Vector<Venue> findVenueByCriteria(int[] costRange, int[] capacityRange,
			TimeSlot preferredTime, SearchCriteria type)
	{
		Vector<Venue> returnList = new Vector<Venue>();
		
		
		switch(type)
		{
			case CAPACITY:
			case CAPACITY_TIME:
			case COST_CAPACITY:
			case ALL_THREE:
			{
				Vector<Venue> firstRoundCapacity = mbs.get_venue_by_capacity(capacityRange[1], capacityRange[0]); 
				
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
				Vector<Venue> firstRoundCost = mbs.get_venue_by_cost(costRange[1], costRange[0]); ;
				
				
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
				Vector<Venue> allVenue = mbs.get_all_venue();
				returnList = shortListByTimeSlot(allVenue, preferredTime);
				
			}
		}
		
		if(returnList == null)
			returnList = new Vector<Venue>();
		
		return returnList;
	}

	/**
	 * Checks a venue with venueID from a list of venues is available at a specified time
	 * slot.
	 * 
	 * @param listVenue - Vector<Venue>
	 * @param venueID - int
	 * @param preferredTime - TimeSlot
	 * @return true if a venue with venueID in the list of venues is available at the given
	 * time slot. 
	 * @return false otherwise.
	 * @throws Exception If there does exist a venue with venueID in the list.
	 */
	public static boolean isAvailable(Vector<Venue> listVenue, int venueID, TimeSlot preferredTime) throws Exception
	{
		int index = findIndex(listVenue, venueID);
		System.out.println("I'm here. Venue ID: " + venueID + " Index: " + index + " Time: " + preferredTime);
		if(index < 0)		// A venue with venueID does not exist
			throw new Exception("There is no venue with such an ID in the table above!");
		System.out.println(listVenue.get(index));
		return listVenue.get(index).isAvailable(preferredTime);
	}
	
	/**
	 * Returns a string that contains all information about a venue with venueID in the list of venues.
	 * 
	 * @param listVenue - Vector<Venue>
	 * @param venueID - int
	 * @return venueDetail - String
	 * @throws Exception if there does not exist such a venue with venueID in the list.
	 */
	public static String getVenueDetail(Vector<Venue> listVenue, int venueID) throws Exception
	{
		int index = findIndex(listVenue, venueID);
		
		if(index < 0)		// A venue with venueID does not exist
			throw new Exception("There is no venue with such an ID in the table above!");
		String[] infoPieces = listVenue.get(index).getAllVenueInformation();
		
		String output = "Name: " + infoPieces[0] + "\nAddress: " + infoPieces[1] + 
				"\nDescription: " + infoPieces[2] + "\nMaximum Capacity: " + infoPieces[3] +
				"\nCost: $" + infoPieces[4];
		
		return output;
	}
	
	/**
	 * Checks if a venue with venueID is in the input list of venues.
	 * 
	 * @param listVenue - Vector<Venue>
	 * @param venueID - int
	 * @return true if there exists a venue with venueID in the list.
	 * @return false otherwise
	 */
	public static boolean isInTheList(Vector<Venue> listVenue, int venueID)
	{
		if(findIndex(listVenue, venueID) < 0)
			return false;
		return true;
	}
	
	/**
	 * Returns the index of a venue with venueID in the input list of venues.
	 * 
	 * @param listVenue
	 * @param venueID
	 * @return the index of a venue with venueID in the list of venues (listVenue)
	 * @return -1 if such an element does not exist.
	 */
	private static int findIndex(Vector<Venue> listVenue, int venueID)
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
	private static Vector<Venue> shortListByTimeSlot(Vector<Venue> listVenue,
						TimeSlot preferTime)
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
	private static Vector<Venue> shortListByCost(Vector<Venue> listVenue,
						int lower, int upper)
	{
		Vector<Venue> returnList = new Vector<Venue>();
		
		for(int index = 0; index < listVenue.size(); index++)
			if(listVenue.get(index).getCost() >= lower &&
				listVenue.get(index).getCost() <= upper)
				returnList.add(listVenue.get(index));
		
		return returnList;
	}
	
}
