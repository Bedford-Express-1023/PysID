package frc.robot;

public class Constants {
    public static final double DRIVETRAIN_TRACKWIDTH_METERS = 0.2794;
    public static final double DRIVETRAIN_WHEELBASE_METERS = 0.2794;

    public static final double SWERVE_MAX_VOLTAGE = 12.0;
    public static final double MAX_VELOCITY_METERS_PER_SECOND = 4.14528;
    public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = MAX_VELOCITY_METERS_PER_SECOND /
            Math.hypot(Constants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0, Constants.DRIVETRAIN_WHEELBASE_METERS / 2.0);

    public static final int DRIVETRAIN_PIGEON_ID = 0;

    public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR = 4;
    public static final int FRONT_LEFT_MODULE_STEER_MOTOR = 3;
    public static final int FRONT_LEFT_MODULE_STEER_ENCODER = 2;
    public static final double FRONT_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(34.54);

    public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 2;
    public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 1;
    public static final int FRONT_RIGHT_MODULE_STEER_ENCODER = 1;
    public static final double FRONT_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(118.52);

    public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 8;
    public static final int BACK_LEFT_MODULE_STEER_MOTOR = 7;
    public static final int BACK_LEFT_MODULE_STEER_ENCODER = 4;
    public static final double BACK_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(71.99);

    public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 6;
    public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 5;
    public static final int BACK_RIGHT_MODULE_STEER_ENCODER = 3;
    public static final double BACK_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(261.71);
}