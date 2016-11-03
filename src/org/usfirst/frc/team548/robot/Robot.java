
package org.usfirst.frc.team548.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private XboxController xbox;
	private boolean fun = true;
	
    public void robotInit() {
      xbox = new XboxController(Constants.XBOX_PORT);
      DriveTrain.getInstance();
    }
    

    public void autonomousInit() {

    }

    public void autonomousPeriodic() {
    	DriveTrain.setAllLocation(0);
    	DriveTrain.stopDrive();
    }
    
   
    public void disabledPeriodic() {
    	DriveTrain.setOffSets();
    }
  
    public void teleopPeriodic() {    	
    	 DriveTrain.fieldCentricDrive(xbox.getLeftStickYAxis(), xbox.getLeftStickXAxis(), Math.pow(xbox.getRightStickXAxis(), 3));
//    	else DriveTrain.fieldCentricDrive(xbox.getLeftStickYAxis()*.75, xbox.getLeftStickXAxis()*.75, Math.pow(xbox.getRightStickXAxis()*.75, 3));
//    	if(xbox.getXButton() && xbox.getLeftBumper()) fun = false;
//    	else if(xbox.getStartButton()) fun = true;
    	
    	SmartDashboard.putBoolean("Big Bird Turn Encoder", DriveTrain.isBigBirdTurnEncConnected());
    	SmartDashboard.putBoolean("Big Horse Turn Encoder", DriveTrain.isBigHorseTurnEncConnected());
    	SmartDashboard.putBoolean("Big Giraffe Turn Encoder", DriveTrain.isBigGiraffeTurnEncConnected());
    	SmartDashboard.putBoolean("Big Sushi Turn Encoder", DriveTrain.isBigSushiTurnEncConnected());
    	//SmartDashboard.putNumber("KEY", DriveTrain.bigSushi.getTurnEncPos());
    	SmartDashboard.putNumber("Hyro", DriveTrain.getHyroAngle());
    	
    }
    
    public void testPeriodic() {
    	//DriveTrain.resetAllEnc();
    }
    
    
   
    
}
