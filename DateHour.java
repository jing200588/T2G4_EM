/**
 * @author Nguyen Truong Duy (Team 31 - CS2103)
 * 
 * DateHour: immutable!
 */
public class DateHour implements Comparable<DateHour> {
	/*************************************************************************
	 * Class member variable
	 ************************************************************************/
	private int m_year;
	private int m_month;
	private int m_day;
	private int m_hour;
	
	/*************************************************************************
	 * Constructor
	 * 
	 * Assumption: The validity of the date and hour were checked
	 ************************************************************************/
	public DateHour(int year, int month, int day, int hour)
	{
		m_year = year;
		m_month = month;
		m_day = day;
		m_hour = hour;
	}
	
	public DateHour(DateHour anotherObj)
	{
		m_year = anotherObj.getYear();
		m_month = anotherObj.getMonth();
		m_day = anotherObj.getDay();
		m_hour = anotherObj.getHour();
	}
	
	// Create a DateHour object from the input string which has the format:
	//			<day>/<month>/<year>/<hour>
	// That is the 4 fields (day, month, year, hour) are separated by '/'
	// Assumption: the input string is in the correct format
	public DateHour(String dateHourRepresentation)
	{
		String[] arrString = dateHourRepresentation.split("/");
		m_day = Integer.parseInt(arrString[0]);
		m_month = Integer.parseInt(arrString[1]);
		m_year = Integer.parseInt(arrString[2]);
		m_hour = Integer.parseInt(arrString[3]);
	}
	
	/************************************************************************
	 * Methods that support extracting information
	 ************************************************************************/
	public int getYear()
	{
		return m_year;
	}
	
	public int getMonth()
	{
		return m_month;
	}
	
	public int getDay()
	{
		return m_day;
	}
	
	public int getHour()
	{
		return m_hour;
	}
	
	// Return a String object that represent the DateHour object.
	// The format is dd/mm/yyyy/hh
	public String getDateHourRepresentation()
	{
		char[] charArr = new char[13];
		charArr[2] = charArr[5] = charArr[10] = '/';
		int day = m_day;
		int month = m_month;
		int year = m_year;
		int hour = m_hour;
		for(int i = 0; i < 2; i++)
		{
			charArr[1 - i] = Character.forDigit(day % 10, 10);
			charArr[4 - i] = Character.forDigit(month % 10, 10);
			charArr[12 - i] = Character.forDigit(hour % 10, 10);
			day /= 10;
			month /= 10;
			hour /=10;
 		}
		for(int i = 0; i < 4; i++)
		{
			charArr[9 - i] = Character.forDigit(year % 10, 10);
			year /= 10;
		}
		return new String(charArr);
	}
	
	// Return a String object that represents the date of DateHour object.
	// The format is dd/mm/yyyy
	public String getDateRepresentation()
	{
		char[] charArr = new char[10];
		charArr[2] = charArr[5] = '/';
		int day = m_day;
		int month = m_month;
		int year = m_year;
	
		for(int i = 0; i < 2; i++)
		{
			charArr[1 - i] = Character.forDigit(day % 10, 10);
			charArr[4 - i] = Character.forDigit(month % 10, 10);
			day /= 10;
			month /= 10;
 		}
		for(int i = 0; i < 4; i++)
		{
			charArr[9 - i] = Character.forDigit(year % 10, 10);
			year /= 10;
		}
		return new String(charArr);
	}
	
	/**************************************************************************
	 * Methods that support querying
	 *************************************************************************/
	/**
	 * @param year
	 * @return true if the given input is a valid leap year. It returns false
	 * 		   otherwise.
	 */
	public static boolean isLeapYear(int year)
	{
		if(year < 0)
			return false;
		if(year % 400 == 0)
			return true;
		if(year % 100 != 0 && year % 4 == 0)
			return true;
		return false;
	}
	
	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @return true if the given parameters are of a valid date and hour. It
	 * 		   returns false otherwise
	 * 
	 * month: 1 - 12
	 * day: 1 - 31
	 * hour: 0 - 23
	 */
	public static boolean isValidDateHour(int year, int month, int day, int hour)
	{
		if(year < 0 || month < 1 || month > 12 || day < 1 || day > 31
				|| hour < 0 || hour > 23)
			return false;
		
		int maxDay = 0;		
		switch(month)
		{
			case 1: 
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				maxDay = 31;
				break;
			case 2:
				if(isLeapYear(year) == true)
					maxDay = 29;
				else
					maxDay = 28;
				break;
			default:
				maxDay = 30;
		}
		if(day > maxDay)
			return false;
		
		// The date and hour pass all validity check. Hence, they are valid.
		return true;
	}

	/**
	 * 
	 * @param comparedObj
	 * @return 1 if this object is larger than comparedObj
	 * @return -1 if this object is smaller than comparedObj
	 * @return 0 if this object is equal to comparedObj
	 */
	@Override
	public int compareTo(DateHour comparedObj) {
		if(m_year < comparedObj.getYear())
			return -1;
		if(m_year > comparedObj.getYear())
			return 1;
		
		if(m_month < comparedObj.getMonth())
			return -1;
		if(m_month > comparedObj.getMonth())
			return 1;
		
		if(m_day < comparedObj.getDay())
			return -1;
		if(m_day > comparedObj.getDay())
			return 1;
		
		if(m_hour < comparedObj.getHour())
			return -1;
		if(m_hour > comparedObj.getHour())
			return 1;
		
		return 0;
	}

	@Override
	public String toString() {
		return "DateHour [m_year=" + m_year + ", m_month=" + m_month
				+ ", m_day=" + m_day + ", m_hour=" + m_hour + "]";
	}
	
	// For testing purpose
	public static void main(String[] args)
	{
		DateHour myDate = new DateHour(850, 12, 1, 12);
		String inputDate = myDate.getDateHourRepresentation();
		System.out.println(inputDate);
		System.out.println(inputDate.length());
		DateHour newObj = new DateHour(inputDate);
		System.out.println(newObj.getDateHourRepresentation());
		System.out.println(newObj.toString());
		
	}
}
