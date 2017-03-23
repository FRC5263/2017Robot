package org.usfirst.frc.team5263.robot;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * DashboardCommunication deals with all communication to/from the
 * drivers via the dashboard. This includes diagnostics, autonomous settings,
 * teleop assist communication, etc.
 */
public class DashboardCommunication {
	double AutoKey;
	final String centGear = "centerGear";
	final String stop = "stop";
	final String gyroAuto = "Gyro";
	Diagnostics diagnostics; 
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>(); 
	Sensing sensing;
	 public DashboardCommunication(Sensing sensing, Diagnostics diagnostics) {
		 this.diagnostics = diagnostics;
		 this.sensing = sensing; 

	 }
	public void init() {
		chooser.addDefault("Center Gear", centGear);
		chooser.addObject("Stop", stop);
		chooser.addObject("Gyro", gyroAuto);
		SmartDashboard.putData("Auto choices", chooser);
	}
	public void dashperiodic() {
		SmartDashboard.putNumber("Check Gyro", sensing.getGyroAngle());
		SmartDashboard.putNumber("Encoder1: ", sensing.getEncoder1()); 
		SmartDashboard.putNumber("Encoder2: ", sensing.getEncoder2());
		SmartDashboard.putNumber("Ultra Range: ", sensing.getUltraRange());
		//SmartDashboard.putNumber("feet traveled", (driveDistanceFeet * 12) / (pi * 6) * 1440 / 4;)
		SmartDashboard.getNumber("Counter: ", 0);
		//		SmartDashboard.putNumber("Counter: ", counter);
		SmartDashboard.putNumber("CAN Value", diagnostics.getLeftMotorCurrent());
	}
	public String getSelectedAutonMode() {
		String selected = chooser.getSelected();
		System.out.println(chooser);
		//TODO: check if getSelected returned null (that would indicate nothing was
		//selected on the dashboard); figure out how to behave in that case
		//Return something useful here
		return selected;
	
	}
//	public Double getFoo(){
//		                                                                                                                                                                         
//	}
}
