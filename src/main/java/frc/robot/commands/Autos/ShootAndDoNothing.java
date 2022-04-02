// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootAndDoNothing extends SequentialCommandGroup {
  ShooterSubsystem m_shooter;
  IndexerSubsystem m_indexer;
  SwerveDriveSubsystem m_drivetrain;
  HoodSubsystem m_hood;
  // Creates a new ShootAndDoNothing.
  public ShootAndDoNothing(ShooterSubsystem shooterSubsystem, IndexerSubsystem indexerSubsystem, HoodSubsystem hoodSubsystem) {
    m_shooter = shooterSubsystem;
    m_indexer = indexerSubsystem;
    m_hood = hoodSubsystem;
    addCommands( 
    new ShootOnce(m_indexer, m_shooter, m_hood));
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
  }
}
