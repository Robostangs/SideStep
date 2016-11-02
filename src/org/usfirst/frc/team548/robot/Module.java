package org.usfirst.frc.team548.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Module {
	private CANTalon drive, turn;
	private final double FULL_ROTATION = 1d, TURN_P, TURN_I, TURN_D, TURN_IZONE, OFFSET;
	
	/**
	 * Lets make a new module :)
	 * @param driveTalonID First I gotta know what talon we are using for driving
	 * @param turnTalonID Next I gotta know what talon we are using to turn
	 * @param tP I probably need to know the P constant for the turning PID
	 * @param tI I probably need to know the I constant for the turning PID
	 * @param tD I probably need to know the D constant for the turning PID
	 * @param tIZone I might not need to know the I Zone value for the turning PID
	 */
	public Module(int driveTalonID, int turnTalonID, double tP, double tI, double tD, double tIZone, double offset) {
		drive = new CANTalon(driveTalonID);
		turn = new CANTalon(turnTalonID);
		turn.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		TURN_P = tP;
		TURN_I = tI;
		TURN_D = tD;
		TURN_IZONE = tIZone;
		OFFSET = offset;
		turn.reverseOutput(false);
	}
	
	/**
	 * Setting turn motor power
	 * @param p value from -1 to 1
	 */
	public void setTurnPower(double p) {
		this.turn.changeControlMode(TalonControlMode.PercentVbus);
		this.turn.set(p);
		
	}

	/**
	 * Setting drive motor power
	 * @param p value from -1 to 1
	 */
	public void setDrivePower(double p) {
		this.drive.set(p);
	}

	/**
	 * Getting the turn encoder position
	 * @return turn encoder postition
	 */
	public double getTurnEncPos() {
		return turn.getPosition() -OFFSET;
	}

	/**
	 * Lets reset the turn encoder position to 0
	 */
	public void restTurnEnc() {
		this.turn.setPosition(0);
	}

	/**
	 * Is electrical good? Probably not.... Is the turn encoder connected?
	 * @return true if the encoder is connected
	 */
	public boolean isTurnEncConnected() {
		return turn.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative) == FeedbackDeviceStatus.FeedbackStatusPresent;
	}
	
	public int getTurnRotations() {
		return (int) (getTurnEncPos() / FULL_ROTATION);
	}
	
	
	public double getTurnLocation() {
		return  (getTurnEncPos() > 0 ) ? Math.abs(getTurnEncPos() - getTurnRotations()) : 1-Math.abs(getTurnEncPos() - getTurnRotations());
	}
	


	public void setTurnPIDToSetPoint(double setpoint) {
		turn.changeControlMode(TalonControlMode.Position);
		turn.set(setpoint);
	}
	
	/**
	 * Set turn to pos from 0 to 1 using PID
	 * @param setLoc location to set to
	 */
//	public void setTurnLocation(double setLoc) {
//		double error = Math.abs(setLoc - getTurnEncPos());
//		setTurnPIDToSetPoint(setLoc);
//		SmartDashboard.putNumber("Err Base", error);
//		SmartDashboard.putNumber("Set", turn.getSetpoint());
//		//SmartDashboard.putNumber("Base", tickBase);
//	}
	
//	public void setTurnLocation(double setLoc) {
//		setLoc = 1-setLoc;
//		double tickBase = getTurnRotations() * FULL_ROTATION;
//		if (setLoc - getTurnLocation() > .5) {
//			tickBase += (getTurnEncPos() >= 0) ? -FULL_ROTATION : FULL_ROTATION;
//		} else if (setLoc - getTurnLocation() < -.5) {
//			tickBase += (getTurnEncPos() >= 0) ? -FULL_ROTATION : FULL_ROTATION;
//		}
//		if(getTurnEncPos() > 0) {
//			setTurnPIDToSetPoint((setLoc * FULL_ROTATION) + (tickBase));
//		} else {
//			setTurnPIDToSetPoint((tickBase)-(setLoc * FULL_ROTATION));
//		}
//		
//		SmartDashboard.putNumber("Err Base", setLoc - getTurnLocation());
//		SmartDashboard.putNumber("Set", turn.getSetpoint());
//		SmartDashboard.putNumber("Base", tickBase);
//	}
	
	
	public void setTurnLocation(double loc) {
		//loc = 1-loc;
		//turn.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		turn.changeControlMode(TalonControlMode.Position);
		double base = getTurnRotations() * FULL_ROTATION;
		if (getTurnEncPos() >= 0) {
			if ((base + (loc * FULL_ROTATION)) - getTurnEncPos() < -FULL_ROTATION/2) {
				base += FULL_ROTATION;
			} else if ((base + (loc * FULL_ROTATION)) - getTurnEncPos() > FULL_ROTATION/2) {
				base -= FULL_ROTATION;
			}
			turn.set((((loc * FULL_ROTATION) + (base)))+OFFSET);
			//SmartDashboard.putNumber("Error", (base + (loc * FULL_ROTATION))- getTurnEncPos());
			
		} else {
			if ((base - ((1-loc) * FULL_ROTATION)) - getTurnEncPos() < -FULL_ROTATION/2) {
				base += FULL_ROTATION;
			} else if ((base -((1-loc) * FULL_ROTATION)) - getTurnEncPos() > FULL_ROTATION/2) {
				base -= FULL_ROTATION;
			}
			turn.set((base- (((1-loc) * FULL_ROTATION)))+OFFSET);
			//SmartDashboard.putNumber("Error", (base - ((1-loc) * FULL_ROTATION))- getTurnEncPos());
			
		}
		
		//SmartDashboard.putNumber("Set", turn.getSetpoint());
		//SmartDashboard.putNumber("Base", base);

		
	}
	
	public void stopBoth() {
		setDrivePower(0);
		setTurnPower(0);
	}
	
	public void stopDrive() {
		setDrivePower(0);
	}
	
	public void setFeedBackToQual() {
		turn.setFeedbackDevice(FeedbackDevice.QuadEncoder);
	}
}
