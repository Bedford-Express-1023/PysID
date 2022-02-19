// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.Indexer.FeedShooter;
import frc.robot.commands.Intake.DeployIntake;
import frc.robot.commands.Shooter.ShooterRunAtVelocity;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootOneAndGo extends SequentialCommandGroup {
  ShooterSubsystem m_shooter;
  IndexerSubsystem m_indexer;
  IntakeSubsystem m_intake;
  SwerveDriveSubsystem m_swerve;
  /** Creates a new ShootAndGo. */
  public ShootOneAndGo(ShooterSubsystem shooterSubsystem, IndexerSubsystem indexerSubsystem, IntakeSubsystem intakeSubsystem, SwerveDriveSubsystem swerveDriveSubsystem) {
    m_shooter = shooterSubsystem;
    m_indexer = indexerSubsystem;
    m_intake = intakeSubsystem;
    m_swerve = swerveDriveSubsystem;
    addCommands(new ShooterRunAtVelocity(m_shooter), new WaitCommand(0.75), 
      new FeedShooter(m_indexer), new WaitCommand(2.0)
      /*, new DriveCommand(m_swerve, 0.0, 0.5, 0.0, true, 0.5)*/);
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
  }
}
