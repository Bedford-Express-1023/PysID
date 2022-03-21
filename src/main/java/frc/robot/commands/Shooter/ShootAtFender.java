// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootAtFender extends CommandBase {
  ShooterSubsystem m_ShooterSubsystem;
  IndexerSubsystem m_IndexerSubsystem;
  HoodSubsystem m_HoodSubsystem;
  /** Creates a new ShootAtFender. */
  public ShootAtFender(ShooterSubsystem shooterSubsystem, HoodSubsystem hoodSubsystem, IndexerSubsystem indexerSubsystem) {
    m_ShooterSubsystem = shooterSubsystem;
    m_IndexerSubsystem = indexerSubsystem;
    m_HoodSubsystem = hoodSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_ShooterSubsystem, m_IndexerSubsystem, m_HoodSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_HoodSubsystem.hoodFenderShot();
    m_ShooterSubsystem.shooterRunAtFenderVelocity();
    if (m_ShooterSubsystem.shooterReadyFender() == true){
          m_IndexerSubsystem.feedShooter();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_ShooterSubsystem.shootStop();
    m_HoodSubsystem.hoodReturnToZero();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
