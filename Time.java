

public class Time {
	private int _hours, _mins;
	private String _time;
	
	public Time (int h, int m) {
		_hours = h;
		_mins = m;
		
		_time = "" + h + ":";
		
		if (m == 0)
			_time += "00";
		else 
			_time += m;
	}
	
	public String getTime () {
		return _time;
	}
}