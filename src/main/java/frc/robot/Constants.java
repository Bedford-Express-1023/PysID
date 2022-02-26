package frc.robot;

import frc.robot.Utils.Gains;

public class Constants {
    //////////////////////
    //DRIVETRAIN CONSTANTS
    //////////////////////
    public static final double DRIVETRAIN_TRACKWIDTH_METERS = 0.521;
    public static final double DRIVETRAIN_WHEELBASE_METERS = 0.521;

    public static final double SWERVE_MAX_VOLTAGE = 12;
    public static final double SWERVE_SPEED_MULTIPLIER = 0.85; //use to limit speed
    public static final double MAX_VELOCITY_METERS_PER_SECOND = 5.25; //Literally a measure of how fast the swerves could possibly go
    public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = MAX_VELOCITY_METERS_PER_SECOND /
            Math.hypot(Constants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0, Constants.DRIVETRAIN_WHEELBASE_METERS / 2.0);

    public static final int DRIVETRAIN_PIGEON_ID = 0;

    public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR = 42;
    public static final int FRONT_LEFT_MODULE_STEER_MOTOR = 2;
    public static final int FRONT_LEFT_MODULE_STEER_ENCODER = 3;
    public static final double FRONT_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(265-180);

    public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 5;
    public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 6;
    public static final int FRONT_RIGHT_MODULE_STEER_ENCODER = 4;
    public static final double FRONT_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(288);

    public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 7;
    public static final int BACK_LEFT_MODULE_STEER_MOTOR = 8;
    public static final int BACK_LEFT_MODULE_STEER_ENCODER = 9;
    public static final double BACK_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(5.3);

    public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 11;
    public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 12;
    public static final int BACK_RIGHT_MODULE_STEER_ENCODER = 10;
    public static final double BACK_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(344);

    ///////////////////
    //SHOOTER CONSTANTS
    ///////////////////
    public static final double kNominalVoltage = 12.0;
    public static final int kFalconCPR = 2048; //pulses per rotation
    public static final int kCANTimeoutMs = 250;

    public final static int shooterPID = 0;
    public static int shooterTimeout = 100;
    public static final Gains kBottomGains = new Gains(0.06 , 0.0, 0.0, 0.05, 0, 0.8);//FIXME run sysid
    public static final Gains kTopGains = new Gains(0.05, 0.0, 0.0, 0.05, 0, 0.8);
    public final static int SLOT_0 = 0;
    public final static int ShooterLowRPM = 10000;
    public final static int ShooterHighRPM = 10000;

}