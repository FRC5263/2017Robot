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
	// double rep = 0;

	// int turnDegrees = 90;
	// int smallerDegrees = turnDegrees - 5;
	// int biggerDegrees = turnDegrees + 5;
	// int after;
	// int autoRunner;
	// int encoderSet; // delete
	private class drivestraight{
		double distance;
		double drivePower;
		drivestraight(double distance, double drivePower){
			this.distance = distance;
			this.drivePower = drivePower;
		}
	}
	private class rotate{
		double turn;
		rotate (double turn){
			this.turn = turn;
		}
	}
	int step = 0; // this will go up, for every completed step

	double[] turn = { 90, 90, 90, 90 };
	double[] distance = { -7, -7, -7, -7 };
	double[] drivePower = { 0.6, 0.6, 0.6, 0.6 };
	Object[] autosteps = {new rotate(90), new drivestraight(-7, .6)};
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
	boolean doRotate;
	double currentAngle;
	double drivingMin;
	double drivingMax;
	
	
	
	
	
	double leftSpeed;
	double rightSpeed;
	double targetAngle;
	double driveAngle;
	
	

	// ================================================

	// ================================================ for rotate
	double degrees;
	double pastDegrees;
	double angle;
	int rotateBeenDone;
	int rotateSet;
	double minMargin;
	double maxMargin;
	boolean doDrive;
	int rotateRunner;
	
	boolean overallRun;
	
	static final double kP = 0.05;
    static final double kI = 0.00;
    static final double kD = 0.00;
    static final double kF = 0.00;
    boolean runPID;
    double PIDTolerance = 2; //this is the "margin" of degrees the PID considers on target.

	PIDController turnController;
	
	// ===============================================

	public AutoVirtualDriver(Sensing sensing, CameraMan cameraMan, CameraMonitor cameraMonitor,
			Manipulators manipulators, DashboardCommunication dashComm) {

		this.sensing = sensing;
		this.manipulators = manipulators;
		this.dashComm = dashComm;

	}

	public void init(String mode) {
		sensing.gyro.reset();
		sensing.encoder1.reset();
		
		//===========================================
		//drive
		
		doRotate = false;
		
		//===========================================
		//rotate 
		
		rotateSet = 0;
		doDrive = true;
		rotateRunner = 0;
		overallRun = true;
		
		//===========================================
	}
	
	public void clean() {
		sensing.gyro.reset();
		sensing.encoder1.reset();
		runPID = false;
		overallRun = false;
		doDrive = false;
		doRotate = false;
		step = 0;
		encoderSet = 0;
		try{
			turnController.disable();
		} catch (Exception e){
			
		}
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
		//System.out.println("program terminated.");

	}

//=================================================================================================

	public boolean DriveStraightN(double driveDistance, double power) {


		encoder1Val = sensing.getEncoder1();
		driveAngle = sensing.getGyroAngle();
		encoderSet = encoderSet + 1;
		
		if (encoderSet == 1) {
			System.out.println("running drive");
			drivePulses = (driveDistance * 12) / (pi * 6) * 1440 / 4;
			sensing.encoder1.reset();
			//sensing.gyro.reset();
			encoderMin = drivePulses - 50;
			encoderMax = drivePulses + 50;
			//drivingMin = currentAngle - 5;
			//drivingMax = currentAngle + 5;
			targetAngle = sensing.getGyroAngle();
			
			
		}
		
		
		leftSpeed = power + (driveAngle - targetAngle) / 100; 
        rightSpeed = power - (driveAngle - targetAngle) / 100; 
        
        if (encoder1Val < encoderMin) {
			System.out.println("encoder val " + encoder1Val + " less than " + drivePulses + " power at " + power + " left speed at " + leftSpeed + " right speed at " + rightSpeed);
			manipulators.myRobot.tankDrive(-rightSpeed, -leftSpeed);
		} else if (encoder1Val > encoderMax) {
			System.out.println("encoder val " + encoder1Val + " more than " + drivePulses + " power at " + power + " left speed at " + leftSpeed + " right speed at " + rightSpeed);
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

//==================================================
	
	public boolean Rotate(double degrees) {
		
		rotateSet++;
		if (rotateSet == 1) {
			pastDegrees = pastDegrees + degrees;
			//minMargin = pastDegrees - 2;
			//maxMargin = pastDegrees + 2;
			//sensing.gyro.reset();
			System.out.println("ROTATE INITAL SET");
			
			runPID = true;
			turnController = new PIDController(kP, kD, kI, kF, sensing.getGyroPIDSource(), new PIDOutput() {
				
				@Override
				public void pidWrite(double output) {
					// TODO Auto-generated method stub
					manipulators.myRobot.tankDrive(output, -output);
					
				}
			});
			turnController.setSetpoint(degrees);
			turnController.setInputRange(-360.0f,  360.0f); //was -180 180
	        turnController.setOutputRange(-1.0, 1.0);
	        turnController.setAbsoluteTolerance(PIDTolerance);
			 
		}

		angle = sensing.getGyroAngle();
		System.out.println("output " + turnController.get());
		System.out.println("error " + turnController.getError());
		System.out.println("angle " + angle);
		
		
		if ( runPID ) {
            turnController.enable();
        } else {
        	turnController.disable();
        }
		System.out.println("runPID " + runPID);
		if(turnController.onTarget()){
			rotateBeenDone ++;
		}else{
			rotateBeenDone = 0;
		}
		System.out.println();
		//if(rotateBeenDone > 50){
		//	runPID = false;
		//	return false;
		//}
		
		
		 
		 
		 

		/**if (angle < minMargin) {
			manipulators.myRobot.tankDrive(0.6, -0.6);
			System.out.println("angle " + angle + " less than specified " + degrees);
			rotateBeenDone = 0;
		} else if (angle > maxMargin) {
			manipulators.myRobot.tankDrive(-0.6, 0.6);
			System.out.println("angle " + angle + " less than specified " + degrees);
			rotateBeenDone = 0;
		} else {
			manipulators.myRobot.tankDrive(0, 0);
			rotateBeenDone = rotateBeenDone + 1;
			System.out.println("rotate been done " + rotateBeenDone);
			if (rotateBeenDone > 50) {
				runPID = false;
				return false;
			}
		}
		**/
		
		return true;

	}

}
