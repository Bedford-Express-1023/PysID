// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Gyroscope180;
import frc.robot.commands.PointTowardsHub;
import frc.robot.commands.Indexer.IndexBalls;
import frc.robot.commands.Intake.DeployIntake;
import frc.robot.commands.Shooter.ShootAtTarmac;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TwoBallAtTarmac extends SequentialCommandGroup {
  /** Creates a new TwoBallAtTarmac. */
  SwerveDriveSubsystem m_drivetrain;
  IntakeSubsystem intakeSubsystem;
  public TwoBallAtTarmac(SwerveDriveSubsystem drivetrain, IntakeSubsystem intakeSubsystem, ShooterSubsystem m_shooter, HoodSubsystem m_hood, IndexerSubsystem m_indexer) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());

    addCommands(
      new DriveBackFromTarmac(drivetrain).alongWith(
        new DeployIntake(intakeSubsystem).withTimeout(2.0)),
      new PointTowardsHub(drivetrain).alongWith(
        new ShootAtTarmac(m_shooter, m_hood, m_indexer)).withTimeout(7),
      new Gyroscope180(drivetrain));
  }
}
