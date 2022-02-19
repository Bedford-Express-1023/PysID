// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.opencv.video.FarnebackOpticalFlow;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
  private final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(20);
  //private final DoubleSolenoid leftCylinder = new DoubleSolenoid(PneumaticsModuleType.REVPH, 14, 15);
  private final Solenoid rightCylinder = new Solenoid(PneumaticsModuleType.REVPH, 15);
  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {
  }

  public void deployIntake(){
    //leftCylinder.set(Value.kReverse);
    rightCylinder.set(true);
    intakeMotor.set(1.0);
  }

  public void stowIntake(){
    //leftCylinder.set(Value.kForward);
    rightCylinder.set(false);
    intakeMotor.set(0.0);
  }

  public void unjamIntake(){
    //leftCylinder.set(Value.kForward);
    rightCylinder.set(true);
    intakeMotor.set(-0.4);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
