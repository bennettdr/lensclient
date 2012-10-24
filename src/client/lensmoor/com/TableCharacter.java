package client.lensmoor.com;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

public class TableCharacter extends Table {
	public static final int CHARACTER_CHARACTERNAME_COL = 0;
	public static final int CHARACTER_WORLD_COL =  CHARACTER_CHARACTERNAME_COL + 1;
	public static final int CHARACTER_PASSWORD_COL =  CHARACTER_WORLD_COL + 1;
	public static final int CHARACTER_LEVEL_COL =  CHARACTER_PASSWORD_COL + 1;
	public static final int CHARACTER_SEX_COL =  CHARACTER_LEVEL_COL + 1;
	public static final int CHARACTER_RACE_COL =  CHARACTER_SEX_COL + 1;
	public static final int CHARACTER_SIZE_COL =  CHARACTER_RACE_COL + 1;
	public static final int CHARACTER_SPECIALIZATION_COL =  CHARACTER_SIZE_COL + 1;
	public static final int CHARACTER_CLAN_COL =  CHARACTER_SPECIALIZATION_COL + 1;
	public static final int CHARACTER_BIRTHDAY_COL =  CHARACTER_CLAN_COL + 1;
	public static final int CHARACTER_AGE_COL =  CHARACTER_BIRTHDAY_COL + 1;
	public static final int CHARACTER_STRENGTH_COL =  CHARACTER_AGE_COL + 1;
	public static final int CHARACTER_INTELLIGENCE_COL =  CHARACTER_STRENGTH_COL + 1;
	public static final int CHARACTER_WISDOM_COL =  CHARACTER_INTELLIGENCE_COL + 1;
	public static final int CHARACTER_DEXERITY_COL =  CHARACTER_WISDOM_COL + 1;
	public static final int CHARACTER_CONSTITUTION_COL =  CHARACTER_DEXERITY_COL + 1;
	public static final int CHARACTER_CHARISMA_COL =  CHARACTER_CONSTITUTION_COL + 1;
	public static final int CHARACTER_BASEHITPOINTS_COL =  CHARACTER_CHARISMA_COL + 1;
	public static final int CHARACTER_BASEMANAPOINTS_COL =  CHARACTER_BASEHITPOINTS_COL + 1;
	public static final int CHARACTER_PRACTICES_COL =  CHARACTER_BASEMANAPOINTS_COL + 1;
	public static final int CHARACTER_TRAINS_COL =  CHARACTER_PRACTICES_COL + 1;
	public static final int CHARACTER_QUESTPOINTS_COL =  CHARACTER_TRAINS_COL + 1;
	public static final int CHARACTER_QUESTDIFFICULTY_COL =  CHARACTER_QUESTPOINTS_COL + 1;
	public static final int CHARACTER_ALIGN_COL =  CHARACTER_QUESTDIFFICULTY_COL + 1;
	public static final int CHARACTER_HOMETOWN_COL =  CHARACTER_ALIGN_COL + 1;
	public static final int CHARACTER_KILLS_COL =  CHARACTER_HOMETOWN_COL + 1;
	public static final int CHARACTER_PLAYERKILLS_COL =  CHARACTER_KILLS_COL + 1;
	public static final int CHARACTER_DEATHS_COL =  CHARACTER_PLAYERKILLS_COL + 1;
	public static final int CHARACTER_PLAYERDEATHS_COL =  CHARACTER_DEATHS_COL + 1;
	public static final int CHARACTER_FLEEAT_COL =  CHARACTER_PLAYERDEATHS_COL + 1;
	public static final int CHARACTER_SKILLCAP_COL =  CHARACTER_FLEEAT_COL + 1;
	private static final int CHARACTER_DATE_CREATED_COL = CHARACTER_SKILLCAP_COL + 1;
	private static final int CHARACTER_USER_CREATED_COL = CHARACTER_DATE_CREATED_COL + 1;
	private static final int CHARACTER_DATE_UPDATED_COL = CHARACTER_USER_CREATED_COL + 1;
	private static final int CHARACTER_USER_UPDATED_COL = CHARACTER_DATE_UPDATED_COL + 1;
	private static final int CHARACTER_NUMBER_COLUMNS = CHARACTER_USER_UPDATED_COL + 1;
	
	private static final String columnAttr[] = {
		"CharacterName", "TEXT", null,
		"WorldName", "TEXT", null,
		"Password", "TEXT", null,
		"Level", "INTEGER", null,
		"Sex", "INTEGER", null,
		"Race", "TEXT", null,
		"Size", "INTEGER", null,
		"Specialization", "TEXT", null,
		"Clan", "TEXT", null,
		"Birthday", "INTEGER", null,
		"Age", "INTEGER", null,
		"Strength", "INTEGER", null,
		"Intelligence", "INTEGER", null,
		"Wisdom", "INTEGER", null,
		"Dexerity", "INTEGER", null,
		"Constitution", "INTEGER", null,
		"Charisma", "INTEGER", null,
		"BaseHitPoints", "INTEGER", null,
		"BaseManaPoints", "INTEGER", null,
		"Practices", "INTEGER", null,
		"Trains", "INTEGER", null,
		"QuestPoints", "INTEGER", null,
		"QuestDifficulty", "INTEGER", null,
		"Align", "INTEGER", null,
		"Hometown", "TEXT", null,
		"Kills", "INTEGER", null,
		"PlayerKills", "INTEGER", null,
		"Deaths", "INTEGER", null,
		"PlayerDeaths", "INTEGER", null,
		"FleeAt", "INTEGER", null,
		"SkillCap", "INTEGER", null,
		"DateCreated", "INTEGER", null,
		"UserCreated", "TEXT", null,
		"DateUpdated", "INTEGER", null,
		"UserUpdated", "TEXT", null,
		null
	};
	private static final String keyList[] = {
		"CharacterName",
		"WorldName",
		null
	};
	private static final String indexList[] [] = {
		null
	};
	private static final String tableName = "CharacterInformation";

	public TableCharacter(LensClientDBHelper currentDBHelper) {
		super(currentDBHelper, tableName, columnAttr);
	}

	public static void onCreate (SQLiteDatabase db) {
		LensClientDBHelper.initializeTable(db, tableName, columnAttr, keyList, indexList);
	}
		
	public static void onUpgrade (SQLiteDatabase db, int newVersion, int oldVersion) {
		if (newVersion > oldVersion) {
		//	onCreate(db);
		}
	}

	// Database Support
	public Character[] GetCharacterList(SQLiteDatabase db, String characterName, String worldName) {
		Character [] characterList;
		String whereClause = null;
		Cursor cursor;
		int i = 0;
		int countAddresses;
	
		if ((characterName != null) && (characterName.length() > 0)) {
			whereClause = "((" + columnList[CHARACTER_CHARACTERNAME_COL] + " = \"" + characterName + "\")";
			whereClause = whereClause + " AND (" + columnList[CHARACTER_WORLD_COL] + " = \"" + worldName + "\"))";
			countAddresses = (int)DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + tableName + " WHERE " + whereClause, null);
		} else {
			countAddresses = (int)DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + tableName, null);
		}

		characterList = new Character[countAddresses + 1];

		cursor = db.query(tableName, selectColumnList, whereClause, null, null, null, null, null);
		
		if(cursor != null) {
			cursor.moveToFirst();
			while(!cursor.isAfterLast()) {
				characterList[i] = new Character(dbHelper);
				characterList[i].setCharacterName(cursor.getString(cursor.getColumnIndex(columnList[CHARACTER_CHARACTERNAME_COL])));
				characterList[i].setWorldName(cursor.getString(cursor.getColumnIndex(columnList[CHARACTER_WORLD_COL])));
				characterList[i].setPassword(cursor.getString(cursor.getColumnIndex(columnList[CHARACTER_PASSWORD_COL])));
				characterList[i].setLevel(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_LEVEL_COL])));
				characterList[i].setSex(EnumSex.getSex(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_SEX_COL]))));
				characterList[i].setRace(EnumRace.getRace(cursor.getString(cursor.getColumnIndex(columnList[CHARACTER_RACE_COL])),false));
				characterList[i].setSize(EnumSize.getSize(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_SIZE_COL]))));
				characterList[i].setSpecialization(EnumSpecialization.getSpecialization(cursor.getString(cursor.getColumnIndex(columnList[CHARACTER_SPECIALIZATION_COL]))));
				characterList[i].setClan(cursor.getString(cursor.getColumnIndex(columnList[CHARACTER_CLAN_COL])));
				characterList[i].setBirthday(new CalendarDate(cursor.getLong(cursor.getColumnIndex(columnList[CHARACTER_BIRTHDAY_COL]))));
				characterList[i].setAge(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_AGE_COL])));
				characterList[i].setAttribute(EnumAttribute.STRENGTH, cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_STRENGTH_COL])));
				characterList[i].setAttribute(EnumAttribute.INTELLIGENCE, cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_INTELLIGENCE_COL])));
				characterList[i].setAttribute(EnumAttribute.WISDOM, cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_WISDOM_COL])));
				characterList[i].setAttribute(EnumAttribute.DEXTERITY, cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_DEXERITY_COL])));
				characterList[i].setAttribute(EnumAttribute.CONSTITUTION, cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_CONSTITUTION_COL])));
				characterList[i].setAttribute(EnumAttribute.CHARISMA, cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_CHARISMA_COL])));
				characterList[i].setBaseHitPoints(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_BASEHITPOINTS_COL])));
				characterList[i].setBaseManaPoints(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_BASEMANAPOINTS_COL])));
				characterList[i].setPractices(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_PRACTICES_COL])));
				characterList[i].setTrains(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_TRAINS_COL])));
				characterList[i].setQuestPoints(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_QUESTPOINTS_COL])));
				characterList[i].setQuestDifficulty(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_QUESTDIFFICULTY_COL])));
				characterList[i].setAlign(EnumAlign.getAlign(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_ALIGN_COL]))));
				characterList[i].setHometown(cursor.getString(cursor.getColumnIndex(columnList[CHARACTER_HOMETOWN_COL])));
				characterList[i].setKills(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_KILLS_COL])));
				characterList[i].setPlayerKills(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_PLAYERKILLS_COL])));
				characterList[i].setDeaths(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_DEATHS_COL])));
				characterList[i].setPlayerDeaths(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_PLAYERDEATHS_COL])));
				characterList[i].setFleeAt(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_FLEEAT_COL])));
				characterList[i].setSkillCap(cursor.getInt(cursor.getColumnIndex(columnList[CHARACTER_SKILLCAP_COL])));
				characterList[i].setDateCreated(cursor.getLong(cursor.getColumnIndex(columnList[CHARACTER_DATE_CREATED_COL])));
				characterList[i].setUserCreated(cursor.getString(cursor.getColumnIndex(columnList[CHARACTER_USER_CREATED_COL])));
				characterList[i].setDateUpdated(cursor.getLong(cursor.getColumnIndex(columnList[CHARACTER_DATE_UPDATED_COL])));
				characterList[i].setUserUpdated(cursor.getString(cursor.getColumnIndex(columnList[CHARACTER_USER_UPDATED_COL])));
				i++;
				cursor.moveToNext();
			}
			cursor.close();
		}
		characterList[i] = new Character(dbHelper);
		characterList[i].setIsNull();
		return characterList;
	}
	
	public void SaveCharacter(SQLiteDatabase db, Character character) {
		ContentValues values = new ContentValues(CHARACTER_NUMBER_COLUMNS);
		String whereClause;
		int countCharacters;
		
		if ((character == null) || (character.getCharacterName() == null) || (character.getCharacterName().length() == 0)) {
			return;
		}

		whereClause = "((" + selectColumnList[CHARACTER_CHARACTERNAME_COL] + " = \"" + character.getCharacterName() + "\")";
		whereClause = whereClause + " AND (" + columnList[CHARACTER_WORLD_COL] + " = \"" + character.getCharacterWorld() + "\"))";

		countCharacters = (int)DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + tableName + " WHERE " + whereClause, null);
		character.setUpdateTime();
		values.put(columnList[CHARACTER_PASSWORD_COL], character.getPassword());
		values.put(columnList[CHARACTER_LEVEL_COL], character.getLevel());
		values.put(columnList[CHARACTER_SEX_COL], character.getSex().getInt());
		values.put(columnList[CHARACTER_RACE_COL], character.getRace().getString(false));
		values.put(columnList[CHARACTER_SIZE_COL], character.getSize().getInt());
		values.put(columnList[CHARACTER_SPECIALIZATION_COL], character.getSpecialization().getString());
		values.put(columnList[CHARACTER_CLAN_COL], character.getClan());
		values.put(columnList[CHARACTER_BIRTHDAY_COL], character.getBirthday().getCalendarDateAsLong());
		values.put(columnList[CHARACTER_AGE_COL], character.getAge());
		values.put(columnList[CHARACTER_STRENGTH_COL], character.getAttribute(EnumAttribute.STRENGTH));
		values.put(columnList[CHARACTER_INTELLIGENCE_COL], character.getAttribute(EnumAttribute.INTELLIGENCE));
		values.put(columnList[CHARACTER_WISDOM_COL], character.getAttribute(EnumAttribute.WISDOM));
		values.put(columnList[CHARACTER_DEXERITY_COL], character.getAttribute(EnumAttribute.DEXTERITY));
		values.put(columnList[CHARACTER_CONSTITUTION_COL], character.getAttribute(EnumAttribute.CONSTITUTION));
		values.put(columnList[CHARACTER_CHARISMA_COL], character.getAttribute(EnumAttribute.CHARISMA));
		values.put(columnList[CHARACTER_BASEHITPOINTS_COL], character.getBaseHitPoints());
		values.put(columnList[CHARACTER_BASEMANAPOINTS_COL], character.getBaseManaPoints());
		values.put(columnList[CHARACTER_PRACTICES_COL], character.getPractices());
		values.put(columnList[CHARACTER_TRAINS_COL], character.getTrains());
		values.put(columnList[CHARACTER_QUESTPOINTS_COL], character.getQuestPoints());
		values.put(columnList[CHARACTER_QUESTDIFFICULTY_COL], character.getQuestDifficulty());
		values.put(columnList[CHARACTER_ALIGN_COL], character.getAlign().getInt());
		values.put(columnList[CHARACTER_HOMETOWN_COL], character.getHometown());
		values.put(columnList[CHARACTER_KILLS_COL], character.getKills());
		values.put(columnList[CHARACTER_PLAYERKILLS_COL], character.getPlayerKills());
		values.put(columnList[CHARACTER_DEATHS_COL], character.getDeaths());
		values.put(columnList[CHARACTER_PLAYERDEATHS_COL], character.getPlayerDeaths());
		values.put(columnList[CHARACTER_FLEEAT_COL], character.getFleeAt());
		values.put(columnList[CHARACTER_SKILLCAP_COL], character.getSkillCap());
		values.put(columnList[CHARACTER_DATE_UPDATED_COL], character.getDateUpdated());
		values.put(columnList[CHARACTER_USER_UPDATED_COL], character.getUserUpdate());
		if(countCharacters > 0) {
			db.update(tableName, values, whereClause, null);			
		} else {
			values.put(columnList[CHARACTER_CHARACTERNAME_COL], character.getCharacterName());
			values.put(columnList[CHARACTER_WORLD_COL], character.getCharacterWorld());
			values.put(columnList[CHARACTER_DATE_CREATED_COL], character.getDateCreated());
			values.put(columnList[CHARACTER_USER_CREATED_COL], character.getUserCreated());			
			db.insert(tableName, null, values);			
		}

	}	

	public void DeleteCharacter(SQLiteDatabase db, Character character) {
		String whereClause;

		if (character.getIsNull()) {
			return;
		}

		whereClause = "((" + columnList[CHARACTER_CHARACTERNAME_COL] + " = \"" + character.getCharacterName() + "\")";
		whereClause = whereClause + " AND (" + columnList[CHARACTER_WORLD_COL] + " = \"" + character.getCharacterWorld() + "\"))";
		db.delete(tableName, whereClause, null);
	}
}
