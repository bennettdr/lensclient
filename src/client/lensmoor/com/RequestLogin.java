package client.lensmoor.com;

public class RequestLogin extends Request {
	static final int LOGIN_NAME_LINE = 0;
	static final int LOGIN_INVALIDNAME_LINE = LOGIN_NAME_LINE + 1;
	static final int LOGIN_PASSWORD_LINE = LOGIN_INVALIDNAME_LINE + 1;
	static final int LOGIN_INVALIDPASSWORD_LINE = LOGIN_PASSWORD_LINE + 1;
	static final int LOGIN_SUCCESS_LINE = LOGIN_INVALIDPASSWORD_LINE + 1;
	static final int LOGIN_PARSER_SIZE = LOGIN_SUCCESS_LINE + 1; 
	
	private ParseMatch parser[] = {
		new ParseMatch(LOGIN_NAME_LINE, "What is your name?", EnumParseType.WHITESPACE),
		new ParseMatch(LOGIN_INVALIDNAME_LINE, "Is your chosen name acceptable by the standards above", EnumParseType.WHITESPACE),
		new ParseMatch(LOGIN_PASSWORD_LINE, "Password:", EnumParseType.WHITESPACE),
		new ParseMatch(LOGIN_INVALIDPASSWORD_LINE, "Wrong password.", EnumParseType.WHITESPACE),
		new ParseMatch(LOGIN_SUCCESS_LINE, "Welcome back", EnumParseType.WHITESPACE)
	};

	private Character character;

	public RequestLogin (LensClientTelnetHelper telnetHelper, Character character) {
		super(telnetHelper);
		this.character = character;
		parser[LOGIN_NAME_LINE].setMatchFragment();
		parser[LOGIN_PASSWORD_LINE].setMatchFragment();
		setParser(parser, LOGIN_PARSER_SIZE);
	}



	@Override
	public boolean parseLine(LensClientTelnetHelper telnetHelper, RollingBuffer buffer) {
		String input_line;
		int lineCode;

		if(buffer.isEmpty()) {
			input_line = buffer.peekString();
			if(input_line.length() > 0 ) {
				lineCode = matchFragment(input_line);
			} else {
				lineCode = LOGIN_PARSER_SIZE;
			}
		} else {
			input_line = buffer.readString();
			lineCode = matchLine(input_line);
		}
		switch (lineCode) {
			case LOGIN_NAME_LINE:
				input_line = buffer.readString(true);
				telnetHelper.addOutputString(character.getCharacterName());
				break;
			case LOGIN_PASSWORD_LINE:
				input_line = buffer.readString(true);
				telnetHelper.addOutputString(character.getPassword());
				break;
			case LOGIN_INVALIDNAME_LINE:
				LogWriter.write(EnumLogType.SYSTEM, "Invalid Character");
				break;				
			case LOGIN_INVALIDPASSWORD_LINE:
				LogWriter.write(EnumLogType.SYSTEM, "Invalid Password");
				break;				
			case LOGIN_SUCCESS_LINE:
				setComplete();
				break;				
			default:
				break;				
		}
		return true;
	}

	@Override
	void OutboundRequest() {
	}

}
