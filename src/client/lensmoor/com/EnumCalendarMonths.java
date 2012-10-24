package client.lensmoor.com;

public enum EnumCalendarMonths {
	TOAD (1,"Toad"),
	TIGER (2,"Tiger"),
	ELEPHANT (3,"Elephant"),
	BEETLE (4,"Beetle"),
	RAVEN (5,"Raven"),
	SNAKE (6,"Snake"),
	FOX (7,"Fox"),
	BEAR (8,"Bear"),
	HORSE (9,"Horse"),
	HAWK (10,"Hawk"),
	BADGER (11,"Badger"),
	WOLF (12,"Wolf"),
	RAM (13,"Ram"),
	RAT (14,"Rat"),
	DOG (15,"Dog"),
	RABBIT (16,"Rabbit"),
	BUTTERFLY (17,"Butterfly"),
	ERROR(-1, "ERROR");

	private int value;
	private String string;

	private EnumCalendarMonths (int value, String string) {
		this.string = string;
		this.value = value;
	}

	public int getInt() {
		return (value);
	}
	public String getString() {
		return (string);
	}

	public static EnumCalendarMonths getCalendarMonth(String string) {
		for (EnumCalendarMonths current : values()) {
			if(current.getString().equalsIgnoreCase(string)) {
				return current;
			}
		}
		return EnumCalendarMonths.ERROR;
	}

	public static EnumCalendarMonths getCalendarMonth(int value) {
		for (EnumCalendarMonths current : values()) {
			if(current.getInt() == value) {
				return current;
			}
		}
		return EnumCalendarMonths.ERROR;
	}
}
