package client.lensmoor.com;

public interface InterfaceInboundFilter {
	public boolean parseLine(LensClientTelnetHelper telnetHelper, RollingBuffer input_buffer);
	public boolean removeFilter();
}
