package client.lensmoor.com;

import java.util.concurrent.TimeUnit;

import android.util.Log;

public class TelnetTransmitter implements Runnable {
	LensClientTelnetHelper telnetHelper;

	public TelnetTransmitter(LensClientTelnetHelper telnet_helper) {
		telnetHelper = telnet_helper;
	}

	@Override
	public void run() {
		String next_command;
		// TODO Auto-generated method stub
		while (true)
		{
			next_command = telnetHelper.readOutputString();
			if(next_command.length() == 0) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				LogWriter.write(EnumLogType.TELNET, next_command);
				Log.i("Sent: ", next_command);
				telnetHelper.write(next_command);
				telnetHelper.write("\n");
			}
		}
	}
}

