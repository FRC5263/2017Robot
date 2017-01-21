package org.usfirst.frc.team5263.robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Diagnostics {
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	double current = pdp.getCurrent(14);
	public void init() {
		// TODO Auto-generated method stub
		
	}
	public void diagperiodic() {
		
	}
	
}
