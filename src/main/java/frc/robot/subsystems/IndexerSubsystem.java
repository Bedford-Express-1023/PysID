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
  private final DigitalInput shooterBeamBreak = new DigitalInput(8); //FIXME
  private final DigitalInput indexerBeamBreak = new DigitalInput(2);
  private final DigitalInput spitterBeamBreak = new DigitalInput(9);
  private final double indexingSpeed = 0.9;

  boolean shooterBeamBreakState;
  boolean indexerBeamBreakState;
  boolean spitterBeamBreakState;

  /** Creates a new IndexerSubsystem. */
  public IndexerSubsystem() {
  }

  public void indexBalls(){
    if (shooterBeamBreakState == true) {
    indexerFrontMotor.set(indexingSpeed);
    indexerTopMotor.set(indexingSpeed);
    indexerBackMotor.stopMotor();
    }
    else if (indexerBeamBreakState == true && shooterBeamBreakState == false) {
      indexerFrontMotor.set(indexingSpeed);
      indexerTopMotor.stopMotor();
      indexerBackMotor.stopMotor();
    }
    else if (indexerBeamBreakState == false && shooterBeamBreakState == false) {
      indexerFrontMotor.stopMotor();
      indexerTopMotor.stopMotor();
      indexerBackMotor.stopMotor();
    }
    else {
      indexerStop();
    }
  }

  public void ballSpitter(){
    indexerFrontMotor.set(indexingSpeed);
    indexerTopMotor.stopMotor();
    indexerBackMotor.set(-indexingSpeed);
  }

  public void ballSpitterStop(){
    indexerBackMotor.stopMotor();
  }

  public boolean readyCheck(){
    if(shooterBeamBreakState == false){
      return true;
    }
    else{
      return false;
    }
  }
  
  public void feedShooter(){
    indexerTopMotor.set(indexingSpeed);
  }

  public void indexerUnjam(){
    indexerTopMotor.set(-indexingSpeed);
    indexerFrontMotor.set(-indexingSpeed);
    indexerBackMotor.set(-indexingSpeed);
  }

  public void indexerStop(){
    indexerTopMotor.stopMotor();
    indexerFrontMotor.stopMotor();
    indexerBackMotor.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    if (shooterBeamBreak.get()){
      shooterBeamBreakState = true;
    }
    else {
      shooterBeamBreakState = false;
    }

    if (indexerBeamBreak.get()) {
      indexerBeamBreakState = true;
    } 
    else {
      indexerBeamBreakState = false;
    }

    if (spitterBeamBreak.get()) {
      spitterBeamBreakState = true;
    } 
    else {
      spitterBeamBreakState = false;
    }
  }
}
