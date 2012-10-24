package client.lensmoor.com;

public class CalendarDate {
	private int year;
	private EnumCalendarMonths month;
	private int date;
	
	public CalendarDate(int year, int month, int date) {
		this.year = year;
		this.month = EnumCalendarMonths.getCalendarMonth(month);
		this.date = date;
	}
	
	public CalendarDate(int year, EnumCalendarMonths month, int date) {
		this.year = year;
		this.month = month;
		this.date = date;
	}

	public CalendarDate(long date) {
		this.year = Long.valueOf(date/10000).intValue();
		this.month = EnumCalendarMonths.getCalendarMonth(Long.valueOf((date%10000/100)).intValue());
		this.date = Long.valueOf(date%100).intValue();;
	}

	public long getCalendarDateAsLong() {
		return (10000 * year + 100 * month.getInt() + date);
	}
}

//2 RL minutes  = 1 game hour
//24 game hours = 1 game day (RL: 48m)
//7 days        = 1 week     (RL: 5h 36m)
//35 days       = 1 month    (RL: 28h)
//17 months     = 1 year     (RL: 19d 20h)