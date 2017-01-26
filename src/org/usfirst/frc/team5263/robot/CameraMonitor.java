package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * This class starts up the camera, sets appropriate camera settings,
 * and watches for telemetry broadcast by GRIP. When said telemetry is received,
 * CameraMonitor provides the data to a listener to be used.
 */
public class CameraMonitor {
	NetworkTable table;
	public CameraMonitor(ICameraTelemetryReceiver listener) {
		//TODO: save the listener, register for NetworkTable's notifications when updates
		//happen, and call back on the listener when updates are received
	}
	
	public boolean doPeriodicDiagnostics() {
		//TODO: check that the camera is actively streaming and we're getting live telemetry from GRIP.
		return false;
	}
	public void CameraInit (){
		CameraServer.getInstance().startAutomaticCapture();
		NetworkTable.getTable("GRIP/myContoursReport"); 
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
