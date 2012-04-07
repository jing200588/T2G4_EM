package emanComparator;

import java.util.Comparator;

import venue.Venue;

public class VenueCapacityComparator implements Comparator<Venue>
{

//	@Override
	public int compare(Venue objOne, Venue objTwo) {
		// TODO Auto-generated method stub
		if(objOne.getMaxCapacity() > objTwo.getMaxCapacity())
			return 1;
		if(objOne.getMaxCapacity() < objTwo.getMaxCapacity())
			return -1;
		return 0;
	}
}
