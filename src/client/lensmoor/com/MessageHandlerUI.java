package client.lensmoor.com;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

public class MessageHandlerUI extends Handler {
	private TextView outputBox;

	public MessageHandlerUI(Looper looper, TextView output_box) {
		super(looper);
		outputBox = output_box;
	}
	@Override
	public void handleMessage(Message msg) {
		String message_string;
		
		message_string = msg.obj.toString();
		outputBox.append(message_string);
	}
	
}
