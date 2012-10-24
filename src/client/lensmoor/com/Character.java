package client.lensmoor.com;

public class Character extends LensClientSerializedObject {
	// Key
	String characterName = "";
	String worldName = "";
	// Additional Data
	String password = "";
	int level;
	EnumSex sex = EnumSex.ERROR;
	EnumRace race = EnumRace.ERROR;
	EnumSize size = EnumSize.ERROR;
	EnumSpecialization specialization = EnumSpecialization.NONE;
	String clan = "";
	CalendarDate birthday;
	int age;
	int attribute[];
	int baseHitPoints;
	int baseManaPoints;
	int practices;
	int trains;
	int questPoints;
	int questDifficulty;
	EnumAlign align = EnumAlign.ERROR;
	String hometown = "";
	int kills;
	int playerKills;
	int deaths;
	int playerDeaths;
	int fleeAt;
	int skillCap;

	public Character (LensClientDBHelper dbHelper) {
		super(dbHelper);
		attribute = new int [EnumAttribute.SIZEOF.getInt()];
		birthday = new CalendarDate((long)0);
	}
	
	static Character GetCharacter(LensClientDBHelper dbHelper, String characterName, String worldName) {
		return dbHelper.GetCharacter(characterName, worldName);
	}
	
	static Character [] GetCharacter(LensClientDBHelper dbHelper) {
		return dbHelper.GetCharacterList();
	}

	public void DeleteCharacter() { dbHelper.DeleteCharacter(this); }
		
	public String getCharacterName() { return characterName; }
	public String getCharacterWorld() { return worldName; }
	public String getPassword() { return password; }
	public int getLevel() { return level; }
	public EnumSex getSex() { return sex; }
	public EnumRace getRace() { return race; }
	public EnumSize getSize() { return size; }
	public EnumSpecialization getSpecialization() { return specialization; }
	public String getClan() { return clan; }
	public CalendarDate getBirthday() { return birthday; }
	public int getAge() { return age; }
	public int getAttribute(EnumAttribute attribute) { return this.attribute[attribute.getInt()]; }
	public int getBaseHitPoints() { return baseHitPoints; }
	public int getBaseManaPoints() { return baseManaPoints; }
	public int getPractices() { return practices; }
	public int getTrains() { return trains; }
	public int getQuestPoints() { return questPoints; }
	public int getQuestDifficulty() { return questDifficulty; }
	public EnumAlign getAlign() { return align; }
	public String getHometown() { return hometown; }
	public int getKills() { return kills; }
	public int getPlayerKills() { return playerKills; }
	public int getDeaths() { return deaths; }
	public int getPlayerDeaths() { return playerDeaths; }
	public int getFleeAt() { return fleeAt; }
	public int getSkillCap() { return skillCap; }

	public void setCharacterName(String characterName) { this.characterName = characterName; }
	public void setWorldName(String worldName) { this.worldName = worldName; }
	public void setPassword(String password) { this.password = password; }
	public void setLevel(int level) { this.level = level; }
	public void setSex(EnumSex sex) { this.sex = sex; }
	public void setRace(EnumRace race) { this.race = race; }
	public void setSize(EnumSize size) { this.size = size; }
	public void setSpecialization(EnumSpecialization specialization) { this.specialization = specialization; }
	public void setClan(String clan) { this.clan = clan; }
	public void setBirthday(CalendarDate birthday) { this.birthday = birthday; }
	public void setAge(int age) { this.age = age; }
	public void setAttribute(EnumAttribute attribute, int value) { this.attribute[attribute.getInt()] = value; }
	public void setBaseHitPoints(int baseHitPoints) { this.baseHitPoints = baseHitPoints; }
	public void setBaseManaPoints(int baseManaPoints) { this.baseManaPoints = baseManaPoints; }
	public void setPractices(int practices) { this.practices = practices; }
	public void setTrains(int trains) { this.trains = trains; }
	public void setQuestPoints(int questPoints) { this.questPoints = questPoints; }
	public void setQuestDifficulty(int questDifficulty) { this.questDifficulty = questDifficulty; }
	public void setAlign(EnumAlign align) { this.align = align; }
	public void setHometown(String hometown) { this.hometown = hometown; }
	public void setKills(int kills) { this.kills = kills; }
	public void setPlayerKills(int playerKills) { this.playerKills = playerKills; }
	public void setDeaths(int deaths) { this.deaths = deaths; }
	public void setPlayerDeaths(int playerDeaths) { this.playerDeaths = playerDeaths; }
	public void setFleeAt(int fleeAt) { this.fleeAt = fleeAt; }
	public void setSkillCap(int skillCap) { this.skillCap = skillCap; }

	public String getCharacterWorldString() {
		return (getCharacterName() + " (" + getCharacterWorld() + ")");
	}
}
