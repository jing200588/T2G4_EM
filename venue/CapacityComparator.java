package venue;

import java.util.Comparator;

public class CapacityComparator implements Comparator<Venue>
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
