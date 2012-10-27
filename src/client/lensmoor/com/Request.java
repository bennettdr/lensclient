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
	
	@Override
	public boolean removeFilter() {
		return complete;
	}
}
