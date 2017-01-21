package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Diagnostics {
	PowerDistributionPanel pdp;
	double current;
	public void init() {
		pdp = new PowerDistributionPanel();

	 	
	}
	public void diagperiodic() {
		 current = pdp.getCurrent(14);
	}
	
}
