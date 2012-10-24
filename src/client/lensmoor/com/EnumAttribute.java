package client.lensmoor.com;

public enum EnumAttribute {
	STRENGTH (0, "Str", "Strength"),
	INTELLIGENCE (1, "Int", "Intelligence"),
	WISDOM (2, "Wis", "Wisdom"),
	DEXTERITY (3, "Dex", "Dexterity"),
	CONSTITUTION (4, "Con", "Constitution"),
	CHARISMA (5, "Cha", "Charisma"),
	SIZEOF(6, "ArraySize", "ArraySize"),
	ERROR (-1, "ERROR", "ERROR");
	
	
	private int value;
	private String shortform;
	private String string;

	private EnumAttribute (int value, String shortform, String string) {
		this.value = value;
		this.shortform = shortform;
		this.string = string;
	}

	public int getInt() {
		return (value);
	}

	public String getShortString() {
		return(shortform);
	}
	
	public String getString() {
		return (string);
	}
	
	public static EnumAttribute getAttribute(String string, boolean shortform) {
		for (EnumAttribute current : values()) {
			if((current.getString().equalsIgnoreCase(string) && !shortform)
					|| (current.getShortString().equalsIgnoreCase(string) && !shortform)) {
				return current;
			}
		}
		return EnumAttribute.ERROR;
	}

	public static EnumAttribute getAttribute(int value) {
		for (EnumAttribute current : values()) {
			if(current.getInt() == value) {
				return current;
			}
		}
		return EnumAttribute.ERROR;
	}
}
