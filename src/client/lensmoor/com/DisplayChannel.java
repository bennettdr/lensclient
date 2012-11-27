package client.lensmoor.com;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class DisplayChannel extends Dialog implements View.OnClickListener, OnEditorActionListener {
	private Context context;
	private LensClientDBHelper dbHelper;
	private LensClientTelnetHelper telnetHelper;
	private MessageHandlerUI uiHandler;
	private EnumChannel channel;
	private View view;

	public DisplayChannel(LensClientTelnetHelper telnetHelper, LensClientDBHelper dbHelper, MessageHandlerUI uiHandler, Context context, int theme, EnumChannel channel) {
		super(context, theme);
		InitDisplayChannel(telnetHelper, dbHelper, uiHandler, context, channel);
	}

	public DisplayChannel(LensClientTelnetHelper telnetHelper, LensClientDBHelper dbHelper, MessageHandlerUI uiHandler, Context context, EnumChannel channel) {
		super(context);
		InitDisplayChannel(telnetHelper, dbHelper, uiHandler, context, channel);
	}

	void InitDisplayChannel(LensClientTelnetHelper telnetHelper, LensClientDBHelper dbHelper, MessageHandlerUI uiHandler, Context context, EnumChannel channel) {
		this.context = context;
		this.channel = channel;
		this.dbHelper = dbHelper;
		this.telnetHelper = telnetHelper;
		this.uiHandler = uiHandler;
	}
	
	public TextView getTextView() { return (TextView)view.findViewById(R.id.channeloutputbox); }
	public EnumChannel getChannel() { return channel; }

	@Override	
	public void onCreate(Bundle savedInstanceState) {
		int i;
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.displaychannel, null, false);
		setContentView(view);

		if(channel.getChannelType() == EnumChannelType.GOSSIP) {
			EditText inputbox = (EditText)view.findViewById(R.id.channelinputbox);
			inputbox.setOnEditorActionListener(this);
		}
		ChannelMessage [] messageList = dbHelper.GetChannelMessageList(channel, telnetHelper.getWorldName());
		for (i = 0; i < messageList.length; i++) {
			uiHandler.sendMessage(uiHandler.obtainMessage(MessageHandlerLoop.CHANNELMESSAGE, messageList[i].getFormattedMessage()));
		}
		uiHandler.sendMessage(uiHandler.obtainMessage(MessageHandlerLoop.CHANNELMESSAGESEEN, channel.getInt(), 0));
	}

	@Override
	public void onClick(View v) {
		dismiss();		
	}
	
	@Override
	public boolean onEditorAction(TextView inputbox, int actionId, KeyEvent event) {
		if(actionId == EditorInfo.IME_ACTION_DONE) {
			Editable inputtext = inputbox.getEditableText();
			inputbox.setText("");
			InputMethodManager imm = (InputMethodManager)inputbox.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(inputbox.getWindowToken(), 0);
			telnetHelper.addOutputString(inputtext.toString());
			return true;
		}
		return false;
	}
}
