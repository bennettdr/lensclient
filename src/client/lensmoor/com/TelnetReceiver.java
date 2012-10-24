package client.lensmoor.com;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import android.util.Log;

public class TelnetReceiver implements Runnable {
	private int char_recieved;
	LensClientTelnetHelper telnetHelper;

	public TelnetReceiver(LensClientTelnetHelper telnet_helper) {
		telnetHelper = telnet_helper;
	}
	
	@Override
	public void run() {
		char c;
		// TODO Auto-generated method stub
		try {
			while (true)
			{ 
				StringBuffer line_received = new StringBuffer();
				char_recieved = telnetHelper.read();
				c = (char)char_recieved;
				while ((char_recieved > 0) && (c != '\n') && (c != '\r')) {
					line_received .append(c);
					char_recieved = telnetHelper.read();
					c = (char)char_recieved;
				}
				if (char_recieved >= 0) {
					line_received .append(c);
				}
				telnetHelper.addInputString(line_received .toString());
				Log.i("Found: ", line_received.toString());

				if(char_recieved < 0) {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}



