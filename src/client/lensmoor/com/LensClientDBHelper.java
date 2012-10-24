package client.lensmoor.com;

//Android Imports
import android.content.*;
import android.database.sqlite.*;

public class LensClientDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "LensmoorClient.db";
	private static final int DATABASE_VERSION = 1;
	private static final int DB_MAX_RETURNED_ROWS = 500;
	
	private SQLiteDatabase db;
	
	public LensClientDBHelper(Context context) {
		super (context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// Update/create of database
	@Override
	public void onCreate(SQLiteDatabase database) {
		TableSavedState.onCreate(database);
		TableWorld.onCreate(database);
		TableCharacter.onCreate(database);
		TableConfig.onCreate(database);
//		TableCampaign.onCreate(database);
//		TableVoterCampaignInformation.onCreate(database);
//		TableAddressCampaign.onCreate(database);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
//		TableVoter.onUpgrade(database, oldVersion, newVersion);
//		TableAddress.onUpgrade(database, oldVersion, newVersion);
//		TableCampaign.onUpgrade(db, newVersion, oldVersion);
//		TableSavedState.onUpgrade(database, newVersion, oldVersion);
//		TableVoterCampaignInformation.onUpgrade(database, newVersion, oldVersion);
//		TableAddressCampaign.onCreate(database);
	}
	
	@Override
	public void onOpen(SQLiteDatabase database) {
		db = database;
		super.onOpen(db);
	}
	
	// Get Routines
	public String getDatabaseName() {
		return DATABASE_NAME;
	}

	public String getPath() {
		return db.getPath();
	}

//	public void MarkAddressAsSeen(long addressReferenceNumber, boolean talkedWith) {
//		TableAddressCampaign addressCampaignTable = new TableAddressCampaign(this);
//		addressCampaignTable.MarkAddressAsSeen(db, addressReferenceNumber, talkedWith);
//	}
	// Saved State Table Operations
	public LensClientSavedState GetSavedState(String worldName) {
		TableSavedState savedStateTable = new TableSavedState(this);
		return savedStateTable.GetSavedState(db, worldName);
	}
	
	public void SaveState(LensClientSavedState savedState) {
		TableSavedState savedStateTable = new TableSavedState(this);
		savedStateTable.setSavedState(savedState, db);
	}

	// World Table Operations
	public World GetWorld(String worldName) {
		TableWorld worldTable = new TableWorld(this);
		World worldList[] = worldTable.GetWorldList(db, worldName);
		return worldList[0];
	}
	public World [] GetWorldList() {
		TableWorld worldTable = new TableWorld(this);
		return worldTable.GetWorldList(db, null);
	}
	public void DeleteWorld(World world) {
		TableWorld worldTable = new TableWorld(this);
		worldTable.DeleteWorld(db, world);		
	}
	public void SaveWorld(World world) {
		TableWorld worldTable = new TableWorld(this);
		worldTable.SaveWorld(db, world);		
	}

	// Character Table Operations
	public Character GetCharacter(String characterName, String worldName) {
		TableCharacter characterTable = new TableCharacter(this);
		Character characterList[] = characterTable.GetCharacterList(db, characterName, worldName);
		return characterList[0];
	}
	public Character [] GetCharacterList() {
		TableCharacter characterTable = new TableCharacter(this);
		return characterTable.GetCharacterList(db, null, null);
	}
	public void SaveCharacter(Character character) {
		TableCharacter characterTable = new TableCharacter(this);
		characterTable.SaveCharacter(db, character);		
	}
	public void DeleteCharacter(Character character) {
		TableCharacter characterTable= new TableCharacter(this);
		characterTable.DeleteCharacter(db, character);		
	}
	
	// Database Helper Routines
	static public String dbCreateTable(String table, String attributes[], String key[]) {
		String createString;
		int i = 0;
		
		createString = "CREATE TABLE " + table + " (";
		while(attributes[i] != null) {
			if(i > 0) {
				createString = createString + ",";
			}
			createString = createString + attributes[i] + " " + attributes[i+1];
			if(attributes[i+2] != null) {
				createString = createString + " " + attributes[i+2];
			}
			i = i+ 3;
		}
		
		i = 0;
		createString = createString + ", PRIMARY KEY (";
		while(key[i] != null) {
			if(i > 0) {
				createString = createString + ",";
			}
			createString = createString + key[i];
			i++;
		}
		createString = createString + "));";
		return createString;
	}
	
	static public String dbCreateIndex(String tableName, int index_number, String[] index) {
		String createString;
		createString = "CREATE INDEX IF NOT EXISTS " + tableName + index_number + " ON " + tableName + " (";
		for(int i = 0; index[i] != null; i++) {
			if(i > 0) {
				createString = createString + ", ";
			}
			createString = createString + index[i];
		}
		createString = createString + ")";
		return createString;
	}
	
	static public String [] dbCreateColumnListFromAttr(String [] attributeList) {
		int i;
		int j;
		for(i = 0, j = 0; attributeList[i] != null; i = i + 3, j++);
		String columnList[] = new String[j];
		for(i = 0, j = 0; j < columnList.length; i = i + 3, j++) {
			columnList[j] = attributeList[i];
		}
		return columnList;
	}
	
	static public String [] dbCreateSelectColumnList (String tableName, String [] columnList) {
		int i;
		String [] selectColumnList = new String [columnList.length];
		for(i = 0; i < columnList.length; i++) {
			selectColumnList[i] = tableName + "." + columnList[i];
		}
		return selectColumnList;
	}

	public static void initializeTable (SQLiteDatabase db, String tableName, String [] columnAttr, String [] keyList, String [][] indexList) {
		String createTableStatement;
		String createIndexStatement;

		createTableStatement = LensClientDBHelper.dbCreateTable(tableName, columnAttr, keyList);
		db.execSQL(createTableStatement);
		for(int i = 0; indexList[i] != null; i++) {
			createIndexStatement = LensClientDBHelper.dbCreateIndex(tableName, i, indexList[i]);
			db.execSQL(createIndexStatement);
		}
	}

	static public int dbQueryMaxRowLimit() {
		return DB_MAX_RETURNED_ROWS;
	}
}
