package client.lensmoor.com;

abstract class Request implements InterfaceInboundFilter {
	private LensClientTelnetHelper telnetHelper;
	private ParseMatch [] matches;
	private int numberMatches;
	private boolean complete;
	
	abstract void OutboundRequest();

	public Request (LensClientTelnetHelper telnetHelper) {
		this.telnetHelper = telnetHelper;
		this.numberMatches = 0;
		this.complete = false;
	}

	private String strip(String inputString, String regex) {
		String token_list[];
		String output_string;

		token_list = inputString.split(regex);
		output_string = token_list[0];
		for(int i = 1; i < token_list.length; i++) {
			output_string = output_string + token_list[i];
		}
		return(output_string);
	}

	public void setParser(ParseMatch [] matches, int numberMatches) {
		this.matches = matches;
		this.numberMatches = numberMatches;
	}

	public void write(String requestString) {
		telnetHelper.write(requestString);
	}
	
	public void setComplete() { complete = true; }

	public int matchLine(String line) {
		int match_number;

		for (match_number = 0; match_number < numberMatches; match_number++) {
			if (matches[match_number].isMatch(line)) {
				return matches[match_number].getLineCode();
			}
		}
		return (-1);
	}

	public int matchFragment(String fragment) {
		int match_number;

		for (match_number = 0; match_number < numberMatches; match_number++) {
			if (matches[match_number].isMatchFragment(fragment)) {
				return matches[match_number].getLineCode();
			}
		}
		return (-1);
	}

	public String getField(String line, int lineCode, int field_no) {
		return (getField(line, lineCode, field_no, 0, 0));
	}

	public String getField(String line, int lineCode, int field_no, int offset, int length) {
		int match_number;
		for (match_number = 0; (match_number < numberMatches) && (matches[match_number].getLineCode() != lineCode); match_number++);

		if(match_number < numberMatches) {
			return (matches[match_number].getField(line, field_no, offset, length));
		}
		return null;
	}

	public int getIntField(String line, int lineCode, int field_no) {
		return (getIntField(line, lineCode, field_no, 0, 0));
	}
	public int getIntField(String line, int lineCode, int field_no, int offset, int length) {
		String field = getField(line, lineCode, field_no, offset, length);
		field = strip(field, "\\D+");
		return Integer.parseInt(field);
		
	}
	
	public String stripAnsiCodes(String inputString) {
		return strip(inputString, "\\e\\[\\d*\\;?\\d+m");
	}
	
	
	@Override
	public boolean removeFilter() {
		return complete;
	}
}
