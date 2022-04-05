// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Indexer.FeedShooter;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoShootTime extends SequentialCommandGroup {
  HoodSubsystem m_hood;
  ShooterSubsystem m_shooter;
  IndexerSubsystem m_indexer;
  /** Creates a new AutoShootTime. */
  public AutoShootTime(HoodSubsystem hoodSubsystem, ShooterSubsystem shooterSubsystem, IndexerSubsystem indexerSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    m_hood = hoodSubsystem;
    m_shooter = shooterSubsystem;
    m_indexer = indexerSubsystem;
    addCommands(new ShootAuto(m_hood, m_shooter), new FeedShooter(m_indexer).alongWith(new ShootAuto(m_hood, m_shooter)).beforeStarting(new WaitCommand(0.5).alongWith(new ShootAuto(m_hood, m_shooter))));
  }
}
