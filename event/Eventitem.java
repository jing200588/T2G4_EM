package event;

import java.util.*;
import venue.*;
import budget.*;
import participant.*;
import program.*;


public class Eventitem {
	private int eventId;
	private String eventName = "";
	private String eventDescription	= "";
	private Vector<Item> eventItemList = new Vector<Item>();
	private double eventBudget	= 0.0;
	private Vector<BookedVenueInfo> eventBviList;
	private MyDateTime eventStartDateTime;
	private MyDateTime eventEndDateTime;
	private List<Participant> eventParticipantList =  new Vector<Participant>();
	private Vector<EventFlowEntry> eventFlow = new Vector<EventFlowEntry>();
	private boolean expired;
	
	/**
	 * Description: Constructs the Event item object with the respective parameters 
	 * @param inputName Event Name 
	 * @param inputStartYear Start Year
	 * @param inputStartMonth Start Month
	 * @param inputStartDay Start Day 
	 * @param inputEndYear End Year
	 * @param inputEndMonth End Month
	 * @param inputEndDay End Day
 	 * @param inputStartHour Start Hour 
	 * @param inputStartMin Start Minute
	 * @param inputEndHour End Hour
	 * @param inputEndMin End Minute
	 */
	public Eventitem (String inputName, int inputStartYear, int inputStartMonth, int inputStartDay, int inputEndYear, int inputEndMonth, int inputEndDay, int inputStartHour, int inputStartMin, int inputEndHour, int inputEndMin, String inputDescription) {
		eventName = inputName;
		eventStartDateTime = new MyDateTime (inputStartYear, inputStartMonth, inputStartDay, inputStartHour, inputStartMin);
		eventEndDateTime = new MyDateTime(inputEndYear, inputEndMonth, inputEndDay, inputEndHour, inputEndMin);
		eventDescription = inputDescription;
		eventBviList = new Vector<BookedVenueInfo>();
		expired = false;
	//	_participant_list = new Vector<Participant>();
		eventParticipantList.add(new Participant("Jonathan", "A1234567", "65554493", "chuichui@gmail.com", "Block 123 Tamp street 81", "-"));
		//System.out.println("hello " + _participant_list.size());
	}
	
	/**
	 * Description: Constructs the Event item object with the respective parameters
	 * @param name
	 * @param startdate
	 * @param enddate
	 * @param starttime
	 * @param endtime
	 * @param item_list List of items according to budget
	 * @param budget
	 * @param BVI_list List of booked venue information
	 */
	public Eventitem (String name, String startdate, String enddate, String starttime, String endtime, Vector<Item> item_list, double budget, Vector<BookedVenueInfo> BVI_list){
		eventName = name;
		eventStartDateTime = new MyDateTime(startdate, starttime);
		eventEndDateTime = new MyDateTime(enddate, endtime);
		eventItemList = item_list;
		eventBudget = budget;
		eventBviList = BVI_list;
	}
	
	/**
	 * Description: Constructs the Event item object with the respective parameters
	 * @param name
	 * @param startdate
	 * @param enddate
	 * @param starttime
	 * @param endtime
	 */
	public Eventitem (String name, String startdate, String enddate, String starttime, String endtime) {
		eventName = name;
		eventStartDateTime = new MyDateTime(startdate, starttime);
		eventEndDateTime = new MyDateTime(enddate, endtime);
	}
	
	/**********************************************************************************************
	 * 
	 * GETTER AND SETTERS
	 * 
	 *********************************************************************************************/
	public void setDescription (String descript) {
		eventDescription = descript;
	}
	
	public void setID(int id){
		eventId = id;
	}
	
	public String getDescription () {
		return eventDescription;
	}
	
	public void setName(String name) {
		eventName = name;
	}
	
	public String getName () {
		return eventName;
	}
	
//	public void setStartDate (String start) {
//		_startdate = new Date(start);
//	}
	
	public void setStartDate (int y, int m, int d) {
		eventStartDateTime = new MyDateTime(y, m, d, eventStartDateTime.getHour(), eventStartDateTime.getMinute());
	}
	
	
	public MyDateTime getStartDateTime () {
		return eventStartDateTime;
	}
	
//	public void setEndDate (String end) {
//		_enddate = new Date(end);
//	}
	
	public void setEndDate (int y, int m, int d) {
		eventEndDateTime = new MyDateTime(y, m, d, eventEndDateTime.getHour(), eventEndDateTime.getMinute());
	}
	
	public MyDateTime getEndDateTime () {
		return eventEndDateTime;
	}	
	
//	public void setStartTime (String start) {
//		_starttime = new Time(start);
//	}
	
	public void setStartTime (int hr, int min) {
		eventStartDateTime = new MyDateTime(eventStartDateTime.getYear(), eventStartDateTime.getMonth(),
				eventStartDateTime.getDay(), hr, min);
	}
	
	
//	public void setEndTime (String end) {
//		_endtime = new Time(end);
//	}
	
	public void setEndTime (int hr, int min) {
		eventEndDateTime = new MyDateTime(eventEndDateTime.getYear(), eventEndDateTime.getMonth(),
				eventEndDateTime.getDay(), hr, min);
	}
	
	
	public int getID(){
		return eventId;
	}


	public Vector<Item> getItemList() {
		return eventItemList;
	}

	public Vector<EventFlowEntry> getEventFlow()
	{
		return eventFlow;
	}

	public void setItemList(Vector<Item> inputItemList) {
		eventItemList = inputItemList;
	}


	public double getBudget() {
		return eventBudget;
	}


	public void setBudget(double budget) {
		eventBudget = budget;
	}


	public Vector<BookedVenueInfo> getBviList() {
		return eventBviList;
	}


	public void addBVI(BookedVenueInfo BVI) {
		eventBviList.add(BVI);
	}
	
	public void addBVI(Vector<BookedVenueInfo> BVI){
		eventBviList = BVI;

	}

	public void setParticipantList(List<Participant> newlist) {
		eventParticipantList = newlist;
	}
	
	public List<Participant> getParticipantList() {
		return eventParticipantList;
	}
	
	public void setIsExpired(boolean flag) {
		expired = flag;
	}
	public void setEventFlow(Vector<EventFlowEntry> newEventFlow)
	{
		eventFlow = newEventFlow;
	}
	public boolean isExpired() {
		return expired;
	}
}
