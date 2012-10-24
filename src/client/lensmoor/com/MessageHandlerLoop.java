package client.lensmoor.com;

import android.os.Looper;
import android.os.Handler;
import android.os.Message;

public class MessageHandlerLoop extends Handler {
	public static final int OUTPUTMESSAGE = 1;
	public static final int INPUTMESSAGE = 2;

	LensClientTelnetHelper telnetHelper;
	Handler uiHandler;
	
	public MessageHandlerLoop (Looper looper, LensClientTelnetHelper telnet_helper, Handler ui_handler) {
		super(looper);
		telnetHelper = telnet_helper;
		uiHandler = ui_handler;
	}

	@Override
	public void handleMessage(Message msg) {
		String message_string;
			
		message_string = msg.obj.toString();
		switch(msg.what) {
			case OUTPUTMESSAGE:
				telnetHelper.write(message_string);
				break;
			case INPUTMESSAGE:
				// Send the message to the screen
				uiHandler.sendMessage(uiHandler.obtainMessage(MessageHandlerLoop.INPUTMESSAGE, message_string));
				break;
		}
	}
}
