
package org.usfirst.frc.team548.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private XboxController xbox;
	private boolean fun = true;
	private DriverStation dt;
    public void robotInit() {
      xbox = new XboxController(Constants.XBOX_PORT);
      DriveTrain.getInstance();
      dt = DriverStation.getInstance();
    }
    

    public void autonomousInit() {

    }

    public void autonomousPeriodic() {
    	DriveTrain.setAllLocation(0);
    	DriveTrain.stopDrive();
    }
    
   
    public void disabledPeriodic() {
    	DriveTrain.setOffSets();
    	xbox.setRightRumble(0);
    	xbox.setLeftRumble(0);
    }
  
    public void teleopPeriodic() {    	
    	 //DriveTrain.fieldCentricDrive(xbox.getLeftStickYAxis(), xbox.getLeftStickXAxis(), powTwoThing(xbox.getRightStickXAxis()));
    	DriveTrain.pidDrive(xbox.getLeftStickYAxis(), xbox.getLeftStickXAxis(), changeAngle(xbox.getRightStickXAxis(), xbox.getRightStickYAxis()));
    	// DriveTrain.tankDrive(xbox.getLeftStickYAxis(), xbox.getRightStickYAxis());
    	//DriveTrain.humanDrive(xbox.getLeftStickYAxis(), xbox.getLeftStickXAxis(), Math.pow(xbox.getRightStickXAxis(), 3));
    	SmartDashboard.putBoolean("Big Bird Turn Encoder", DriveTrain.isBigBirdTurnEncConnected());
    	SmartDashboard.putBoolean("Big Horse Turn Encoder", DriveTrain.isBigHorseTurnEncConnected());
    	SmartDashboard.putBoolean("Big Giraffe Turn Encoder", DriveTrain.isBigGiraffeTurnEncConnected());
    	SmartDashboard.putBoolean("Big Sushi Turn Encoder", DriveTrain.isBigSushiTurnEncConnected());
    	//SmartDashboard.putNumber("KEY", DriveTrain.bigSushi.getTurnEncPos());
    	SmartDashboard.putNumber("Hyro", DriveTrain.getHyroAngle());
    	SmartDashboard.putNumber("Avg. Error", DriveTrain.getAverageError());
    	xbox.setRightRumble(Math.pow(DriveTrain.getAverageError()/1300d, 2));
    	xbox.setLeftRumble((dt.isBrownedOut() ? 1 : 0));
    
    	//DriveTrain.resetOffSet();
    }
    
    public void testPeriodic() {
    	//DriveTrain.resetAllEnc();
    	LiveWindow.addSensor("DriveSystem", "Hyro", DriveTrain.getHyro());
    }
    
    
    
    private double powTwoThing(double v) {
    	return (v > 0 ) ? Math.pow(v, 2) : -Math.pow(v, 2);
    }
    
    public double changeAngle(double x, double y) {
    	if (Math.sqrt((x*x)+(y*y)) > .5) {
			return Math.atan2(x, y)*(180d/Math.PI);
		} else {
			return 0;
		}
    }
    
}
