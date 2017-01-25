package org.usfirst.frc.team5263.robot;

import java.lang.reflect.Array;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoVirtualDriver {
	

	Sensing sensing;
	Manipulators manipulators;
	double rep = 0;
	double angleOffset;
	double firstAngle;
	double encoderMin;
	double encoderMax;
	
	int turnDegrees = 90;
	int smallerDegrees = turnDegrees - 5;
	int biggerDegrees = turnDegrees + 5;
	int after;
	int autoRunner;
	int encoderSet;
	
	int step = 0; //this will go up, for every completed step
	int steps = 10; //This variable is a finite number, the number of tasks to complete
	double[] turn = {90, 90, 90};
	double[] distance = {200, 200, 200};
	double encoder1Val;
	double angle;
	
	public AutoVirtualDriver(Sensing sensing, CameraMan cameraMan, CameraMonitor cameraMonitor, Manipulators manipulators, DashboardCommunication dashComm) {
		
		this.sensing = sensing;
		this.manipulators = manipulators; 
		
		
	}
	
	public void init(String mode) {
		angleOffset  = sensing.getGyroAngle();
		firstAngle = angleOffset;
		
		
		encoder1Val = sensing.getEncoder1();
		angleOffset = sensing.getGyroAngle();
		angle = angleOffset - firstAngle;
		/**
		System.out.println("steps" + steps);
		turn[steps] = 90;
		distance[steps] = 50;
		steps ++;
		turn[steps] = 180;
		distance[steps] = 30;
		steps++;
		turn[steps] = 45;
		distance[steps] = 100;
		**/
		autoRunner = 1;
		
	}
	
	public void periodicAuto() { 
		
		
		//SmartDashboard.putInt("Encoder1", encoder1.get()); 
		

		
		
		switch (autoRunner){

		case 1: 
			encoder1Val = sensing.getEncoder1();
			angleOffset = sensing.getGyroAngle();
			angle = angleOffset - firstAngle;
			System.out.println("angle Offset: " + angleOffset);
			System.out.println("first angle: " + firstAngle);
			rep = rep + 1;
			System.out.println("counter: " + rep);
			
		
			if(rep == 1){
				angle = angle - angleOffset; 
			}
			
			autoRunner = 2;
			break;
			
			
			
		case 2: 
			encoder1Val = sensing.getEncoder1();
			angleOffset = sensing.getGyroAngle();
			angle = angleOffset - firstAngle;
			double minMargin = turn[step] - 5;
			double maxMargin = turn[step] + 5;
			
			if(angle < minMargin){
				
				if(minMargin - angle > 20){
					manipulators.myRobot.tankDrive(0.6, -0.6);
					System.out.println("fast min");
				}else{
					manipulators.myRobot.tankDrive(0.375, -0.375);
					System.out.println("slow min");
					}
				System.out.println("angle " + angle + " less than specified " + turn[step]);
				
			}else if(angle > maxMargin){
				
				if(angle - maxMargin > 20){
					manipulators.myRobot.tankDrive(-0.6, 0.6);
					System.out.println("fast max");
				}else {
					manipulators.myRobot.tankDrive(-0.375, -0.375);
					System.out.println("slow max");
					}
				System.out.println("angle " + angle + " more than specified " + turn[step]);
				
			}else{
				autoRunner = 3;
			}
	
			break;
			
			
		case 3: 
			encoder1Val = sensing.getEncoder1();
			System.out.println("finished turn steps");
			encoderSet = encoderSet + 1;
			if(encoderSet == 1){
				sensing.encoder1.reset();
				encoderMin = distance[step] - 10;
				encoderMax = distance[step] + 10;
			}
			if(encoder1Val < encoderMin){
				System.out.println("encoder val " + encoder1Val + " less than " + distance[step]);
				manipulators.myRobot.tankDrive(0.4, 0.4); 
			}else if(encoder1Val > encoderMax){
				System.out.println("encoder val " + encoder1Val + " more than " + distance[step]);
				manipulators.myRobot.tankDrive(-0.4, -0.4); 
			}else{
				if(step < steps){
					step = step + 1;
					System.out.println("STEP HIGHER");
					autoRunner = 1;
				}
			}
		
		
		
			break;
		}
		
		
		
		
		
		
		
		
		
		/**
		
		double encoder1Val = sensing.getEncoder1();
		angleOffset = sensing.getGyroAngle();
		System.out.println("angle Offset: " + angleOffset);
		System.out.println("first angle: " + firstAngle);
		rep = rep + 1;
		System.out.println("counter: " + rep);
		double angle = angleOffset - firstAngle;
		
	
		if(rep == 1){
			angle = angle - angleOffset; 
		}
		**/
		
		// =======================================================================================
		
		/**
		if(angle < smallerDegrees && rep < 500){
			manipulators.myRobot.tankDrive(0.6, -0.6); //try this
			System.out.println("angle smaller than specified :" + angle);
		}else if(angle > biggerDegrees && rep < 500){
			manipulators.myRobot.tankDrive(-0.6, 0.6); //maybe change
			System.out.println("angle larger than specified :" + angle );
		}
		else{
			if(rep < 500 && encoder1Val < 100){
				manipulators.myRobot.tankDrive(0.4, 0.4);
				System.out.println("driving straght");
			}
			
		}
		**/
		
		//===================================================================================================
		
		/**
		
		double minMargin = turn[step] - 5;
		double maxMargin = turn[step] + 5;
		
		if(angle < minMargin){
			if(minMargin - angle > 20){
				manipulators.myRobot.tankDrive(0.6, -0.6);
				System.out.println("fast min");
			}else {
				manipulators.myRobot.tankDrive(0.375, -0.375);
				System.out.println("slow min");
				}
			
			System.out.println("angle " + angle + " less than specified " + turn[step]);
		}else if(angle > maxMargin){
			if(angle - maxMargin > 20){
				manipulators.myRobot.tankDrive(-0.6, 0.6);
				System.out.println("fast max");
			}else {
				manipulators.myRobot.tankDrive(-0.375, -0.375);
				System.out.println("slow max");
				}
			System.out.println("angle " + angle + " more than specified " + turn[step]);
		}else{
			System.out.println("finished turn steps");
			after = after + 1;
			if(after == 1){
				afterTurn = encoder1Val;
			}
			System.out.println("encoder val " + encoder1Val);
			System.out.println("AFTER TURN " + afterTurn + " AFTER " + after);
			if(encoder1Val < (distance[step] + afterTurn)){
				System.out.println("encoder val " + encoder1Val + " less than " + distance[step] + " plus " + " after turn " + afterTurn + " : " + distance[step] + afterTurn);
				manipulators.myRobot.tankDrive(0.4, 0.4); //what i saw was that the encoder val goes up from turning, reset that with a variable.
			}else{
				System.out.println("finished turn step");
				if(step < steps){
					//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa steps " + steps + " step " + step);
					step ++;
				}
				System.out.println("steps writtern: " + steps); 
				System.out.println("current step: " + step);
			}
		};
		
		
		**/
	}
}
