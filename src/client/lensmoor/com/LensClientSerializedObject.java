package client.lensmoor.com;

import client.lensmoor.com.LensClientDBHelper;
import client.lensmoor.com.SerializedObject;

public class LensClientSerializedObject extends SerializedObject {
	LensClientDBHelper dbHelper;

	LensClientSerializedObject() {
		super();
	}

	LensClientSerializedObject(LensClientDBHelper dbHelper) {
		super();
		this.dbHelper = dbHelper;
	}

	LensClientDBHelper getDBHelper() { return dbHelper; }
}
