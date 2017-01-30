package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.Joystick;

public class TeleOperated {

	Joystick main;
	Joystick log1;
	Joystick log2;
	double left_pow;
	double right_pow;
	Sensing sensing;
	Manipulators manipulators;
	
	
	
	public TeleOperated(Sensing sensing, CameraMan cameraMan, CameraMonitor cameraMonitor, Manipulators manipulators) {


		this.sensing = sensing;
		this.manipulators = manipulators;
		
		//manipulators.myRobot.tankDrive(, 0.5);
		
	}
	
	public void init(String mode) {
		main = new Joystick(0);
		log1 = new Joystick(0);
		log2 = new Joystick(2);
	}
	
	//left_pow = main.getRawAxis(1);
	
	
	
	
	
}