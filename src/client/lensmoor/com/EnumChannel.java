package client.lensmoor.com;

public enum EnumChannel {
	GOSSIP (1, "Gossip", EnumChannelType.GOSSIP),
	PKILLER (2, "PKiller", EnumChannelType.GOSSIP),
	OOC (3, "OOC", EnumChannelType.GOSSIP),
	GRATS (4, "Grats", EnumChannelType.GOSSIP),
	MUSIC (5, "Music", EnumChannelType.GOSSIP),
	AUCTION (6, "Auction", EnumChannelType.OTHER),
	CLAN (7, "Clan", EnumChannelType.GOSSIP),
	CHAT (8, "Chat", EnumChannelType.GOSSIP),
	GROUP (9, "Group", EnumChannelType.GOSSIP),
	NEWBIEHELP (10, "NewbieHelp", EnumChannelType.GOSSIP),
	SHOUT (11, "Shout", EnumChannelType.OTHER),
	INFO (12, "INFO", EnumChannelType.INFO),
	WORSHIPPER (13, "Worshipper", EnumChannelType.GOSSIP),
	QUEST (14, "QUEST", EnumChannelType.INFO),
	GAMES (15, "Games", EnumChannelType.INFO),
	THOUGHT (16, "Thought", EnumChannelType.PRIVATE),
	TELL (17, "Tell", EnumChannelType.PRIVATE),
	BEEP (18, "Beep", EnumChannelType.OTHER),
	SAY (19, "Say", EnumChannelType.OTHER),
	ROOMOOC (20, "Roomooc", EnumChannelType.GOSSIP),
	EMOTE (21, "Emote", EnumChannelType.OTHER),
	SIGN (22, "Sign", EnumChannelType.OTHER),
	DAMAGE (23, "Damage", EnumChannelType.OTHER),
	ERROR (-1, "Error", EnumChannelType.ERROR);

	private int value;
	private String string;
	EnumChannelType type;

	private EnumChannel (int value, String string, EnumChannelType type) {
		this.value = value;
		this.string = string;
	}

	public int getInt() {
		return (value);
	}
	public String getString() {
		return(string);
	}
	public EnumChannelType getChannelType() {
		return(type);
	}
	
	public static EnumChannel getChannel(String string) {
		for (EnumChannel current : values()) {
			if(current.getString().equalsIgnoreCase(string)) {
				return current;
			}
		}
		return EnumChannel.ERROR;
	}

	public static EnumChannel getChannel(int value) {
		for (EnumChannel current : values()) {
			if(current.getInt() == value) {
				return current;
			}
		}
		return EnumChannel.ERROR;
	}
}
