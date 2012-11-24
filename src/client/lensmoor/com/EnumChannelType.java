package client.lensmoor.com;

public enum EnumChannelType {
	INFO (1,"Information"),
	GOSSIP (2,"Chatter"),
	PRIVATE (3, "Conversation"),
	OTHER (3,"No defined"),
	ERROR (-1, "Error");

	private int value;
	private String string;
	
	private EnumChannelType (int value, String string) {
		this.value = value;
		this.string = string;
	}

	public int getInt() {
		return (value);
	}
	public String getString() {
		return(string);
	}

	public static EnumChannelType getChannelType(String string) {
		for (EnumChannelType current : values()) {
			if(current.getString().equalsIgnoreCase(string)) {
				return current;
			}
		}
		return EnumChannelType.ERROR;
	}

	public static EnumChannelType getChannelType(int value) {
		for (EnumChannelType current : values()) {
			if(current.getInt() == value) {
				return current;
			}
		}
		return EnumChannelType.ERROR;
	}
}
