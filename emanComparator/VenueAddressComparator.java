package emanComparator;

import java.util.Comparator;

import venue.Venue;

public class VenueAddressComparator implements Comparator<Venue>
{

//	@Override
	public int compare(Venue objOne, Venue objTwo) {
		// TODO Auto-generated method stub
		return objOne.getAddress().compareToIgnoreCase(objTwo.getAddress());
	}
	
}
