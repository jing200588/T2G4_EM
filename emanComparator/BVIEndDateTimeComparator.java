package emanComparator;

import java.util.Comparator;

import venue.BookedVenueInfo;

public class BVIEndDateTimeComparator implements Comparator<BookedVenueInfo>{

	//@Override
	public int compare(BookedVenueInfo objOne, BookedVenueInfo objTwo) {
		// TODO Auto-generated method stub
		return objOne.getBookedTimeSlot().getEndDateTime().compareTo(
				objTwo.getBookedTimeSlot().getEndDateTime());
	}
}
