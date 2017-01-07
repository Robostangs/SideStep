package org.usfirst.frc.team548.robot;

public class Constants {
	public final static int XBOX_PORT = 0;
	
	/*
	 * Drive Train
	 */
	//Big Bird
	public final static int DT_BB_DRIVE_TALON_ID = 2;
	public final static int DT_BB_TURN_TALON_ID = 1;
	public final static double DT_BB_ABS_ZERO = 0.68115234375;
	//Big Horse
	public final static int DT_BH_DRIVE_TALON_ID = 6;
	public final static int DT_BH_TURN_TALON_ID = 5;
	public final static double DT_BH_ABS_ZERO = 0.02978515625;
	//Big Giraffe
	public final static int DT_BG_DRIVE_TALON_ID = 4;
	public final static int DT_BG_TURN_TALON_ID = 3;
	public final static double DT_BG_ABS_ZERO = 0.59521484375;
	//Big Sushi
	public final static int DT_BS_DRIVE_TALON_ID = 7;
	public final static int DT_BS_TURN_TALON_ID = 8;
	public final static double DT_BS_ABS_ZERO = 0.5157509157509158;
	
	//Rot PID .01, 0.0001, 0.008,
	public final static double DT_ROT_PID_P = .007;
	public final static double DT_ROT_PID_I =.0004;
	public final static double DT_ROT_PID_D= .000;
	public final static double DT_ROT_PID_IZONE = 18;
}
