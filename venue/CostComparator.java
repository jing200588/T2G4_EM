package venue;

import java.util.Comparator;

public class CostComparator implements Comparator<Venue>
{

	@Override
	public int compare(Venue objOne, Venue objTwo) {
		// TODO Auto-generated method stub
		if(objOne.getCost() > objTwo.getCost())
			return 1;
		if(objOne.getCost() < objTwo.getCost())
			return -1;
		return 0;
	}
	
}
