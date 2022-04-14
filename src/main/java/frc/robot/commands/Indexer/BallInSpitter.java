// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexerSubsystem;

public class BallInSpitter extends CommandBase {
  IndexerSubsystem m_IndexerSubsystem;
  /** Creates a new BallInSpitter. */
  public BallInSpitter(IndexerSubsystem indexerSubsystem) {
    m_IndexerSubsystem = indexerSubsystem;
    addRequirements(indexerSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_IndexerSubsystem.ballSpitter2();
  }

  @Override
  public void end(boolean interrupted) {
      m_IndexerSubsystem.indexerBackMotor.stopMotor();
  }
}
