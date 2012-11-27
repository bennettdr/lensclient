package client.lensmoor.com;

public class TableUpdate {
	private int databaseVersion;
	private String updateStatement;
	
	TableUpdate(int version, String update) {
		databaseVersion = version;
		updateStatement = update;
	}

	public int getVersion() { return databaseVersion; }
	public String getUpdateStatement() { return updateStatement; } 
}
