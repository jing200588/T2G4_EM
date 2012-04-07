package venue;

import java.util.Comparator;

public class BVINameComparator implements Comparator<BookedVenueInfo>{

	//@Override
	public int compare(BookedVenueInfo objOne, BookedVenueInfo objTwo) {
		// TODO Auto-generated method stub
		return objOne.getName().compareToIgnoreCase(objTwo.getName());
	}
	

}
