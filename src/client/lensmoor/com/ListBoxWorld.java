package client.lensmoor.com;

//Android Imports
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class ListBoxWorld extends Dialog implements AdapterView.OnItemClickListener, DialogInterface.OnDismissListener {
	LensClientDBHelper dbHelper;
	String [] stringList;
	int itemCount;
	int selectedItem;
	boolean editable;
	
	
	public ListBoxWorld(LensClientDBHelper dbHelper, boolean editable, String title, Context context, int theme) {
		super(context, theme);
		InitWorldListDialogBox(dbHelper, title, editable);
	}

	public ListBoxWorld(LensClientDBHelper dbHelper, boolean editable, String title, Context context) {
		super(context);
		InitWorldListDialogBox(dbHelper, title, editable);
	}

	void InitWorldListDialogBox(LensClientDBHelper dbHelper, String title, boolean editable) {
		World [] worldList = dbHelper.GetWorldList();
		LensClientSavedState savedState = LensClientSavedState.GetSavedState(dbHelper);
		this.dbHelper = dbHelper;
		this.editable = editable;

		for(itemCount = 0; !worldList[itemCount].getIsNull(); itemCount++);
		stringList = new String [itemCount + 2];
		for(itemCount = 0; !worldList[itemCount].getIsNull(); itemCount++) {
			stringList[itemCount] = worldList[itemCount].getWorldName();
			if(savedState.getSavedWorldName() == worldList[itemCount].getWorldName()) {
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

		ListView WorldListView = new ListView(this.getContext());
		LensClientListBox adapter = new LensClientListBox(this.getContext(), stringList, itemCount, selectedItem);
		WorldListView.setAdapter(adapter);
		WorldListView.setItemsCanFocus(true);
		WorldListView.setOnItemClickListener(this);
		setContentView(WorldListView);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(position < itemCount) {
			World [] worldList = dbHelper.GetWorldList();
			if (editable) {
				DialogWorld dialog_world = new DialogWorld(dbHelper, parent.getContext(), worldList[position]);
				dialog_world.show();							
			} else {
				LensClientSavedState savedState = LensClientSavedState.GetSavedState(dbHelper);
				savedState.setSavedWorldName(worldList[position].getWorldName());
				dbHelper.SaveState(savedState);
			}
			dismiss();
		} else if (position == itemCount) {
			DialogWorld dialog_world = new DialogWorld(dbHelper, parent.getContext(), null);
			dialog_world.setOnDismissListener(this);
			dialog_world.show();
		} else {
			dismiss();
		}		
	}

	// Wait until child process for adding a new world is done before dismissing
	@Override
	public void onDismiss(DialogInterface dialog) {
		dismiss();
	}

}
