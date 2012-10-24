package client.lensmoor.com;

public enum EnumAlign {
	DIABOLIC (-13, "Diabolic"),
	EVIL (-12, "Evil"),
	CORRUPT (-11, "Corrupt"),
	EVILALIGN (-10, "EvilAlign"),
	MEAN (-1, "Mean"),
	NEUTRAL (0, "Neutral"),
	KIND (1, "Kind"),
	GOODALIGN (10, "GoodAlign"),
	PIOUS (11, "Pious"),
	GOOD (12, "Good"),
	PURE (13, "Pure"),
	ERROR (9999, "ERROR");
	
	private int value;
	private String string;

	private EnumAlign (int value, String string) {
		this.value = value;
		this.string = string;
	}

	public int getInt() {
		return (value);
	}
	public String getString() {
		return(string);
	}
	
	public static EnumAlign getAlign(String string) {
		for (EnumAlign current : values()) {
			if(current.getString().equalsIgnoreCase(string)) {
				return current;
			}
		}
		return EnumAlign.ERROR;
	}

	public static EnumAlign getAlign(int value) {
		for (EnumAlign current : values()) {
			if(current.getInt() == value) {
				return current;
			}
		}
		return EnumAlign.ERROR;
	}
}
