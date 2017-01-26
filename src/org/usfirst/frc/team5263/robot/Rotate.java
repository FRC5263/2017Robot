package org.usfirst.frc.team5263.robot;

public class Rotate {

	
	double degrees;

	double angleOffset;
	double firstAngle;

	double angle;
	
	
	public Rotate(double degrees, Sensing sensing, Manipulators manipulators, AutoVirtualDriver virtualDriver) {
		this.degrees = degrees;

		angleOffset  = sensing.getGyroAngle();
		firstAngle = angleOffset;
		angle = angleOffset - firstAngle;
		double minMargin = degrees - 5;
		double maxMargin = degrees + 5;
		
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
		
	}
	
	
}
