package client.lensmoor.com;

//Java Imports

//Android Imports
import android.database.*;
import android.database.sqlite.*;
import android.content.ContentValues;

public class TableSavedState extends Table {

	private static final int SAVED_WORLD_COL = 0;
	private static final int SAVED_ACTIVE_FLAG = SAVED_WORLD_COL + 1; 
	private static final int SAVED_CURRENT_CHARACTER = SAVED_ACTIVE_FLAG + 1; 
	private static final int SAVED_DATE_CREATED_COL = SAVED_CURRENT_CHARACTER + 1;
	private static final int SAVED_USER_CREATED_COL = SAVED_DATE_CREATED_COL + 1;
	private static final int SAVED_DATE_UPDATED_COL = SAVED_USER_CREATED_COL + 1;
	private static final int SAVED_USER_UPDATED_COL = SAVED_DATE_UPDATED_COL + 1;
	private static final int SAVED_NUMBER_COLUMNS = SAVED_USER_UPDATED_COL + 1;

	private static final String columnAttr[] = {
		"SavedWorld", "TEXT", null,
		"SavedActiveFlag", "INTEGER", null,
		"SavedCurrentCharacter", "TEXT", null,
		"DateCreated", "INTEGER", null,
		"UserCreated", "TEXT", null,
		"DateUpdated", "INTEGER", null,
		"UserUpdated", "TEXT", null,
		null
	};	

	private static final String keyList[] = {
		"SavedWorld",
		null
	};
	private static final String indexList[] [] = {
		{"SavedActiveFlag", null},
		null
	};
	private static final String tableName = "LensClientState";
	
	public TableSavedState(LensClientDBHelper currentDBHelper) {
		super(currentDBHelper, tableName, columnAttr);
	}
		
	public static void onCreate (SQLiteDatabase db) {
		LensClientDBHelper.initializeTable(db, tableName, columnAttr, keyList, indexList);
	}
		
	public static void onUpgrade (SQLiteDatabase db, int newVersion, int oldVersion) {
		if (newVersion > oldVersion) {
			onCreate(db);
		}
	}
	
	public LensClientSavedState GetSavedState(SQLiteDatabase db, String worldName) throws SQLException {
		Cursor cursor;
		String whereClause;
		LensClientSavedState savedState = new LensClientSavedState(dbHelper);

		if ((worldName == null) || (worldName.length() == 0)) {
			whereClause = "(" + selectColumnList[SAVED_ACTIVE_FLAG] + " = 1)";
		} else {
			whereClause = "(" + selectColumnList[SAVED_WORLD_COL] + " = " + worldName + ")";			
		}
		cursor = db.query(tableName, selectColumnList, whereClause, null, null, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			if(!cursor.isAfterLast()) {
				savedState.setSavedWorldName(cursor.getString(cursor.getColumnIndex(columnList[SAVED_WORLD_COL])));			
				savedState.setSavedActiveFlag(cursor.getInt(cursor.getColumnIndex(columnList[SAVED_ACTIVE_FLAG])));			
				savedState.setSavedCharacterName(cursor.getString(cursor.getColumnIndex(columnList[SAVED_CURRENT_CHARACTER])));			
				savedState.setDateCreated(cursor.getLong(cursor.getColumnIndex(columnList[SAVED_DATE_CREATED_COL])));
				savedState.setUserCreated(cursor.getString(cursor.getColumnIndex(columnList[SAVED_USER_CREATED_COL])));
				savedState.setDateUpdated(cursor.getLong(cursor.getColumnIndex(columnList[SAVED_DATE_UPDATED_COL])));
				savedState.setUserUpdated(cursor.getString(cursor.getColumnIndex(columnList[SAVED_USER_UPDATED_COL])));
			} else {
				savedState.setIsNull();
			}
			cursor.close();
		} else {
			savedState.setIsNull();
		}
		return savedState;
	}
	
	public void setSavedState(LensClientSavedState savedState, SQLiteDatabase db) throws SQLException {
		ContentValues values = new ContentValues(SAVED_NUMBER_COLUMNS);
		String whereClauseOnActiveFlag;
		String whereClauseOnWorldName;
		String [] whereArgs = new String[1];
		LensClientSavedState originalState = GetSavedState(db, null);
		int countStates;

		whereClauseOnActiveFlag = "(" + selectColumnList[SAVED_ACTIVE_FLAG] + "= ?)";
		whereClauseOnWorldName = "(" + selectColumnList[SAVED_WORLD_COL] + "= ?)";

		if(!originalState.getIsNull() && !originalState.getSavedWorldName().equals(savedState.getSavedWorldName())) {
			whereArgs[0] = originalState.getSavedWorldName();
			values.put(columnList[SAVED_DATE_UPDATED_COL], savedState.getDateUpdated());
			originalState.setUpdateTime();
			values.put(columnList[SAVED_USER_UPDATED_COL], savedState.getUserUpdate());
			values.put(columnList[SAVED_ACTIVE_FLAG], (int)0);
			db.update(tableName, values, whereClauseOnWorldName, whereArgs);
		}
		if(originalState.getIsNull()
				|| !originalState.getSavedWorldName().equals(savedState.getSavedWorldName())
				|| !originalState.getSavedCharacterName().equals(savedState.getSavedCharacterName()) ) {
			LogWriter.setLogFileName(EnumLogType.TELNET, savedState.getSavedWorldName(), savedState.getSavedCharacterName());
			LogWriter.setLogFileName(EnumLogType.SYSTEM, savedState.getSavedWorldName(), savedState.getSavedCharacterName());
			LogWriter.setLogFileName(EnumLogType.DEBUG, savedState.getSavedWorldName(), savedState.getSavedCharacterName());
		}

		whereArgs[0] = savedState.getSavedWorldName();
		countStates = (int)DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + tableName + " WHERE " + whereClauseOnWorldName, whereArgs);
		savedState.setUpdateTime();
		values.put(columnList[SAVED_ACTIVE_FLAG], (int)1);
		values.put(columnList[SAVED_CURRENT_CHARACTER], savedState.getSavedCharacterName());
		values.put(columnList[SAVED_DATE_UPDATED_COL], savedState.getDateUpdated());
		values.put(columnList[SAVED_USER_UPDATED_COL], savedState.getUserUpdate());
		if(countStates > 0) {
			db.update(tableName, values, whereClauseOnWorldName, whereArgs);			
		} else {
			values.put(columnList[SAVED_WORLD_COL], savedState.getSavedWorldName());
			values.put(columnList[SAVED_DATE_CREATED_COL], savedState.getDateCreated());
			values.put(columnList[SAVED_USER_CREATED_COL], savedState.getUserCreated());			
			db.insert(tableName, null, values);			
		}
	}
}
