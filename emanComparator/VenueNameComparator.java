package emanComparator;

import java.util.Comparator;

import venue.Venue;

public class VenueNameComparator implements Comparator<Venue>
{

//	@Override
	public int compare(Venue objOne, Venue objTwo) {
		// TODO Auto-generated method stub
		return objOne.getName().compareToIgnoreCase(objTwo.getName());
	}
	
}
