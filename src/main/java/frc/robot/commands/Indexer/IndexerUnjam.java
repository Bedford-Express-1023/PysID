// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexerSubsystem;

public class IndexerUnjam extends CommandBase {
  IndexerSubsystem m_IndexerSubsystem;
  /** Creates a new IndexerUnjam. */
  public IndexerUnjam() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_IndexerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_IndexerSubsystem.indexerUnjam();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_IndexerSubsystem.indexerStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
