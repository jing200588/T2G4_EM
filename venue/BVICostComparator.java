package venue;

import java.util.Comparator;

public class BVICostComparator implements Comparator<BookedVenueInfo>{

	@Override
	public int compare(BookedVenueInfo objOne, BookedVenueInfo objTwo) {
		// TODO Auto-generated method stub
		if(objOne.getCostInCent() > objTwo.getCostInCent())
			return 1;
		if(objOne.getCostInCent() < objTwo.getCostInCent())
			return -1;
		return 0;
	}
}

