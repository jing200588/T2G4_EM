/**
 * 
 */

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
	 * 
	 * @param event
	 * @param bookedVenueID
	 * @param listVenues
	 * @param wantedTimeSlot
	 * 
	 * Description: 
	 * + Book a venue with the specified time slot. 
	 * + Update the new information in the database of venues.
	 * + Update the new information in the EventItem object itself.
	 * 
	 * Assumption: The venue is available within the time slot (i.e. The
	 * 		availability checking should have been done before calling
	 * 		this function).
	 * 
	 * @return true if the booking is successful.
	 * @return false if there is an error during the booking process.
	 * 
	 * (DATABASE):
	 * 		+ A method to update a venue of an event object with event ID.
	 * 		+ A method to update booked time slot of a venue.
	 * 		+ Do you think we need a venue ID in the class Venue?
	 */
	public static boolean bookVenue(Eventitem event, Vector<Venue> listVenues,
			int bookedVenueID, TimeSlot wantedTimeSlot)
	{	
		mbs.add_booking_to_db(event.getID(), bookedVenueID, wantedTimeSlot);
		
		int index = findIndex(listVenues, bookedVenueID);
		// index is in valid range!!
		event.addBVI(listVenues.get(index), wantedTimeSlot);
		// If the booking is successful
		return true;
	}
	
	/**
	 * 
	 * @param eventID
	 * @param outside
	 * @param wanted
	 * @return true if the information is updated in the database successfully.
	 * @return false if the updating cannot be done.
	 * 
	 * Description: 
	 *    + Update the information about a venue that is not in the venue 
	 *      database in the record of an event specified by eventID.
	 *    + The system will not book such a venue. Event manager has to do
	 *    	the booking and time checking on his / her own.
	 */
	public static boolean chooseOutsideVenue(int eventID, Venue outside, TimeSlot wanted)
	{
		// Update in the database of event
		
		return true;
	}
	/**
	 * Description:
	 * 	+ Look up in the database and return the venue with the specified
	 * 		name. 
	 * @param venueName
	 * @return the venue object with the specified name.
	 * @return null if such a venue does not exist.
	 * 
	 * (DATABASE):
	 * 	+ A method to return a venue with the specific name.
	 */
	public static Vector<Venue> findVenueByName(String venueName)
	{
		// look up in the database.
		// Search venue by Name
		//return null;
		
		Vector<Venue> list = new Vector<Venue>();
		list = mbs.find_venue_by_name(venueName);
		
		return list;
	}
	
	/**
	 * 
	 * @param costRange (a pair of double numbers (a, b) where a <= b)
	 * @param capacityRange (a pair of integers (a, b) where a <= b)
	 * @param preferredTime (a TimeSlot object)
	 * @param type
	 * @return a list of venues satisfying the specified criteria.
	 * 
	 * Assumption: The input is correct.
	 * 
	 * (DATABASE):
	 * 	1) A method to return a list of venues such that their costs 
	 * 		are in the range [A, B]
	 * 	2) A method to return a list of venues such that their capacity
	 * 		are in the range [A, B]
	 * 	3) A method to return a list of venues such that they are available
	 * 			(not clashed) at the specified time slot.
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
	 * 
	 * @param listVenue
	 * @param venueID
	 * @param preferredTime
	 * @return true if a venue with venueID in the list of venues is available at the given
	 * 				time slot. 
	 * @return false otherwise.
	 * 
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
	 * 
	 * @param listVenue
	 * @param venueID
	 * @return a string containing the details of a venue with venueID in the list of venues.
	 * 
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
	 * 
	 * @param listVenue
	 * @param venueID
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
	 * 
	 * @param listVenue
	 * @param preferTime
	 * @return a subset of listVenue such that all the venues in the subset
	 * 		is available at the given time slot.
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
	 * 
	 * @param listVenue
	 * @param lower
	 * @param upper
	 * @return a subset of listVenue such that all the venues in the subset 
	 * 		has cost in the range [lower upper]
	 */
	private static Vector<Venue> shortListByCost(Vector<Venue> listVenue,
						double lower, double upper)
	{
		Vector<Venue> returnList = new Vector<Venue>();
		
		for(int index = 0; index < listVenue.size(); index++)
			if(listVenue.get(index).getCost() > lower - THRESHOLD &&
				listVenue.get(index).getCost() < upper + THRESHOLD)
				returnList.add(listVenue.get(index));
		
		return returnList;
	}
	
}
