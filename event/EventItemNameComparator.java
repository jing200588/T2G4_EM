package event;

import java.util.Comparator;

public class EventItemNameComparator implements Comparator<EventItem> {

	//@Override
	public int compare(EventItem objOne, EventItem objTwo) {
		// TODO Auto-generated method stub
		return objOne.getName().compareToIgnoreCase(objTwo.getName());
	}
	

}
