package client.lensmoor.com;

public enum EnumOnOff {
	OFF (0, "Off"),
	ON (1, "On"),
	ERROR (-1, "ERROR");
		
	private int value;
	private String string;
		
	private EnumOnOff(int value, String string) {
		this.value = value;
		this.string = string;
	}

	public int getInt() {
		return (value);
	}

	public String getString() {
		return (string);		
	}
		
	public boolean getBoolean() {
		return value == ON.getInt();
	}
	
	public static EnumOnOff getOnOff(String string) {
		for (EnumOnOff current : values()) {
			if(current.getString().equalsIgnoreCase(string)) {
				return current;
			}
		}
		return EnumOnOff.ERROR;
	}

	public static EnumOnOff getOnOff(int value) {
		for (EnumOnOff current : values()) {
			if(current.getInt() == value) {
				return current;
			}
		}
		return EnumOnOff.ERROR;
	}

	public static EnumOnOff getOnOff(boolean value) {
		if (value) {
			return EnumOnOff.ON;
		} else {
			return EnumOnOff.OFF;
		}
	}
}
