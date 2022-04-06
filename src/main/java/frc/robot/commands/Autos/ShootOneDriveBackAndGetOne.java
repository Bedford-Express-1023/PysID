// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootOneDriveBackAndGetOne extends SequentialCommandGroup {
  private final SwerveDriveSubsystem m_drivetrain;
  private final IndexerSubsystem m_indexer;
  private final ShooterSubsystem m_shooter;
  private final IntakeSubsystem m_intake;
  /** Creates a new ShootOneAndDriveBack. */
  public ShootOneDriveBackAndGetOne(SwerveDriveSubsystem drivetrain, IndexerSubsystem indexerSubsystem, ShooterSubsystem shooterSubsystem, IntakeSubsystem intakeSubsystem) {
    m_drivetrain = drivetrain;
    m_indexer = indexerSubsystem;
    m_shooter = shooterSubsystem;
    m_intake = intakeSubsystem;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    /*addCommands(new ShootAndDoNothing(m_shooter, m_indexer).withTimeout(1.0), 
          new ShootStop(m_shooter).withTimeout(0.1), new DeployIntake(m_intake).alongWith(
          new DriveBack(m_drivetrain)).alongWith(new IndexBalls(m_indexer)).withTimeout(3.0),
          new StowIntake(m_intake).alongWith(new DriveForward(m_drivetrain)).withTimeout(3.0), 
          new ShootAndDoNothing(m_shooter, m_indexer), new ShootStop(m_shooter).withTimeout(0.1),
          new Gyroscope180(m_drivetrain));*/
  }
}