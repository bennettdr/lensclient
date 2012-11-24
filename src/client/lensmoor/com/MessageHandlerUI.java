package client.lensmoor.com;

// Java imports
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Android imports
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.BackgroundColorSpan;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ScrollView;

public class MessageHandlerUI extends Handler {
	private static final Pattern ansiMatch = Pattern.compile("\\e\\[(\\d+\\;)*\\d+?m");
	private static final Pattern numberMatch = Pattern.compile("\\d+");
	private static final String defaultAnsiCode = "\27[0;40;37m";

	private LensClientActivity activity;
	private ScrollView scrollView;
	private TextView outputBox;
	private TextView roomName;
	private TextView roomDesription;
	private boolean bold;
	private String lastAnsiCode;
	private Button infoChannel;
	private Button questChannel;

	public MessageHandlerUI(Looper looper, LensClientActivity activity) {
		super(looper);
		this.activity = activity;
		
		// set up channels
		infoChannel = (Button)activity.findViewById(R.id.channelInfo);
		questChannel = (Button)activity.findViewById(R.id.channelQuest);
	
		outputBox = (TextView)activity.findViewById(R.id.outputbox);
		roomName = (TextView)activity.findViewById(R.id.currentroom);
		roomDesription = (TextView)activity.findViewById(R.id.currentroomdesc);
		scrollView = (ScrollView)outputBox.getParent();
		lastAnsiCode = defaultAnsiCode;
	}
	@Override
	public void handleMessage(Message msg) {
		String message_string;

		switch(msg.what) {
			case MessageHandlerLoop.INPUTMESSAGE:
				message_string = msg.obj.toString();
				// Handle Ansi Coding
				lastAnsiCode = addAnsiCompliantStringToView(outputBox, message_string, lastAnsiCode);
				// scroll screen
				sendMessage(obtainMessage(MessageHandlerLoop.SCROLLTEXTTOBOTTOMMESSAGE));
				break;
			case MessageHandlerLoop.SCROLLTEXTTOBOTTOMMESSAGE:
				scrollView.scrollTo(0, outputBox.getHeight());
				break;
			case MessageHandlerLoop.SCROLLCHANNELTOBOTTOMMESSAGE:
				if(activity != null) {
					scrollView.scrollTo(0, activity.getActiveChannel().getTextView().getHeight());
				}
				break;
			case MessageHandlerLoop.CHANGEROOMMESSAGE:
				String string;
				String lastAnsiCodeLocal;
				int i;

				Bundle room_data = msg.getData();
				Room room = (Room)room_data.getParcelable("client.lensmoor.com.Room");
				roomName.setText("");
				addAnsiCompliantStringToView(roomName, room.getTitle(), defaultAnsiCode);

				roomDesription.setText("");
				lastAnsiCodeLocal = defaultAnsiCode;
				for(i = 0; (string = room.getDescription(i)) != null; i++) {
					lastAnsiCodeLocal = addAnsiCompliantStringToView(roomDesription, string, lastAnsiCodeLocal);
				}

				
				lastAnsiCodeLocal = defaultAnsiCode;
				lastAnsiCodeLocal = addAnsiCompliantStringToView(outputBox, room.getExits(0), lastAnsiCodeLocal);
				for(i = 0; (string = room.getObjects(i)) != null; i++) {
					lastAnsiCodeLocal = addAnsiCompliantStringToView(outputBox, string, lastAnsiCodeLocal);
				}
				for(i = 0; (string = room.getMobiles(i)) != null; i++) {
					lastAnsiCodeLocal = addAnsiCompliantStringToView(outputBox, string, lastAnsiCodeLocal);
				}
				sendMessage(obtainMessage(MessageHandlerLoop.SCROLLTEXTTOBOTTOMMESSAGE));
				break;
			case MessageHandlerLoop.CHANNELMESSAGE:
				if(activity != null) {
					message_string = msg.obj.toString();
					lastAnsiCodeLocal = defaultAnsiCode;
					lastAnsiCodeLocal = addAnsiCompliantStringToView(activity.getActiveChannel().getTextView(), message_string, lastAnsiCodeLocal);
					sendMessage(obtainMessage(MessageHandlerLoop.SCROLLCHANNELTOBOTTOMMESSAGE));
				}
				break;
		}
	}

	private String addAnsiCompliantStringToView(TextView view, String string, String lastAnsiCode) {
		String tokenized_fields[];
		SpannableString  spannable_fragment;

		// Handle Ansi Coding
		tokenized_fields = string.split("\\e\\[(\\d+\\;)*\\d+?m");
		Matcher ansiParse = ansiMatch.matcher(string);

		for(int i = 0; i < tokenized_fields.length; i++) {
			if(tokenized_fields[i].length() > 0) {
				spannable_fragment = spanAnsiCodes(tokenized_fields[i], lastAnsiCode);
				view.append(spannable_fragment);
			}
			if(ansiParse.find()) {
				lastAnsiCode = ansiParse.group();
				ansiParse.region(ansiParse.end(), string.length());
			}
		}
		return lastAnsiCode;		
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
								color = activity.getResources().getColor(R.color.DKGREY);
							} else {
								color = activity.getResources().getColor(R.color.BLACK);
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
								color = activity.getResources().getColor(R.color.BRIGHTRED);
							} else {
								color = activity.getResources().getColor(R.color.RED);
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
								color = activity.getResources().getColor(R.color.BRIGHTGREEN);
							} else {
								color = activity.getResources().getColor(R.color.GREEN);
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
								color = activity.getResources().getColor(R.color.BRIGHTYELLOW);
							} else {
								color = activity.getResources().getColor(R.color.YELLOW);
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
								color = activity.getResources().getColor(R.color.BRIGHTBLUE);
							} else {
								color = activity.getResources().getColor(R.color.BLUE);
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
								color = activity.getResources().getColor(R.color.BRIGHTMAGENTA);
							} else {
								color = activity.getResources().getColor(R.color.MAGENTA);
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
								color = activity.getResources().getColor(R.color.BRIGHTCYAN);
							} else {
								color = activity.getResources().getColor(R.color.CYAN);
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
								color = activity.getResources().getColor(R.color.BRIGHTWHITE);
							} else {
								color = activity.getResources().getColor(R.color.WHITE);
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
