


public class Time {
	private int _hours, _mins;
	private String _time;
	
	/**
	 * Constructs the Time object with the respective parameters
	 * @param h
	 * @param m
	 */
	public Time (int h, int m) {
		_hours = h;
		_mins = m;
		
		if (h < 10)
			_time = "0" + h + ":";
		else
			_time = "" + h + ":";
		if (m < 10)
			_time += "0" + m;
		else
			_time += m;
			
	}
	
	public int get_hours() {
		return _hours;
	}

	public void set_hours(int _hours) {
		this._hours = _hours;
	}

	public int get_mins() {
		return _mins;
	}

	public void set_mins(int _mins) {
		this._mins = _mins;
	}

	/**
	 * Description: Splits the Time value in String format into integer hours and minutes
	 * @param time
	 */
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