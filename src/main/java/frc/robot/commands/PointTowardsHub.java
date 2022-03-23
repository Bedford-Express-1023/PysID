package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.SwerveDriveSubsystem;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.Trajectory.State;
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
            SmartDashboard.putString("sucess?", "Yes");
            double toRotate = LimeLightRotationPID.calculate(0, -NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0) * Math.PI/180);
            drivetrain.drive(new ChassisSpeeds(0, 0, toRotate + 0.2 * Math.signum(toRotate)));
        } else {SmartDashboard.putString("sucess?", "No");}
    }

    @Override
    public void end(boolean interrupted) {
        // Stop the drivetrain
        drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
    }
}