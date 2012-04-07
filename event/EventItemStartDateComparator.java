package event;

import java.util.Comparator;

public class EventItemStartDateComparator implements Comparator<EventItem> {

	@Override
	public int compare(EventItem objOne, EventItem objTwo) {
		// TODO Auto-generated method stub
		return objOne.getStartDateTime().compareTo(objTwo.getStartDateTime());
	}
	

}
