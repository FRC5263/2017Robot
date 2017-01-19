package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.RobotDrive;

public class AutoVirtualDriver {
	

	Sensing sensing;
	Manipulators manipulators;
	double rep = 0; //isaiah
	double angleOffset;
	double firstAngle = angleOffset;
	
	
	int turnDegrees = 90;
	public AutoVirtualDriver(Sensing sensing, CameraMan cameraMan, CameraMonitor cameraMonitor, Manipulators manipulators) {
		
		this.sensing = sensing;
		this.manipulators = manipulators; 
		angleOffset  = sensing.getGyroAngle();
		
	}
	
	public void init(String mode) {
		
	}
	
	public void periodicAuto() { 
		
		// Put custom auto code here
		// isaiah
		//double angleOfset = sensing.getGyroAngle();

		angleOffset = sensing.getGyroAngle();
		System.out.println("angle Offset: " + angleOffset);
		System.out.println("first angle: " + firstAngle);
		rep = rep + 1;
		System.out.println("counter: " + rep);
		double angle = angleOffset - firstAngle;
		if(rep == 1){
			angle = angle - angleOffset; 
		}
		
		if(angle > turnDegrees && rep < 500){
			manipulators.myRobot.tankDrive(0.6, -0.6); //try this
			System.out.println("angle smaller than 170 :" + angle );
		}else if(angle < turnDegrees && rep < 500){
			manipulators.myRobot.tankDrive(-0.6, 0.6); //maybe change
			System.out.println("angle larger than 190 :" + angle );
		}
		else{
			if(rep < 500){
				manipulators.myRobot.tankDrive(0.4, 0.4);
				System.out.println("driving straght");
			}
			
		}
		
			
		
		
	}
}
