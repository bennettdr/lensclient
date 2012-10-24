package client.lensmoor.com;

import client.lensmoor.com.LensClientDBHelper;

public class Table {
	protected String columnList [];
	protected String selectColumnList[];
	protected LensClientDBHelper dbHelper;

	Table(LensClientDBHelper currentDBHelper, String tableName, String columnAttr[]) {
		this.dbHelper = currentDBHelper;
		columnList = LensClientDBHelper.dbCreateColumnListFromAttr(columnAttr);
		selectColumnList = LensClientDBHelper.dbCreateSelectColumnList(tableName, columnList);		
	}
}
