

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
	
	public Time(String time) {
		String hm[] = time.split(":");
		_hours = Integer.parseInt(hm[0]);;
		_mins = Integer.parseInt(hm[1]);;
		_time = time;
	}

	public String getTime () {
		return _time;
	}
}