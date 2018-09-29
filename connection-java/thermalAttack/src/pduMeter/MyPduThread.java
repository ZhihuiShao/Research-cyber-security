package pduMeter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyPduThread extends Thread{
	private String ip;
	private String username;
	private String password;
	private String outFileName;
	private int maxSampleNumber;
	public MyPduThread(String ip,String outFileName, int maxSampleNumbe) {
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.username = "apc";
		this.password = "apc";
		this.outFileName = outFileName;
		this.maxSampleNumber = maxSampleNumbe;
		
	}
	@Override
	public void run(){
		Date now = null;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	String myTime=null;
		System.out.println("PDU: "+ip+" started!");
		String command = "Olreading 1-24 power";
		AutomatedTelnetClient telnet = new AutomatedTelnetClient(ip, username, password);
		System.out.println("Connection success!");
		// save the title
		BufferedWriter fileOut=null;
    		try {
			fileOut = new BufferedWriter(new FileWriter(outFileName,true));
			fileOut.write("Time,");
			for(int i=1;i<=24;++i){
				fileOut.write("Port"+i+",");
			}
			fileOut.newLine();
			fileOut.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally{
			try {
				fileOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int i=0;i<maxSampleNumber;i++){
			
			String result = telnet.sendCommand(command);
			System.out.print(i+":\t");
			String[] resultArray = result.split("\r\n");
			
			now = new Date();
	        myTime = sdf.format(now);
			String resultData = myTime+",";
			for(int j=1;j<=24;++j){
				String[] temp = resultArray[j+1].split(":|W");
				resultData += (temp[2].trim()+",");
			}
			System.out.println(resultData);
			try {
	    		fileOut = new BufferedWriter(new FileWriter(outFileName,true));
	        //write to out
	    			fileOut.write(resultData); 
	        		fileOut.write("\r\n");
	        		fileOut.flush();
	        }
	        catch (IOException e)
	        {
	        	System.out.println("Exception ");
	        }
	        finally {
				try {
					fileOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//sleep the thread
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		telnet.disconnect();
	}
}
