package org.usfirst.frc.team5263.robot;
import edu.wpi.first.wpilibj.ADXRS450_Gyro; 
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * DashboardCommunication deals with all communication to/from the
 * drivers via the dashboard. This includes diagnostics, autonomous settings,
 * teleop assist communication, etc.
 */
public class DashboardCommunication {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	SendableChooser<String> chooser = new SendableChooser<>(); 
	public void init() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		SmartDashboard.putNumber("Check Gyro", gyro.getAngle());
		//I'm testing git from home. Delete me!  - Evan
	}
	
	public String getSelectedAutonMode() {
		String selected = chooser.getSelected();
		//TODO: check if getSelected returned null (that would indicate nothing was
		//selected on the dashboard); figure out how to behave in that case
		//Return something useful here
		return null;
	}
}
