package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * This class contains reference to all motor driver channels, all pneumatics,
 * all servos etc, and provides functions to manage the state of these channels
 * to anyone wishing to control them--teleop and all auton modes.
 */

public class Manipulators {
	Sensing sensing;

	Victor flywheel;
	Victor climber;
	Victor sweeper;
	Servo servo1;
	PIDController pidMotor;
	boolean isAutoFlywheel = false;
	boolean flywheelPIDEnbled;

	public Manipulators(Sensing sensing) {

		this.sensing = sensing;

	}

	public RobotDrive myRobot = new RobotDrive(0, 1);
	
	
	public class MyPIDOutputEncoder implements PIDOutput {
		@Override
		public void pidWrite(double output) {
			//System.out.println("outout " + output);
			//System.out.println("error " + pidMotor.getError());
			//System.out.println("rate " + sensing.encoFlywheel.getRate());
			flywheel.setInverted(false);
			flywheel.set(output);
			
		}
	}

	public void init() {
		System.out.println("running manipulators");
		flywheel = new Victor(2);
		climber = new Victor(3);
		sweeper = new Victor(4);
		sweeper.setInverted(true);
		flywheel.setInverted(true);
		servo1 = new Servo(5);
		
		pidMotor = new PIDController(0.0001, 0, 0, 0, sensing.getFlywheelPIDSource(), new MyPIDOutputEncoder());
		pidMotor.disable();
		pidMotor.setAbsoluteTolerance(20);
		// pidMotor.setOutputRange(-1, 0);
		pidMotor.initTable(NetworkTable.getTable("pidtable"));


	} 
	
	public void flywheelEnabled(boolean flywheelRun) {
		if  (flywheelRun == true) {
			flywheelPIDEnbled = true;  
			pidMotor.enable();
			//System.out.println("flywheelrun true");
		} else if (flywheelRun == false){
			//System.out.println("flywheelrun false");
			flywheelPIDEnbled = false; 
			pidMotor.disable();
		}
	}
	public void flywheelSetPoint(double setpoint){

		//System.out.println("flywheel set setpoint");
		pidMotor.setSetpoint(-setpoint);
	}
	public boolean flywheelDone (){
		if(pidMotor.onTarget()){

			//System.out.println("flywheel on target");
			return true;
		} else {

			//System.out.println("flywheel not on target");
			return false;
		}
	}

	
}
