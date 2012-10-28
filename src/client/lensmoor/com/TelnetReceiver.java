package client.lensmoor.com;

import java.io.IOException;

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
				char_recieved = telnetHelper.read();					
				c = (char)char_recieved;
				if (c == '\r') {
					// Suppress \r characters
				} else if (c == '\n') {
					telnetHelper.addInputStringFragment(java.lang.Character.toString(c), true);
				} else if (char_recieved > 0) {
					telnetHelper.addInputStringFragment(java.lang.Character.toString(c), false);
				}
				LogWriter.write(EnumLogType.TELNET, java.lang.Character.toString(c));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}



