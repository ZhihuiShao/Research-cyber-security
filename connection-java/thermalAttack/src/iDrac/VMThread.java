package iDrac;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
/*
this VM thread implement how to interact with ubuntu in dell server
 */
public class VMThread extends Thread{
	private String ip;
	private String username;
	private String password;
	private String outFileName;
	private int maxSampleNumber;
	public VMThread(String ip, String outFileName, int maxSampleNumbe) {
		// TODO Auto-generated constructor stub
		this.ip=ip;
		this.username = "****";//replace with your username
		this.password = "*******";//replace with your password
		this.outFileName = outFileName;
		this.maxSampleNumber = maxSampleNumbe;
	}
	@Override
	public void run(){
		System.out.println(ip+": started!");
		//#2===execute
        String CPUcommand = "dmidecode --type processor | grep -i speed";//get processor information
        String RAMcommand = "dmidecode --type memory | grep -i speed";//get memory information
        String HDDcommand = "sudo hdparm -t /dev/sda";
        Shell shell = new Shell(ip,username,password);
        Date now = null;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//get local time
    	String myTime=null;
    	BufferedWriter fileOut=null;
//    	try {
//			fileOut = new BufferedWriter(new FileWriter(outFileName,true));
//			fileOut.write("Time\tBoardTemp\tCPUTemp\tSystemPower\tFanSpeed");
//			fileOut.newLine();
//			fileOut.flush();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		finally{
//			try {
//				fileOut.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
    	int i=0;
    	while(i<maxSampleNumber){
    	//test CPU
    	now = new Date();
        myTime = sdf.format(now);
        shell.execute(CPUcommand,0);
        ArrayList<String> CPUstdout = shell.getStandardOutput();
        String CPUspeed = CPUstdout.toString().split(":|MHz")[3].trim();
    	//test memory
        shell.execute(RAMcommand,0);
        ArrayList<String> RAMstdout = shell.getStandardOutput();
        String RAMspeed = RAMstdout.toString().split(":|MHz")[1].trim();
        //test HDD
//        shell.execute(HDDcommand,5);
//        ArrayList<String> HDDstdout = shell.getStandardOutput();
//        System.out.println(HDDstdout);
//        String HDDspeed = HDDstdout.toString().split("=|MB/sec")[1].trim();
//		String CPUTemp = stdout.stream().filter(s -> s.startsWith("CPU1 Temp")).toArray(size -> new String[size])[0].split("Ok|C ")[1].trim();
//		String bPower = stdout.stream().filter(s -> s.startsWith("System Board Pwr Consumption")).toArray(size -> new String[size])[0].split("Ok|Watts ")[1].trim();
//		String[] FanSpeed = stdout.stream().filter(s -> s.startsWith("System Board Fan")).filter(s -> s.contains("RPM")).map(s -> s.split("Ok|RPM ")[1].trim()).toArray(size -> new String[size]);		
		String dataResult = myTime+"\t"+CPUspeed+"\t"+RAMspeed;
		System.out.println(dataResult);
        
		//#3 save the dataResult into the out file
        
//        try {
//    		fileOut = new BufferedWriter(new FileWriter(outFileName,true));
//        //write to out
//    			fileOut.write(dataResult); 
//        		fileOut.write("\r\n");
//        		fileOut.flush();
//        }
//        catch (IOException e)
//        {
//        	System.out.println("Exception ");
//        }
//        finally {
//			try {
//				fileOut.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
        i++;
    	}
    	shell.close();
    	System.out.println(ip+": closed!");
    	
    	
	}
}