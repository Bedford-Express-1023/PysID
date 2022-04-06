// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class Gyroscope180 extends CommandBase {
  SwerveDriveSubsystem m_SwerveDriveSubsystem;
  /** Creates a new Gyroscope180. */
  public Gyroscope180(SwerveDriveSubsystem swerveDriveSubsystem) {
    m_SwerveDriveSubsystem = swerveDriveSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_SwerveDriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_SwerveDriveSubsystem.Gyroscope180();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
