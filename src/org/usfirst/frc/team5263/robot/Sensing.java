package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/**
 * This class is responsible for all sensors except the camera.
 * It is the only class which will directly read values from any
 * sensor. It runs periodic diagnostics on all sensors to verify
 * that the sensor data is in range and live.
 */
public class Sensing {
	/**
	 * Resets gyroscope angle and encoders to 0.
	 */
	ADXRS450_Gyro gyro;
	
	public void reset() {
		
	}
	
	public void init(){
		gyro = new ADXRS450_Gyro();
		
	}
	/**
	 * Gets number of degrees the robot has rotated since the last call
	 * to reset(). Positive values indicate rotation to the right, from the
	 * robot's perspective (clockwise if looking down from above).
	 * @return degrees rotated, or null if gyro is not healthy
	 */
	public Double getGyroAngle() {
		return gyro.getAngle();
	}
	
	/**
	 * Gets number of feet the given wheel has traveled since the last
	 * call to reset(). Positive values indicate forward motion.
	 * @param wheel Which wheel's encoder should be returned:
	 *               0: Front-left
	 *               1: Front-right
	 *               2: Rear-left
	 *               3: Rear-right
	 * @return distance traveled, or null if encoder is not healthy
	 */
	public Double getWheelDistance(int wheel) {
		//TODO: implement me!
		return (double)0;
	}
	
	/**
	 * Called repeatedly while the robot is powered up.
	 * Does diagnostics to check that sensor values look good.
	 * 
	 * @return true if all sensors pass diagnostics, false otherwise.
	 */
	public boolean doPeriodicDiagnostics() {
		return false;
	}
}
