package client.lensmoor.com;

//Java Imports

//Android Imports
import android.app.*;
import android.os.Bundle;
import android.content.Context;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class DialogWorld extends Dialog implements View.OnClickListener {
	Context context;
	LensClientDBHelper dbHelper;
	World world;

	public DialogWorld(LensClientDBHelper dbHelper, Context context, int theme, World world) {
		super(context, theme);
		InitializeDialogWorld(dbHelper, context, world);

	}
	
	public DialogWorld(LensClientDBHelper dbHelper, Context context, World world) {
		super(context);
		InitializeDialogWorld(dbHelper, context, world);
	}
	
	public void InitializeDialogWorld(LensClientDBHelper dbHelper, Context context, World world) {
		this.context = context;
		this.dbHelper = dbHelper;
		if (world == null) {
			this.world = new World(dbHelper);
			this.world.setIsNull();
		} else {
			this.world = world;
		}
	}

	@Override	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialogworld, null, false);
		setContentView(view);
		getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

		if (!world.getIsNull()) {
			EditText nameEditText = (EditText)view.findViewById(R.id.worldnametext);
			nameEditText.setText(world.getWorldName());
			EditText urlEditText = (EditText)view.findViewById(R.id.worldurltext);
			urlEditText.setText(world.getURL());
			EditText ipEditText = (EditText)view.findViewById(R.id.worldiptext);
			ipEditText.setText(world.getIPAddress());
			EditText portEditText = (EditText)view.findViewById(R.id.worldporttext);
			portEditText.setText(Integer.toString(world.getPort()));
		}

		Button saveButton = (Button)view.findViewById(R.id.world_savebutton);
		saveButton.setClickable(true);
		saveButton.setOnClickListener(this);

		Button cancelButton = (Button)view.findViewById(R.id.world_cancelbutton);
		cancelButton.setClickable(true);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View buttonView) {
				dismiss();
			}
		});
		
		Button deleteButton = (Button)view.findViewById(R.id.world_deletebutton);
		deleteButton.setClickable(true);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View buttonView) {
				world.DeleteWorld();
				LensClientSavedState savedState = LensClientSavedState.GetSavedState(dbHelper);
				savedState.setSavedWorldName("");
				dbHelper.SaveState(savedState);
				dismiss();
			}
		});
	}


	@Override
	public void onClick(View buttonView) {
		LinearLayout worldView = (LinearLayout)buttonView.getParent();

		EditText nameEditText = (EditText)worldView.findViewById(R.id.worldnametext);
		if(!world.getWorldName().equals(nameEditText.getText().toString())) {
			world.DeleteWorld();
			world.setIsNull();
		}
		world.setWorldName(nameEditText.getText().toString());
		EditText urlEditText = (EditText)worldView.findViewById(R.id.worldurltext);
		world.setURL(urlEditText.getText().toString());
		EditText ipEditText = (EditText)worldView.findViewById(R.id.worldiptext);
		world.setIPAddress(ipEditText.getText().toString());
		EditText portEditText = (EditText)worldView.findViewById(R.id.worldporttext);
		String port_number = portEditText.getText().toString();
		if((port_number != null) && (port_number.length() > 0)) {
			world.setPort(Integer.parseInt(port_number));
		} else {
			world.setPort(0);
		}
		dbHelper.SaveWorld(world);
		
		// Make default world
		LensClientSavedState savedState = LensClientSavedState.GetSavedState(dbHelper);
		savedState.setSavedWorldName(world.getWorldName());
		dbHelper.SaveState(savedState);
		dismiss();
	}

}
