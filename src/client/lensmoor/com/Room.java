package client.lensmoor.com;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Room implements Parcelable, Parcelable.Creator<Room> {
	private String title;
	private ArrayList<String> description;
	private ArrayList<String> exits;
	private ArrayList<String> objects;
	private ArrayList<String> mobiles;

	Room() {
		super();
		initRoom();
	}

	Room(Parcel in) {
		super();
		initRoom();
		createFromParcel(in);
	}

	private void initRoom() {
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

	// Parcelable Support
	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		int i;
		
		dest.writeString(title);
		dest.writeInt(description.size());
		for(i = 0; i < description.size(); i++) {
			dest.writeString(description.get(i));
		}
		dest.writeInt(exits.size());
		for(i = 0; i < exits.size(); i++) {
			dest.writeString(exits.get(i));
		}
		dest.writeInt(objects.size());
		for(i = 0; i < objects.size(); i++) {
			dest.writeString(objects.get(i));
		}
		dest.writeInt(mobiles.size());
		for(i = 0; i < mobiles.size(); i++) {
			dest.writeString(mobiles.get(i));
		}
	}

	@Override
	public Room createFromParcel(Parcel source) {
		int i, array_len;
		
		title = source.readString();
		array_len = source.readInt();
		for(i = 0; i < array_len; i++) {
			description.add(source.readString());
		}
		array_len = source.readInt();
		for(i = 0; i < array_len; i++) {
			exits.add(source.readString());
		}
		array_len = source.readInt();
		for(i = 0; i < array_len; i++) {
			objects.add(source.readString());
		}
		array_len = source.readInt();
		for(i = 0; i < array_len; i++) {
			mobiles.add(source.readString());
		}
		return null;
	}

	@Override
	public Room[] newArray(int size) {
		return new Room[size];
	}

	public static final Parcelable.Creator<Room> CREATOR = 
			new Parcelable.Creator<Room>() {
				public Room createFromParcel(Parcel in) { return new Room(in); }
				public Room [] newArray (int size) { return new Room[size]; }
			};
	// End Parcelable Support
	
}
