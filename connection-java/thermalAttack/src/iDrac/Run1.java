package iDrac;
/*
test for iDracThread and VMThread
 */

public class Run1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath = "C:\\researchCode\\data\\test";
		double totalHour = 1.5;
		int totalN = (int)(3600*totalHour/2);
		String iteration = "1";
		VMThread m0 = new VMThread("169.235.14.134", filePath+"server02-"+iteration+".txt",totalN);
//		iDracThread m0 = new iDracThread("169.235.14.59", filePath+"server04-"+iteration+".txt",totalN);
//		iDracThread m1 = new iDracThread("169.235.14.60", filePath+"server05-"+iteration+".txt",totalN);
//		iDracThread m2 = new iDracThread("169.235.14.61", filePath+"server06-"+iteration+".txt",totalN);
//		iDracThread m3 = new iDracThread("169.235.14.62", filePath+"server07-"+iteration+".txt",totalN);
		//MyThread m5 = new MyThread("169.235.14.59", filePath+"r125-1.txt",20);
		m0.start();
//		m1.start();
//		m2.start();
//		m3.start();
		//m5.start();
	}

}

