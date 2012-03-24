/**
 * A MyDateTime object keeps track of the date and time regarding year, month, day, hour and minute. 
 * A MyDateTime object is immutable!
 * 
 * @author Nguyen Truong Duy (Team 31 - CS2103)
 */
public class MyDateTime implements Comparable<MyDateTime> {
	/*************************************************************************
	 * Class member variable
	 ************************************************************************/
	private int m_year;
	private int m_month;
	private int m_day;
	private int m_hour;
	private int m_minute;
	
	/*************************************************************************
	 * Constructor
	 * 
	 * Assumption: The validity of the date and hour were checked
	 ************************************************************************/
	
	/**
	 * Constructor: Creates a MyDateTime object. It is assumed that the input
	 * 		year, month, day, hour and minute are valid.
	 * 
	 * @param year - int
	 * @param month - int
	 * @param day - int
	 * @param hour - int
	 * @param minute - int
	 */
	public MyDateTime(int year, int month, int day, int hour, int minute)
	{
		m_year = year;
		m_month = month;
		m_day = day;
		m_hour = hour;
		m_minute = minute;
	}
	
	/**
	 * Constructor: Creates a MyDateTime object. It is assumed that the input
	 * 		year, month, day and hour are valid. Minute will be set to 0.
	 * 
	 * @param year - int
	 * @param month - int
	 * @param day - int
	 * @param hour - int
	 */
	public MyDateTime(int year, int month, int day, int hour)
	{
		m_year = year;
		m_month = month;
		m_day = day;
		m_hour = hour;
		m_minute = 0;
	}
	
	/**
	 * Constructor: Creates a MyDateTime object from another MyDateTime object.
	 * 
	 * @param anotherObj - MyDateTime
	 */
	public MyDateTime(MyDateTime anotherObj)
	{
		m_year = anotherObj.getYear();
		m_month = anotherObj.getMonth();
		m_day = anotherObj.getDay();
		m_hour = anotherObj.getHour();
		m_minute = anotherObj.getMinute();
	}
	
	/**
	 * Constructor: Creates a MyDateTime object from the input string which has the format:
	 * <day>/<month>/<year>/<hour>/<minute>. The 4 fields (day, month, year, hour) are separated by '/'.
	 * It is assumed the input string is in the correct format.
	 * 
	 * @param MyDateTimeRepresentation - String
	 */
	public MyDateTime(String dateTimeRepresentation)
	{
		String[] arrString = dateTimeRepresentation.split("/");
		
		/* For testing purpose
		System.out.println(arrString.length);
		for(int i = 0; i < arrString.length; i++)
			System.out.println(arrString[i]);
		*/
		
		m_day = Integer.parseInt(arrString[0]);
		m_month = Integer.parseInt(arrString[1]);
		m_year = Integer.parseInt(arrString[2]);
		m_hour = Integer.parseInt(arrString[3]);
		m_minute = Integer.parseInt(arrString[4]);
	}
	
	/**
	 * Constructor: Creates a MyDateTime object from two input strings. The string dateRepresenation has
	 * 		the format <day>/<month>/<year>. The string timeRepresentation has the format
	 * 		<hour>:<minute>
	 * It is assumed the input strings are in the correct format.
	 * 
	 * @param dateRepresentation
	 * @param timeRepresentation
	 */
	public MyDateTime(String dateRepresentation, String timeRepresentation)
	{
		String[] dateString = dateRepresentation.split("/");
		String[] timeString = timeRepresentation.split(":");
		
		m_day = Integer.parseInt(dateString[0]);
		m_month = Integer.parseInt(dateString[1]);
		m_year = Integer.parseInt(dateString[2]);
		m_hour = Integer.parseInt(timeString[0]);
		m_minute = Integer.parseInt(timeString[1]);
	}
	/************************************************************************
	 * Methods that support extracting information
	 ************************************************************************/
	
	/**
	 * 
	 * @return m_year - int
	 */
	public int getYear()
	{
		return m_year;
	}
	
	/**
	 * 
	 * @return m_month - int
	 */
	public int getMonth()
	{
		return m_month;
	}
	
	/**
	 * 
	 * @return m_day - int
	 */
	public int getDay()
	{
		return m_day;
	}
	
	/**
	 * 
	 * @return m_hour - int
	 */
	public int getHour()
	{
		return m_hour;
	}
	
	/**
	 * 
	 * @return m_minute - int
	 */
	public int getMinute()
	{
		return m_hour;
	}
	
	/**
	 * Returns a String object that represents the MyDateTime object. The string is dd/mm/yyyy/hh/mm
	 * 
	 * @return dateTimeRepresentation - String
	 */
	public String getDateTimeRepresentation()
	{
		char[] charArr = new char[16];
		charArr[2] = charArr[5] = charArr[10] = charArr[13] =  '/';
		int day = m_day;
		int month = m_month;
		int year = m_year;
		int hour = m_hour;
		int minute = m_minute;
		for(int i = 0; i < 2; i++)
		{
			charArr[1 - i] = Character.forDigit(day % 10, 10);
			charArr[4 - i] = Character.forDigit(month % 10, 10);
			charArr[12 - i] = Character.forDigit(hour % 10, 10);
			charArr[15 - i] = Character.forDigit(minute % 10, 10);
			day /= 10;
			month /= 10;
			hour /=10;
			minute /= 10;
 		}
		for(int i = 0; i < 4; i++)
		{
			charArr[9 - i] = Character.forDigit(year % 10, 10);
			year /= 10;
		}
		return new String(charArr);
	}
	
	/**
	 * Returns a String object that represents the date of a MyDateTime object. 
	 * The string format is dd/mm/yyyy
	 * 
	 * @return dateRepresentation - String
	 */
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
	
	/**
	 * Returns a String object that represents the time of a MyDateTime object. 
	 * The string format is hh:mm
	 * 
	 * @return dateRepresentation - String
	 */
	public String getTimeRepresentation()
	{
		String hourStr = Integer.toString(m_hour);
		if(hourStr.length() == 1)		// Note that hourStr = 1 or 2
			hourStr = "0" + hourStr;
		
		String minuteStr = Integer.toString(m_minute);
		if(minuteStr.length() == 1)		// Note that minuteStr = 1 or 2
			minuteStr = "0" + minuteStr;
		
		return hourStr + ":" + minuteStr;
	}
	
	/**************************************************************************
	 * Methods that support querying
	 *************************************************************************/
	
	/**
	 * Checks if an input year is a leap year.
	 * 
	 * @param year - int
	 * @return true if the given input is a valid leap year.
	 * @return false otherwise.
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
	 * Checks if a given input (year, month, day, hour) represents the valid date and time.
	 * Month should be from 1 to 12. Day should be from 1 to 31. Hour should be from 0 to 23.
	 * Minute should be from 0 to 59.
	 * 
	 * @param year - int
	 * @param month - int
	 * @param day - int
	 * @param hour - int
	 * @return true if the given parameters are of a valid date and hour.
	 * @return false otherwise
	 */
	public static boolean isValidDateTime(int year, int month, int day, int hour, int minute)
	{
		if(year < 0 || month < 1 || month > 12 || day < 1 || day > 31
				|| hour < 0 || hour > 23 || minute < 0 || minute > 59)
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
	 * Compares two MyDateTime objects according to the chronological order.
	 * 
	 * @param comparedObj - MyDateTime
	 * @return 1 if this object is larger than comparedObj
	 * @return -1 if this object is smaller than comparedObj
	 * @return 0 if this object is equal to comparedObj
	 */
//	@Override
	public int compareTo(MyDateTime comparedObj) {
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
		
		if(m_minute < comparedObj.getMinute())
			return -1;
		if(m_minute > comparedObj.getMinute())
			return 1;
		
		return 0;
	}

	@Override
	
	public String toString() {
		return "MyDateTime [m_year=" + m_year + ", m_month=" + m_month
				+ ", m_day=" + m_day + ", m_hour=" + m_hour + ", m_minute=" + m_minute + "]";
	}
	
	/* For testing purpose
	public static void main(String[] args)
	{
		MyDateTime myDate = new MyDateTime(850, 12, 1, 12);
		String inputDate = myDate.getDateTimeRepresentation();
		System.out.println(inputDate);
		System.out.println(inputDate.length());
		MyDateTime newObj = new MyDateTime(inputDate);
		System.out.println(newObj.getDateTimeRepresentation());
		System.out.println(newObj.toString());
		
	} */
}
