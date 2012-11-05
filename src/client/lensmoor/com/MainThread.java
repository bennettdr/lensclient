package client.lensmoor.com;

import java.util.concurrent.TimeUnit;

import android.os.Looper;
import android.os.Handler;
import android.os.HandlerThread;

public class MainThread extends Thread {
	LensClientTelnetHelper telnetHelper;
	Handler messageLoopHandler;
	Handler uiHandler;
	private InterfaceInboundFilter filters[];
	private boolean running;
	int numberOfFilters;
	
	public MainThread(LensClientTelnetHelper telnet_helper, Handler handler) {
		super("Main Thread");
		telnetHelper = telnet_helper;
		uiHandler = handler;
	}

	public void addFilter(InterfaceInboundFilter new_filter) {
		InterfaceInboundFilter newArray[];
		int i;

		newArray = new InterfaceInboundFilter [numberOfFilters + 1];

		for(i = 0; i < numberOfFilters; i++) {
			newArray[i] = filters[i];
		}
		newArray[i] = new_filter;
		filters = newArray;
		numberOfFilters++;
	}

	public void removeFilter(int filter_offset) {
		int i;

		for(i = filter_offset; i < numberOfFilters - 1; i++) {
			filters[i] = filters[i + 1];
		}
		numberOfFilters--;
		filters[i] = null;
	}

	public void run() {
		// Set up message handling
		String read_string;
		
		// Get a messenger thread and start it
		HandlerThread message_thread = new HandlerThread("Message Loop");
		message_thread.start();
		
		// Set the handler for the thread
		Looper message_loop = message_thread.getLooper();
		messageLoopHandler = new MessageHandlerLoop(message_loop, telnetHelper, uiHandler);
		
		// Set up Main thread
		setName("Main Thread");
		running = true;

		while(running) {
			if (!telnetHelper.isOutputEmpty()) {
				read_string = telnetHelper.readOutputString();
				messageLoopHandler.sendMessage(messageLoopHandler.obtainMessage(MessageHandlerLoop.OUTPUTMESSAGE, read_string));
			} else {
				if (!telnetHelper.isInputEmpty()) {
					processBufferLine(telnetHelper.getInputBuffer());
				} else {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void processBufferLine(RollingBuffer buffer) {
		while(!buffer.isEmpty()) {
			processThisBufferLine(buffer, false);
		}
		if (buffer.hasFragment()) {
			LogWriter.write(EnumLogType.DEBUG, buffer.peekString());
			LogWriter.write(EnumLogType.DEBUG, "\n");
			processThisBufferLine(buffer, true);
		}
	}

	private void processThisBufferLine(RollingBuffer buffer, boolean matchFragment) {
		int i = numberOfFilters;
		while(i > 0) {
			i--;
			if(filters[i].parseLine(telnetHelper, buffer)) {
				if(filters[i].removeFilter()) {
					removeFilter(i);				
				}
				i = 0;
			} else {
				if ((i == 0) && !buffer.isEmpty()) {
					messageLoopHandler.sendMessage(messageLoopHandler.obtainMessage(MessageHandlerLoop.INPUTMESSAGE, buffer.readString()));
				}
			}
		}
	}
}
