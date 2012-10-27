package client.lensmoor.com;

import java.io.*;

import android.text.format.Time;
import android.util.Log;

public class LogWriter {
	static private File directory;
	static private File logfiles[] = {
		null,
		null,
		null};
	
	static void setLogInfo(File directory) {
		LogWriter.directory = directory;
	}
	
	static void setLogFileName (EnumLogType logType, String worldString, String characterString) {
		String fileName;
		String extension;
		
		switch(logType) {
			case TELNET:
				extension = ".log";
				break;
			case SYSTEM:
				extension = ".sys";
				break;
			case DEBUG:
				extension = ".dbg";
				break;
			default:
				extension = ".err";	
				break;
		}
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		fileName = worldString + today.format("%Y%m%d") + characterString + extension;
		logfiles[logType.getInt()] = new File(directory, fileName);
		logfiles[logType.getInt()].getParentFile().mkdirs();
	}

	static void write(EnumLogType logType, String output) {
		try {
			OutputStream logOutput = new FileOutputStream(logfiles[logType.getInt()].getAbsolutePath(), true);
			logOutput.write(output.getBytes(), 0, output.length());
			logOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
