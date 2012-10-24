package client.lensmoor.com;

public enum EnumParseType {
	ERROR (-1),
	FIXED (0),
	WHITESPACE (1),
	DELIMITED (2);
	
	private int value;
	
	private EnumParseType (int value) {
		this.value = value;
	}

	public int getInt() {
		return (value);
	}
	
	public static EnumParseType getAlign(int value) {
		for (EnumParseType current : values()) {
			if(current.getInt() == value) {
				return current;
			}
		}
		return EnumParseType.ERROR;
	}

}
