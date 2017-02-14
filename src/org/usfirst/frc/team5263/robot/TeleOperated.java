package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class TeleOperated {

	Joystick main;
	double left_pow;
	double right_pow;
	Sensing sensing;
	Manipulators manipulators;
	int buttonA = 1;
	int buttonB = 2;
	int buttonX = 3;
	int buttonY = 4;
	boolean buttonAtoggle = false;
	boolean buttonDisableA = false;
	int buttonBeenDisabledA;

	boolean isAutoFlywheel = false;

	public TeleOperated(Sensing sensing, CameraMan cameraMan, CameraMonitor cameraMonitor, Manipulators manipulators) {

		this.sensing = sensing;
		this.manipulators = manipulators;

		main = new Joystick(0);
	}

	public void init(String mode) {
	}

	public void Periodic() {
		left_pow = -main.getRawAxis(1) + main.getRawAxis(4);
		right_pow = -main.getRawAxis(1) - main.getRawAxis(4);

		manipulators.myRobot.tankDrive(left_pow * 0.5, right_pow * 0.5);
		/**
		 * if(main.getRawButton(buttonA)){ isAutoFlywheel = true;
		 * manipulators.flywheelSetPoint(1000);
		 * manipulators.flywheelEnabled(true); System.out.println("starting
		 * loop"); }else if(main.getRawButton(buttonX)){ isAutoFlywheel = false;
		 * manipulators.flywheelEnabled(false); System.out.println("Stopping
		 * loop"); }
		 **/
		
		if (main.getPOV() == 0){
			manipulators.climber.set(1);
		} else {
			manipulators.climber.set(0.0);
		}
		
		if (main.getRawButton(buttonA) && buttonDisableA == false) {
			buttonAtoggle = !buttonAtoggle;
			buttonDisableA = true;
		}

		if (buttonDisableA) {
			buttonBeenDisabledA++;
			if (buttonBeenDisabledA > 20) {
				buttonBeenDisabledA = 0;
				buttonDisableA = false;
			}
		}

		
		
		
		if (buttonAtoggle) {
			isAutoFlywheel = true;
			manipulators.flywheelSetPoint(4500);
			manipulators.flywheelEnabled(true);
			//System.out.println("starting loop");
		} else if (!buttonAtoggle) {
			isAutoFlywheel = false;
			manipulators.flywheelEnabled(false);
			//System.out.println("Stopping loop");
		}
		if (isAutoFlywheel == false) {

			System.out.println("is auto flywheel false");
			// flywheel.set(main.getRawAxis(3));
			manipulators.flywheel.set(main.getRawAxis(3));
		}
		
		//if(manipulators.flywheelDone()){
		//	manipulators.flywheelSetPoint(4500);
		//}
	}

	// left_pow = main.getRawAxis(1);

}