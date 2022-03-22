// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {

  private final WPI_TalonFX climberRightMotor = new WPI_TalonFX(55);
  private final WPI_TalonFX climberLeftMotor = new WPI_TalonFX(56);
  private final Solenoid climberSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 4);
  /** Creates a new climberSubsytem. */
  public ClimberSubsystem() {
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void climberUnlock(){
    climberSolenoid.set(true);
  }

  public void climberLock(){
    climberSolenoid.set(false);
  }

  public void climbLock(){
    climberSolenoid.set(true);
  }

  public void climberUp(){
    climberUnlock();
    climberLeftMotor.set(ControlMode.PercentOutput, -0.85);
    climberRightMotor.set(ControlMode.PercentOutput, 0.85);
  }

  public void climberDown(){
    climberLeftMotor.set(ControlMode.PercentOutput, -0.85);
    climberRightMotor.set(ControlMode.PercentOutput, 0.85);
  }

  public void climberOff(){
    climberLock();
    climberLeftMotor.set(ControlMode.PercentOutput, 0);
    climberRightMotor.set(ControlMode.PercentOutput, 0);
  }
}
