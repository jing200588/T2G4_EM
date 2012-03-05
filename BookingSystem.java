/**
 * 
 */

/**
 * @author Nguyen Truong Duy (Team 31 - CS2103)
 *
 */
import java.util.Vector;

public class BookingSystem {
	
	public static enum SearchCriteria{COST, CAPACITY, TIME, COST_TIME,
						CAPACITY_TIME, COST_CAPACITY, ALL_THREE}
	public static final double THRESHOLD = Double.MIN_VALUE;
	/**
	 * 
	 * @param eventID
	 * @param bookedVenue
	 * @param wantedTimeSlot
	 * 
	 * Description: Book a venue with the specified time slot. Update the
	 * 		new information in the database of venues.
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
	public static boolean bookVenue(int eventID, Venue bookedVenue, TimeSlot wantedTimeSlot)
	{
		bookedVenue.bookTimeSlot(wantedTimeSlot);
		
		// Update in the database of venue.
		
		// Update in the database of event.
		
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
	public static Venue findVenueByName(String venueName)
	{
		// look up in the database.
		
		return null;
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
		Vector<Venue> returnList = null;
		
		switch(type)
		{
			case CAPACITY:
			case CAPACITY_TIME:
			case COST_CAPACITY:
			case ALL_THREE:
			{
				// Search in the DATABASE
				// Dummy code (will be REPLACED) when there is a database
				Vector<Venue> firstRoundCapacity = new Vector<Venue>(); 
				
				// Actual code
				if(type == SearchCriteria.CAPACITY)
				{
					returnList = firstRoundCapacity;
				}
				else
				{
					if(type == SearchCriteria.COST_CAPACITY)
					{
						returnList = shortListByCost(firstRoundCapacity,
								costRange[0], costRange[1]);
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
				// Search in the DATABASE
				// Dummy code (will be REPLACED) when there is a database
				Vector<Venue> firstRoundCost = new Vector<Venue>();
				
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
				// Search in the DATABASE
				// Dummy code (will be REPLACED) when there is a database
				returnList = new Vector<Venue>();
			}
		}
		
		return returnList;
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
			if(listVenue.get(index).isAvailable(preferTime) == true)
				returnList.add(listVenue.get(index));
		
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
