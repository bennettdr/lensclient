package client.lensmoor.com;

public enum EnumLogType {
	TELNET (0), SYSTEM(1), DEBUG (2), SIZEOF(3);
	int value;
	
	EnumLogType(int value) {
		this.value = value;
	}
	
	int getInt() { return value; }
}
