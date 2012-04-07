package venue;

import java.util.Comparator;

public class BVIStartDateTimeComparator implements Comparator<BookedVenueInfo>{

	//@Override
	public int compare(BookedVenueInfo objOne, BookedVenueInfo objTwo) {
		// TODO Auto-generated method stub
		return objOne.getBookedTimeSlot().getStartDateTime().compareTo(
				objTwo.getBookedTimeSlot().getStartDateTime());
	}
}
