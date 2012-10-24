package client.lensmoor.com;

public class World extends LensClientSerializedObject {
	// Key
	private String worldName = "";
	// Additional Data
	private String URL = "";
	private String IPAddress = "";
	private int port;
	
	public World(LensClientDBHelper dbHelper) {
		super(dbHelper);
	}
	
	public void DeleteWorld() { dbHelper.DeleteWorld(this); }

	public String getWorldName() { return worldName; }
	public String getURL() { return URL; }
	public String getIPAddress() { return IPAddress; }
	public int getPort() { return port; }

	public void setWorldName(String world_name) { worldName = world_name; }
	public void setURL(String url) { URL = url; }
	public void setIPAddress(String ip) { IPAddress = ip; }
	public void setPort(int port_no) { port = port_no; }
}
