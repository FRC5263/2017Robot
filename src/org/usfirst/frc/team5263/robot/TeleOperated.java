package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.Joystick;

public class TeleOperated {

	Joystick main;
	Joystick main2;
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

	boolean buttonBtoggle = false;
	boolean buttonDisableB = false;
	int buttonBeenDisabledB;

	boolean buttonXtoggle = false;
	boolean buttonDisableX = false;
	int buttonBeenDisabledX;

	boolean buttonYtoggle = false;
	boolean buttonDisableY = false;
	int buttonBeenDisabledY;

	int servo1angle;
	boolean servodone = false;
	boolean isAutoFlywheel = false;
	double speedMultiplier = 0.7;
	double ultraRange;

	public TeleOperated(Sensing sensing, CameraMan cameraMan, CameraMonitor cameraMonitor, Manipulators manipulators) {

		this.sensing = sensing;
		this.manipulators = manipulators;

		main = new Joystick(0);
		main2 = new Joystick(1);
	}

	public void init(String mode) {
		manipulators.servo1.setAngle(90);
		servo1angle = 90;
	}

	public void Periodic() {

		left_pow = -main.getRawAxis(1) + main.getRawAxis(4);
		right_pow = -main.getRawAxis(1) - main.getRawAxis(4);

		manipulators.driveRobot(left_pow * speedMultiplier, right_pow * speedMultiplier);
		/**
		 * if(main.getRawButton(buttonA)){ isAutoFlywheel = true;
		 * manipulators.flywheelSetPoint(1000);
		 * manipulators.flywheelEnabled(true); System.out.println("starting
		 * loop"); }else if(main.getRawButton(buttonX)){ isAutoFlywheel = false;
		 * manipulators.flywheelEnabled(false); System.out.println("Stopping
		 * loop"); }
		 **/

		if (main2.getRawButton(buttonA) && buttonDisableA == false) {
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
			manipulators.flywheelSetPoint(3500);
			manipulators.flywheelEnabled(true);
			// System.out.println("starting loop");
		} else if (!buttonAtoggle) {
			isAutoFlywheel = false;
			// manipulators.flywheelEnabled(false);
			if (manipulators.pidMotor.isEnabled()) {
				manipulators.pidMotor.disable();
			}
			// System.out.println("Stopping loop");
		}

		// if (main2.getRawButton(buttonX) && buttonDisableX == false) {
		// buttonXtoggle = !buttonXtoggle;
		// buttonDisableX = true;
		// }
		//
		//
		//
		// if (buttonDisableX) {
		// buttonBeenDisabledX++;
		// if (buttonBeenDisabledX > 20) {
		// buttonBeenDisabledX = 0;
		// buttonDisableA = false;
		// }
		// }
		// if (buttonXtoggle) {
		// if(servo1angle < 180 && servodone == false){
		// System.out.println("MAX!!!!!!!!!!!!!!!!!!!!");
		// servo1angle++;
		// if (servo1angle >= 180){
		// servodone = true;
		// }
		//
		// }
		// if (servodone == true) {
		// servo1angle = servo1angle - 1;
		// if (servo1angle < 5) {
		// servodone = false;
		// }
		// }
		//
		// } else if (!buttonXtoggle) {
		// servo1angle = servo1angle;
		// }

		manipulators.climber.set(main2.getRawAxis(2));

		if (main2.getPOV() == 0) {
			if (servo1angle < 180) {
				servo1angle++;
			}
		} else if (main2.getPOV() == 180) {
			if (servo1angle > 0) {
				servo1angle--;
			}
		}

		manipulators.servo1.setAngle(servo1angle);

		if (main2.getRawButton(buttonY) && buttonDisableY == false) {
			buttonYtoggle = !buttonYtoggle;
			buttonDisableY = true;
		}

		if (buttonDisableY) {
			buttonBeenDisabledY++;
			if (buttonBeenDisabledY > 20) {
				buttonBeenDisabledY = 0;
				buttonDisableY = false;
			}
		}
		if (buttonYtoggle) {

			speedMultiplier = 1.0;

		} else if (!buttonYtoggle) {

			speedMultiplier = 0.7;
		}

		// System.out.println("speed multiplier " + speedMultiplier);

		if (isAutoFlywheel == false) {

			// System.out.println("is auto flywheel false");
			// flywheel.set(main.getRawAxis(3));
			manipulators.flywheel.set(-main2.getRawAxis(3));
		}

		if (main2.getRawButton(buttonB) && buttonDisableB == false) {
			buttonBtoggle = !buttonBtoggle;
			buttonDisableB = true;
		}

		if (buttonDisableB) {
			buttonBeenDisabledB++;
			if (buttonBeenDisabledB > 20) {
				buttonBeenDisabledB = 0;
				buttonDisableB = false;
			}
		}

		if (buttonBtoggle) {
			manipulators.sweeper.set(1.0);
		} else if (!buttonAtoggle) {
			manipulators.sweeper.set(0.0);
		}

		// if(manipulators.flywheelDone()){
		// manipulators.flywheelSetPoint(4500);
		// }
	}

	// left_pow = main.getRawAxis(1);

}