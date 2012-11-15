package client.lensmoor.com;

import java.util.ArrayList;

public class Room {
	private String title;
	private ArrayList<String> description;
	private ArrayList<String> exits;
	private ArrayList<String> objects;
	private ArrayList<String> mobiles;

	Room() {
		description = new ArrayList<String>();
		exits = new ArrayList<String>();
		objects = new ArrayList<String>();
		mobiles = new ArrayList<String>();
	}

	public String getTitle() { return title; }
	public String getDescription(int line_no) { if(line_no < description.size()) return description.get(line_no); else return null; }
	public String getExits(int line_no) { if(line_no < exits.size()) return exits.get(line_no); else return null; }
	public String getObjects(int line_no) { if(line_no < objects.size()) return objects.get(line_no); else return null; }
	public String getMobiles(int line_no) { if(line_no < mobiles.size()) return mobiles.get(line_no); else return null; }

	public void setTitle(String title) { this.title = title; }
	public void setDescription(String description) { this.description.add(description); }
	public void setExits(String exit) { this.exits.add(exit); }
	public void setObjects(String object) { this.objects.add(object); }
	public void setMobiles(String mobile)  { this.mobiles.add(mobile); }
}
