package client.lensmoor.com;

import java.util.Calendar;

public class ChannelMessage extends LensClientSerializedObject {
	EnumChannel channel;
	int time;
	String message;
	
	ChannelMessage(LensClientDBHelper dbHelper, EnumChannel channel) {
		super(dbHelper);
		this.channel = channel;
		Calendar calendar = Calendar.getInstance();
		setDateCreated(calendar.get(Calendar.YEAR) * 10000 + calendar.get(Calendar.MONTH) * 100 + calendar.get(Calendar.DAY_OF_MONTH));
		time = calendar.get(Calendar.HOUR_OF_DAY) * 10000 + calendar.get(Calendar.MINUTE) * 100 + calendar.get(Calendar.SECOND);
	}

	public EnumChannel getChannel() { return channel; }
	public int getTime() { return time; }
	public String getMessage() { return message; }
	
	public void setChannel(EnumChannel channel) { this.channel = channel; }
	public void setTime(int time) { this.time = time; }
	public void setMessage(String message) { this.message = message; }
	
	public String getFormattedMessage() {
		String output_string;
		int hour = time/10000;
		int minute = (time - (hour * 10000))/100;

		if (hour > 12) {
			hour = hour - 12;
		}
		output_string = "[" + channel.toString() + "@" + Integer.toString(hour) + ":" + Integer.toString(minute) + "] " + message;
		return output_string;
	}
}
