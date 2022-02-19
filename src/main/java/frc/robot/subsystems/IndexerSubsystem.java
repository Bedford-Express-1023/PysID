// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IndexerSubsystem extends SubsystemBase {
  private final CANSparkMax indexerTopMotor = new CANSparkMax(31, MotorType.kBrushless); //FIXME
  private final CANSparkMax indexerFrontMotor = new CANSparkMax(30, MotorType.kBrushless); //FIXME
  private final CANSparkMax indexerBackMotor = new CANSparkMax(32, MotorType.kBrushless); //FIXME

  //private final DigitalInput indexerBeamBreak = new DigitalInput(9);
  private final DigitalInput shooterBeamBreak = new DigitalInput(0); //FIXME
  private final DigitalInput indexerBeamBreak = new DigitalInput(2);
  private final double indexingSpeed = 0.9;
  /** Creates a new IndexerSubsystem. */
  public IndexerSubsystem() {
  }

  public void indexBalls(){
    if(indexerBeamBreak.get() && shooterBeamBreak.get()){
      indexerTopMotor.set(indexingSpeed);
      indexerFrontMotor.set(indexingSpeed);
      indexerBackMotor.stopMotor();
    }
    else if(indexerBeamBreak.get() && !shooterBeamBreak.get()){
      indexerTopMotor.stopMotor();
      indexerFrontMotor.set(indexingSpeed);
      indexerBackMotor.stopMotor();
    }
    else if(!indexerBeamBreak.get() && !shooterBeamBreak.get()){
      indexerTopMotor.stopMotor();
      indexerFrontMotor.stopMotor();
    }
    else if (!indexerBeamBreak.get() && shooterBeamBreak.get()){
      indexerTopMotor.set(indexingSpeed);
      indexerFrontMotor.set(indexingSpeed);
    }
    else{
      indexerStop();
    }
  }

  public boolean readyCheck(){
    if(shooterBeamBreak.get()){
      return true;
    }
    else{
      return false;
    }
  }
  
  public void feedShooter(){
    indexerTopMotor.set(1.0);
  }

  public void indexerUnjam(){
    indexerTopMotor.set(-1.0);
    indexerFrontMotor.set(-1.0);
  }

  public void indexerStop(){
    indexerTopMotor.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    boolean shooterBeamBreakState;
    if (shooterBeamBreak.get()){
      shooterBeamBreakState = true;
    }
    else {
      shooterBeamBreakState = false;
    }
  }
}
