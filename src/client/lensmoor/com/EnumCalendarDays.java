package client.lensmoor.com;

public enum EnumCalendarDays {
	REST (1, "Rest"),
	LEARNING (2, "Learning"),
	WORK (3, "Work"),
	PLAY (4, "Play"),
	DREAMING (5, "Dreaming"),
	FORTUNE (6, "Fortune"),
	WORSHIP (7, "Worship"),
	ERROR (-1, "ERROR");
	
	private int value;
	private String string;

	private EnumCalendarDays (int value, String string) {
		this.value = value;
		this.string = string;
	}

	public int getInt() {
		return (value);
	}
	
	public String getString() {
		return (string);
	}
	
	public static EnumCalendarDays getCalendarDay(String string) {
		for (EnumCalendarDays current : values()) {
			if(current.getString().equalsIgnoreCase(string)) {
				return current;
			}
		}
		return EnumCalendarDays.ERROR;
	}

	public static EnumCalendarDays getCalendarDay(int value) {
		for (EnumCalendarDays current : values()) {
			if(current.getInt() == value) {
				return current;
			}
		}
		return EnumCalendarDays.ERROR;
	}
}
