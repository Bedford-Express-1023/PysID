// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Gyroscope180;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootOneAndDriveBack extends SequentialCommandGroup {
  private final SwerveDriveSubsystem m_drivetrain;
  private final IndexerSubsystem m_indexer;
  private final ShooterSubsystem m_shooter;
  private final HoodSubsystem m_hood;
  /** Creates a new ShootOneAndDriveBack. */
  public ShootOneAndDriveBack(SwerveDriveSubsystem drivetrain, IndexerSubsystem indexerSubsystem, ShooterSubsystem shooterSubsystem, HoodSubsystem hoodSubsystem) {
    m_drivetrain = drivetrain;
    m_indexer = indexerSubsystem;
    m_shooter = shooterSubsystem;
    m_hood = hoodSubsystem;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new ShootAndDoNothing(m_shooter, m_indexer, m_hood).withTimeout(3.0),
    new DriveBack(m_drivetrain).withTimeout(3.0), new Gyroscope180(m_drivetrain));
  }
}