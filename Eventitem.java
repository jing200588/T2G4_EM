import java.util.Vector;


public class Eventitem {
	private String _name;
	private String _description;
//	private Vector<Venue> _venuelist;
//	private Vector<Participants> _partlist;
	//private eventflow??
	//private budget
	//private advertise
	private Date _startdate;
//	private Time _starttime;
	private Date _enddate;
//	private Time _endtime;
	
	public Eventitem (String name, int sy, int sm, int sd, int ey, int em, int ed) {
		_name = name;
		_startdate = new Date (sy, sm, sd);
		_enddate = new Date (ey, em, ed);
	}
	
	public void setDescription (String descript) {
		_description = descript;
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
	
	public void setStartDate (Date start) {
		_startdate = start;
	}
	
	public String getStartDate () {
		return _startdate.getDate();
	}
	
	public void setEndDate (Date end) {
		_enddate = end;
	}
	
	public String getEndDate () {
		return _enddate.getDate();
	}	
	
}
