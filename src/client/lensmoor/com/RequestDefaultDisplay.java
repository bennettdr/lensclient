package client.lensmoor.com;

public class RequestDefaultDisplay extends Request {
	static final int DEFAULTDISPLAY_INFO_LINE = 0;
	static final int DEFAULTDISPLAY_QUEST_LINE = DEFAULTDISPLAY_INFO_LINE + 1;
	static final int DEFAULTDISPLAY_PARSER_SIZE = DEFAULTDISPLAY_QUEST_LINE + 1;

	private ParseMatch parser[] = {
			new ParseMatch(DEFAULTDISPLAY_INFO_LINE, "[INFO", EnumParseType.WHITESPACE),
			new ParseMatch(DEFAULTDISPLAY_QUEST_LINE, "[QUEST", EnumParseType.WHITESPACE)
	};
	
	MessageHandlerLoop messageLoopHandler;
	
	public RequestDefaultDisplay (LensClientTelnetHelper telnetHelper, LensClientDBHelper dbHelper, MessageHandlerLoop messageLoopHandler) {
		super(telnetHelper, dbHelper);
		this.messageLoopHandler = messageLoopHandler; 
		setParser(parser, DEFAULTDISPLAY_PARSER_SIZE);
	}

	@Override
	public boolean parseLine(LensClientTelnetHelper telnetHelper, RollingBuffer input_buffer) {
		String raw_input_line;
		String input_line;
		int lineCode = DEFAULTDISPLAY_PARSER_SIZE;
		
		if(!input_buffer.isEmpty() || input_buffer.hasFragment()) {
			if(!input_buffer.isEmpty()) {
				raw_input_line = input_buffer.readString();
			} else {
				raw_input_line = input_buffer.readString(true);
			}
			input_line = stripAnsiCodes(raw_input_line);
			lineCode = matchLine(input_line);

			switch(lineCode) {
				case DEFAULTDISPLAY_INFO_LINE:
					ChannelMessage info_message = new ChannelMessage(getDBHelper(), EnumChannel.INFO);
					info_message.setMessage(raw_input_line);
					getDBHelper().InsertChannelMessage(info_message);
					break;
				case DEFAULTDISPLAY_QUEST_LINE:
					ChannelMessage quest_message = new ChannelMessage(getDBHelper(), EnumChannel.QUEST);
					quest_message.setMessage(raw_input_line);
					getDBHelper().InsertChannelMessage(quest_message);
					break;
				default:
					messageLoopHandler.sendMessage(messageLoopHandler.obtainMessage(MessageHandlerLoop.INPUTMESSAGE, raw_input_line));
			}
		}
		return true;
	}

	@Override
	void OutboundRequest() {
		super.OutboundRequest();
	}

}
