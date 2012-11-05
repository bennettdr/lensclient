package client.lensmoor.com;

import java.io.IOException;

public class TelnetReceiver implements Runnable {
	private final char ANSI_ESCAPE = '\u001b';
	private final char ANSI_BRACKET = '[';
	private final char SUPPRESS = '\r';
	private final int ANSISTATE_NOSTATE = 0;
	private final int ANSISTATE_ESCAPESEEN = 1;
	private final int ANSISTATE_BRACKETSEEN = 2;
	private int char_recieved;
	LensClientTelnetHelper telnetHelper;
	StringBuffer ansiCode;
	int ansiState = 0;
	boolean ansiTransmitCode = false;

	public TelnetReceiver(LensClientTelnetHelper telnet_helper) {
		telnetHelper = telnet_helper;
		ansiCode = new StringBuffer();
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
				c = ansiCoding(c);
				if (ansiTransmitCode) {
					if(ansiCode.toString().charAt(ansiCode.toString().length() - 1) == '\n') {
						telnetHelper.addInputStringFragment(ansiCode.toString(), true);
					} else {
						telnetHelper.addInputStringFragment(ansiCode.toString(), false);
					}
					ansiCode.delete(0, ansiCode.length());
					ansiTransmitCode = false;
				}
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

	char ansiCoding(char c) {
		if ((ansiState == ANSISTATE_NOSTATE) && (c != ANSI_ESCAPE)) {
			// Do not start escape processing
			return c;
		} else if ((ansiState == ANSISTATE_NOSTATE) && (c == ANSI_ESCAPE)) {
			// Start escape processing - seen escape
			ansiState = ANSISTATE_ESCAPESEEN;		
		} else if ((ansiState == ANSISTATE_ESCAPESEEN) && (c == ANSI_BRACKET)) {
			// Continue escape processing - seen escape and bracket
			ansiState = ANSISTATE_BRACKETSEEN;
		} else if ((ansiState == ANSISTATE_BRACKETSEEN) && (((c >= '0') && (c <= '9')) || (c != ';'))) {
			// Continue escape processing - seen escape and bracket and numbers or semi-colons
			// Continue to add characters to ansi code string
		} else {
			// Fall out of ansi code loop, transmit ansicode string
			ansiState = ANSISTATE_NOSTATE;
			ansiTransmitCode = true;	
		}
		ansiCode.append(c);
		return SUPPRESS;
	}
}



