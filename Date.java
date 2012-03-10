
public class Date {
	private int _year, _month, _day;
	private String _date;
	
	/**
	 * Description: Constructs the Date object with the respective parameters
	 * @param y
	 * @param m
	 * @param d
	 */
	public Date (int y, int m, int d) {
		_year = y;
		_month = m;
		_day = d;
		String day, month, hour, min;
		
		if (d < 10)
			day = "0" + d;
		else
			day = "" + d;
		if (m < 10)
			month = "0" + m;
		else
			month = "" + m;
		
			
		_date = "" + day + "-" + month + "-" + y;
	}
	
	/**
	 * Description: Splits the date value in String format into integer year, month and day
	 * @param date
	 */
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

