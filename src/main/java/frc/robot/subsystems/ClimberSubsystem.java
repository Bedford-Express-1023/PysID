// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {

  public final WPI_TalonFX climberLeftMotor = new WPI_TalonFX(71);
  public final WPI_TalonFX climberRightMotor = new WPI_TalonFX(72);
  /** Creates a new climberSubsytem. */
  public ClimberSubsystem() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void climberUp(){
    climberLeftMotor.set(ControlMode.PercentOutput, 0.5);
    climberRightMotor.set(ControlMode.PercentOutput, 0.5);
  }

  public void climberDown(){
    climberLeftMotor.set(ControlMode.PercentOutput, -0.5);
    climberRightMotor.set(ControlMode.PercentOutput, -0.5);

  }

  public void climberOff(){
    climberLeftMotor.set(ControlMode.PercentOutput, 0);
    climberRightMotor.set(ControlMode.PercentOutput, 0);

  }
}
