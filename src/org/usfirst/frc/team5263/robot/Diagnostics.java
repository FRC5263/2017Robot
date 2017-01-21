package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Diagnostics {
	PowerDistributionPanel pdp;
	double current;
	public void init() {
		pdp = new PowerDistributionPanel();

	 	
	}
	public void diagperiodic() {
		 current = pdp.getCurrent(0);
	}
	public double getLeftMotorCurrent() {
		// TODO Auto-generated method stub
		return pdp.getCurrent(1);
	}
	
}
