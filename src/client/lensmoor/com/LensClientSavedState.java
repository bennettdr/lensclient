package client.lensmoor.com;

import client.lensmoor.com.LensClientDBHelper;

public class LensClientSavedState extends LensClientSerializedObject {
	// Key
	private String savedWorldName = "";
	// Alt Key
	private boolean savedActiveFlag;
	// Additional Data
	private String savedCharacterName = "";

	LensClientSavedState(LensClientDBHelper dbHelper) {
		super(dbHelper);
	}

	public String getSavedWorldName() { return savedWorldName; }
	public boolean getSavedActiveFlag() { return savedActiveFlag; }
	public int getSavedActiveFlagAsInt() { if (savedActiveFlag) { return 1; } return 0; }
	public String getSavedCharacterName() { return savedCharacterName; }
	
	public void setSavedWorldName(String world_name) { savedWorldName = world_name; }
	public void setSavedActiveFlag(boolean active_flag) { savedActiveFlag = active_flag; }
	public void setSavedActiveFlag(int active_flag) { savedActiveFlag = !(active_flag == 0); }
	public void setSavedCharacterName(String character_name) { savedCharacterName = character_name; }
	
	static LensClientSavedState GetSavedState(LensClientDBHelper dbHelper) { return(dbHelper.GetSavedState(null)); }
	static LensClientSavedState GetSavedState(LensClientDBHelper dbHelper, String worldName) { return(dbHelper.GetSavedState(worldName)); }
}
