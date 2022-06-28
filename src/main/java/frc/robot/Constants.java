package frc.robot;

import frc.robot.Utils.Gains;
import frc.robot.Utils.LinearInterpolator;

public class Constants {
    //////////////////////
    //DRIVETRAIN CONSTANTS
    //////////////////////
    public static final double DRIVETRAIN_TRACKWIDTH_METERS = 0.521;
    public static final double DRIVETRAIN_WHEELBASE_METERS = 0.521;

    public static final double SWERVE_MAX_VOLTAGE = 12;
    public static final double SWERVE_SPEED_MULTIPLIER = 100; //use to limit speed
    public static final double MAX_VELOCITY_METERS_PER_SECOND = 5.25; //Literally a measure of how fast the swerves could possibly go
    public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = MAX_VELOCITY_METERS_PER_SECOND /
            Math.hypot(Constants.DRIVETRAIN_TRACKWIDTH_METERS / 4.0, Constants.DRIVETRAIN_WHEELBASE_METERS / 4.0);

    public static final int DRIVETRAIN_PIGEON_ID = 0;

    public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR = 42;
    public static final int FRONT_LEFT_MODULE_STEER_MOTOR = 2;
    public static final int FRONT_LEFT_MODULE_STEER_ENCODER = 3;
    public static final double FRONT_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(264);

    public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 5;
    public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 6;
    public static final int FRONT_RIGHT_MODULE_STEER_ENCODER = 4;
    public static final double FRONT_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(289);

    public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 7;
    public static final int BACK_LEFT_MODULE_STEER_MOTOR = 8;
    public static final int BACK_LEFT_MODULE_STEER_ENCODER = 9;
    public static final double BACK_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(4.9);

    public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 11;
    public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 12;
    public static final int BACK_RIGHT_MODULE_STEER_ENCODER = 10;
    public static final double BACK_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(342);

    ///////////////////
    //SHOOTER CONSTANTS
    ///////////////////
    public static final double kNominalVoltage = 12.0;
    public static final int kFalconCPR = 2048; //pulses per rotation
    public static final int kCANTimeoutMs = 250;

    public final static int shooterPID = 0;
    public static int shooterTimeout = 100;
    public static final Gains kBottomGains = new Gains(0.06 , 0.0, 0.0, 0.05, 0, 0.8);
    public static final Gains kTopGains = new Gains(0.05, 0.0, 0.0, 0.05, 0, 0.8);
    public final static int SLOT_0 = 0;
    public final static int ShooterLowRPM = 10000;
    public final static int ShooterHighRPM = 10000;

    public static final class TargetConstants{
		public static final double[][] HOOD_ARRAY = {
                {-18.1, 180},
                {-17.4, 180},
                {-16.1, 185},
                {-15.6, 165},
                {-15.1, 155},
                {-14.9, 160},
                {-14, 155},
                {-12.5,170},
                {-11.1, 165},
                {-10.1, 165},
                {-9.5, 155},
                {-8.5,145},
                {-8,139},
                {-7.2, 130},
				{-5.8,120},
                {-4.2,100},
                {-2.4, 90},
				{-1.1,100},
				{0,100},
				{4.5, 87},
				{5,80},
				{7,70},
                {7.5,70},
				{10.7,65},
                {12.2,70},
                {15.5,55},
                {17.6,55}
			};

		public static final double[][] SHOOTER_TOP_ARRAY = {
            {17.6,7400},
            {15.5, 7800},
            {12.2,8000},
			{10.7,8000},
            {7.5,8300},
            {5.5,8550},
			{4,8600},
			{0.0,8500},
			{-1.1,8500},
            {-2.4, 8500},
            {-4.2,8500},
			{-5.8,8500},
            {-7.2, 9000},
            {-8,9261},
            {-8.5, 9800},
            {-9.5, 10000},
            {-10.1, 10200},
            {-11.1, 10200},
            {-12.5,10500},
            {-14, 10558},
            {-14.9,10858},
            {-15.1, 10958},
            {-15.6, 11100},
            {-16.1, 11200},
            {-17.4, 11900},
            {-18.1, 12400}
            
		};

        public static final double[][] SHOOTER_BOTTOM_ARRAY = {
            {17.6,11300},
            {15.5,11300},
            {12.2,11000},
			{10.7,11000},
            {7.5,11400},
            {5.5,11050},
			{4,11200},
			{0.0,11600},
			{-1.1,12300},
            {-2.4, 12300},
            {-4.2, 12400},
			{-5.8,12000},
            {-7.2, 11700},
            {-8, 12680},
            {-8.5, 12200},
            {-9.5, 12550},
            {-10.1, 13100},
            {-11.1,12002},
            {-12.5, 13600},
            {-14,14000 },
            {-14.9, 13585},
            {-15.1, 13902},
            {-15.6, 14202},
            {-16.1, 14700},
            {-17.4, 14702},
            {-18.1, 14900}
        };

		public static final LinearInterpolator HOOD_INTERPOLATOR = new LinearInterpolator(HOOD_ARRAY);
		public static final LinearInterpolator SHOOTER_TOP_SPEED_INTERPOLATOR = new LinearInterpolator(SHOOTER_TOP_ARRAY);
        public static final LinearInterpolator SHOOTER_BOTTOM_SPEED_INTERPOLATOR = new LinearInterpolator(SHOOTER_BOTTOM_ARRAY);
    }
}