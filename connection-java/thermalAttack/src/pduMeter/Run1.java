package pduMeter;

public class Run1 {
	public static void main(String[] args) {
		String filePath = "D:/thermalAttackData/Jan31/";
		
		double totalHour = 0.5;
		int totalN = (int)(3600*totalHour);
		int iteration = 2;
		MyPduThread m0 = new MyPduThread("169.235.14.147", filePath+"PDU-"+iteration+".csv", totalN);
		m0.start();
	}
}
