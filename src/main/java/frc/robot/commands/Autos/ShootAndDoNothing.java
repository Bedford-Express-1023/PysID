// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Indexer.FeedShooter;
import frc.robot.commands.Indexer.IndexBalls;
import frc.robot.commands.Shooter.ShooterRunAtVelocity;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootAndDoNothing extends ParallelCommandGroup {
  ShooterSubsystem m_shooter;
  IndexerSubsystem m_indexer;
  /** Creates a new ShootAndDoNothing. */
  public ShootAndDoNothing(ShooterSubsystem shooterSubsystem, IndexerSubsystem indexerSubsystem) {
    m_shooter = shooterSubsystem;
    m_indexer = indexerSubsystem;
    addCommands(new IndexBalls(m_indexer), new WaitCommand(2.0), 
    new FeedShooter(m_indexer), new WaitCommand(2.0),
    new ShooterRunAtVelocity(m_shooter), new WaitCommand(3.0));
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
  }
}
