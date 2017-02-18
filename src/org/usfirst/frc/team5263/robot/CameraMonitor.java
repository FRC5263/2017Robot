package org.usfirst.frc.team5263.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

/**
 * This class starts up the camera, sets appropriate camera settings,
 * and watches for telemetry broadcast by GRIP. When said telemetry is received,
 * CameraMonitor provides the data to a listener to be used.
 */
public class CameraMonitor {
	NetworkTable table;
	CameraMan cameraMan;
	UsbCamera Camera;
	
	double[] areas;
	double[] centerXS;
	double[] centerYS;
	double[] widthS;
	double[] centerXarray;
	double[] widtharray;
	boolean visible;
	int counter = 0;
	
	public CameraMonitor(CameraMan cameraMan) {
		//TODO: save the listener, register for NetworkTable's notifications when updates
		//happen, and call back on the listener when updates are received
		this.cameraMan = cameraMan;
		
	}
	
	
	@SuppressWarnings("null")
	public void periodicFunction() {
		
		
		
		areas = table.getNumberArray("area", (double[])null);
		centerXS = table.getNumberArray("centerX", (double[])null);
		centerYS = table.getNumberArray("centerY", (double[])null);
		widthS = table.getNumberArray("width", (double[])null);
		centerXarray = new double[1];
		widtharray = new double[1];
		
		
		//TODO: check that the camera is actively streaming and we're getting live telemetry from GRIP.
		if (counter == 50){
			try {
				Camera.setExposureManual(5);
			}catch(Exception E){
				System.out.println("Setting Manual Exposure fuction failed");
			}
			try{
				Camera.setResolution(160, 120);
				Camera.setFPS(15);
			}catch(Exception E){
				System.out.println("FPS & RESOLUTION FAILED");
			}
		}

//		for (double area : areas){
//			//System.out.println("AREA: " + area);
//		}
//		for (double centerX : centerXS){
//			//System.out.println("CENTERX: " + centerX);
//		}
//		for (double centerY : centerYS)
//		{
//			//System.out.println("CENTERY: " + centerY);
//		}
//		for (double width: widthS)
//		{
//			//System.out.println("Width: " + width);
//		}
			
			centerXarray[0] = 109.0;
			widtharray[0] = 32.0;
			
			//array1[1] = 53.0;
			if (centerXS.length <= 0){
				//System.out.println("Cannot see a vision target");
				visible = false;
			}else{
	 		//System.out.println("Can see a vision target");
	 			visible = true;
				
			}
			
		
		counter++;
	}
	public void CameraInit (){
		Camera = CameraServer.getInstance().startAutomaticCapture();
		table = NetworkTable.getTable("GRIP/myContoursReport");
		int inches = 18;
		int feet = (int) 1.5;
		inches  = feet;
		double [] defaultValue = new double[0];
		
	}
	
}
