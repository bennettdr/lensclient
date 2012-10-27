package client.lensmoor.com;

import java.nio.ByteBuffer;

public class ParseMatch {
	int lineCode;
	String matchString;
	EnumParseType parseType;
	int	startOffset;
	String delimiter;
	boolean matchFragment;

	ParseMatch(int lineCode, String matchString, EnumParseType parseType, int startOffset, String delimiter) {
		initParseMatch(lineCode, matchString, parseType, startOffset, delimiter);
	}
	
	ParseMatch(int lineCode, String matchString, EnumParseType parseType, int startOffset) {
		initParseMatch(lineCode, matchString, parseType, startOffset, null);
	}
	
	ParseMatch(int lineCode, String matchString, EnumParseType parseType) {
		initParseMatch(lineCode, matchString, parseType, 0, null);
	}
	
	public void initParseMatch(int lineCode, String matchString, EnumParseType parseType, int startOffset, String delimiter) {
		this.lineCode = lineCode;
		this.matchString = matchString;
		this.parseType = parseType; 		
		this.startOffset = startOffset;
		this.delimiter = delimiter;
		this.matchFragment = false;
	}
	public boolean isMatchFragment(String string) { return(matchFragment && isMatch(string)); }
	public boolean isMatch(String string) { return (string.indexOf(matchString, startOffset) == startOffset); }
	public int getLineCode() { return lineCode; }
	public void setMatchFragment() { matchFragment = true; }

	public String getField(String line, int fieldNumber, int offset, int length) {
		ByteBuffer buffer = ByteBuffer.allocate(length + 1); 
		String field;
		String tokenized_fields[];

		if (parseType == EnumParseType.FIXED) {
			line.getBytes(offset, offset + length, buffer.array(), 0);
			field = buffer.toString();
		} else if (parseType == EnumParseType.WHITESPACE) {
			tokenized_fields = line.split("\\s+");
			field = tokenized_fields[fieldNumber];
		} else {
			tokenized_fields = line.split(delimiter);
			field = tokenized_fields[fieldNumber];
		}
		return field;
	}
}
