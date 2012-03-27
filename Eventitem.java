import java.util.*;


public class Eventitem {
	private int _id;
	private String _name;
	private String _description;
	private Vector<Item> _item_list = new Vector<Item>();
	private double _budget;
	private Vector<BookedVenueInfo> _BVI_list;
	private MyDateTime _startDateTime;
	private MyDateTime _endDateTime;
	private List<Participant> _participant_list =  new Vector<Participant>();
	
	/**
	 * Description: Constructs the Event item object with the respective parameters 
	 * @param name Event Name 
	 * @param sy Start Year
	 * @param sm Start Month
	 * @param sd Start Day 
	 * @param ey End Year
	 * @param em End Month
	 * @param ed End Day
 	 * @param shour Start Hour 
	 * @param smin Start Minute
	 * @param ehour End Hour
	 * @param emin End Minute
	 */
	public Eventitem (String name, int sy, int sm, int sd, int ey, int em, int ed, int shour, int smin, int ehour, int emin) {
		_name = name;
		_startDateTime = new MyDateTime (sy, sm, sd, shour, smin);
		_endDateTime = new MyDateTime(ey, em, ed, ehour, emin);
		_description = "";
		_BVI_list = new Vector<BookedVenueInfo>();
	//	_participant_list = new Vector<Participant>();
		_participant_list.add(new Participant("Jonathan", "A1234567", "65554493", "chuichui@gmail.com", "Block 123 Tamp street 81", "-"));
		System.out.println("hello " + _participant_list.size());
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
		_name = name;
		_startDateTime = new MyDateTime(startdate, starttime);
		_endDateTime = new MyDateTime(enddate, endtime);
		_item_list = item_list;
		_budget = budget;
		_BVI_list = BVI_list;
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
		_name = name;
		_startDateTime = new MyDateTime(startdate, starttime);
		_endDateTime = new MyDateTime(enddate, endtime);
	}
	
	/**********************************************************************************************
	 * 
	 * GETTER AND SETTERS
	 * 
	 *********************************************************************************************/
	public void setDescription (String descript) {
		_description = descript;
	}
	
	public void setID(int id){
		_id = id;
	}
	
	public String getDescription () {
		return _description;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public String getName () {
		return _name;
	}
	
//	public void setStartDate (String start) {
//		_startdate = new Date(start);
//	}
	
	public void setStartDate (int y, int m, int d) {
		_startDateTime = new MyDateTime(y, m, d, _startDateTime.getHour(), _startDateTime.getMinute());
	}
	
	
	public MyDateTime getStartDateTime () {
		return _startDateTime;
	}
	
//	public void setEndDate (String end) {
//		_enddate = new Date(end);
//	}
	
	public void setEndDate (int y, int m, int d) {
		_endDateTime = new MyDateTime(y, m, d, _endDateTime.getHour(), _endDateTime.getMinute());
	}
	
	public MyDateTime getEndDateTime () {
		return _endDateTime;
	}	
	
//	public void setStartTime (String start) {
//		_starttime = new Time(start);
//	}
	
	public void setStartTime (int hr, int min) {
		_startDateTime = new MyDateTime(_startDateTime.getYear(), _startDateTime.getMonth(),
				_startDateTime.getDay(), hr, min);
	}
	
	
//	public void setEndTime (String end) {
//		_endtime = new Time(end);
//	}
	
	public void setEndTime (int hr, int min) {
		_endDateTime = new MyDateTime(_endDateTime.getYear(), _endDateTime.getMonth(),
				_endDateTime.getDay(), hr, min);
	}
	
	
	public int getID(){
		return _id;
	}


	public Vector<Item> getitem_list() {
		return _item_list;
	}


	public void setitem_list(Vector<Item> item_list) {
		_item_list = item_list;
	}


	public double getBudget() {
		return _budget;
	}


	public void setBudget(double budget) {
		_budget = budget;
	}


	public Vector<BookedVenueInfo> getBVI_list() {
		return _BVI_list;
	}


	public void addBVI(BookedVenueInfo BVI) {
		_BVI_list.add(BVI);
	}
	
	public void addBVI(Vector<BookedVenueInfo> BVI){
		_BVI_list = BVI;

	}

	public void setParticipantList(List<Participant> newlist) {
		_participant_list = newlist;
	}
	
	public List<Participant> getParticipantList() {
		return _participant_list;
	}
}
