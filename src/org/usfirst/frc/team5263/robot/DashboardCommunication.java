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
	final String baseline = "baseline";
	final String redShoot = "redShoot";
	final String blueShoot = "blueShoot";
	final String Camdrive = "Camdrive";

	double newRotationKP;
	double newRotationKI;
	double newRotationKD;
	double newRotationKF;
	
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
		chooser.addObject("Break the baseline", baseline);
		chooser.addObject("Red Shoot", redShoot);
		chooser.addObject("Blue Shoot", blueShoot);
		chooser.addObject("Camera Drive", Camdrive);
		SmartDashboard.putData("Auto choices", chooser);
	}
	public void dashperiodic() {
		SmartDashboard.putNumber("Check Gyro", sensing.getGyroAngle());
		SmartDashboard.putNumber("Encoder1: ", sensing.getEncoder1()); 
		SmartDashboard.putNumber("Encoder2: ", sensing.getEncoder2());
		SmartDashboard.putNumber("Ultra Range: ", sensing.getUltraRange());
		SmartDashboard.getNumber("Counter: ", 0);
		SmartDashboard.putNumber("CAN Value", diagnostics.getLeftMotorCurrent());

		if (!SmartDashboard.containsKey("Rotation Kp")) {
			SmartDashboard.putNumber("Rotation Kp", 0.0034);
			SmartDashboard.putNumber("Rotation Ki", 0.0005);
			SmartDashboard.putNumber("Rotation Kd", 0.0004);
			SmartDashboard.putNumber("Rotation Kf", 0.00);
		}
		
		newRotationKP = SmartDashboard.getNumber("Rotation Kp", 0.0034);
		newRotationKI = SmartDashboard.getNumber("Rotation Ki", 0.0005);
		newRotationKD = SmartDashboard.getNumber("Rotation Kd", 0.0004);
		newRotationKF = SmartDashboard.getNumber("Rotation Kf", 0.00);
	}
	public double getNewRotationKP() {
		return newRotationKP;
	}
	public double getNewRotationKI() {
		return newRotationKI;
	}
	public double getNewRotationKD() {
		return newRotationKD;
	}
	public double getNewRotationKF() {
		return newRotationKF;
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
