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

public class Turn90 extends CommandBase {
  SwerveDriveSubsystem m_drivetrain;
  private Rotation2d toRotate;
  private double rotationTarget;

  /** Creates a new Turn90. */
  public Turn90(SwerveDriveSubsystem drivetrain) {
    m_drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    rotationTarget = m_drivetrain.odometry.getPoseMeters().getRotation().getRadians() + Math.PI/2;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double toRotate = m_drivetrain.rotationPID.calculate(m_drivetrain.getRotation().getRadians(), rotationTarget);
    m_drivetrain.drive(new ChassisSpeeds(0, 0, toRotate + 0.2 * Math.signum(toRotate)));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_drivetrain.rotationPID.getPositionError() < 0.2;
  }
}
