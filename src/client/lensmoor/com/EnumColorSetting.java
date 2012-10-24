package client.lensmoor.com;

public enum EnumColorSetting {
	NONE (0, "NONE"),
	BASIC (1, "BASIC"),
	FULL(2, "FULL"),
	CODES(3, "CODES"),
	ERROR(-1, "ERROR");

	private int value;
	private String string;
	
		
	private EnumColorSetting(int value, String string) {
		this.value = value;
		this.string = string;
	}
		
	public int getInt() {
		return value;
	}

	public String getString() {
		return string;
	}
	
	public static EnumColorSetting getColorSetting(String string) {
		for (EnumColorSetting current : values()) {
			if(current.getString().equalsIgnoreCase(string)) {
				return current;
			}
		}
		return EnumColorSetting.ERROR;
	}

	public static EnumColorSetting getColorSetting(int value) {
		for (EnumColorSetting current : values()) {
			if(current.getInt() == value) {
				return current;
			}
		}
		return EnumColorSetting.ERROR;
	}
}