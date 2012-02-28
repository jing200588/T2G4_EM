/**
 * 
 */

/**
 * @author Nguyen Truong Duy (Team 31 - CS2103)
 *
 */
public class BookingSystem {
	
	/**
	 * 
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
	 */
	public static boolean bookVenue(Venue bookedVenue, TimeSlot wantedTimeSlot)
	{
		bookedVenue.bookTimeSlot(wantedTimeSlot);
		
		// Update in the database
		
		// If the booking is successful
		return true;
	}
	
	/**
	 * Description:
	 * 	+ Look up in the database and return the venue with the specified
	 * 		name. 
	 * @param venueName
	 * @return the venue object with the specified name.
	 * @return null if such a venue does not exist.
	 */
	public Venue findVenueByName(String venueName)
	{
		// look up in the database.
		
		return null;
	}
	
}
