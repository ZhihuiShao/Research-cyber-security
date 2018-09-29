package iDrac;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyThread extends Thread {
	private String ip;
	private String username;
	private String password;
	private String outFileName;
	private int maxSampleNumber;

	public MyThread(String ip, String outFileName, int maxSampleNumbe) {
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.username = "root";
		this.password = "srenserver";
		this.outFileName = outFileName;
		this.maxSampleNumber = maxSampleNumbe;
	}

	@Override
	public void run() {
		System.out.println(ip + ": started!");
		// #2===execute
		String command = "racadm getsensorinfo";
		Shell shell = new Shell(ip, username, password);
		Date now = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String myTime = null;
		BufferedWriter fileOut = null;
		try {
			fileOut = new BufferedWriter(new FileWriter(outFileName, true));
			fileOut.write("Time\tBoardTemp\tCPUTemp\tSystemPower\tFanSpeed");
			fileOut.newLine();
			fileOut.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				fileOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int i = 0;

		while (i < maxSampleNumber) {
			System.out.print(i + ":\t");
			shell.execute(command,0);
			ArrayList<String> stdout = shell.getStandardOutput();
			now = new Date();
			myTime = sdf.format(now);
			System.out.print(myTime + "\t");
			String myBoardTemp = stdout.get(8).substring(38, 40);
			System.out.print(myBoardTemp + "\t");
			String myCPUTemp = stdout.get(11).substring(38, 40);
			System.out.print(myCPUTemp + "\t");
			String mySystemPower = stdout.get(51).substring(38, 40);
			System.out.print(mySystemPower + "\t");
			String myFanSpeed = "";

			for (int j = 0; j <= 9; j++) {
				myFanSpeed += stdout.get(j + 17).substring(44, 48);
				myFanSpeed += "\t";
			}
			// String myFanSpeed1 = stdout.get(17).substring(44,48);//17-26
			System.out.println(myFanSpeed);

			String dataResult = (myTime + "\t" + myBoardTemp + "\t" + myCPUTemp + "\t" + mySystemPower + "\t"
					+ myFanSpeed);
			// #3 save the dataResult into the out file

			try {
				fileOut = new BufferedWriter(new FileWriter(outFileName, true));
				// write to out
				fileOut.write(dataResult);
				fileOut.write("\r\n");
				fileOut.flush();
			} catch (IOException e) {
				System.out.println("Exception ");
			} finally {
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
		System.out.println(ip + ": closed!");

	}
}
