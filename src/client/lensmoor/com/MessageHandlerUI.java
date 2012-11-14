package client.lensmoor.com;

// Java imports
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Android imports
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.BackgroundColorSpan;
import android.widget.TextView;
import android.widget.ScrollView;

public class MessageHandlerUI extends Handler {
	public static final int INPUTMESSAGE = 1;
	public static final int SCROLLMESSAGE = 2;

	private static final Pattern ansiMatch = Pattern.compile("\\e\\[(\\d+\\;)*\\d+?m");
	private static final Pattern numberMatch = Pattern.compile("\\d+");

	private ScrollView scrollView;
	private TextView outputBox;
	private boolean bold;
	private String lastAnsiCode = "\27[0;40;37m";

	public MessageHandlerUI(Looper looper, TextView output_box) {
		super(looper);
		outputBox = output_box;
		scrollView = (ScrollView)outputBox.getParent();
	}
	@Override
	public void handleMessage(Message msg) {
		String message_string;
		String tokenized_fields[];
		SpannableString  spannable_fragment;

		switch(msg.what) {
			case INPUTMESSAGE:
				message_string = msg.obj.toString();
				// Handle Ansi Coding
				tokenized_fields = message_string.split("\\e\\[(\\d+\\;)*\\d+?m");
				Matcher ansiParse = ansiMatch.matcher(message_string);

				for(int i = 0; i < tokenized_fields.length; i++) {
					if(tokenized_fields[i].length() > 0) {
						spannable_fragment = spanAnsiCodes(tokenized_fields[i], lastAnsiCode);
						outputBox.append(spannable_fragment);
					}
					if(ansiParse.find()) {
						lastAnsiCode = ansiParse.group();
						ansiParse.region(ansiParse.end(), message_string.length());
					}
				}
				// scroll screen
				sendMessage(obtainMessage(SCROLLMESSAGE));
				break;
			case SCROLLMESSAGE:
				scrollView.scrollTo(0, outputBox.getHeight());
				break;
		}
	}

	private SpannableString spanAnsiCodes(String text, String ansiCode) {
		Matcher numberParse;
		ForegroundColorSpan foregroundColor = null;
		BackgroundColorSpan backgroundColor = null;
		int color;
		String match_string;
		int parameter;
		char command;
		boolean background;
		SpannableString return_string;

		command = ansiCode.charAt(ansiCode.length() - 1);
		return_string = new SpannableString(text);

		numberParse = numberMatch.matcher(ansiCode);
		while(numberParse.find()) {
			match_string = numberParse.group();
			parameter = Integer.parseInt(match_string);
			numberParse.region(numberParse.end(), ansiCode.length());
			background = false;
			switch(command) {
				case 'm':
					switch(parameter) {
						case 0:
							bold = false;
							break;
						case 1:
							bold = true;
							break;
						case 40:
							// Background Black
							background = true;
						case 30:
							// Black
							if(bold) {
								color = Color.rgb(128, 128, 128);
							} else {
								color = Color.rgb(0, 0, 0);
							}
							if(background) {
								backgroundColor = new BackgroundColorSpan(color);
							} else {
								foregroundColor = new ForegroundColorSpan(color);
							}
							break;
						case 41:
							// Background Red
							background = true;
						case 31:
							// Red
							if(bold) {
								color = Color.rgb(255, 0, 0);
							} else {
								color = Color.rgb(128, 0, 0);
							}
							if(background) {
								backgroundColor = new BackgroundColorSpan(color);
							} else {
								foregroundColor = new ForegroundColorSpan(color);
							}
							break;
						case 42:
							// Background Green
							background = true;
						case 32:
							// Green
							if(bold) {
								color = Color.rgb(0, 255, 0);
							} else {
								color = Color.rgb(0, 128, 0);
							}
							if(background) {
								backgroundColor = new BackgroundColorSpan(color);
							} else {
								foregroundColor = new ForegroundColorSpan(color);
							}
							break;
						case 43:
							// Background Yellow
							background = true;
						case 33:
							// Yellow
							if(bold) {
								color = Color.rgb(255, 255, 0);
							} else {
								color = Color.rgb(128, 128, 0);
							}
							if(background) {
								backgroundColor = new BackgroundColorSpan(color);
							} else {
								foregroundColor = new ForegroundColorSpan(color);
							}
							break;
						case 44:
							// Background Blue
							background = true;
						case 34:
							// Blue
							if(bold) {
								color = Color.rgb(0, 0, 255);
							} else {
								color = Color.rgb(0, 0, 128);
							}
							if(background) {
								backgroundColor = new BackgroundColorSpan(color);
							} else {
								foregroundColor = new ForegroundColorSpan(color);
							}
							break;
						case 45:
							// Background Magenta
							background = true;
						case 35:
							// Magenta
							if(bold) {
								color = Color.rgb(255, 0, 255);
							} else {
								color = Color.rgb(128, 0, 128);
							}
							if(background) {
								backgroundColor = new BackgroundColorSpan(color);
							} else {
								foregroundColor = new ForegroundColorSpan(color);
							}
							break;
						case 46:
							// Background Cyan
							background = true;
						case 36:
							// Cyan
							if(bold) {
								color = Color.rgb(0, 255, 255);
							} else {
								color = Color.rgb(0, 128, 128);
							}
							if(background) {
								backgroundColor = new BackgroundColorSpan(color);
							} else {
								foregroundColor = new ForegroundColorSpan(color);
							}
							break;
						case 47:
							// Background White
							background = true;
						case 37:
							// White
							if(bold) {
								color = Color.rgb(192, 192, 192);
							} else {
								color = Color.rgb(255, 255, 255);
							}
							if(background) {
								backgroundColor = new BackgroundColorSpan(color);
							} else {
								foregroundColor = new ForegroundColorSpan(color);
							}
							break;
						default:
							break;
					}
					break;
			}
			if(background) {
				return_string.setSpan(backgroundColor, 0, return_string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else {
				return_string.setSpan(foregroundColor, 0, return_string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		return return_string;
	}
}
