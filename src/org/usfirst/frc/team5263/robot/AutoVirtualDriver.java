package org.usfirst.frc.team5263.robot;

import java.lang.reflect.Array;

//import java.lang.reflect.Array;

//import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoVirtualDriver {

	Sensing sensing;
	Manipulators manipulators;
	// double rep = 0;

	// int turnDegrees = 90;
	// int smallerDegrees = turnDegrees - 5;
	// int biggerDegrees = turnDegrees + 5;
	// int after;
	// int autoRunner;
	// int encoderSet; // delete

	int step = 0; // this will go up, for every completed step

	double[] turn = { 90, 90, 90, 90 };
	double[] distance = { -5, -5, -5, -5 };
	double[] drivePower = { 0.5, 0.5, 0.5, 0.5 };
	int steps = Array.getLength(turn); // This variable is a finite number, the
										// number of tasks to complete

	// =============================================== for driving straight

	double driveDistance;
	int encoderSet;
	double drivePulses;
	double encoder1Val;
	double pi = Math.PI;
	double encoderMin;
	double encoderMax;
	double driveBeenDone;
	double power;
	boolean doRotate = false;
	double Kp = 0.03;

	// ================================================

	// ================================================ for rotate
	double degrees;

	double angleOffset;
	double firstAngle;

	double angle;
	int rotateBeenDone;
	int rotateSet = 0;
	double minMargin;
	double maxMargin;
	boolean doDrive = true;
	int rotateRunner = 0;
	
	boolean overallRun = true;
	// ===============================================

	public AutoVirtualDriver(Sensing sensing, CameraMan cameraMan, CameraMonitor cameraMonitor,
			Manipulators manipulators, DashboardCommunication dashComm) {

		this.sensing = sensing;
		this.manipulators = manipulators;

	}

	public void init(String mode) {
		angleOffset = sensing.getGyroAngle();
		encoder1Val = sensing.getEncoder1();
		// firstAngle = angleOffset;
		// angleOffset = sensing.getGyroAngle();
		// angle = angleOffset - firstAngle;
		/**
		 * System.out.println("steps" + steps); turn[steps] = 90;
		 * distance[steps] = 50; steps ++; turn[steps] = 180; distance[steps] =
		 * 30; steps++; turn[steps] = 45; distance[steps] = 100;
		 **/

	}

	public void periodicAuto() {

		// SmartDashboard.putInt("Encoder1", encoder1.get());
		if (overallRun == true) {
			if (doDrive == true) {
				if (DriveStraightN(distance[step], drivePower[step])) {

				} else {
					System.out.println("ran ELSE inside DRIVE STRAIGHT");
					manipulators.myRobot.tankDrive(0, 0);
					doRotate = true;
					doDrive = false;
				}
			}
			if (doRotate == true) {
				System.out.println("rotate runner " + rotateRunner + " rotateSet " + rotateSet);
				rotateRunner++;
				if (rotateRunner == 1) {
					rotateSet = 0;
				}
				if (Rotate(turn[step])) {

				} else {
					manipulators.myRobot.tankDrive(0, 0);
					if (step < steps - 1) {
						step++;
						System.out.println("STEPS UP AAAAAAAAAAAAAAAAAAAAAAAAAA");
						rotateRunner = 0;
					} else {//if (step == steps - 1) {
						System.out.println("program done.");
						overallRun = false;
					}
					System.out.println("ran ELSE inside ROTATE with steps at " + step);
					doRotate = false;
					doDrive = true;
				}
			}
		}
		System.out.println("program terminated.");

	}

	// if ()) {

	// } else {
	// manipulators.myRobot.tankDrive(0, 0);
	// step ++;
	// }

	/** 
myRobot.drive(-1.0, -angle*Kp);
	 * 
	 * switch (autoRunner){
	 * 
	 * case 1:
	 * 
	 * 
	 * 
	 * 
	 * encoder1Val = sensing.getEncoder1(); angleOffset =
	 * sensing.getGyroAngle(); angle = angleOffset - firstAngle;
	 * System.out.println("angle Offset: " + angleOffset);
	 * System.out.println("first angle: " + firstAngle); rep = rep + 1;
	 * System.out.println("counter: " + rep);
	 * 
	 * 
	 * if(rep == 1){ angle = angle - angleOffset; } System.out.println(step);
	 * autoRunner = 2; break;
	 * 
	 * 
	 * 
	 * case 2: encoder1Val = sensing.getEncoder1(); angleOffset =
	 * sensing.getGyroAngle(); angle = angleOffset - firstAngle; double
	 * minMargin = turn[step] - 5; double maxMargin = turn[step] + 5;
	 * 
	 * if(angle < minMargin){
	 * 
	 * if(minMargin - angle > 20){ manipulators.myRobot.tankDrive(0.6, -0.6);
	 * System.out.println("fast min"); }else{
	 * manipulators.myRobot.tankDrive(0.375, -0.375); System.out.println("slow
	 * min"); } System.out.println("angle " + angle + " less than specified " +
	 * turn[step]);
	 * 
	 * }else if(angle > maxMargin){
	 * 
	 * if(angle - maxMargin > 20){ manipulators.myRobot.tankDrive(-0.6, 0.6);
	 * System.out.println("fast max"); }else {
	 * manipulators.myRobot.tankDrive(-0.375, -0.375); System.out.println("slow
	 * max"); } System.out.println("angle " + angle + " more than specified " +
	 * turn[step]);
	 * 
	 * }else{ autoRunner = 3; }
	 * 
	 * break;
	 * 
	 * 
	 * case 3: encoder1Val = sensing.getEncoder1(); System.out.println("running
	 * drive"); System.out.println("step " + step + " steps " + steps);
	 * encoderSet = encoderSet + 1; if(encoderSet == 1){
	 * sensing.encoder1.reset(); encoderMin = distance[step] - 50; encoderMax =
	 * distance[step] + 50; } if(encoder1Val < encoderMin){
	 * System.out.println("encoder val " + encoder1Val + " less than " +
	 * distance[step]); manipulators.myRobot.tankDrive(0.4, 0.4); }else
	 * if(encoder1Val > encoderMax){ System.out.println("encoder val " +
	 * encoder1Val + " more than " + distance[step]);
	 * manipulators.myRobot.tankDrive(-0.4, -0.4); }else{ if(step < steps - 1){
	 * //whats happening is that this is repeated over and over and always
	 * defaults to the else, and the condition is true step = step + 1;
	 * System.out.println("STEP HIGHER"); autoRunner = 4;
	 * 
	 * } }
	 * 
	 * 
	 * 
	 * break; case 4: autoRunner = 1; break; default: break; }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 **/

	/**
	 * 
	 * double encoder1Val = sensing.getEncoder1(); angleOffset =
	 * sensing.getGyroAngle(); System.out.println("angle Offset: " +
	 * angleOffset); System.out.println("first angle: " + firstAngle); rep = rep
	 * + 1; System.out.println("counter: " + rep); double angle = angleOffset -
	 * firstAngle;
	 * 
	 * 
	 * if(rep == 1){ angle = angle - angleOffset; }
	 **/

	// =======================================================================================

	/**
	 * if(angle < smallerDegrees && rep < 500){
	 * manipulators.myRobot.tankDrive(0.6, -0.6); //try this
	 * System.out.println("angle smaller than specified :" + angle); }else
	 * if(angle > biggerDegrees && rep < 500){
	 * manipulators.myRobot.tankDrive(-0.6, 0.6); //maybe change
	 * System.out.println("angle larger than specified :" + angle ); } else{
	 * if(rep < 500 && encoder1Val < 100){ manipulators.myRobot.tankDrive(0.4,
	 * 0.4); System.out.println("driving straght"); }
	 * 
	 * }
	 **/

	// ===================================================================================================

	/**
	 * 
	 * double minMargin = turn[step] - 5; double maxMargin = turn[step] + 5;
	 * 
	 * if(angle < minMargin){ if(minMargin - angle > 20){
	 * manipulators.myRobot.tankDrive(0.6, -0.6); System.out.println("fast
	 * min"); }else { manipulators.myRobot.tankDrive(0.375, -0.375);
	 * System.out.println("slow min"); }
	 * 
	 * System.out.println("angle " + angle + " less than specified " +
	 * turn[step]); }else if(angle > maxMargin){ if(angle - maxMargin > 20){
	 * manipulators.myRobot.tankDrive(-0.6, 0.6); System.out.println("fast
	 * max"); }else { manipulators.myRobot.tankDrive(-0.375, -0.375);
	 * System.out.println("slow max"); } System.out.println("angle " + angle + "
	 * more than specified " + turn[step]); }else{ System.out.println("finished
	 * turn steps"); after = after + 1; if(after == 1){ afterTurn = encoder1Val;
	 * } System.out.println("encoder val " + encoder1Val);
	 * System.out.println("AFTER TURN " + afterTurn + " AFTER " + after);
	 * if(encoder1Val < (distance[step] + afterTurn)){
	 * System.out.println("encoder val " + encoder1Val + " less than " +
	 * distance[step] + " plus " + " after turn " + afterTurn + " : " +
	 * distance[step] + afterTurn); manipulators.myRobot.tankDrive(0.4, 0.4);
	 * //what i saw was that the encoder val goes up from turning, reset that
	 * with a variable. }else{ System.out.println("finished turn step"); if(step
	 * < steps){
	 * //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa steps "
	 * + steps + " step " + step); step ++; } System.out.println("steps
	 * writtern: " + steps); System.out.println("current step: " + step); } };
	 * 
	 * 
	 **/

	public boolean DriveStraightN(double driveDistance, double power) {

		drivePulses = (driveDistance * 12) / (pi * 6) * 1440 / 4;
		System.out.println("distance: " + driveDistance);
		System.out.println("drive pulses: " + drivePulses);

		encoder1Val = 	sensing.getEncoder1();
		angle = sensing.getGyroAngle();
		System.out.println("running drive");
		encoderSet = encoderSet + 1;
		if (encoderSet == 1) {
			sensing.encoder1.reset();
			sensing.gyro.reset();
			encoderMin = drivePulses - 50;
			encoderMax = drivePulses + 50;
		}
		if (encoder1Val < encoderMin) {
			System.out.println("encoder val " + encoder1Val + " less than " + drivePulses);
			//manipulators.myRobot.tankDrive(power, power); //power, power
			manipulators.myRobot.drive(power, -angle*Kp);
		} else if (encoder1Val > encoderMax) {
			System.out.println("encoder val " + encoder1Val + " more than " + drivePulses);
			//manipulators.myRobot.tankDrive(-power, -power);
				manipulators.myRobot.drive(-power, -angle*Kp);
				
		} else {
			System.out.println("between margins, stopped.");
			driveBeenDone = driveBeenDone + 1;
			System.out.println("drive been done: " + driveBeenDone);
			if (driveBeenDone > 50) {
				encoderSet = 0;

				return false;
			}
		}
		return true;
	}

	public boolean Rotate(double degrees) {
		// this.degrees = degrees;

		// angleOffset = sensing.getGyroAngle();

		rotateSet++;
		if (rotateSet == 1) {
			// firstAngle = angleOffset;
			minMargin = degrees - 5;
			maxMargin = degrees + 5;
			sensing.gyro.reset();
			System.out.println("ROTATE INITAL SET");
		}

		// angle = angleOffset - firstAngle;

		angle = sensing.getGyroAngle();

		if (angle < minMargin) {
			manipulators.myRobot.tankDrive(0.6, -0.6);
			System.out.println("angle " + angle + " less than specified " + degrees);
		} else if (angle > maxMargin) {
			manipulators.myRobot.tankDrive(-0.6, 0.6);
			System.out.println("angle " + angle + " less than specified " + degrees);
		} else {
			manipulators.myRobot.tankDrive(0, 0);
			rotateBeenDone = rotateBeenDone + 1;
			System.out.println("rotate been done " + rotateBeenDone);
			if (rotateBeenDone > 50) {
				return false;
			}
		}

		/**
		 * if(angle < minMargin){
		 * 
		 * if(minMargin - angle > 20){ manipulators.myRobot.tankDrive(0.6,
		 * -0.6); //faster, farther System.out.println("fast min"); }else{
		 * manipulators.myRobot.tankDrive(0.375, -0.375); //slower, closer
		 * System.out.println("slow min"); } System.out.println("angle " + angle
		 * + " less than specified " + degrees);
		 * 
		 * }else if(angle > maxMargin){
		 * 
		 * if(angle - maxMargin > 20){ manipulators.myRobot.tankDrive(-0.6,
		 * 0.6); System.out.println("fast max"); }else {
		 * manipulators.myRobot.tankDrive(-0.375, 0.375);
		 * System.out.println("slow max"); } System.out.println("angle " + angle
		 * + " more than specified " + degrees);
		 * 
		 * }else{ System.out.println("Rotation between margins, stopping.");
		 * rotateBeenDone = rotateBeenDone + 1; if(rotateBeenDone > 1000){ //add
		 * return thing return false; } }
		 **/
		return true;

	}

}
