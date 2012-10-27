package client.lensmoor.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListBoxSystemMenu extends Dialog implements AdapterView.OnItemClickListener {
	LensClientDBHelper dbHelper;
	
	private static final String stringList[] = {
			"Select Character",
			"Edit Character",
			"Edit World",
			"Save Database",
			"Load Database",
			"Back"
	};
	private static final int selectCharacter = 0;
	private static final int editCharacter = selectCharacter + 1;
	private static final int editWorld = editCharacter + 1;
	private static final int saveDatabase = editWorld + 1;
	private static final int loadDatabase = saveDatabase + 1;
	private static final int itemCount = loadDatabase + 1;

	public ListBoxSystemMenu(LensClientDBHelper dbHelper, String title, Context context, int theme) {
		super(context, theme);
		InitSystemMenuListDialogBox(dbHelper, title);
	}

	public ListBoxSystemMenu(LensClientDBHelper dbHelper, String title, Context context) {
		super(context);
		InitSystemMenuListDialogBox(dbHelper, title);
	}

	void InitSystemMenuListDialogBox(LensClientDBHelper dbHelper, String title) {
		this.dbHelper = dbHelper;
		setTitle(title);
	}

	@Override	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ListView SystemMenuView = new ListView(this.getContext());
		LensClientListBox adapter = new LensClientListBox(this.getContext(), stringList, itemCount, 0);
		SystemMenuView.setAdapter(adapter);
		SystemMenuView.setItemsCanFocus(true);
		SystemMenuView.setOnItemClickListener(this);
		setContentView(SystemMenuView );
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ListBoxCharacter choose_character;
    	// Set up for database Save
		File loadPath = new File(dbHelper.getPath());
		File dbPath = new File("/sdcard/LensClient/", dbHelper.getDatabaseName());

		switch (position) {
			case selectCharacter:
    			choose_character = new ListBoxCharacter(dbHelper, false, "Choose Character", this.getContext());
    			choose_character.show();
				break;
			case editCharacter:
    			choose_character = new ListBoxCharacter(dbHelper, true, "Edit Character", this.getContext());
    			choose_character.show();
				break;
			case editWorld:
    			ListBoxWorld edit_world = new ListBoxWorld (dbHelper, true, "Edit World", this.getContext());
    			edit_world.show();
				break;
    		case loadDatabase:
    			// Load reads from sdcard to protected region
    			dbPath = new File(dbHelper.getPath());
    			loadPath = new File("/sdcard/LensClient/", dbHelper.getDatabaseName());
    		case saveDatabase:
    			dbHelper.close();
    			try {
    				//Open your local db as the input stream
    				InputStream myInput = new FileInputStream(loadPath);
    				//Open the empty db as the output stream
    				OutputStream myOutput = new FileOutputStream(dbPath);
    				//transfer bytes from the inputfile to the outputfile
    				byte[] buffer = new byte[1024];
    				int length;
    				while ((length = myInput.read(buffer))>0) {
    					myOutput.write(buffer, 0, length);
    				}
    				//Close the streams
    				myOutput.flush();
    				myOutput.close();
    				myInput.close();
    			} catch (IOException e) {
    				AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
    				builder.setTitle("Error");
    				builder.setMessage(e.getMessage());
    				AlertDialog alertDialog = builder.create();
    				alertDialog.setCanceledOnTouchOutside(true);
    				alertDialog.show();
    			}
    			try {
    				dbHelper.getWritableDatabase();
    			} catch (SQLiteException e) {
    				AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
    				builder.setTitle("Error");
    				builder.setMessage(e.getMessage());
    				AlertDialog alertDialog = builder.create();
    				alertDialog.setCanceledOnTouchOutside(true);
    				alertDialog.show();
    			}
    			break;
    		default:
    			break;
		}
		dismiss();
	}
}
