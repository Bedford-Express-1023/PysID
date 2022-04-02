// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootAtVelocity extends CommandBase {
  IndexerSubsystem m_IndexerSubsystem;
  ShooterSubsystem m_ShooterSubsystem;
  HoodSubsystem m_HoodSubsystem;
  /** Creates a new ShootAtVelocity. */
  public ShootAtVelocity(IndexerSubsystem indexerSubsystem, ShooterSubsystem shooterSubsystem, HoodSubsystem hoodSubsystem) {
    m_IndexerSubsystem = indexerSubsystem;
    m_ShooterSubsystem = shooterSubsystem;
    m_HoodSubsystem = hoodSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_ShooterSubsystem, m_IndexerSubsystem, m_HoodSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_ShooterSubsystem.getBottomShooterVelocity(m_ShooterSubsystem);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_HoodSubsystem.hoodCheck() == true && m_HoodSubsystem.centeredCheck() == true && m_ShooterSubsystem.shooterReady() == true){
          m_IndexerSubsystem.feedShooter();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
