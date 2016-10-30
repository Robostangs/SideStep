
package org.usfirst.frc.team548.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private XboxController xbox;
	
    public void robotInit() {
      xbox = new XboxController(Constants.XBOX_PORT);
      DriveTrain.getInstance();
    }
    

    public void autonomousInit() {

    }

    public void autonomousPeriodic() {
    	DriveTrain.setAllLocation(0);
    }
    
   
    public void disabledPeriodic() {
    	DriveTrain.setOffSets();
    }
  
    public void teleopPeriodic() {    	
    	//DriveTrain.humanDrive(xbox.getLeftStickYAxis(), xbox.getLeftStickXAxis(), xbox.getRightStickXAxis());
    	DriveTrain.fieldCentricDrive(xbox.getLeftStickYAxis(), xbox.getLeftStickXAxis(), xbox.getTriggers());
    	SmartDashboard.putBoolean("Big Bird Turn Encoder", DriveTrain.isBigBirdTurnEncConnected());
    	SmartDashboard.putBoolean("Big Horse Turn Encoder", DriveTrain.isBigHorseTurnEncConnected());
    	SmartDashboard.putBoolean("Big Giraffe Turn Encoder", DriveTrain.isBigGiraffeTurnEncConnected());
    	SmartDashboard.putBoolean("Big Sushi Turn Encoder", DriveTrain.isBigSushiTurnEncConnected());
    	SmartDashboard.putNumber("Hyro", DriveTrain.getHyroAngle());
    }
    
    public void testPeriodic() {
    	//DriveTrain.resetAllEnc();
    }
    
    
   
    
}
