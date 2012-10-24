package client.lensmoor.com;

public enum EnumRace {
	ENT (1, "Ent", "Ents"),
	GNOME (2, "Gnome", "Gnomes"),
	HAIKJADEAM (3, "Haikjadeam", "Haikjadeams"),
	HUMAN (4, "Human", "Humans"),
	QUICKLING (5, "Quickling", "Quicklings"),
	SELKIE (6, "Selkie", "Selkies"),
	SIDHE (7, "Sidhe", "Sidhes"),
	SPRITE (8, "Sprite", "Sprites"),
	ALAEMN (-1, "Alaemn", "Alaemns"),
	ARACHNAIA (-2, "Arachnaia", "Arachnaias"),
	CYCLOPS (-3, "Cyclops", "Cyclopses"),
	GANDOR (-4, "Gandor", "Gandors"),
	LOURYL (-5, "Louryl", "Louryls"),
	ORC (-6, "Orc", "Orcs"),
	SKULK (-7, "Skulk", "Skulks"),
	XORRTO (-8, "Xorrto", "Xorrtos"),
	ERROR (9999, "ERROR", "ERROR");

	private int value;
	private String string;
	private String pluralstring;

	private EnumRace (int value, String string, String pluralstring) {
		this.value = value;
		this.string = string;
		this.pluralstring = pluralstring;
	}

	public int getInt() {
		return (value);
	}

	public String getString() {
		return string;
	}

	public String getString(boolean plural) {
		if (plural) {
			return(pluralstring);
		}
		return string;
	}

	public static EnumRace getRace(String string, boolean plural) {
		for (EnumRace current : values()) {
			if(current.getString(plural).equalsIgnoreCase(string)) {
				return current;
			}
		}
		return EnumRace.ERROR;
	}

	public static EnumRace getRace(int value) {
		for (EnumRace current : values()) {
			if(current.getInt() == value) {
				return current;
			}
		}
		return EnumRace.ERROR;
	}
}
