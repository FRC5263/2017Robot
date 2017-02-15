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
	int counter = 0;
	public CameraMonitor(CameraMan cameraMan) {
		//TODO: save the listener, register for NetworkTable's notifications when updates
		//happen, and call back on the listener when updates are received
		this.cameraMan = cameraMan;
	}
	
	@SuppressWarnings("null")
	public void periodicFunction() {
		//TODO: check that the camera is actively streaming and we're getting live telemetry from GRIP.
		if (counter < 1){
			counter++;
			Camera.setExposureManual(20);
			System.out.println(counter);
		}
		double[] areas = table.getNumberArray("area", (double[])null);
		double[] centerXS = table.getNumberArray("centerX", (double[])null);
		double[] centerYS = table.getNumberArray("centerY", (double[])null);
		double[] widthS = table.getNumberArray("width", (double[])null);
		for (double area : areas){
			System.out.println("AREA: " + area);
		}
		for (double centerX : centerXS){
			System.out.println("CENTERX: " + centerX);
		}
		for (double centerY : centerYS)
		{
			System.out.println("CENTERY: " + centerY);
		}
		for (double width: widthS)
		{
			System.out.println("Width: " + width);
		}
		
	}
	public void CameraInit (){
		Camera = CameraServer.getInstance().startAutomaticCapture();
		table = NetworkTable.getTable("GRIP/myContoursReport");
		int inches = 18;
		int feet = (int) 1.5;
		inches  = feet;
		double [] defaultValue = new double[0];
//		while(true){
//			double [] areas = table.getNumberArray("area", defaultValue);
//			System.out.println("Areas: ");
//			for(double area : areas){
//				System.out.println(area + " ");
//			}
//			System.out.println();
//			Timer.delay(1);
//		}
		
		
	}
	
}
