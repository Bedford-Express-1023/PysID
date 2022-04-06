// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootOnce extends SequentialCommandGroup {
  IndexerSubsystem m_indexer;
  ShooterSubsystem m_shooter;
  HoodSubsystem m_hood;

  /** Creates a new ShootOnce. */
  public ShootOnce(IndexerSubsystem indexerSubsystem, HoodSubsystem hoodSubsystem, ShooterSubsystem shooterSubsystem) {
    m_indexer = indexerSubsystem;
    m_shooter = shooterSubsystem;
    m_hood = hoodSubsystem;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    //addCommands(new ShootAtFender(shooterSubsystem, hoodSubsystem, indexerSubsystem).withTimeout(2.0), 
        //new ShootStop(m_shooter));
  }
}
