package emanComparator;

import java.util.Comparator;

import venue.BookedVenueInfo;

public class BVICapacityComparator implements Comparator<BookedVenueInfo>{

	//@Override
	public int compare(BookedVenueInfo objOne, BookedVenueInfo objTwo) {
		// TODO Auto-generated method stub
		if(objOne.getMaxCapacity() > objTwo.getMaxCapacity())
			return 1;
		if(objOne.getMaxCapacity() < objTwo.getMaxCapacity())
			return -1;
		return 0;
	}
	

}
