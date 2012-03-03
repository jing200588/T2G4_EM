
public class Date {
	private int _year, _month, _day;
	private String _date;
	
	public Date (int y, int m, int d) {
		_year = y;
		_month = m;
		_day = d;
		_date = "" + d + "-" + m + "-" + y;
	}
	
	public String getDate () {
		return _date;
	}
	
}

