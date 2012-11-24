package client.lensmoor.com;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

public class TableInfoChannel extends Table {
	private static final int CHANNELINFO_CHANNEL_COL = 0;
	private static final int CHANNELINFO_TIME_COL =  CHANNELINFO_CHANNEL_COL + 1;
	private static final int CHANNELINFO_MESSAGE_COL =  CHANNELINFO_TIME_COL + 1;
	private static final int CHANNELINFO_DATE_CREATED_COL = CHANNELINFO_MESSAGE_COL + 1;
	private static final int CHANNELINFO_USER_CREATED_COL = CHANNELINFO_DATE_CREATED_COL + 1;
	private static final int CHANNELINFO_DATE_UPDATED_COL = CHANNELINFO_USER_CREATED_COL + 1;
	private static final int CHANNELINFO_USER_UPDATED_COL = CHANNELINFO_DATE_UPDATED_COL + 1;
	private static final int CHANNELINFO_NUMBER_COLUMNS = CHANNELINFO_USER_UPDATED_COL + 1;

	private static final String columnAttr[] = {
		"Channel", "TEXT", null,
		"Time", "INTEGER", null,
		"Message", "TEXT", null,
		"DateCreated", "INTEGER", null,
		"UserCreated", "TEXT", null,
		"DateUpdated", "INTEGER", null,
		"UserUpdated", "TEXT", null,
		null
	};
	private static final String keyList[] = {
		"Channel",
		"DateCreated",
		"Time",
		null
	};
	private static final String indexList[] [] = {
		null
	};
	private static final String tableName = "InfoChannelMessages";

	public TableInfoChannel(LensClientDBHelper currentDBHelper) {
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
	public ChannelMessage[] GetChannelMessageList(SQLiteDatabase db, EnumChannel channel) {
		ChannelMessage [] messageList;
		String whereClause = null;
		Cursor cursor;
		int i = 0;
		int countAddresses;

		whereClause = "(" + columnList[CHANNELINFO_CHANNEL_COL] + " = \"" + channel.getString() + "\")";
		countAddresses = (int)DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + tableName + " WHERE " + whereClause, null);

		messageList = new ChannelMessage[countAddresses + 1];

		cursor = db.query(tableName, selectColumnList, whereClause, null, null, null, null, null);
		
		if(cursor != null) {
			cursor.moveToFirst();
			while(!cursor.isAfterLast()) {
				messageList[i] = new ChannelMessage(dbHelper, channel);
				messageList[i].setTime(cursor.getInt(cursor.getColumnIndex(columnList[CHANNELINFO_TIME_COL])));
				messageList[i].setMessage(cursor.getString(cursor.getColumnIndex(columnList[CHANNELINFO_MESSAGE_COL])));
				messageList[i].setDateCreated(cursor.getLong(cursor.getColumnIndex(columnList[CHANNELINFO_DATE_CREATED_COL])));
				messageList[i].setUserCreated(cursor.getString(cursor.getColumnIndex(columnList[CHANNELINFO_USER_CREATED_COL])));
				messageList[i].setDateUpdated(cursor.getLong(cursor.getColumnIndex(columnList[CHANNELINFO_DATE_UPDATED_COL])));
				messageList[i].setUserUpdated(cursor.getString(cursor.getColumnIndex(columnList[CHANNELINFO_USER_UPDATED_COL])));
				i++;
				cursor.moveToNext();
			}
			cursor.close();
		}
		messageList[i] = new ChannelMessage(dbHelper, channel);
		messageList[i].setIsNull();
		return messageList;
	}
	
	public void InsertChannelMessage(SQLiteDatabase db, ChannelMessage channelMessage) {
		ContentValues values = new ContentValues(CHANNELINFO_NUMBER_COLUMNS);
		
		if ((channelMessage == null) || (channelMessage.getIsNull())) {
			return;
		}

		values.put(columnList[CHANNELINFO_CHANNEL_COL], channelMessage.getChannel().getString());
		values.put(columnList[CHANNELINFO_TIME_COL], channelMessage.getTime());
		values.put(columnList[CHANNELINFO_MESSAGE_COL], channelMessage.getMessage());
		values.put(columnList[CHANNELINFO_DATE_CREATED_COL], channelMessage.getDateCreated());
		values.put(columnList[CHANNELINFO_USER_CREATED_COL], channelMessage.getUserCreated());
		values.put(columnList[CHANNELINFO_DATE_UPDATED_COL], channelMessage.getDateUpdated());
		values.put(columnList[CHANNELINFO_USER_UPDATED_COL], channelMessage.getUserUpdate());
		db.insert(tableName, null, values);			
	}	
}
