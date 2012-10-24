package client.lensmoor.com;

public enum EnumSex {
	MALE (0, "M", "Male", "Man", "B", "Boy"),
	FEMALE (1, "F", "Female", "Woman", "G", "Girl"),
	NEITHER (2, "N", "Neither", "Neither", "N", "Neither"),
	ERROR(-1, "E", "ERROR", "ERROR", "E", "ERROR");

	private int value;
	private String abbrev;
	private String string;
	private String altString;
	private String youngAbbrev;
	private String youngString;
	
	private EnumSex(int value, String abbrev, String string, String altString, String youngAbbrev, String youngString) {
		this.value = value;
		this.abbrev = abbrev;
		this.string = string;
		this.altString = altString;
		this.youngAbbrev = youngAbbrev;
		this.youngString = youngString;
	}

	public int getInt() {
		return (value);
	}

	public String getString(boolean conversational, boolean young) {
		if (young) {
			return(youngString);
		}
		if (conversational) {
			return (altString);
		}
		return (string);
	}

	public static EnumSex getSex(String string) {
		for (EnumSex current : values()) {
			if(current.abbrev.equalsIgnoreCase(string)
					|| current.string.equalsIgnoreCase(string)
					|| current.altString.equalsIgnoreCase(string)
					|| current.youngAbbrev.equalsIgnoreCase(string)
					|| current.youngString.equalsIgnoreCase(string)) {
				return current;
			}
		}
		return EnumSex.ERROR;
	}

	public static EnumSex getSex(int value) {
		for (EnumSex current : values()) {
			if(current.getInt() == value) {
				return current;
			}
		}
		return EnumSex.ERROR;
	}
}
