package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.Encoder;

public class DriveStraight {
	
	     double distance;
	     
	     int encoderSet;
	     double encoder1Val;
	     
	     
	     public DriveStraight(double distance, Sensing sensing, Manipulators manipulators) {
	          this.distance = distance;
	          
	          
	  		encoder1Val = sensing.getEncoder1();
			System.out.println("running drive");
			System.out.println("step " + step + " steps " + steps);
			encoderSet = encoderSet + 1;
			if(encoderSet == 1){
				sensing.encoder1.reset();
				encoderMin = distance[step] - 50;
				encoderMax = distance[step] + 50;
			}
			if(encoder1Val < encoderMin){
				System.out.println("encoder val " + encoder1Val + " less than " + distance[step]);
				manipulators.myRobot.tankDrive(0.4, 0.4); 
			}else if(encoder1Val > encoderMax){
				System.out.println("encoder val " + encoder1Val + " more than " + distance[step]);
				manipulators.myRobot.tankDrive(-0.4, -0.4); 
			}else{
				if(step < steps - 1){ //whats happening is that this is repeated over and over and always defaults to the else, and the condition is true
					step = step + 1;
					System.out.println("STEP HIGHER");
					autoRunner = 4;
					
				}
			}
	     }

	
}
