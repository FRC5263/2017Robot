package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Ultrasonic;

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
	Encoder encoder1;
	Encoder encoder2;
	Encoder encoFlywheel;
	Ultrasonic ultra;
	
	public void reset() {
		
	}
	
	public void init(){
		
		Ultrasonic ultra = new Ultrasonic(1,1); //output, input
		ultra.setAutomaticMode(true);
		gyro = new ADXRS450_Gyro();
		encoder1 = new Encoder(0, 1);
		encoder2 = new Encoder(2, 3);
		
		encoFlywheel = new Encoder(8, 9);
		encoFlywheel.setPIDSourceType(PIDSourceType.kRate);
		encoFlywheel.setDistancePerPulse(2.9);
		encoFlywheel.setReverseDirection(true);
		
		
	}
	/**
	 * Gets number of degrees the robot has rotated since the last call
	 * to reset(). Positive values indicate rotation to the right, from the
	 * robot's perspective (clockwise if looking down from above).
	 * @return degrees rotated, or null if gyro is not healthy
	 */
	public Double getGyroAngle() {
		double gyroAngle = gyro.getAngle();
		return gyroAngle * (360.0/340.0); //(360.0/330.0);
	}
	public Double getEncoder1() {
		return (double) encoder1.get();
	}
	public Double getEncoder2() {
		return (double) encoder2.get();
	}
	public Double getFlywheelEncoder() {
		return (double) encoFlywheel.get();
	}
	public Double getUltraRange() {
		return (double) ultra.getRangeInches(); 
	}
	
	
	
	public PIDSource getGyroPIDSource(){
		return new PIDSource() {
			
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public double pidGet() {
				
				// TODO Auto-generated method stub
				return getGyroAngle();
			}
			
			@Override
			public PIDSourceType getPIDSourceType() {
				// TODO Auto-generated method stub
				
				return PIDSourceType.kDisplacement;
			}
		};
	}
	
	public PIDSource getFlywheelPIDSource(){
		return encoFlywheel;
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
