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
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	final String gyroAuto = "Gyro";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>(); 
	Sensing sensing;
	public DashboardCommunication (Sensing sensing){
		this.sensing = sensing; 
	}
	public void init() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		chooser.addObject("Gyro", gyroAuto);
		SmartDashboard.putData("Auto choices", chooser);
	}
	public void dashperiodic() {
		SmartDashboard.putNumber("Check Gyro", sensing.getGyroAngle());
		SmartDashboard.putNumber("Encoder1", sensing.getEncoder1()); 
//		SmartDashboard.putNumber("Counter: ", counter);
	}
	public String getSelectedAutonMode() {
		String selected = chooser.getSelected();
		System.out.println(chooser);
		//TODO: check if getSelected returned null (that would indicate nothing was
		//selected on the dashboard); figure out how to behave in that case
		//Return something useful here
		return selected;
	}
}
