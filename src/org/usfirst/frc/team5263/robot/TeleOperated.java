package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.Joystick;

public class TeleOperated {

	Sensing sensing;
	Manipulators manipulators;
	
	
	
	
	
	public TeleOperated(Sensing sensing, CameraMan cameraMan, CameraMonitor cameraMonitor, Manipulators manipulators) {

		this.sensing = sensing;
		this.manipulators = manipulators;
	
	}
	
	
	
	
	public void init(String mode) {
	}
	
	public void Periodic(){
		
	}
	
	//left_pow = main.getRawAxis(1);
	
	
	
	
	
}