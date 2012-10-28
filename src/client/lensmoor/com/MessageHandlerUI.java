package client.lensmoor.com;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableString;
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
		String tokenized_fields[];
		SpannableString  spannable_fragment;
		Object span;
		int offset;

		message_string = msg.obj.toString();
		// Handle Ansi Coding
		tokenized_fields = message_string.split("\\e\\[\\d*\\;?\\d+m");
		for(int i = 0; i < tokenized_fields.length; i++) {
			spannable_fragment = new SpannableString(tokenized_fields[i]);
			//spannable_fragment.setSpan(what, 0, tokenized_fields[0].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			outputBox.append(spannable_fragment);
		}
	}
	
	private void spanAnsiCodes(String inputString, int offset) {
		// Ansi Codes are Esc, [, number, :, number, number?, m, ;
	}
	
	
}
