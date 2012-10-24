package client.lensmoor.com;

public interface InterfaceInboundFilter {
	public RollingBuffer parseLine(LensClientTelnetHelper telnetHelper, String input_line);
	public boolean removeFilter();
}
