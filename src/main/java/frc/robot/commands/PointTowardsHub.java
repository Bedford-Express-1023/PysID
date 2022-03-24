package frc.robot.commands;

import frc.robot.subsystems.SwerveDriveSubsystem;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PointTowardsHub extends CommandBase {
    private final SwerveDriveSubsystem drivetrain;
    public PIDController LimeLightRotationPID;


    public PointTowardsHub(SwerveDriveSubsystem drivetrain) {
        this.drivetrain = drivetrain;
    }
    @Override
    public void initialize() {
        LimeLightRotationPID = new PIDController(5, 0, 0);
        LimeLightRotationPID.enableContinuousInput(-Math.PI, Math.PI);
    }

    @Override
    public void execute() {
        drivetrain.CommandVariable = "TowardsHub";
        if (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1) {
            double toRotate = LimeLightRotationPID.calculate(0, -NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0) * Math.PI/180);
            drivetrain.drive(new ChassisSpeeds(0, 0, toRotate + 0.2 * Math.signum(toRotate)));
        }
    }

    @Override
    public void end(boolean interrupted) {
        // Stop the drivetrain
        drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
    }
}