package client.lensmoor.com;

public enum EnumSpecialization {
	WEAPONMASTER (1, "weaponmaster", EnumAttribute.STRENGTH),
	HEALING (2, "healing", EnumAttribute.CHARISMA),
	ILLUSIONS (3, "illusions", EnumAttribute.CHARISMA),
	NECROMANCY (4,"necromancy", EnumAttribute.CONSTITUTION),
	THIEVING (5, "thieving", EnumAttribute.DEXTERITY),
	ENCHANTING (6, "enchanting", EnumAttribute.INTELLIGENCE),
	ASSASSINATION (7, "assassination", EnumAttribute.DEXTERITY),
	SORCERY (8, "sorcery", EnumAttribute.INTELLIGENCE),
	PROTECTION(9, "protection", EnumAttribute.WISDOM),
	CONJURER(10, "conjurer", EnumAttribute.INTELLIGENCE),
	DIVINING(11, "divining", EnumAttribute.INTELLIGENCE),
	BARBARISM(12, "barbarism", EnumAttribute.STRENGTH),
	MAGIC (13, "magic", EnumAttribute.INTELLIGENCE),
	PSIONICS(14, "psionics", EnumAttribute.WISDOM),
	DRUID (15, "druid", EnumAttribute.WISDOM),
	BARD (16, "bard", EnumAttribute.CHARISMA),
	CRAFTER (17, "crafter", EnumAttribute.CONSTITUTION),
	WITCHCRAFT (18, "witchcraft", EnumAttribute.CHARISMA),
	PALADIN (19, "paladin", EnumAttribute.STRENGTH),
	ANIMAL (20, "animal", EnumAttribute.CONSTITUTION),
	GLAMOUR (21, "glamour", EnumAttribute.CHARISMA),
	NONE (0, "none", EnumAttribute.SIZEOF),
	ALL (-1, "all", EnumAttribute.WISDOM);

	private int value;
	private String string;
	private EnumAttribute attribute;

	private EnumSpecialization (int value, String string, EnumAttribute attribute) {
		this.value = value;
		this.string = string;
		this.attribute = attribute;
	}

	public int getInt() {
		return (value);
	}

	public String getString() {
		return (string);
	}
		
	public EnumAttribute getStat() {
		return (attribute);
	}
	
	public static EnumSpecialization getSpecialization(String string) {
		for (EnumSpecialization current : values()) {
			if(current.getString().equalsIgnoreCase(string)) {
				return current;
			}
		}
		return EnumSpecialization.NONE;
	}

	public static EnumSpecialization getSpecialization(int value) {
		for (EnumSpecialization current : values()) {
			if(current.getInt() == value) {
				return current;
			}
		}
		return EnumSpecialization.NONE;
	}	
}