package client.lensmoor.com;

public enum EnumChannel {
	GOSSIP (1, "Gossip", EnumChannelType.GOSSIP, R.color.MAGENTA),
	PKILLER (2, "PKiller", EnumChannelType.GOSSIP, R.color.BRIGHTRED),
	OOC (3, "OOC", EnumChannelType.GOSSIP, R.color.BRIGHTWHITE),
	GRATS (4, "Grats", EnumChannelType.GOSSIP, R.color.BRIGHTYELLOW),
	MUSIC (5, "Music", EnumChannelType.GOSSIP, R.color.YELLOW),
	AUCTION (6, "Auction", EnumChannelType.OTHER, R.color.WHITE),
	CLAN (7, "Clan", EnumChannelType.GOSSIP, R.color.BRIGHTYELLOW),
	CHAT (8, "Chat", EnumChannelType.GOSSIP, R.color.BRIGHTMAGENTA),
	GROUP (9, "Group", EnumChannelType.GOSSIP, R.color.RED),
	NEWBIEHELP (10, "NewbieHelp", EnumChannelType.GOSSIP, R.color.BRIGHTYELLOW),
	SHOUT (11, "Shout", EnumChannelType.OTHER, 0),
	INFO (12, "INFO", EnumChannelType.INFO, R.color.BRIGHTGREEN),
	WORSHIPPER (13, "Worshipper", EnumChannelType.GOSSIP, R.color.BLUE),
	QUEST (14, "QUEST", EnumChannelType.INFO, R.color.BRIGHTYELLOW),
	GAMES (15, "Games", EnumChannelType.INFO, 0),
	THOUGHT (16, "Thought", EnumChannelType.PRIVATE, 0),
	TELL (17, "Tell", EnumChannelType.PRIVATE, 0),
	BEEP (18, "Beep", EnumChannelType.OTHER, 0),
	SAY (19, "Say", EnumChannelType.OTHER, 0),
	ROOMOOC (20, "Roomooc", EnumChannelType.GOSSIP, 0),
	EMOTE (21, "Emote", EnumChannelType.OTHER, 0),
	SIGN (22, "Sign", EnumChannelType.OTHER, 0),
	DAMAGE (23, "Damage", EnumChannelType.OTHER, 0),
	ERROR (-1, "Error", EnumChannelType.ERROR, 0);

	private String string;
	private EnumChannelType type;
	private int colorId;
	private int value;

	private EnumChannel (int value, String string, EnumChannelType type, int color_id) {
		this.colorId = color_id;
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
	public int getColorId() {
		return colorId;
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
