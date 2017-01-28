package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.Encoder;

public class DriveStraight {

	double distance;
	int encoderSet;
	double drivePulses;
	double encoder1Val;
	double pi = Math.PI;
	double encoderMin;
	double encoderMax;
	double beenDone;

	public DriveStraight(double distance, Sensing sensing, Manipulators manipulators, AutoVirtualDriver virtualDriver) {
		this.distance = distance;

		drivePulses = (distance * 12) / (pi * 6) * 1440;
		System.out.println("distance: " + distance);
		System.out.println("drive pulses: " + drivePulses);

		encoder1Val = sensing.getEncoder1();
		System.out.println("running drive");
		encoderSet = encoderSet + 1;
		if (encoderSet == 1) {
			sensing.encoder1.reset();
			encoderMin = drivePulses - 50;
			encoderMax = drivePulses + 50;
		}
		if (encoder1Val < encoderMin) {
			System.out.println("encoder val " + encoder1Val + " less than " + drivePulses);
			manipulators.myRobot.tankDrive(0.4, 0.4);
		} else if (encoder1Val > encoderMax) {
			System.out.println("encoder val " + encoder1Val + " more than " + drivePulses);
			manipulators.myRobot.tankDrive(-0.4, -0.4);
		} else {
			System.out.println("between margins, stopped.");
			beenDone = beenDone + 1;
			if(beenDone > 1000){
				
			}
		}
	}

}
