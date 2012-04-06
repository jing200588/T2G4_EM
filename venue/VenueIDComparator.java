package venue;

import java.util.Comparator;

public class VenueIDComparator implements Comparator<Venue>
{

//	@Override
	public int compare(Venue objOne, Venue objTwo) {
		// TODO Auto-generated method stub
		if(objOne.getVenueID() > objTwo.getVenueID())
			return 1;
		if(objOne.getVenueID() < objTwo.getVenueID())
			return -1;
		return 0;
	}
	
}
