// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Indexer.IndexerStop;
import frc.robot.commands.Intake.DeployIntake;
import frc.robot.commands.Shooter.AutoShootCommand;
import frc.robot.commands.Shooter.ShootAtTarmac;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class fourBall extends SequentialCommandGroup {
/** Creates a new fiveBall. */
public fourBall(IntakeSubsystem intake, IndexerSubsystem indexer, SwerveDriveSubsystem drivetrain, 
ShooterSubsystem shooter, HoodSubsystem hood) {
  addCommands(
    new threeBall(intake, indexer, drivetrain, shooter, hood ),
    new fourBall1(drivetrain).deadlineWith(
      new DeployIntake(intake)),
    new fourBall2(drivetrain).deadlineWith(
      new DeployIntake(intake)),
    new fourBall3(drivetrain).deadlineWith(
      new DeployIntake(intake).alongWith(new IndexerStop(indexer))), 
    new WaitCommand(0.5),
    new AutoShootCommand(hood, shooter, indexer)
  );
}
}
