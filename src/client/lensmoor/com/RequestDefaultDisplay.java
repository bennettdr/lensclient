package client.lensmoor.com;

public class RequestDefaultDisplay extends Request {
	static final int DEFAULTDISPLAY_PARSER_SIZE = 0;
	
	MessageHandlerLoop messageLoopHandler;
	
	public RequestDefaultDisplay (LensClientTelnetHelper telnetHelper, LensClientDBHelper dbHelper, MessageHandlerLoop messageLoopHandler) {
		super(telnetHelper, dbHelper);
		this.messageLoopHandler = messageLoopHandler; 
		setParser(null, DEFAULTDISPLAY_PARSER_SIZE);
	}

	@Override
	public boolean parseLine(LensClientTelnetHelper telnetHelper, RollingBuffer input_buffer) {
		String input_line;
		if(!input_buffer.isEmpty() || input_buffer.hasFragment()) {
			if(!input_buffer.isEmpty()) {
				input_line = input_buffer.readString();
			} else {
				input_line = input_buffer.readString(true);
			}
			messageLoopHandler.sendMessage(messageLoopHandler.obtainMessage(MessageHandlerLoop.INPUTMESSAGE, input_line));
		}
		return true;
	}

	@Override
	void OutboundRequest() {
		super.OutboundRequest();
	}

}
