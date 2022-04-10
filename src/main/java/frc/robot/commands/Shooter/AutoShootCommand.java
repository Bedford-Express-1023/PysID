// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoShootCommand extends CommandBase {
  HoodSubsystem m_hood;
  ShooterSubsystem m_shooter;
  IndexerSubsystem m_indexer;
  /** Creates a new AutoShootCommand. */
  public AutoShootCommand(HoodSubsystem hoodSubsystem, ShooterSubsystem shooterSubsystem, IndexerSubsystem indexerSubsystem) {
    m_hood = hoodSubsystem;
    m_shooter = shooterSubsystem;
    m_indexer = indexerSubsystem;
    addRequirements(m_hood, m_shooter, m_indexer);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_hood.setHoodPositionAuto();
    m_shooter.setShooterSpeedsAuto();
    if (m_shooter.shooterReadyAuto() == true  ){
      m_indexer.feedShooter();
    }
    else {
      return;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.shootStop();
    m_hood.setHoodPositionAuto();
    m_indexer.indexBalls();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
