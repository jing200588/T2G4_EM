import java.util.*;


public class Eventitem {
	private int _id;
	private String _name;
	private String _description;
	private Vector<Item> _item_list;
	private double _budget;
	//private Vector<BookedVenueInfo> _BVI_list;
	private Date _startdate;
	private Time _starttime;
	private Date _enddate;
	private Time _endtime;
	//	private Vector<Participants> _partlist;
	//private eventflow??
	//private advertise
	
	public Eventitem (String name, int sy, int sm, int sd, int ey, int em, int ed, int shour, int smin, int ehour, int emin) {
		_name = name;
		_startdate = new Date (sy, sm, sd);
		_enddate = new Date (ey, em, ed);
		_starttime = new Time (shour, smin);
		_endtime = new Time (ehour, emin);
		_description = "";
		//_BVI_list = new Vector<BookedVenueInfo>();
	}
	
	
	public Eventitem (String name, String startdate, String enddate, String starttime, String endtime, Vector<Item> item_list, double budget) {//, BookedVenueInfo BVI_list){
		_name = name;
		_startdate = new Date(startdate);
		_enddate = new Date(enddate);
		_starttime = new Time(starttime);
		_endtime = new Time(endtime);
		_item_list = item_list;
		_budget = budget;
		//_BVI_list = BVI_list;
	}
	
	public Eventitem (String name, String startdate, String enddate, String starttime, String endtime) {
		_name = name;
		_startdate = new Date(startdate);
		_enddate = new Date(enddate);
		_starttime = new Time(starttime);
		_endtime = new Time(endtime);
	}
	
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
	
	public void setStartDate (String start) {
		_startdate = new Date(start);
	}
	
	public String getStartDate () {
		return _startdate.getDate();
	}
	
	public void setEndDate (String end) {
		_enddate = new Date(end);
	}
	
	public String getEndDate () {
		return _enddate.getDate();
	}	
	
	public void setStartTime (String start) {
		_starttime = new Time(start);
	}
	
	public String getStartTime () {
		return _starttime.getTime();
	}
	
	public void setEndTime (String end) {
		_endtime = new Time(end);
	}
	
	public String getEndTime () {
		return _endtime.getTime();
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

/*
	public Vector<Venue> getBVI_list() {
		return _BVI_list;
	}


	public void addBVI(BookedVenueInfo BVI) {
		_BVI_list.add(BVI);
	}
	
*/
}
