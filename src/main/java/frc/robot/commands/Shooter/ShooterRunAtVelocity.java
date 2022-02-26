// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterRunAtVelocity extends CommandBase {
  ShooterSubsystem m_ShooterSubsystem;
  double shooterRPM = (3000 * 3.33); 
  int shooterLowClicks = 10000;
  int shooterHighClicks = 10000;
  /** Creates a new shootClose. */
  public ShooterRunAtVelocity(ShooterSubsystem shooterSubsystem) {
    m_ShooterSubsystem = shooterSubsystem;
    addRequirements(m_ShooterSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_ShooterSubsystem.shooterRunAtVelocity(9818, 10710);
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
