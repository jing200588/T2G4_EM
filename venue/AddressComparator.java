package venue;

import java.util.Comparator;

public class AddressComparator implements Comparator<Venue>
{

//	@Override
	public int compare(Venue objOne, Venue objTwo) {
		// TODO Auto-generated method stub
		return objOne.getAddress().compareToIgnoreCase(objTwo.getAddress());
	}
	
}
