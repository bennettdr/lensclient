package client.lensmoor.com;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;

public class LensClientTelnetHelper {
	private TelnetClient telnet;
	private InputStream in;
	private PrintStream out;
	private String host = "75.126.141.202";
	private int port = 3500;

	private RollingBuffer inputBuffer;
	private RollingBuffer outputBuffer;

	public LensClientTelnetHelper(World world) {
		boolean connected = false;
		
		if(world.getURL().length() > 0) {
			connected = initializeTelnetClient(world.getURL(), world.getPort());			
		}
		if(!connected) {
			initializeTelnetClient(world.getIPAddress(), world.getPort());
		}
	}
	
	public LensClientTelnetHelper(String host_ip, int port_no) {
		initializeTelnetClient(host_ip, port_no);
	}

	public LensClientTelnetHelper() {
		initializeTelnetClient("", 0);
	}

	private boolean initializeTelnetClient(String host_ip, int port_no)
	{
		boolean connected = true;
		
		inputBuffer = new RollingBuffer();
		outputBuffer = new RollingBuffer();
		
		if (port_no > 0 ) {
			port = port_no;
		}
		if (host_ip.length() > 0) {
			host = host_ip;
		}
		telnet = new TelnetClient(); 
		try {
			telnet.connect(host, port);
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());
			telnet.setKeepAlive(true);
			Thread mInputThread = new Thread (new TelnetReceiver(this), "Telnet Receiver");
			Thread mOutputThread = new Thread (new TelnetTransmitter(this), "Telnet Transmitter");
			mInputThread.start();
			mOutputThread.start();
		} catch (Exception e) {
			connected = false;
			e.printStackTrace();
		}
		return (connected);
	}

	public int read() throws IOException { return in.read(); };
	public void write(String output_string) { out.println(output_string); out.println("\r"); out.flush(); }
	public void disconnect() throws IOException { in.close(); out.close(); telnet.disconnect(); };

	public void addInputString(String receivedString) { inputBuffer.writeString(receivedString); };
	public void addOutputString(String receivedString) { outputBuffer.writeString(receivedString); };
	public String readInputString() { return inputBuffer.readString(); };
	public String readOutputString() { return outputBuffer.readString(); };
}