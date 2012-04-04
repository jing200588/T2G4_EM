package program;

import java.util.Vector;

import venue.TimeSlot;

public class ControllerEventFlow {
	
	/**
	 * Add an EventFlowEntry object into an already sorted list of EventFlowEntry objects.
	 * 
	 * @param sortedList - Vector<EventFlowEntry>
	 * @param newEntry - EventFlowEntry
	 * 
	 * Assumption: sortedList should be in increasing order of EventFlowEntry objects.
	 */
	public static void insertSortedList(Vector<EventFlowEntry> sortedList, EventFlowEntry newEntry)
	{
		if(newEntry == null || sortedList == null)
			return;
		
		int index = sortedList.size() - 1;
		
		while(index >= 0 && sortedList.get(index).compareTo(newEntry) > 0)
		{
			index--;
		}
		
		sortedList.add(index + 1, newEntry);
	}
	
	/**
	 * Finds all EventFlowEntry objects whose activity names contains the input string as a substring. 
	 * We base on case-insensitivity criteria.
	 * 
	 * @param entireList - Vector<EventFlowEntry>
	 * @param activityName - String
	 * @return listIndex - Vector<Integer> - It contains the indices of all satisfying EventFlowEntry objects.
	 */
	public static Vector<Integer> filterByActivityName(Vector<EventFlowEntry> entireList, String activityName)
	{
		if(activityName == null || entireList == null)
			return new Vector<Integer>();
		
		activityName = activityName.toLowerCase();
		Vector<Integer> listIndex = new Vector<Integer>();
		
		for(int index = 0; index < entireList.size(); index++)
			if(entireList.get(index).getActivityName().toLowerCase().indexOf(activityName) >= 0)
				listIndex.add((Integer) index);
		
		return listIndex;
	}
	
	/**
	 * Finds all EventFlowEntry objects whose activity names contains the input string as a substring. 
	 * We base on case-insensitivity criteria. The indices of elements we consider are given in the
	 * input parameters.
	 * 
	 * In case an entry in the list of indices is not a valid index value of the list of EventFlowEntry
	 * 		objects, we ignore that index entry.
	 *  
	 * @param entireList - Vector<EventFlowEntry>
	 * @param indexList - Vector<Integer>
	 * @param activityName - String
	 * @return listIndex - Vector<Integer> - It contains the indices of all satisfying EventFlowEntry objects.
	 */
	public static Vector<Integer> filterByActivityName(Vector<EventFlowEntry> entireList, 
			Vector<Integer> indexList, String activityName)
	{
		if(activityName == null || indexList == null || entireList == null)
			return new Vector<Integer>();
		
		activityName = activityName.toLowerCase();
		Vector<Integer> resultListIndex = new Vector<Integer>();
		
		for(int index = 0; index < indexList.size(); index++)
		{
			if(indexList.get(index) >= 0 && indexList.get(index) < entireList.size() &&
					entireList.get(indexList.get(index)).getActivityName().toLowerCase().indexOf(activityName) >= 0)
				resultListIndex.add(indexList.get(index));
		}
		
		return resultListIndex;
	}
	
	/**
	 * Finds all EventFlowEntry objects whose duration are within the input TimeSlot object. 
	 * 
	 * @param entireList - Vector<EventFlowEntry>
	 * @param inputTimeSlot - TimeSlot
	 * @return listIndex - Vector<Integer> - It contains the indices of all satisfying EventFlowEntry objects.
	 */
	public static Vector<Integer> filterByTimeSlot(Vector<EventFlowEntry> entireList, TimeSlot inputTimeSlot)
	{
		if(entireList == null || inputTimeSlot == null)
			return new Vector<Integer>();
		
		Vector<Integer> listIndex = new Vector<Integer>();
		
		for(int index = 0; index < entireList.size(); index++)
			if(entireList.get(index).getDuration().isContained(inputTimeSlot) == true)
				listIndex.add((Integer) index);
		
		return listIndex;
	}
	
	/**
	 * Finds all EventFlowEntry objects whose duration are within the input TimeSlot object. The indices 
	 * 		of elements we consider are given in the input parameters.
	 * 
	 * In case an entry in the list of indices is not a valid index value of the list of EventFlowEntry
	 * 		objects, we ignore that index entry.
	 * 
	 * @param entireList - Vector<EventFlowEntry>
	 * @param indexList - Vector<Integer>
	 * @param inputTimeSlot - TimeSlot
	 * @return listIndex - Vector<Integer> - It contains the indices of all satisfying EventFlowEntry objects.
	 */
	public static Vector<Integer> filterByTimeSlot(Vector<EventFlowEntry> entireList, 
			Vector<Integer> indexList, TimeSlot inputTimeSlot)
	{
		if(entireList == null || indexList == null || inputTimeSlot == null)
			return new Vector<Integer>();
		
		Vector<Integer> resultListIndex = new Vector<Integer>();
		
		for(int index = 0; index < entireList.size(); index++)
			if(indexList.get(index) >= 0 && indexList.get(index) < entireList.size() &&
					entireList.get(indexList.get(index)).getDuration().isContained(inputTimeSlot) == true)
				resultListIndex.add(indexList.get(index));
		
		return resultListIndex;
	}
	
	
	/**
	 * Finds all EventFlowEntry objects in the entireList with the input venueID 
	 * 
	 * @param entireList - Vector<EventFlowEntry>
	 * @param inputTimeSlot - TimeSlot
	 * @return listIndex - Vector<Integer> - It contains the indices of all satisfying EventFlowEntry objects.
	 */
	public static Vector<Integer> filterByVenue(Vector<EventFlowEntry> entireList, int venueID)
	{
		if(entireList == null)
			return new Vector<Integer>();
		
		Vector<Integer> listIndex = new Vector<Integer>();
		
		for(int index = 0; index < entireList.size(); index++)
			if(entireList.get(index).getVenueID() == venueID)
				listIndex.add((Integer) index);
		
		return listIndex;
	}
	
	/**
	 * Finds all EventFlowEntry objects in the entireList with the input venueID. The indices 
	 * 		of elements we consider are given in the input parameters.
	 * 
	 * In case an entry in the list of indices is not a valid index value of the list of EventFlowEntry
	 * 		objects, we ignore that index entry.
	 * 
	 * @param entireList - Vector<EventFlowEntry>
	 * @param indexList - Vector<Integer>
	 * @param inputTimeSlot - TimeSlot
	 * @return listIndex - Vector<Integer> - It contains the indices of all satisfying EventFlowEntry objects.
	 */
	public static Vector<Integer> filterByVenue(Vector<EventFlowEntry> entireList,
			Vector<Integer> indexList, int venueID)
	{
		if(entireList == null || indexList == null)
			return new Vector<Integer>();
		
		Vector<Integer> resultListIndex = new Vector<Integer>();
		
		for(int index = 0; index < entireList.size(); index++)
			if(indexList.get(index) >= 0 && indexList.get(index) < entireList.size() &&
					entireList.get(indexList.get(index)).getVenueID() == venueID)
				resultListIndex.add(indexList.get(index));
		
		return resultListIndex;
	}
	
	/**
	 * Selects a subset of EventFlowEntry objects in the original list based on the indices given
	 * by the user.
	 * If an index is invalid, we ignore it.
	 * 
	 * @param entireList - Vector<EventFlowEntry>
	 * @param wantIndices - Vector<Integer>
	 * @return chosenList - Vector<EventFlowEntry>
	 */
	public static Vector<EventFlowEntry> selectEntry(Vector<EventFlowEntry> entireList, 
			Vector<Integer> wantIndices)
	{
		if(entireList == null || wantIndices == null)
			return new Vector<EventFlowEntry>();
		
		Vector<EventFlowEntry> chosenList = new Vector<EventFlowEntry>();
		for(int index = 0; index < wantIndices.size(); index++)
			// Check validity
			if(wantIndices.get(index) >= 0 && wantIndices.get(index) < entireList.size())
				chosenList.add(entireList.get(wantIndices.get(index)));
		
		return chosenList;
	}
}
