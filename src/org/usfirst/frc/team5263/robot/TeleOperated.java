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
	boolean buttonXtoggle = false;
	boolean buttonDisableX = false;
	int buttonBeenDisabledX;
	int servo1angle;
	int servo2angle;
	boolean servodone = false;
	boolean isAutoFlywheel = false;

	public TeleOperated(Sensing sensing, CameraMan cameraMan, CameraMonitor cameraMonitor, Manipulators manipulators) {

		this.sensing = sensing;
		this.manipulators = manipulators;

		main = new Joystick(0);
	}

	public void init(String mode) {
		manipulators.servo1.setAngle(0);
		manipulators.servo2.setAngle(95);
	}

	public void Periodic() {
		left_pow = -main.getRawAxis(1) + main.getRawAxis(4);
		right_pow = -main.getRawAxis(1) - main.getRawAxis(4);

		manipulators.myRobot.tankDrive(left_pow, right_pow);
		/**
		 * if(main.getRawButton(buttonA)){ isAutoFlywheel = true;
		 * manipulators.flywheelSetPoint(1000);
		 * manipulators.flywheelEnabled(true); System.out.println("starting
		 * loop"); }else if(main.getRawButton(buttonX)){ isAutoFlywheel = false;
		 * manipulators.flywheelEnabled(false); System.out.println("Stopping
		 * loop"); }
		 **/
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


		if (main.getRawButton(buttonX) && buttonDisableX == false) {
			buttonXtoggle = !buttonXtoggle;
			buttonDisableX = true;
		}
		
		
		
		if (buttonDisableX) {
			buttonBeenDisabledX++;
			if (buttonBeenDisabledX > 20) {
				buttonBeenDisabledX = 0;
				buttonDisableA = false;
			}
		}
		if (buttonXtoggle) {
			if(servo1angle < 180 && servodone == false){
				System.out.println("MAX!!!!!!!!!!!!!!!!!!!!");
				servo1angle++;
				if (servo1angle >= 180){
					servodone = true;
				}
				
			} 
			if (servodone == true) {
				servo1angle = servo1angle - 1;
				if (servo1angle < 5) {
					servodone = false;
				}
			}
			
		} else if (!buttonXtoggle) {
			servo1angle = servo1angle;
		}
		manipulators.servo1.setAngle(servo1angle);
		manipulators.servo2.setAngle(servo1angle);
		
		if (isAutoFlywheel == false) {

			System.out.println("is auto flywheel false");
			// flywheel.set(main.getRawAxis(3));
			manipulators.flywheel.set(main.getRawAxis(3));
		}
		
		if(manipulators.flywheelDone()){
			manipulators.flywheelSetPoint(4500);
		}
	}

	// left_pow = main.getRawAxis(1);

}