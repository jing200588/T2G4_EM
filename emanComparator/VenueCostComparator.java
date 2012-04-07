package emanComparator;

import java.util.Comparator;

import venue.Venue;

public class VenueCostComparator implements Comparator<Venue>
{

//	@Override
	public int compare(Venue objOne, Venue objTwo) {
		// TODO Auto-generated method stub
		if(objOne.getCost() > objTwo.getCost())
			return 1;
		if(objOne.getCost() < objTwo.getCost())
			return -1;
		return 0;
	}
	
}
