package pduMeter;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.InputStream;
import java.io.PrintStream;
/*
telnet shell for APC outlet with ethenet connection, here default setting of APC outlet is used
 */

public class AutomatedTelnetClient {
	private TelnetClient telnet = new TelnetClient();
	private InputStream in;
	private PrintStream out;
	private String prompt = "apc>";

	public AutomatedTelnetClient(String server, String user, String password) {
		try {
			// Connect to the specified server
			telnet.connect(server, 23);
			// Get input and output stream references
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());
			// Log the user on
			readUntil("User Name : ");//this is default
			write(user);
			readUntil("Password  : ");//this is default
			write(password);
			
			// Advance to a prompt
			readUntil(prompt + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public String readUntil(String pattern) {
		try {
			char lastChar = pattern.charAt(pattern.length() - 1);
			StringBuffer sb = new StringBuffer();
			char ch = (char) in.read();
			while (true) {
				//System.out.print(ch);
				sb.append(ch);
				if (ch == lastChar) {
					if (sb.toString().endsWith(pattern)) {
						return sb.toString();
					}
				}
				ch = (char) in.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void write(String value) {
		try {
			out.println(value);
			out.flush();
			//System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String sendCommand(String command) {
		try {
			write(command);
			return readUntil(prompt + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void disconnect() {
		try {
			telnet.disconnect();
			System.out.println("PDU finish!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
