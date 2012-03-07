
public class Date {
	private int _year, _month, _day;
	private String _date;
	
	public Date (int y, int m, int d) {
		_year = y;
		_month = m;
		_day = d;
		_date = "" + d + "-" + m + "-" + y;
	}
	
	public Date(String date) {
		String hm[] = date.split("-");
		_year = Integer.parseInt(hm[2]);
		_month = Integer.parseInt(hm[1]);
		_day = Integer.parseInt(hm[0]);
		_date = date;
	}

	public String getDate () {
		return _date;
	}
	
}

