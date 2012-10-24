package client.lensmoor.com;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListBoxCharacter extends Dialog implements AdapterView.OnItemClickListener {
	LensClientDBHelper dbHelper;
	String [] stringList;
	int itemCount;
	int selectedItem;
	boolean editable;

	public ListBoxCharacter(LensClientDBHelper dbHelper, boolean editable, String title, Context context, int theme) {
		super(context, theme);
		InitCharacterListDialogBox(dbHelper, title, editable);
	}

	public ListBoxCharacter(LensClientDBHelper dbHelper, boolean editable, String title, Context context) {
		super(context);
		InitCharacterListDialogBox(dbHelper, title, editable);
	}

	void InitCharacterListDialogBox(LensClientDBHelper dbHelper, String title, boolean editable) {
		Character [] characterList = dbHelper.GetCharacterList();
		LensClientSavedState savedState = LensClientSavedState.GetSavedState(dbHelper);
		this.dbHelper = dbHelper;
		this.editable = editable;

		for(itemCount = 0; !characterList[itemCount].getIsNull(); itemCount++);
		stringList = new String [itemCount + 2];
		for(itemCount = 0; !characterList[itemCount].getIsNull(); itemCount++) {
			stringList[itemCount] = characterList[itemCount].getCharacterWorldString();
			if((savedState.getSavedWorldName() == characterList[itemCount].getCharacterWorld()) && 
				(savedState.getSavedCharacterName() == characterList[itemCount].getCharacterName())) {
				selectedItem = itemCount;
			}
		}
		stringList[itemCount] = "Add";
		stringList[itemCount + 1] = "Back";
		setTitle(title);
	}
	
	@Override	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ListView CharacterListView = new ListView(this.getContext());
		LensClientListBox adapter = new LensClientListBox(this.getContext(), stringList, itemCount, selectedItem);
		CharacterListView.setAdapter(adapter);
		CharacterListView.setItemsCanFocus(true);
		CharacterListView.setOnItemClickListener(this);
		setContentView(CharacterListView);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(position < itemCount) {
			Character [] characterList = dbHelper.GetCharacterList();
			if (editable) {
				DialogCharacter dialog_character = new DialogCharacter (dbHelper, parent.getContext(), characterList[position]);
				dialog_character.show();							
			} else {
				LensClientSavedState savedState = LensClientSavedState.GetSavedState(dbHelper);
				savedState.setSavedCharacterName(characterList[position].getCharacterName());
				savedState.setSavedWorldName(characterList[position].getCharacterWorld());
				dbHelper.SaveState(savedState);
			}
			dismiss();
		} else if (position == itemCount) {
			DialogCharacter dialog_character = new DialogCharacter(dbHelper, parent.getContext(), null);
			dialog_character.show();
			dismiss();
		} else {
			dismiss();
		}
	}
}
