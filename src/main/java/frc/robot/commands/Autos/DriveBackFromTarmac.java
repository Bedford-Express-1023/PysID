// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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

public class DriveBackFromTarmac extends CommandBase {
  /** Creates a new DriveBackFromTarmac. */
  SwerveDriveSubsystem drivetrain;
  private final Trajectory driveTrajectory = TrajectoryGenerator.generateTrajectory(new Pose2d(0, 0, new Rotation2d(0)), List.of(/*new Translation2d(-1+0.53, 0.53)*/), new Pose2d(2, 0, new Rotation2d(Math.PI/2)), new TrajectoryConfig(7, 3));
  private double timer = 0;

  public DriveBackFromTarmac(SwerveDriveSubsystem drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.drive(drivetrain.driveController.calculate(drivetrain.odometry.getPoseMeters(), driveTrajectory.sample(timer), driveTrajectory.sample(timer).poseMeters.getRotation()));
    timer += 0.02;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
    timer = 0;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return driveTrajectory.getTotalTimeSeconds() <= timer;
  }
}
