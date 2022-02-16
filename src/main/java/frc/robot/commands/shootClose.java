// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class shootClose extends CommandBase {
  ShooterSubsystem m_ShooterSubsystem = new ShooterSubsystem();
  IndexerSubsystem m_IndexerSubsystem = new IndexerSubsystem();
  double shooterRPM = (3000 * 3.33); 
  int shooterClicks = (int) Math.round(shooterRPM);
  /** Creates a new shootClose. */
  public shootClose(ShooterSubsystem shooterSubsystem, IndexerSubsystem indexerSubsystem) {
    addRequirements(m_ShooterSubsystem);
    addRequirements(m_IndexerSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_ShooterSubsystem.shoot(shooterClicks);
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_ShooterSubsystem.shootStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
