package test;
import iDrac.*;
import pduMeter.*;

public class test01 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String filePath = "D:\\thermalAttackData\\Feb16\\";

//		String filePath = "D:\\testbed\\voltage_project\\power_data";
//		String filePath = "D:\\ffy\\";
		String filePath = "D:\\testbed\\covertChannel\\powerData\\";
		String iteration = "server02_06.01.18_2";
	    double totalHour = 2;
		//--- here is Di Chen's Modification---
//      String filePath = "C:\\Users\\Administrator\\Documents\\Vault111Storage\\PowerForms\\"; 
//		String iteration = "server03_20180605_server03_weknow04";
//      double totalHour = 1.0;
        //--- end of Di Chen's Modification---
		//setting for iDrac
//		int totalN = (int)(3600*totalHour/2);
//		iDracThread m0 = new iDracThread("169.235.14.59", filePath+"server04-"+iteration+".txt",totalN);
//		iDracThread m1 = new iDracThread("169.235.14.60", filePath+"server05-"+iteration+".txt",totalN);
//		iDracThread m2 = new iDracThread("169.235.14.61", filePath+"server06-"+iteration+".txt",totalN);
//		iDracThread m3 = new iDracThread("169.235.14.62", filePath+"server07-"+iteration+".txt",totalN);
		
		//setting for PDU
		int totalNumPDU = (int)(3600*totalHour);
//		MyPduThread mPDU = new MyPduThread("169.235.14.147", filePath+"PDU-"+iteration+".txt", totalNumPDU);
		MyPduThread mPDU = new MyPduThread("169.235.14.147", filePath+"PDU-"+iteration+".csv", totalNumPDU);

//		m0.start();
//		m1.start();
//		m2.start();
//		m3.start();
		mPDU.start();
	}

}
