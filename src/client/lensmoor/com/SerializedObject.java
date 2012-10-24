package client.lensmoor.com;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class SerializedObject {
	// Audit info
	private long dateCreated;
	private String userCreated;
	private long dateUpdated;
	private String userUpdated;
	
	private boolean isNull;

	SerializedObject() {
	}
	
	public long getDateCreated() { return dateCreated; }
	public String getUserCreated() { return userCreated; }
	public long getDateUpdated() { return dateUpdated; }
	public String getUserUpdate() { return userUpdated; }
	public boolean getIsNull() { return isNull; }
	
	public void setDateCreated(long creationDate) {dateCreated = creationDate;}
	public void setUserCreated(String creator) {userCreated = creator;}
	public void setDateUpdated(long updateDate) {dateUpdated = updateDate;}
	public void setUserUpdated(String updater) {userUpdated = updater;}
	public void setIsNull() { isNull = true; }
	
	public void setUpdateTime() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("yyyymmdd");
		String dateNow = formatter.format(currentDate.getTime());
		dateCreated = new Long(dateNow).longValue();
	}
}
