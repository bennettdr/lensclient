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

		newArray = new InterfaceInboundFilter [numberOfFilters];

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
			read_string = telnetHelper.readOutputString();
			if(read_string.length() > 0) {
				messageLoopHandler.sendMessage(messageLoopHandler.obtainMessage(MessageHandlerLoop.OUTPUTMESSAGE, read_string));
			} else {
				read_string = telnetHelper.readInputString();
				if(read_string.length() > 0) {
					messageLoopHandler.sendMessage(messageLoopHandler.obtainMessage(MessageHandlerLoop.INPUTMESSAGE, read_string));
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
		int i;
		String current_string;
		RollingBuffer replacement_buffer;

		while(!buffer.isEmpty()) {
			current_string = buffer.readString();
			i = numberOfFilters;
			while((i > 0) && (current_string.length() > 0)) {
				i--;
				replacement_buffer = filters[i].parseLine(telnetHelper, current_string);
				if(filters[i].removeFilter()) {
					removeFilter(i);				
				}
				processBufferLine(replacement_buffer);
			}
		}
	}
}
