package org.usfirst.frc.team5263.robot;

/**
 * CameraMan's responsibility is to interpret the telemetry coming from the camera.
 * This interpretation is provided to other classes for use during autonomous primarily,
 * possibly during teleop.
 * 
 * Sensing is provided so that CameraMan has gyroscope readings to use in
 * camera stabilization, i.e. the camera can pan as the robot rotates to
 * fixate on target.
 */

public class CameraMan implements ICameraTelemetryReceiver {
	Manipulators manipulators;
	public CameraMan(Sensing sensing, Manipulators manipulators) {
		this.manipulators = manipulators;
	}
	/**
	 * Called by CameraMonitor when GRIP sends us new data.
	 * TODO: needs parameters added
	 */
	public void onTelemetryReceived() {
		
	}
	
	/**
	 * Starts looking for vision goal at top of furnace stack.
	 * TODO: possibly tilts camera up, possibly starts panning around looking for goal.
	 */
	public void lookForFurnace() {
		
	}
	
	/**
	 * Starts looking for vision goal on airship gear loading station.
	 * TODO: possibly tilts camera down and starts panning looking for goal.
	 */
	public void lookForAirship() {
		
	}
	
	/**
	 * Sets camera in a forward-looking static position so the drivers can view
	 * the feed at the dashboard.
	 * TODO: Might change exposure settings to human-viewable?
	 */
	public void lookForward() {
		manipulators.servo1.setAngle(95);
	}	
	public Double getTargetAngle() {
		return 0.0;
	}
	
	public Double getTargetDistance() {
		return 0.0;
	}
}
