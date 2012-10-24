package client.lensmoor.com;

public enum EnumPool {
	HITS (0, "Hit"),
	MANA (1, "Mana"),
	STAMINA (2, "Tired"),
	WILLPOWER (3, "Will"),
	MORALE (4, "Morale"),
	SIZEOF (5, "ArraySize"),
	ERROR (-1, "ERROR");
	
	private int value;
	private String string;

	private EnumPool (int value, String string) {
		this.value = value;
		this.string = string;
	}

	public int getInt() {
		return (value);
	}

	public String getString() {
		return(string);
	}
	
	public static EnumPool getPool(String string) {
		for (EnumPool current : values()) {
			if(current.getString().equalsIgnoreCase(string)) {
				return current;
			}
		}
		return EnumPool.ERROR;
	}

	public static EnumPool getPool(int value) {
		for (EnumPool current : values()) {
			if(current.getInt() == value) {
				return current;
			}
		}
		return EnumPool.ERROR;
	}

}
