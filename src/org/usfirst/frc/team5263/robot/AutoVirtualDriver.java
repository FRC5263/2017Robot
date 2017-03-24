package org.usfirst.frc.team5263.robot;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.Joystick;
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

	private class drivestraight {
		double distance;
		double drivePower;

		drivestraight(double distance, double drivePower) {
			this.distance = distance;
			this.drivePower = drivePower;
		}
	}

	public class sonicDrive {
		double sonicDistance;
		boolean faceGear;

		sonicDrive(double sonicDistance, boolean faceGear) {
			this.sonicDistance = sonicDistance;
			this.faceGear = faceGear;
		}
	}

	private class rotate {
		double turn;

		rotate(double turn) {
			this.turn = turn;
		}
	}

	private class OLDrotate {
		double OLDturn;

		OLDrotate(double OLDturn) {
			this.OLDturn = OLDturn;
		}
	}

	public class shooter {
		double rpm;
		double timer;

		shooter(double rpm, double timer) {
			this.rpm = rpm;
			this.timer = timer;
		}
	}

	public class cameraDrive {

	}

	public class stop {

	}

	public class record {

	}

	int step = 0; // this will go up, for every completed step

	// Object[] autosteps = { new drivestraight(4, 0.5), new rotate(135), new
	// sonicDrive(18), new shooter(3500, 10), new stop()};

	// Object[] autosteps = {
	// //I thought it looked nice -Evan ;)
	// new drivestraight(18, 0.5),
	// //new shooter(4500, 15),
	// new stop() };

	// Object[] autosteps = { new sonicDrive(5, true), new stop() };
//	Object[] autosteps = new Object[10];// { new shooter(2500, 10), new rotate(90), new drivestraight(8, 0.6) };
	ArrayList<Object> autosteps = new ArrayList();
	// Object[] autosteps = { new drivestraight(5, 0.5), new OLDrotate(60), new
	// sonicDrive(8, true) };

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

	// static final double kP = 0.005;
	// static final double kI = 0.0003;
	// static final double kD = 0.0001;
	// static final double kF = 0.00;
	boolean runPID;
	// double PIDTolerance = 2; // this is the "margin" of degrees the PID
	// // considers on target.
	//
	// PIDController turnController;

	// ===============================================
	// for camera
	boolean keepCamDrive;
	double ultraRange;
	double visionX1val;
	double visionX2val;
	double averageXval;
	boolean ranForVision = false;

	// ==============================================
	// for old rotate

	double OLDdegrees;
	double OLDpastDegrees;
	double OLDangle;
	int OLDrotateBeenDone;
	int OLDrotateSet = 0;
	double OLDminMargin;
	double OLDmaxMargin;
	boolean OLDdoDrive = true;
	int OLDrotateRunner = 0;

	boolean overallRun = true;

	// [================================================
	// for shooter

	boolean shooterSet = true;
	double startTime;
	double currentTime;
	int shooterDone;

	// ================================================

	public AutoVirtualDriver(Sensing sensing, CameraMan cameraMan, CameraMonitor cameraMonitor,
			Manipulators manipulators, DashboardCommunication dashComm) {

		this.sensing = sensing;
		this.manipulators = manipulators;
		this.cameraMonitor = cameraMonitor;
		this.dashComm = dashComm;

	}

	public void init(String mode, double newRotateKp, double newRotateKi, double newRotateKd, double newRotateKf) {
		clean();
		System.out.println("mode: " + mode);
		
		autosteps.clear();
		switch(mode){
		case "centerGear": 
			autosteps.add(new sonicDrive(9, true));
			autosteps.add(new stop());
			System.out.println(autosteps);
			break;
		case "stop":
			autosteps.add(new stop());
			break;
		case "baseline": 
			autosteps.add(new drivestraight(16, 0.6));
			autosteps.add(new stop());
		case "redShoot":
			autosteps.add(new shooter(3500, 10));
			autosteps.add(new drivestraight(1, .1));
			autosteps.add(new rotate(95));
			autosteps.add(new drivestraight(10, .1));
			break;
		case "blueShoot":
			break;
		case "Camdrive":
			autosteps.add(new drivestraight(5, .5));
			autosteps.add(new rotate(-45));
			autosteps.add(new cameraDrive());
			autosteps.add(new stop());
			break;
		default:
			autosteps.add(new stop());
			break;
		};
		
		manipulators.setkP(newRotateKp);
		manipulators.setkI(newRotateKi);
		manipulators.setkD(newRotateKd);
		manipulators.setkF(newRotateKf);
		manipulators.setNewRotatePID();
		System.out.println("itital set rotation PID values: P=" + newRotateKp + ", I=" + newRotateKi + ", D=" + newRotateKd + ", F=" + newRotateKf);
		
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
			manipulators.rotateEnabled(false);
		} catch (Exception e) {

		}
	}

	public void periodicAuto() {
		if (autosteps.get(step) instanceof drivestraight) {
			double drivingStraightDistance = ((drivestraight) autosteps.get(step)).distance;
			double drivingStraightPower = ((drivestraight) autosteps.get(step)).drivePower;
			if (!DriveStraightN(drivingStraightDistance, drivingStraightPower)) {
				System.out.println("ran drive, starting next object");
				if (step < autosteps.size() - 1) {
					step++;
				}
			}
		}

		if (autosteps.get(step) instanceof sonicDrive) {
			double driveSonicDist = ((sonicDrive) autosteps.get(step)).sonicDistance;
			boolean doesFaceGear = ((sonicDrive) autosteps.get(step)).faceGear;
			if (!sonicDrive(driveSonicDist, doesFaceGear)) {
				System.out.println("ran sonic drive, starting next object");
				if (step < autosteps.size() - 1) {
					step++;
				}
			}
		}

		if (autosteps.get(step) instanceof rotate) {
			double rotateDegrees = ((rotate) autosteps.get(step)).turn;
			if (!Rotate(rotateDegrees)) {
				System.out.println("rotate done, staring next object. STEP " + step + " steps "
						+ (autosteps.size() - 1));
				if (step < autosteps.size() - 1) {
					step++;
					rotateSet = 0;
					// try{
					// turnController.reset();
					// } catch (Exception e){

					// }
				}
			}
		}

		if (autosteps.get(step) instanceof OLDrotate) {
			double OLDrotateDegrees = ((OLDrotate) autosteps.get(step)).OLDturn;
			if (!OLDRotate(OLDrotateDegrees)) {
				System.out.println("OLDrotate done, staring next object. STEP " + step + " steps "
						+ (autosteps.size() - 1));
				if (step < autosteps.size() - 1) {
					step++;
					rotateSet = 0;
					// try{
					// turnController.reset();
					// } catch (Exception e){

					// }
				}
			}
		}

		if (autosteps.get(step) instanceof shooter) {
			double shooterRPM = ((shooter) autosteps.get(step)).rpm;
			double shooterTimer = ((shooter) autosteps.get(step)).timer;
			if (!shooter(shooterRPM, shooterTimer)) {
				System.out.println("ran shooter, starting next object");
				if (step < autosteps.size() - 1) {
					step++;
				}
			}
		}

		if (autosteps.get(step) instanceof cameraDrive) {
			boolean visible = cameraMonitor.visible;
			if (cameraDrive(visible)) { // !cameraDrive
				System.out.println("camera drive done, staring next object. STEP " + step + " steps "
						+ (autosteps.size() - 1));
				if (step < autosteps.size() - 1) {
					step++;
					rotateSet = 0;
					// try{
					// turnController.reset();
					// } catch (Exception e){

					// }
				}
			}
		}

		if (autosteps.get(step) instanceof stop) {
			if (stop()) { // if stop is false,
				//System.out.println("autonomous stopped");
			} else {
				//System.out.println("autonomous stopped");
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
		// experimental slow down code so we dont have that weird wiggle thing
		// at the end
		if (encoderTargetPulses > 0) {
			if (encoder1Val > encoderTargetPulses - 500) {
				leftSpeed = (power * 0.6) - (driveAngle - targetAngle) / 50;
				rightSpeed = (power * 0.6) + (driveAngle - targetAngle) / 50;
			} else {
				leftSpeed = power - (driveAngle - targetAngle) / 50;
				rightSpeed = power + (driveAngle - targetAngle) / 50;
			}
		} else if (encoderTargetPulses < 0) {
			if (encoder1Val < encoderTargetPulses + 500) {
				leftSpeed = (power * 0.6) - (driveAngle - targetAngle) / 50;
				rightSpeed = (power * 0.6) + (driveAngle - targetAngle) / 50;
			} else {
				leftSpeed = power - (driveAngle - targetAngle) / 50;
				rightSpeed = power + (driveAngle - targetAngle) / 50;
			}
		}

		if (encoder1Val < encoderMin) {
			System.out.println("encoder val " + encoder1Val + " less than " + encoderTargetPulses + " power at " + power
					+ " left speed at " + leftSpeed + " right speed at " + rightSpeed);
			manipulators.driveRobot(-rightSpeed, -leftSpeed);
		} else if (encoder1Val > encoderMax) {
			System.out.println("encoder val " + encoder1Val + " more than " + encoderTargetPulses + " power at " + power
					+ " left speed at " + leftSpeed + " right speed at " + rightSpeed);
			manipulators.driveRobot(rightSpeed, leftSpeed);

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

	// ======================================================

	public boolean sonicDrive(double sonicDistance, boolean faceGear) {

		encoder1Val = sensing.getEncoder1();
		driveAngle = sensing.getGyroAngle();
		encoderSet = encoderSet + 1;
		double ultraDistance = sensing.getUltraRange() - 11; //offsets the ultrasonic readings to the front of the robot, not the sensor itself

		if (encoderSet == 1) {
			System.out.println("running drive");
			sensing.encoder1.reset();
			targetAngle = sensing.getGyroAngle();

		}

		leftSpeed = 0.5 + (driveAngle - targetAngle) / 50;
		rightSpeed = 0.5 - (driveAngle - targetAngle) / 50;

		if (ultraDistance < sonicDistance - 3) {

//			if (!faceGear) {
//				manipulators.driveRobot(rightSpeed, leftSpeed);
//			} else {
//				manipulators.driveRobot(-rightSpeed, -leftSpeed);
//			}
			driveBeenDone++;
			if (driveBeenDone > 50) {
				encoderSet = 0;

				return false;
			}
			
			
		} else if (ultraDistance > sonicDistance + 3) {
			if (!faceGear) {
				manipulators.driveRobot(-rightSpeed, -leftSpeed);
			} else {
				manipulators.driveRobot(rightSpeed, leftSpeed);
			}

		} else {
			System.out.println("between margins, stopped.");
			driveBeenDone++;
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
			pastDegrees = degrees;

			runPID = true;
			if (!(manipulators.turnController == null)) {
				manipulators.turnController.reset();
				manipulators.turnController.disable();
			}
			manipulators.rotateSetPoint(pastDegrees);

		}

		angle = sensing.getGyroAngle();
		System.out.println("output " + manipulators.turnController.get());
		System.out.println("error " + manipulators.turnController.getError());
		System.out.println("angle " + angle);

		if (runPID) {
			manipulators.rotateEnabled(true);
		} else {
			manipulators.rotateEnabled(false);
		}
		System.out.println("runPID " + runPID);
		if (manipulators.rotateDone()) {
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

		return true;

	}

	// ==================================================

	public boolean OLDRotate(double degrees) {

		OLDrotateSet++;
		if (OLDrotateSet == 1) {
			OLDpastDegrees = OLDpastDegrees + degrees;
			OLDminMargin = OLDpastDegrees - 2;
			OLDmaxMargin = OLDpastDegrees + 2;
			// sensing.gyro.reset();
			System.out.println("ROTATE INITAL SET");
		}

		OLDangle = sensing.getGyroAngle();

		if (OLDangle < OLDminMargin) {
			manipulators.myRobot.tankDrive(0.6, -0.6);
			System.out.println("angle " + OLDangle + " less than specified " + OLDdegrees);
			OLDrotateBeenDone = 0;
		} else if (OLDangle > OLDmaxMargin) {
			manipulators.myRobot.tankDrive(-0.6, 0.6);
			System.out.println("angle " + OLDangle + " less than specified " + OLDdegrees);
			OLDrotateBeenDone = 0;
		} else {
			manipulators.myRobot.tankDrive(0, 0);
			OLDrotateBeenDone = OLDrotateBeenDone + 1;
			System.out.println("rotate been done " + OLDrotateBeenDone);
			if (OLDrotateBeenDone > 50) {
				return false;
			}
		}

		return true;

	}

	public boolean stop() {
		manipulators.myRobot.tankDrive(0, 0);
		return true;

	}

	// ======================================================================

//	public boolean NEWrotate(double degrees) {
//		
//		boolean firstRotateRun = true;
//		if (firstRotateRun) {
//			firstRotateRun = false;
//			sensing.gyro.reset();
//			
//		}
//		double speed = 0.6;
//		double angle = sensing.getGyroAngle();
//		double difference = degrees - angle;
//		
//		if (degrees > 0) {
//			manipulators.driveRobot(-speed * ( difference / 100), 0.6);
//		} else if(degrees < 0){
//			
//		}
//		return true;
//	}

	// ======================================================================

	public boolean shooter(double shootRPM, double shootTime) {

		if (shooterSet) {
			startTime = System.currentTimeMillis();
			shooterSet = false;
		}

		currentTime = System.currentTimeMillis();

		if (currentTime <= startTime + (shootTime * 1000)) {
			manipulators.flywheelEnabled(true);
			manipulators.flywheelSetPoint(shootRPM);
			manipulators.sweeper.set(1.0);
			manipulators.agitator.set(1.0);
		} else {
			manipulators.flywheelEnabled(false);
			manipulators.agitator.set(0.0);
			manipulators.sweeper.set(0.0);
			shooterDone++;
		}

		if (shooterDone > 50) {
			manipulators.agitator.set(0.0);
			manipulators.sweeper.set(0.0);
			manipulators.flywheelEnabled(false);
			return false;
		}

		return true;
	}

	// =====================================================================
	public boolean cameraDrive(boolean visible) {
		// if(visible){
		// manipulators.myRobot.tankDrive(.5, .5);
		// }else {
		// manipulators.myRobot.tankDrive(.5, -.5);
		// }
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
			System.out.println("visible");

			if (Array.getLength(cameraMonitor.centerXS) == 2) {
				visionX1val = cameraMonitor.centerXS[0];
				visionX2val = cameraMonitor.centerXS[1];
			} else {
				System.out.println("objects not equal to 2");
			}
			averageXval = (visionX1val + visionX2val) / 2;
			if (averageXval > 280 && manipulators.rotateDone()) {
				System.out.println("avg x val more than 280, turning");
				// manipulators.myRobot.tankDrive(0.55, -0.55);
				manipulators.rotateEnabled(true);
				manipulators.rotateSetPoint(20);
			} else if (averageXval < 250 && manipulators.rotateDone()) {
				System.out.println("avg x val less than 250, turning");
				// manipulators.myRobot.tankDrive(-0.55, 0.55);
				manipulators.rotateEnabled(true);
				manipulators.rotateSetPoint(-20);
			} else {
				if (manipulators.turnController.isEnabled()) {
					manipulators.rotateEnabled(false);
				}
				keepCamDrive = true;
				// if (ultraRange <= 10) {
				// System.out.println("ultrarange less than 10, stopped");
				//
				// keepCamDrive = false;

				// }
			}

		} else {
			System.out.println("not visible.");
			keepCamDrive = false;
		}
		// System.out.println("distance (inches)" + ultraRange);
		if (keepCamDrive == true && ultraRange > 5) {
			manipulators.myRobot.tankDrive(0.5, 0.5);
		}
		return false;
	}

}
