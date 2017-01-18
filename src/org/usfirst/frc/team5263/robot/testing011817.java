package org.usfirst.frc.team5263.robot;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
//2017 Main Robot Code
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	RobotDrive myRobot;
	Joystick main;
	ADXRS450_Gyro gyro; //isaiah
	
	Servo servo_top1;
	Servo servo_top2;
	Servo servo_bot1;
	Servo servo_bot2;
	Servo push_servo;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);

		myRobot = new RobotDrive(0, 1);
		main = new Joystick(0);
		
		gyro = new ADXRS450_Gyro(); //isaiah
		
		servo_top1 = new Servo(5);
		servo_top1.setAngle(10);
		servo_top2 = new Servo(8);
		servo_top2.setAngle(170);
		
		servo_bot1 = new Servo(9);
		servo_bot1.setAngle(0);
		servo_bot2 = new Servo(6);
		servo_bot2.setAngle(180);
		
		push_servo = new Servo(7);
		push_servo.setAngle(180);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// isaiah
			double rep = 0;
			rep = rep + 1;
			System.out.println("counter: " + rep);
			double angle = gyro.getAngle();
			if(angle < 170 && rep < 1000){
				myRobot.tankDrive(-0.1, 0.1);; //try this
				System.out.println("angle smaller than 170 :" + angle );

			}else if(angle > 190 && rep < 1000){
				myRobot.tankDrive(0.1, -0.1); //maybe change
				System.out.println("angle larger than 190 :" + angle );
			}else{
				if(rep < 1000){
					myRobot.tankDrive(0.1, 0.1);
					System.out.println("driving straght");
				}
				
			}
			
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		myRobot.tankDrive(main.getRawAxis(5), main.getRawAxis(1));
	
		if(main.getRawButton(6)){
			servo_top1.setAngle(180);
			servo_top2.setAngle(0);
			
			servo_bot1.setAngle(180);
			servo_bot2.setAngle(0);
		}else if(main.getRawButton(5)){
			servo_top1.setAngle(10);
			servo_top2.setAngle(170);
			
			servo_bot1.setAngle(0);
			servo_bot2.setAngle(180);
			
			push_servo.setAngle(180);
		}else if(main.getRawButton(2)){
			push_servo.setAngle(90);
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

