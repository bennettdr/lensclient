package client.lensmoor.com;

import java.io.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface;
import android.database.sqlite.*;


public class LensClientActivity extends Activity implements OnClickListener, DialogInterface.OnDismissListener {
	private LensClientSavedState savedState;
	private Handler uiHandler;
	private MainThread mainThread;
	private TextView activecharacter;
	private TextView outputbox;
	private EditText inputbox;
	private LensClientTelnetHelper telnetHelper;
	private LensClientDBHelper dbHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		activecharacter = (TextView)findViewById(R.id.activecharacter);
		activecharacter.setOnClickListener(this);
		outputbox = (TextView)findViewById(R.id.outputbox);
		inputbox = (EditText)findViewById(R.id.inputbox);
		Button button = (Button)findViewById(R.id.button);
		button.setOnClickListener(this);

		outputbox.setText("Android Socket\n");
		dbHelper = new LensClientDBHelper(this);
		try {
			dbHelper.getWritableDatabase();
		} catch (SQLiteException e) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Error");
			builder.setMessage(e.getMessage());
			AlertDialog alertDialog = builder.create();
			alertDialog.setCanceledOnTouchOutside(true);
			alertDialog.show();
		}
		LogWriter.setLogInfo(new File(Environment.getExternalStorageDirectory() + "/LensClient/logs/"));
		savedState = LensClientSavedState.GetSavedState(dbHelper);
		if (!savedState.getIsNull() && !savedState.getSavedCharacterName().isEmpty()) {
			Character character = Character.GetCharacter(dbHelper, savedState.getSavedCharacterName(), savedState.getSavedWorldName());;
			activecharacter.setText(character.getCharacterWorldString());
			LogWriter.setLogFileName(EnumLogType.TELNET, savedState.getSavedWorldName(), savedState.getSavedCharacterName());
			LogWriter.setLogFileName(EnumLogType.SYSTEM, savedState.getSavedWorldName(), savedState.getSavedCharacterName());
			LogWriter.setLogFileName(EnumLogType.DEBUG, savedState.getSavedWorldName(), savedState.getSavedCharacterName());
		}
	}

    @Override 
    public boolean onCreateOptionsMenu(Menu menu) { 
    	MenuInflater inflater = getMenuInflater(); 
    	inflater.inflate(R.layout.mainmenu, menu);
    	if ((telnetHelper != null) && telnetHelper.isConnected()) {
    		MenuItem menu_item = (MenuItem)findViewById(R.id.itemconnect);
    		menu_item.setTitle(R.string.menu_disconnect);
    	}
    	return true; 
    } 
   
    @Override 
	public boolean onOptionsItemSelected(MenuItem item) {
    	Character character;
    	switch (item.getItemId()) {
    		case R.id.itemconnect :
    			if ((telnetHelper != null) && telnetHelper.isConnected()) {
    				try {
    					telnetHelper.disconnect();
					} catch (Exception e) {
						e.printStackTrace();
					}    	
    				return true;
    			}
    			outputbox.setText("Connecting...\n");
    			LensClientSavedState savedState = LensClientSavedState.GetSavedState(dbHelper);
    			if(savedState.getSavedWorldName().length() > 0) {
    				World world = dbHelper.GetWorld(savedState.getSavedWorldName());
        			character = Character.GetCharacter(dbHelper, savedState.getSavedCharacterName(), savedState.getSavedWorldName());
        			// Set the telnet and ui interfaces up
    				telnetHelper = new LensClientTelnetHelper(world);
    				uiHandler = new MessageHandlerUI(this.getMainLooper(), outputbox);
    				// Initialize the main thread
    				mainThread = new MainThread(telnetHelper, uiHandler);
    				// Make sure these are set so that the default filter is the lowest
    				// Since its always running and it consumes all information
        			RequestDefaultDisplay defaultFilter = new RequestDefaultDisplay(telnetHelper, dbHelper, uiHandler);
        			mainThread.addFilter(defaultFilter);
        			// Now request to connect
        			RequestLogin character_login = new RequestLogin (telnetHelper, dbHelper, character);
    				mainThread.addFilter(character_login);
    				// Startup
    				mainThread.start();
   				}
    			break;
    		case R.id.itemsystemmenu:
    			ListBoxSystemMenu system_menu = new ListBoxSystemMenu(dbHelper, "System Menu", this);
    			system_menu.setOnDismissListener(this);
    			system_menu.show();
    			break;
    		default:
    			return super.onOptionsItemSelected(item);
    	}
    	return true;
    } 

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
			case R.id.activecharacter:
    			LensClientSavedState savedState = LensClientSavedState.GetSavedState(dbHelper);
    			Character character = Character.GetCharacter(dbHelper, savedState.getSavedCharacterName(), savedState.getSavedWorldName());
				RequestCharacterInformation char_info = new RequestCharacterInformation (telnetHelper, dbHelper, character);
				mainThread.addFilter(char_info);
				char_info.OutboundRequest();
				break;
			case R.id.button:
				Editable inputtext = inputbox.getEditableText();
				telnetHelper.addOutputString(inputtext.toString());
				inputbox.setText("");
				inputbox.clearFocus();
				break;
		}
	}
	
	public void disconnect() {
		try {
			telnetHelper.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		activecharacter.invalidate();
	}
}
