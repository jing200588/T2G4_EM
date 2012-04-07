package emanComparator;

import java.util.Comparator;

import event.EventItem;

public class EventItemNameComparator implements Comparator<EventItem> {

	//@Override
	public int compare(EventItem objOne, EventItem objTwo) {
		// TODO Auto-generated method stub
		return objOne.getName().compareToIgnoreCase(objTwo.getName());
	}
	

}
