package org.usfirst.frc.team548.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain {

	private static DriveTrain instance;
	private static Module bigBird, bigHorse, bigGiraffe, bigSushi;
	private static AHRS hyro;

	public static DriveTrain getInstance() {
		if (instance == null)
			instance = new DriveTrain();
		return instance;
	}

	private DriveTrain() {
		bigBird = new Module(Constants.DT_BB_DRIVE_TALON_ID,
				Constants.DT_BB_TURN_TALON_ID, 4.2, 0.01, 0, 200);// Bottom right
		bigHorse = new Module(Constants.DT_BH_DRIVE_TALON_ID,
				Constants.DT_BH_TURN_TALON_ID, 4.2, 0.01, 0, 200); // Top left
		bigGiraffe = new Module(Constants.DT_BG_DRIVE_TALON_ID,
				Constants.DT_BG_TURN_TALON_ID, 4.2, 0.01, 0, 200); // Top right
		bigSushi = new Module(Constants.DT_BS_DRIVE_TALON_ID,
				Constants.DT_BS_TURN_TALON_ID, 4.2, 0.01, 0, 200); // Bottom left

		hyro = new AHRS(SPI.Port.kMXP);
	}

	public static void setDrivePower(double bbPower, double bhPower,
			double bgPower, double bsPower) {
		bigBird.setDrivePower(bbPower);
		bigHorse.setDrivePower(bhPower);
		bigGiraffe.setDrivePower(bgPower);
		bigSushi.setDrivePower(bsPower);
	}

	public static void setTurnPower(double bbPower, double bhPower,
			double bgPower, double bsPower) {
		bigBird.setTurnPower(bbPower);
		bigHorse.setTurnPower(bhPower);
		bigGiraffe.setTurnPower(bgPower);
		bigSushi.setTurnPower(bsPower);
	}

	public static void setLocation(double bbLoc, double bhLoc, double bgLoc,
			double bsLoc) {
		bigBird.setTurnLocation(bbLoc);
		bigHorse.setTurnLocation(bhLoc);
		bigGiraffe.setTurnLocation(bgLoc);
		bigSushi.setTurnLocation(bsLoc);
	}

	public static void setAllTurnPower(double power) {
		setTurnPower(power, power, power, power);
	}

	public static void setAllDrivePower(double power) {
		setDrivePower(power, power, power, power);
	}

	public static void setAllLocation(double loc) {
		setLocation(loc, loc, loc, loc);
	}

	private static final double l = 21, w = 21, r = Math
			.sqrt((l * l) + (w * w));

	private static double lasta1 = 0, lasta2 = 0, lasta3 = 0, lasta4 = 0;
	public static void humanDrive(double fwd, double str, double rot) {
		if (Math.abs(rot) < 0.01)
			rot = 0;
		
		double a = str - (rot * (l / r));
		double b = str + (rot * (l / r));
		double c = fwd - (rot * (w / r));
		double d = fwd + (rot * (w / r));

		double ws1 = Math.sqrt((b * b) + (c * c));
		double ws2 = Math.sqrt((b * b) + (d * d));
		double ws3 = Math.sqrt((a * a) + (d * d));
		double ws4 = Math.sqrt((a * a) + (c * c));

		double wa1 = Math.atan2(b, c) * 180 / Math.PI;
		double wa2 = Math.atan2(b, d) * 180 / Math.PI;
		double wa3 = Math.atan2(a, d) * 180 / Math.PI;
		double wa4 = Math.atan2(a, c) * 180 / Math.PI;

		double max = ws1;
		max = Math.max(max, ws2);
		max = Math.max(max, ws3);
		max = Math.max(max, ws4);
		if (max > 1) {
			ws1 /= max;
			ws2 /= max;
			ws3 /= max;
			ws4 /= max;
		}
		
		if (Math.abs(fwd) < .15 && Math.abs(str) < .15 && Math.abs(rot) < 0.01) {
//			wa1 = lasta1;
//			wa2 = lasta2;
//			wa3 = lasta3;
//			wa4 = lasta4;
			//rot = 0;
			stopDrive();
		} else {
			lasta1 = wa1;
			lasta2 = wa2;
			lasta3 = wa3;
			lasta4 = wa4;
			
		}
		DriveTrain.setDrivePower(ws4, ws2, ws1, ws3);
		DriveTrain.setLocation(angleToLoc(wa4), angleToLoc(wa2),
				angleToLoc(wa1), angleToLoc(wa3));
		
//		SmartDashboard.putNumber("Wheel loc", angleToLoc(wa1));
//		SmartDashboard.putNumber("Wheel Angle", wa1);
//		SmartDashboard.putNumber("Wheel Speed", ws1);

	}

	public static void fieldCentricDrive(double fwd, double str, double rot) {
		double temp = (fwd * Math.cos(getHyroAngleInRad()))
				+ (str * Math.sin(getHyroAngleInRad()));
		str = (-fwd * Math.sin(getHyroAngleInRad()))
				+ (str * Math.cos(getHyroAngleInRad()));
		fwd = temp;
		humanDrive(fwd, str, rot);
	}

	public static boolean isBigBirdTurnEncConnected() {
		return bigBird.isTurnEncConnected();
	}

	public static boolean isBigHorseTurnEncConnected() {
		return bigHorse.isTurnEncConnected();
	}

	public static boolean isBigGiraffeTurnEncConnected() {
		return bigGiraffe.isTurnEncConnected();
	}

	public static boolean isBigSushiTurnEncConnected() {
		return bigSushi.isTurnEncConnected();
	}

	public static void resetAllEnc() {
		bigBird.restTurnEnc();
		bigHorse.restTurnEnc();
		bigGiraffe.restTurnEnc();
		bigSushi.restTurnEnc();
	}

	public static void stopDrive() {
		bigBird.stopDrive();
		bigHorse.stopDrive();
		bigGiraffe.stopDrive();
		bigSushi.stopDrive();
	}

	private static double angleToLoc(double angle) {
		if (angle < 0) {
			return .5d + ((180d - Math.abs(angle)) / 360d);
		} else {
			return angle / 360d;
		}
	}

	private static boolean offSetSet = false;

	public static void setOffSets() {
		if (Math.abs(DriveTrain.bigBird.getAbsPos()) > 1
				|| Math.abs(DriveTrain.bigHorse.getAbsPos()) > 1
				|| Math.abs(DriveTrain.bigGiraffe.getAbsPos()) > 1
				|| Math.abs(DriveTrain.bigSushi.getAbsPos()) > 1) {
			changeAllToQual();
			return;
		} else {
			double bbOff = 0, bhOff = 0, bgOff = 0, bsOff = 0;
			if (!offSetSet && DriveTrain.bigBird.getAbsPos() != 0
					&& DriveTrain.bigHorse.getAbsPos() != 0
					&& DriveTrain.bigGiraffe.getAbsPos() != 0
					&& DriveTrain.bigSushi.getAbsPos() != 0) {
				bbOff = DriveTrain.bigBird.getAbsPos();
				bhOff = DriveTrain.bigHorse.getAbsPos();
				bgOff = DriveTrain.bigGiraffe.getAbsPos();
				bsOff = DriveTrain.bigSushi.getAbsPos();
				System.out.println("BBoff: " + bbOff);
				System.out.println("BHoff: " + bhOff);
				System.out.println("BGoff: " + bgOff);
				System.out.println("BSoff: " + bsOff);
				changeAllToQual();
				bigBird.setEncPos((int) (weirdSub(bbOff, Constants.DT_BB_ABS_ZERO) * 4095d));
				bigHorse.setEncPos((int) (weirdSub(bhOff, Constants.DT_BH_ABS_ZERO) * 4095d));
				bigGiraffe.setEncPos((int) (weirdSub(bgOff, Constants.DT_BG_ABS_ZERO) * 4095d));
				bigSushi.setEncPos((int) (weirdSub(bsOff, Constants.DT_BS_ABS_ZERO) * 4095d));
				offSetSet = true;
			}
			
		}
	}

	public static void changeAllToQual() {
		bigBird.setFeedBackToQual();
		bigHorse.setFeedBackToQual();
		bigGiraffe.setFeedBackToQual();
		bigSushi.setFeedBackToQual();
	}

	private static double weirdSub(double v, double c) {
		if (v - c > 0) {
			return v - c;
		} else {
			return (1 - c) + v;
		}
	}

	public static double getHyroAngle() {
		return hyro.getAngle();
	}

	public static double getHyroAngleInRad() {
		return hyro.getAngle() * (Math.PI / 180d);
	}
}