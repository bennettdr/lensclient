package client.lensmoor.com;

import android.database.*;
import android.database.sqlite.*;
import android.content.ContentValues;


public class TableWorld extends Table {
	private static final int WORLD_WORLDNAME_COL = 0;
	private static final int WORLD_URL_COL = WORLD_WORLDNAME_COL + 1;
	private static final int WORLD_IP_COL = WORLD_URL_COL + 1;
	private static final int WORLD_PORT_COL= WORLD_IP_COL + 1;
	private static final int WORLD_DATE_CREATED_COL = WORLD_PORT_COL + 1;
	private static final int WORLD_USER_CREATED_COL = WORLD_DATE_CREATED_COL + 1;
	private static final int WORLD_DATE_UPDATED_COL = WORLD_USER_CREATED_COL + 1;
	private static final int WORLD_USER_UPDATED_COL = WORLD_DATE_UPDATED_COL + 1;
	private static final int WORLD_NUMBER_COLUMNS = WORLD_USER_UPDATED_COL + 1;

	private static final String columnAttr[] = {
		"WorldName", "TEXT", null,
		"URL", "TEXT", null,
		"IP", "TEXT", null,
		"Port", "INTEGER", null,
		"DateCreated", "INTEGER", null,
		"UserCreated", "TEXT", null,
		"DateUpdated", "INTEGER", null,
		"UserUpdated", "TEXT", null,
		null
	};
	private static final String keyList[] = {
		"WorldName",
		null
	};
	private static final String indexList[] [] = {
		null
	};
	private static final String tableName = "WorldInformation";
	
	private static final TableUpdate updateList[] = null;

	public TableWorld(LensClientDBHelper currentDBHelper) {
		super(currentDBHelper, tableName, columnAttr);
	}

	public static void onCreate (SQLiteDatabase db) {
		LensClientDBHelper.initializeTable(db, tableName, columnAttr, keyList, indexList);
	}
		
	public static void onUpgrade (SQLiteDatabase db, int newVersion, int oldVersion) {
		LensClientDBHelper.updateTable(db, newVersion, oldVersion, tableName, columnAttr, keyList, indexList, updateList);
	}
	
	// Database Support
	public void SaveWorld(SQLiteDatabase db, World world) throws SQLException {
		ContentValues values = new ContentValues(WORLD_NUMBER_COLUMNS);
		String whereClause;
		int countWorlds;
		
		if ((world == null) || (world.getWorldName() == null) || (world.getWorldName().length() == 0)) {
			return;
		}

		whereClause = "(" + selectColumnList[WORLD_WORLDNAME_COL] + " = \"" + world.getWorldName() + "\")";

		countWorlds = (int)DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + tableName + " WHERE " + whereClause, null);
		world.setUpdateTime();
		values.put(columnList[WORLD_URL_COL], world.getURL());
		values.put(columnList[WORLD_IP_COL], world.getIPAddress());
		values.put(columnList[WORLD_PORT_COL], world.getPort());
		values.put(columnList[WORLD_DATE_UPDATED_COL], world.getDateUpdated());
		values.put(columnList[WORLD_USER_UPDATED_COL], world.getUserUpdate());
		if(countWorlds > 0) {
			db.update(tableName, values, whereClause, null);			
		} else {
			values.put(columnList[WORLD_WORLDNAME_COL], world.getWorldName());
			values.put(columnList[WORLD_DATE_CREATED_COL], world.getDateCreated());
			values.put(columnList[WORLD_USER_CREATED_COL], world.getUserCreated());			
			db.insert(tableName, null, values);			
		}

	}

	public World [] GetWorldList(SQLiteDatabase db, String worldName) {
		World [] worldList;
		String whereClause = null;
		Cursor cursor;
		int i = 0;
		int countAddresses;
	
		if ((worldName != null) && (worldName.length() > 0)) {
			whereClause = "(" + columnList[WORLD_WORLDNAME_COL] + " = \"" + worldName + "\")";			
			countAddresses = (int)DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + tableName + " WHERE " + whereClause, null);
		} else {
			countAddresses = (int)DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + tableName, null);			
		}
		
		worldList = new World[countAddresses + 1];

		cursor = db.query(tableName, selectColumnList, whereClause, null, null, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			while(!cursor.isAfterLast()) {
				worldList[i] = new World(dbHelper);
				worldList[i].setWorldName(cursor.getString(cursor.getColumnIndex(columnList[WORLD_WORLDNAME_COL])));			
				worldList[i].setURL(cursor.getString(cursor.getColumnIndex(columnList[WORLD_URL_COL])));			
				worldList[i].setIPAddress(cursor.getString(cursor.getColumnIndex(columnList[WORLD_IP_COL])));		
				worldList[i].setPort(cursor.getInt(cursor.getColumnIndex(columnList[WORLD_PORT_COL])));		
				worldList[i].setDateCreated(cursor.getLong(cursor.getColumnIndex(columnList[WORLD_DATE_CREATED_COL])));
				worldList[i].setUserCreated(cursor.getString(cursor.getColumnIndex(columnList[WORLD_USER_CREATED_COL])));
				worldList[i].setDateUpdated(cursor.getLong(cursor.getColumnIndex(columnList[WORLD_DATE_UPDATED_COL])));
				worldList[i].setUserUpdated(cursor.getString(cursor.getColumnIndex(columnList[WORLD_USER_UPDATED_COL])));
				i++;
				cursor.moveToNext();
			}
			cursor.close();
		}
		worldList[i] = new World(dbHelper);
		worldList[i].setIsNull();
		return worldList;
	}

	public void DeleteWorld(SQLiteDatabase db, World world) {
		String whereClause;

		if (world.getIsNull()) {
			return;
		}
		whereClause = "(" + columnList[WORLD_WORLDNAME_COL] + " = \"" + world.getWorldName() + "\")";
		db.delete(tableName, whereClause, null);
	}
}
