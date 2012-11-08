package client.lensmoor.com;

import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

import android.os.Looper;
import android.os.Handler;
import android.os.HandlerThread;

public class MainThread extends Thread {
	private LensClientDBHelper dbHelper;
	private LensClientTelnetHelper telnetHelper;
	private Handler messageLoopHandler;
	private Handler uiHandler;
	private boolean running;
	private ArrayList<Request> pendingRequests;
	private ArrayList<InterfaceInboundFilter>activeFilters;
	
	public MainThread(LensClientDBHelper db_helper, LensClientTelnetHelper telnet_helper, Handler handler) {
		super("Main Thread");
		dbHelper = db_helper;
		telnetHelper = telnet_helper;
		uiHandler = handler;
		activeFilters = new ArrayList<InterfaceInboundFilter>();
		pendingRequests = new ArrayList<Request>();
	}

	public void addFilter(Request new_filter) {
		activeFilters.add(new_filter);
		new_filter.OutboundRequest();
	}

	public void removeFilter(int filter_offset) {
		activeFilters.remove(filter_offset);
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

		running = telnetHelper.start();
		startUpRequests();

		while(running) {
			if (!telnetHelper.isOutputEmpty()) {
				read_string = telnetHelper.readOutputString();
				messageLoopHandler.sendMessage(messageLoopHandler.obtainMessage(MessageHandlerLoop.OUTPUTMESSAGE, read_string));
			} else {
				if (!telnetHelper.isInputEmpty()) {
					processBufferLine(telnetHelper.getInputBuffer());
				} else {
					if (pendingRequests.size() > 0) {
						if (pendingRequests.get(0).isComplete()) {
							pendingRequests.remove(0);
						} else if (!pendingRequests.get(0).isStarted()) {
							addFilter(pendingRequests.get(0));
						}
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
	}
	
	private void startUpRequests() {
    	LensClientSavedState savedState;
    	Character character;

		savedState = LensClientSavedState.GetSavedState(dbHelper);
		character = Character.GetCharacter(dbHelper, savedState.getSavedCharacterName(), savedState.getSavedWorldName());

		// Make sure these are set so that the default filter is the lowest
		// Since its always running and it consumes all information
		RequestDefaultDisplay defaultFilter = new RequestDefaultDisplay(telnetHelper, dbHelper, uiHandler);
		addFilter(defaultFilter);
		// Now request to connect
		RequestLogin character_login = new RequestLogin (telnetHelper, dbHelper, character);
		pendingRequests.add(character_login);
		addFilter(character_login);

		RequestCharacterInformation char_info = new RequestCharacterInformation (telnetHelper, dbHelper, character);
		pendingRequests.add(char_info);
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
		int i = activeFilters.size();
		while(i > 0) {
			i--;
			if(activeFilters.get(i).parseLine(telnetHelper, buffer)) {
				if(activeFilters.get(i).removeFilter()) {
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
