package client.lensmoor.com;

import android.app.*;
import android.os.Bundle;
import android.content.Context;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DialogCharacter extends Dialog implements View.OnClickListener, DialogInterface.OnDismissListener {
	Context context;
	View view;
	LensClientDBHelper dbHelper;
	Character character;

	public DialogCharacter(LensClientDBHelper dbHelper, Context context, int theme, Character character) {
		super(context, theme);
		InitializeDialogCharacter(dbHelper, context, character);
	}

	public DialogCharacter(LensClientDBHelper dbHelper, Context context, Character character) {
		super(context);
		InitializeDialogCharacter(dbHelper, context, character);
	}
	
	public void InitializeDialogCharacter(LensClientDBHelper dbHelper, Context context, Character character) {
		this.context = context;
		this.dbHelper = dbHelper;
		if (character == null) {
			this.character = new Character(dbHelper);
			this.character.setIsNull();
		} else {
			this.character = character;			
		}
	}

	@Override	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.dialogcharacter, null, false);
		setContentView(view);
		getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

		if (!character.getIsNull()) {
			EditText nameEditText = (EditText)view.findViewById(R.id.characternametext);
			nameEditText.setText(character.getCharacterName());
			TextView worldTextView = (TextView)view.findViewById(R.id.characterworldtext);
			worldTextView.setText(character.getCharacterWorld());
			EditText passowrdEditText = (EditText)view.findViewById(R.id.characterpasswordtext);
			passowrdEditText.setText(character.getPassword());

			TextView levelTextView = (TextView)view.findViewById(R.id.characterlevelvalue);
			levelTextView.setText(Integer.toString(character.getLevel()));
			TextView sexTextView = (TextView)view.findViewById(R.id.charactersexvalue);
			sexTextView.setText(character.getSex().toString());
			TextView raceTextView = (TextView)view.findViewById(R.id.characterracevalue);
			raceTextView.setText(character.getRace().toString());
			
			TextView sizeTextView = (TextView)view.findViewById(R.id.charactersizevalue);
			sizeTextView.setText(character.getSize().toString());
			TextView specializeTextView = (TextView)view.findViewById(R.id.characterspecializevalue);
			specializeTextView.setText(character.getSpecialization().toString());
			TextView clanTextView = (TextView)view.findViewById(R.id.characterclanvalue);
			clanTextView.setText(character.getClan());

			TextView birthdayTextView = (TextView)view.findViewById(R.id.characterbirthdayvalue);
			birthdayTextView.setText(character.getBirthday().toString());

			TextView strengthTextView = (TextView)view.findViewById(R.id.characterstrengthvalue);
			strengthTextView.setText(Integer.toString(character.getAttribute(EnumAttribute.STRENGTH)));
			TextView practicesTextView = (TextView)view.findViewById(R.id.characterpracticesvalue);
			practicesTextView.setText(Integer.toString(character.getPractices()));

			TextView intelligenceTextView = (TextView)view.findViewById(R.id.characterintelligencevalue);
			intelligenceTextView.setText(Integer.toString(character.getAttribute(EnumAttribute.INTELLIGENCE)));
			TextView trainsTextView = (TextView)view.findViewById(R.id.charactertrainsvalue);
			trainsTextView.setText(Integer.toString(character.getTrains()));

			TextView wisdomTextView = (TextView)view.findViewById(R.id.characterwisdomvalue);
			wisdomTextView.setText(Integer.toString(character.getAttribute(EnumAttribute.WISDOM)));
			TextView alignTextView = (TextView)view.findViewById(R.id.characteralignvalue);
			alignTextView.setText(character.getAlign().toString());

			TextView dexterityTextView = (TextView)view.findViewById(R.id.characterdexterityvalue);
			dexterityTextView.setText(Integer.toString(character.getAttribute(EnumAttribute.DEXTERITY)));

			TextView constitutionTextView = (TextView)view.findViewById(R.id.characterconstitutionvalue);
			constitutionTextView.setText(Integer.toString(character.getAttribute(EnumAttribute.CONSTITUTION)));

			TextView charismaTextView = (TextView)view.findViewById(R.id.charactercharismavalue);
			charismaTextView.setText(Integer.toString(character.getAttribute(EnumAttribute.CHARISMA)));

			TextView toNextLevelTextView = (TextView)view.findViewById(R.id.charactertonextlevelvalue);
			toNextLevelTextView.setText(Integer.toString(character.getExperienceToLevel() * character.getLevel() - character.getExperience()));
			TextView experienceTextView = (TextView)view.findViewById(R.id.characterexperiencevalue);
			experienceTextView.setText(Integer.toString(character.getExperience()));
			
			TextView baseHPTextView = (TextView)view.findViewById(R.id.characterbasehpvalue);
			baseHPTextView.setText(Integer.toString(character.getBaseHitPoints()));
			TextView baseManaTextView = (TextView)view.findViewById(R.id.characterbasempvalue);
			baseManaTextView.setText(Integer.toString(character.getBaseManaPoints()));
			
			TextView ageTextView = (TextView)view.findViewById(R.id.characteragevalue);
			ageTextView.setText(Integer.toString(character.getAge()));
			TextView hometownTextView = (TextView)view.findViewById(R.id.characterhometownvalue);
			hometownTextView.setText(character.getHometown());

		}

		Button chooseWorldButton = (Button)view.findViewById(R.id.character_worldbutton);
		chooseWorldButton.setClickable(true);
		chooseWorldButton.setOnClickListener(this);

		Button saveButton = (Button)view.findViewById(R.id.character_savebutton);
		saveButton.setClickable(true);
		saveButton.setOnClickListener(this);

		Button cancelButton = (Button)view.findViewById(R.id.character_cancelbutton);
		cancelButton.setClickable(true);
		cancelButton.setOnClickListener(this);
		
		Button deleteButton = (Button)view.findViewById(R.id.character_deletebutton);
		deleteButton.setClickable(true);
		deleteButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View buttonView) {
		LensClientSavedState savedState;
		TextView worldText;
		
		switch(buttonView.getId()) {
			case R.id.character_cancelbutton:
				dismiss();
				break;
			case R.id.character_deletebutton:
				savedState = LensClientSavedState.GetSavedState(dbHelper);
				character.DeleteCharacter();
				savedState.setSavedWorldName("");
				savedState.setSavedCharacterName("");
				dbHelper.SaveState(savedState);
				dismiss();
				break;
			case R.id.character_worldbutton:
    			ListBoxWorld choose_world = new ListBoxWorld (dbHelper, false, "Choose World", context);
    			choose_world.setOnDismissListener(this);
    			choose_world.show();
				break;
			case R.id.character_savebutton:
				EditText nameEditText = (EditText)view.findViewById(R.id.characternametext);
				if(!character.getIsNull() && !character.getCharacterName().equals(nameEditText.getText().toString())) {
					character.DeleteCharacter();
					character.setIsNull();
				}
				character.setCharacterName(nameEditText.getText().toString());
				worldText = (TextView)view.findViewById(R.id.characterworldtext);
				character.setWorldName(worldText.getText().toString());
				EditText passwordEditText = (EditText)view.findViewById(R.id.characterpasswordtext);
				character.setPassword(passwordEditText.getText().toString());
				dbHelper.SaveCharacter(character);
		
				// Make default world
				savedState = LensClientSavedState.GetSavedState(dbHelper);
				savedState.setSavedCharacterName(character.getCharacterName());
				savedState.setSavedWorldName(character.getCharacterWorld());
				dbHelper.SaveState(savedState);
				dismiss();
				break;
		}
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		LensClientSavedState savedState = LensClientSavedState.GetSavedState(dbHelper);
		TextView worldText = (TextView)view.findViewById(R.id.characterworldtext);
		worldText.setText(savedState.getSavedWorldName());
		character.setCharacterName(savedState.getSavedWorldName());
		view.invalidate();
	}
}
