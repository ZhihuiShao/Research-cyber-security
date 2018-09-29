package iDrac;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class iDracThread extends Thread{
	private String ip;
	private String username;
	private String password;
	private String outFileName;
	private int maxSampleNumber;
	public iDracThread(String ip, String outFileName, int maxSampleNumbe) {
		// TODO Auto-generated constructor stub
		this.ip=ip;
		this.username = "root";
		this.password = "srenserver";
		this.outFileName = outFileName;
		this.maxSampleNumber = maxSampleNumbe;
	}
	@Override
	public void run(){
		System.out.println(ip+": started!");
		//#2===execute
        String command = "racadm getsensorinfo";
        Shell shell = new Shell(ip,username,password);
        Date now = null;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	String myTime=null;
    	BufferedWriter fileOut=null;
    	try {
			fileOut = new BufferedWriter(new FileWriter(outFileName,true));
			fileOut.write("Time\tBoardTemp\tCPUTemp\tSystemPower\tFanSpeed");
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
    	int i=0;
    	
    	while(i<maxSampleNumber){
        shell.execute(command,0);
        ArrayList<String> stdout = shell.getStandardOutput();
        now = new Date();
        myTime = sdf.format(now);
        //new method to parse string via stream
        String bTemp = stdout.stream().filter(s -> s.startsWith("System Board Inlet Temp")).toArray(size -> new String[size])[0].split("Ok|C ")[1].trim();
		String CPUTemp = stdout.stream().filter(s -> s.startsWith("CPU1 Temp")).toArray(size -> new String[size])[0].split("Ok|C ")[1].trim();
		String bPower = stdout.stream().filter(s -> s.startsWith("System Board Pwr Consumption")).toArray(size -> new String[size])[0].split("Ok|Watts ")[1].trim();
		String[] FanSpeed = stdout.stream().filter(s -> s.startsWith("System Board Fan")).filter(s -> s.contains("RPM")).map(s -> s.split("Ok|RPM ")[1].trim()).toArray(size -> new String[size]);		
		String dataResult = myTime+"\t"+bTemp+"\t"+CPUTemp+"\t"+bPower+"\t"+String.join("\t", FanSpeed);
		System.out.println(dataResult);
        
		//#3 save the dataResult into the out file
        
        try {
    		fileOut = new BufferedWriter(new FileWriter(outFileName,true));
        //write to out
    			fileOut.write(dataResult); 
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
        i++;
    	}
    	shell.close();
    	System.out.println(ip+": closed!");
    	
    	
	}
}