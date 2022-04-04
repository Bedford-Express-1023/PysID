// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
  private final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(20);
  private final Solenoid intakeSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 3);
  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {
  }

  public void deployIntake(){
    intakeSolenoid.set(true);
    intakeMotor.set(-1.0);
  }

  public void stowIntake(){
    intakeSolenoid.set(false);
    intakeMotor.set(0.0);
  }

  public void unjamIntake(){
    intakeSolenoid.set(true);
    intakeMotor.set(-0.4);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
