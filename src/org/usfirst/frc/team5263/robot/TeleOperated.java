package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.Joystick;

public class TeleOperated {

	Joystick main;
	double left_pow;
	double right_pow;
	Sensing sensing;
	Manipulators manipulators;
	
	
	
	public TeleOperated(Sensing sensing, CameraMan cameraMan, CameraMonitor cameraMonitor, Manipulators manipulators) {

		this.sensing = sensing;
		this.manipulators = manipulators;

		main = new Joystick(0);
	}
	
	public void init(String mode) {
	}
	
	public void Periodic(){
		left_pow = -main.getRawAxis(1) + main.getRawAxis(4);
		right_pow = -main.getRawAxis(1) - main.getRawAxis(4);
		System.out.println("HI");
		manipulators.myRobot.tankDrive(left_pow, right_pow);
	}
	
	//left_pow = main.getRawAxis(1);
	
	
	
	
	
}