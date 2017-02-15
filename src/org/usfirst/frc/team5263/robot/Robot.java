package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	Command autonomousCommand;
	SendableChooser autoChooser; 
	Sensing sensing = new Sensing();
	Manipulators manipulators = new Manipulators(sensing);
	CameraMan cameraMan = new CameraMan(sensing, manipulators);
	CameraMonitor cameraMonitor = new CameraMonitor(cameraMan);
	Diagnostics diagnostics = new Diagnostics();
	DashboardCommunication dashComm = new DashboardCommunication(sensing, diagnostics);
	AutoVirtualDriver virtualDriver = new AutoVirtualDriver(sensing, cameraMan, cameraMonitor, manipulators, dashComm);
	TeleOperated teleOp = new TeleOperated(sensing, cameraMan, cameraMonitor, manipulators);
//	Object drive = new DriveStraight(10, sensing, manipulators, virtualDriver);
//	Object rotate = new Rotate(10, sensing, manipulators, virtualDriver);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		dashComm.init();
		sensing.reset();
		sensing.init();
		cameraMonitor.CameraInit();
		diagnostics.init();
		manipulators.init();
		
	
		
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
		virtualDriver.init(dashComm.getSelectedAutonMode());
	}
	
	

	@Override
	public void robotPeriodic() {
		// TODO Auto-generated method stub
		super.robotPeriodic();
		dashComm.dashperiodic();
		diagnostics.diagperiodic();
		cameraMonitor.periodicFunction();
	}

	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub
		super.teleopInit();
		cameraMan.lookForward();
		virtualDriver.clean();
	}

	@Override
	public void testInit() {
		// TODO Auto-generated method stub
		super.testInit();
	
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		virtualDriver.periodicAuto();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		teleOp.Periodic();

		dashComm.dashperiodic();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
	

}

