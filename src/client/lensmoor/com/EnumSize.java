package client.lensmoor.com;

public enum EnumSize {
	TINY (0, "tiny"),
	SMALL (1, "small"),
	MEDIUM (2, "medium-sized"),
	LARGE (3, "large"),
	HUGE (4, "huge"),
	GIANT (5, "giant"),
	MAX (6, "vast"),
	ERROR(-1, "ERROR");
	
	private int value;
	private String string;
	
	EnumSize(int value, String string) {
		this.value = value;
		this.string = string;
	}
	
	public int getInt() {
		return (value);
	}

	public String getString() {
		return(string);
	}

	public static EnumSize getSize(String string) {
		for (EnumSize current : values()) {
			if(current.getString().equalsIgnoreCase(string)) {
				return current;
			}
		}
		return EnumSize.ERROR;
	}

	public static EnumSize getSize(int value) {
		for (EnumSize current : values()) {
			if(current.getInt() == value) {
				return current;
			}
		}
		return EnumSize.ERROR;
	}
}
