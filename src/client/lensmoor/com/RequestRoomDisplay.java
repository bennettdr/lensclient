package client.lensmoor.com;

public class RequestRoomDisplay extends Request {
	static final int DEFAULTDISPLAY_PARSER_SIZE = 0;
	static final int STATE_TITLE = 0;
	static final int STATE_EMPTY_LINE = 1;
	static final int STATE_DESCRIPTION = 2;
	static final int STATE_2ND_EMPTY_LINE = 3;
	static final int STATE_EXITS = 4;
	static final int STATE_OBJECTS = 5;
	static final int STATE_MOBILES = 6;

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
				room.setDescription(input_line);
				currentState++;
				break;
			case STATE_EMPTY_LINE:
				currentState++;
				break;
			case STATE_DESCRIPTION:
				stripped_input_line = stripAnsiCodes(input_line);
				if(stripped_input_line.charAt(0) == '\n') {
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
				} else if ((stripped_input_line.charAt(0) == ' ') &&
						(stripped_input_line.charAt(1) == '(')) {
					room.setMobiles(input_line);
					currentState++;
				} else {
					currentState = 0;
					input_buffer.insertString(input_line);
					return false;
				}
				break;
			case STATE_MOBILES:
				stripped_input_line = stripAnsiCodes(input_line);
				if ((stripped_input_line.charAt(0) == ' ') &&
						(input_line.charAt(1) == '(')) {
					room.setMobiles(input_line);
				} else {
					currentState = 0;
					input_buffer.insertString(input_line);
					messageLoopHandler.obtainMessage(MessageHandlerLoop.CHANGEROOMMESSAGE, room);
					return false;
				}
				break;
		}
		return true;
	}

	@Override
	void OutboundRequest() {
		super.OutboundRequest();
	}
}
