package client.lensmoor.com;

import android.os.Message;
import android.os.Bundle;

public class RequestRoomDisplay extends Request {
	static final int DEFAULTDISPLAY_PARSER_SIZE = 0;
	static final int STATE_TITLE = 0;
	static final int STATE_EMPTY_LINE = STATE_TITLE + 1;
	static final int STATE_DESCRIPTION = STATE_EMPTY_LINE + 1;
	static final int STATE_2ND_EMPTY_LINE = STATE_DESCRIPTION + 1;
	static final int STATE_EXITS = STATE_2ND_EMPTY_LINE + 1;
	static final int STATE_OBJECTS = STATE_EXITS + 1;
	static final int STATE_MOBILES = STATE_OBJECTS + 1;
	static final int STATE_DONE = STATE_MOBILES + 1;

	private boolean inNewRoom;
	private int currentState;
	private Room room;
	
	MessageHandlerLoop messageLoopHandler;
	
	public RequestRoomDisplay (LensClientTelnetHelper telnetHelper, LensClientDBHelper dbHelper, MessageHandlerLoop messageLoopHandler) {
		super(telnetHelper, dbHelper);
		this.messageLoopHandler = messageLoopHandler; 
		setParser(null, DEFAULTDISPLAY_PARSER_SIZE);
		inNewRoom = true;
		currentState = STATE_TITLE;
	}


	@Override
	public boolean parseLine(LensClientTelnetHelper telnetHelper, RollingBuffer input_buffer) {
		String input_line;
		String stripped_input_line;

		// If not looking for room description, return no match, consume no lines;
		if(!inNewRoom || input_buffer.isEmpty()) {
			return false;
		}

		input_line = input_buffer.readString();
		
		switch(currentState) {
			case STATE_TITLE:
				room = new Room();
				room.setTitle(input_line);
				currentState++;
				break;
			case STATE_EMPTY_LINE:
				currentState++;
				break;
			case STATE_DESCRIPTION:
				stripped_input_line = stripAnsiCodes(input_line);
				if((stripped_input_line.length() == 0) || (stripped_input_line.charAt(0) == '\n')) {
					currentState++;
					currentState++;
				} else {
					room.setDescription(input_line);
				}
				break;
			case STATE_EXITS:
				room.setExits(input_line);
				currentState++;
				break;
			case STATE_OBJECTS:
				stripped_input_line = stripAnsiCodes(input_line);
				if((stripped_input_line.charAt(0) == ' ') &&
						(stripped_input_line.charAt(1) == ' ') &&
						(stripped_input_line.charAt(2) == ' ') &&
						(stripped_input_line.charAt(3) == ' ') &&
						(stripped_input_line.charAt(4) == ' ')) {
					room.setObjects(input_line);
				} else if (stripped_input_line.charAt(0) == '(') {
					room.setMobiles(input_line);
					currentState++;
				} else {
					currentState = STATE_DONE;
				}
				break;
			case STATE_MOBILES:
				stripped_input_line = stripAnsiCodes(input_line);
				if (stripped_input_line.charAt(0) == '(') {
					room.setMobiles(input_line);
				} else {
					currentState = STATE_DONE;
				}
				break;
		}

		if (currentState == STATE_DONE) {
			// Push back extra line onto buffer
			input_buffer.insertString(input_line);
			// Tell display to paint room
			Bundle room_data = new Bundle();
			room_data.putParcelable("client.lensmoor.com.Room", room);
			Message msg =  messageLoopHandler.obtainMessage(MessageHandlerLoop.CHANGEROOMMESSAGE);
			msg.setData(room_data);
			messageLoopHandler.sendMessage(msg);
			// Remove this filter (return true will remove this filer)
			setComplete();			
		}
		return true;
	}

	@Override
	void OutboundRequest() {
		super.OutboundRequest();
	}
}
