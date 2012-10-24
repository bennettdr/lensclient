package client.lensmoor.com;

public enum EnumYesNo {
	YES (1, "Yes"),
	NO (0, "No"),
	ERROR (-1, "ERROR");
		
	private int value;
	private String string;
	
	private EnumYesNo(int value, String string) {
		this.value = value;
		this.string = string;
	}

	public int getInt() {
		return(value);
	}

	public String getString() {
		return(string);
	}
		
	public boolean getBoolean() {
		return (value == 1);
	}

	public static EnumYesNo getYesNo(String string) {
		for (EnumYesNo current : values()) {
			if(current.getString().equalsIgnoreCase(string)) {
				return current;
			}
		}
		return EnumYesNo.ERROR;
	}

	public static EnumYesNo getYesNo(int value) {
		for (EnumYesNo current : values()) {
			if(current.getInt() == value) {
				return current;
			}
		}
		return EnumYesNo.ERROR;
	}

	public static EnumYesNo getOnOff(boolean value) {
		if (value) {
			return EnumYesNo.YES;
		} else {
			return EnumYesNo.NO;
		}
	}

}
