package emanComparator;

import java.util.Comparator;

import venue.BookedVenueInfo;

public class BVINameComparator implements Comparator<BookedVenueInfo>{

	//@Override
	public int compare(BookedVenueInfo objOne, BookedVenueInfo objTwo) {
		// TODO Auto-generated method stub
		return objOne.getName().compareToIgnoreCase(objTwo.getName());
	}
	

}
