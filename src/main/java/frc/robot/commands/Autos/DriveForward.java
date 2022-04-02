package frc.robot.commands.Autos;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class DriveForward extends CommandBase {
    private final SwerveDriveSubsystem drivetrain;
    private final Trajectory driveTrajectory = TrajectoryGenerator.generateTrajectory(new Pose2d(3, 0, new Rotation2d(0)), List.of(/*new Translation2d(-1+0.53, 0.53)*/), new Pose2d(-1.0, 0, new Rotation2d(Math.PI/2)), new TrajectoryConfig(7, 3));
    private double timer = 0;

    public DriveForward(SwerveDriveSubsystem drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
        drivetrain.zeroGyroscope();
    }

    @Override
    public void execute() {
    drivetrain.drive(drivetrain.driveController.calculate(drivetrain.getRealOdometry(), driveTrajectory.sample(timer), driveTrajectory.sample(timer).poseMeters.getRotation()));
    timer += 0.02;
    }

    @Override
    public boolean isFinished() {
        return driveTrajectory.getTotalTimeSeconds() <= timer;
    }

    @Override
    public void end(boolean interrupted) {
        // Stop the drivetrain
        drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
        timer = 0;
    }
}