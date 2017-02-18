package org.usfirst.frc.team5263.robot;

import java.lang.reflect.Array;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

//import java.lang.reflect.Array;

//import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoVirtualDriver {

	Sensing sensing;
	Manipulators manipulators;
	DashboardCommunication dashComm;
	CameraMonitor cameraMonitor;
	// double rep = 0;

	// int turnDegrees = 90;
	// int smallerDegrees = turnDegrees - 5;
	// int biggerDegrees = turnDegrees + 5;
	// int after;
	// int autoRunner;
	// int encoderSet; // delete
	private class drivestraight {
		double distance;
		double drivePower;

		drivestraight(double distance, double drivePower) {
			this.distance = distance;
			this.drivePower = drivePower;
		}
	}

	private class rotate {
		double turn;

		rotate(double turn) {
			this.turn = turn;
		}
	}

	public class cameraDrive {

	}

	int step = 0; // this will go up, for every completed step

	// double[] turn = { 90, 90, 90, 90 };
	// double[] distance = { -7, -7, -7, -7 };

	// double[] drivePower = { 0.6, 0.6, 0.6, 0.6 };
	Object[] autosteps = { new cameraDrive() };
	// int steps = Array.getLength(turn); // This variable is a finite number,
	// the
	// number of tasks to complete

	// =============================================== for driving straight

	double driveDistance;
	int encoderSet;
	double encoderTargetPulses;
	double encoder1Val;
	double pi = Math.PI;
	double encoderMin;
	double encoderMax;
	double driveBeenDone;
	// double power;
	boolean doRotate;
	// double currentAngle;
	// double drivingMin;
	// double drivingMax;

	double leftSpeed;
	double rightSpeed;
	double targetAngle;
	double driveAngle;

	// ================================================

	// ================================================ for rotate
	// double degrees;
	double pastDegrees;
	double angle;
	int rotateBeenDone;
	int rotateSet;
	double minMargin;
	double maxMargin;
	// boolean doDrive;
	int rotateRunner;
	double currentDegrees;

	// boolean overallRun;

	static final double kP = 0.005;
	static final double kI = 0.0003;
	static final double kD = 0.0001;
	static final double kF = 0.00;
	boolean runPID;
	double PIDTolerance = 2; // this is the "margin" of degrees the PID
								// considers on target.

	PIDController turnController;

	// ===============================================
	// for camera
	boolean keepCamDrive;
	double ultraRange;
	double visionX1val;
	double visionX2val;
	double averageXval;
	boolean ranForVision = false;

	// ==============================================
	public AutoVirtualDriver(Sensing sensing, CameraMan cameraMan, CameraMonitor cameraMonitor,
			Manipulators manipulators, DashboardCommunication dashComm) {

		this.sensing = sensing;
		this.manipulators = manipulators;
		this.cameraMonitor = cameraMonitor;
		this.dashComm = dashComm;

	}

	public void init(String mode) {
		sensing.gyro.reset();
		sensing.encoder1.reset();

		// ===========================================
		// drive

		// doRotate = false;

		// ===========================================
		// rotate

		rotateSet = 0;
		// doDrive = true;
		// rotateRunner = 0;
		// overallRun = true;

		// ===========================================
	}

	public void clean() {
		sensing.gyro.reset();
		sensing.encoder1.reset();
		runPID = false;
		// overallRun = false;
		// doDrive = false;
		// doRotate = false;
		step = 0;
		encoderSet = 0;
		try {
			turnController.disable();
		} catch (Exception e) {

		}
	}

	public void periodicAuto() {
		ultraRange = sensing.getUltraRange();
		if (autosteps[step] instanceof drivestraight) {
			double drivingStraightDistance = ((drivestraight) autosteps[step]).distance;
			double drivingStraightPower = ((drivestraight) autosteps[step]).drivePower;
			if (!DriveStraightN(drivingStraightDistance, drivingStraightPower)) {
				System.out.println("ran drive, starting next object");
				if (step < Array.getLength(autosteps) - 1) {
					step++;
				}
			}
		}

		if (autosteps[step] instanceof rotate) {
			double rotateDegrees = ((rotate) autosteps[step]).turn;
			if (!Rotate(rotateDegrees)) {
				System.out.println("rotate done, staring next object. STEP " + step + " steps "
						+ (Array.getLength(autosteps) - 1));
				if (step < Array.getLength(autosteps) - 1) {
					step++;
					rotateSet = 0;
					// try{
					// turnController.reset();
					// } catch (Exception e){

					// }
				}
			}
		}

		if (autosteps[step] instanceof cameraDrive) {
			boolean visible = cameraMonitor.visible;
			if (cameraDrive(visible)) { //!cameraDrive
				System.out.println("rotate done, staring next object. STEP " + step + " steps "
						+ (Array.getLength(autosteps) - 1));
				if (step < Array.getLength(autosteps) - 1) {
					step++;
					rotateSet = 0;
					// try{
					// turnController.reset();
					// } catch (Exception e){

					// }
				}
			}
		}

		/**
		 * // SmartDashboard.putInt("Encoder1", encoder1.get()); if (overallRun
		 * == true) { if (doDrive == true) { if (DriveStraightN(distance[step],
		 * drivePower[step])) {
		 * 
		 * } else { System.out.println("ran ELSE inside DRIVE STRAIGHT");
		 * manipulators.myRobot.tankDrive(0, 0); doRotate = true; doDrive =
		 * false; } } if (doRotate == true) { System.out.println("rotate runner
		 * " + rotateRunner + " rotateSet " + rotateSet); rotateRunner++; if
		 * (rotateRunner == 1) { rotateSet = 0; }
		 * 
		 * if (Rotate(turn[step])) {
		 * 
		 * } else { manipulators.myRobot.tankDrive(0, 0); if (step < steps - 1)
		 * { step++; System.out.println("STEPS UP AAAAAAAAAAAAAAAAAAAAAAAAAA");
		 * rotateRunner = 0; } else {//if (step == steps - 1) {
		 * System.out.println("program done."); overallRun = false; }
		 * System.out.println("ran ELSE inside ROTATE with steps at " + step);
		 * doRotate = false; doDrive = true; } } } //System.out.println("program
		 * terminated.");
		 * 
		 **/

	}

	// =================================================================================================

	public boolean DriveStraightN(double driveDistanceFeet, double power) {

		encoder1Val = sensing.getEncoder1();
		driveAngle = sensing.getGyroAngle();
		encoderSet = encoderSet + 1;

		if (encoderSet == 1) {
			System.out.println("running drive");
			encoderTargetPulses = (driveDistanceFeet * 12) / (pi * 6) * 1440 / 4;
			sensing.encoder1.reset();
			// sensing.gyro.reset();
			encoderMin = encoderTargetPulses - 50;
			encoderMax = encoderTargetPulses + 50;
			// drivingMin = currentAngle - 5;
			// drivingMax = currentAngle + 5;
			targetAngle = sensing.getGyroAngle();

		}

		leftSpeed = power + (driveAngle - targetAngle) / 50;
		rightSpeed = power - (driveAngle - targetAngle) / 50;

		// double remainingDistancePulses = encoderTargetPulses - encoder1Val;
		// System.out.println("remaining distance " + remainingDistancePulses +
		// " target pulses " + encoderTargetPulses);
		// if (remainingDistancePulses < 0) {
		// if (remainingDistancePulses > encoderTargetPulses + 25) {
		// leftSpeed = leftSpeed * 0.65;
		// rightSpeed = rightSpeed * 0.65;
		// }
		// System.out.println("Approaching target angle! Decreasing power.
		// Negative distance");
		// } else if (remainingDistancePulses > 0) {
		// if (remainingDistancePulses < encoderTargetPulses - 25) {
		// leftSpeed = leftSpeed * 0.65;
		// rightSpeed = rightSpeed * 0.65;
		// }
		// }

		if (encoder1Val < encoderMin) {
			System.out.println("encoder val " + encoder1Val + " less than " + encoderTargetPulses + " power at " + power
					+ " left speed at " + leftSpeed + " right speed at " + rightSpeed);
			manipulators.myRobot.tankDrive(-rightSpeed, -leftSpeed);
		} else if (encoder1Val > encoderMax) {
			System.out.println("encoder val " + encoder1Val + " more than " + encoderTargetPulses + " power at " + power
					+ " left speed at " + leftSpeed + " right speed at " + rightSpeed);
			manipulators.myRobot.tankDrive(rightSpeed, leftSpeed);

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

	// ==================================================

	public boolean Rotate(double degrees) {

		rotateSet++;
		if (rotateSet == 1) {
			pastDegrees = pastDegrees + degrees;

			runPID = true;
			if (!(turnController == null)) {
				turnController.reset();
				turnController.disable();
			}
			turnController = new PIDController(kP, kD, kI, kF, sensing.getGyroPIDSource(), new PIDOutput() {

				@Override
				public void pidWrite(double output) {
					// TODO Auto-generated method stub
					manipulators.myRobot.tankDrive(output, -output);

				}
			});

			turnController.setSetpoint(pastDegrees);
			turnController.setInputRange(-360.0f, 360.0f); // was -180 180
			turnController.setOutputRange(-1.0, 1.0);
			turnController.setAbsoluteTolerance(PIDTolerance);

		}

		angle = sensing.getGyroAngle();
		System.out.println("output " + turnController.get());
		System.out.println("error " + turnController.getError());
		System.out.println("angle " + angle);

		if (runPID) {
			turnController.enable();
		} else {
			turnController.disable();
		}
		System.out.println("runPID " + runPID);
		if (turnController.onTarget()) {
			rotateBeenDone++;
		} else {
			rotateBeenDone = 0;
		}

		if (rotateBeenDone > 20) {

			runPID = false;
		}
		if (rotateBeenDone > 50) {
			currentDegrees = 0;
			return false;
		}
		// if(rotateBeenDone > 50){
		// runPID = false;
		// return false;
		// }

		/**
		 * if (angle < minMargin) { manipulators.myRobot.tankDrive(0.6, -0.6);
		 * System.out.println("angle " + angle + " less than specified " +
		 * degrees); rotateBeenDone = 0; } else if (angle > maxMargin) {
		 * manipulators.myRobot.tankDrive(-0.6, 0.6); System.out.println("angle
		 * " + angle + " less than specified " + degrees); rotateBeenDone = 0; }
		 * else { manipulators.myRobot.tankDrive(0, 0); rotateBeenDone =
		 * rotateBeenDone + 1; System.out.println("rotate been done " +
		 * rotateBeenDone); if (rotateBeenDone > 50) { runPID = false; return
		 * false; } }
		 **/

		return true;

	}

	// =====================================================================
	public boolean cameraDrive(boolean visible) {
		// if (cameraMonitor.centerXS[0] == cameraMonitor.centerXS[1]){
		//
		// }

		// if (cameraMonitor.centerXS.length < 2) {
		// System.out.println("2 Vision targets not found");
		// } else if (cameraMonitor.centerXS.length == 2) {
		// // if(widthS[0] == widtharray[0])
		// // {
		// System.out.println("2 Vision targets found");
		// // }
		// } else {
		// System.out.println("NOPEEE NOPPEEE NOPEEEE");
		// }
		ultraRange = sensing.getUltraRange(); // this is in inches
		if (visible) {
			int checkVision;
			if(ranForVision == false){
				for (checkVision = 0; checkVision < Array.getLength(cameraMonitor.centerXS); checkVision++) {
					if (checkVision == 1) {
						visionX1val = cameraMonitor.centerXS[checkVision];
					} else if(checkVision == 2){
						visionX2val = cameraMonitor.centerXS[checkVision];
					} else if (checkVision == 3){
						return true;
					}
					ranForVision = true;
				}
			}
			averageXval = (visionX1val + visionX2val)/2;
			if(averageXval > 280){
				manipulators.myRobot.tankDrive(0.3, -0.3);
			}else if(averageXval < 250){
				manipulators.myRobot.tankDrive(-0.3, 0.3);
				
			}else{
				keepCamDrive = true;
				if (ultraRange <= 10) {
					keepCamDrive = false;
				}
			}
			

		} else {
			
		}

		if (keepCamDrive == true) {
			manipulators.myRobot.tankDrive(0.5, 0.5);
		}else if(!visible){
			manipulators.myRobot.tankDrive(0.5, -0.5);
		}
		return false;
	}

}
