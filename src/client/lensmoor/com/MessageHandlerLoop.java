package client.lensmoor.com;

import android.os.Looper;
import android.os.Handler;
import android.os.Message;

public class MessageHandlerLoop extends Handler {
	public static final int OUTPUTMESSAGE = 1;
	public static final int INPUTMESSAGE = 2;
	public static final int CHANGEROOMMESSAGE = 3;
	public static final int SCROLLTEXTTOBOTTOMMESSAGE = 4;
	public static final int CHANNELMESSAGE = 5;
	public static final int SCROLLCHANNELTOBOTTOMMESSAGE = 6;

	LensClientTelnetHelper telnetHelper;
	MessageHandlerUI uiHandler;
	
	public MessageHandlerLoop (Looper looper, LensClientTelnetHelper telnet_helper, MessageHandlerUI ui_handler) {
		super(looper);
		telnetHelper = telnet_helper;
		uiHandler = ui_handler;
	}

	@Override
	public void handleMessage(Message msg) {
		String message_string;
			
		switch(msg.what) {
			case OUTPUTMESSAGE:
				message_string = msg.obj.toString();
				telnetHelper.write(message_string);
				break;
			case INPUTMESSAGE:
			case CHANGEROOMMESSAGE:
			case CHANNELMESSAGE:
				// Send the message to the screen
				Message new_msg = uiHandler.obtainMessage(msg.what, msg.obj);
				new_msg.setData(msg.getData());
				uiHandler.sendMessage(new_msg);
				break;
		}
	}
}
