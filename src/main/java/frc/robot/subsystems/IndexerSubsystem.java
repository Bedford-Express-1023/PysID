// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IndexerSubsystem extends SubsystemBase {
  private final CANSparkMax indexerTopMotor = new CANSparkMax(31, MotorType.kBrushless); 
  private final CANSparkMax indexerFrontMotor = new CANSparkMax(30, MotorType.kBrushless); 
  private final CANSparkMax indexerBackMotor = new CANSparkMax(32, MotorType.kBrushless); 

  //private final DigitalInput indexerBeamBreak = new DigitalInput(9);
  private final DigitalInput shooterBeamBreak = new DigitalInput(7); 
  private final DigitalInput indexerBeamBreak = new DigitalInput(2);
  private final DigitalInput spitterBeamBreak = new DigitalInput(9);
  private final double indexingSpeed = 0.9;
  
  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

 
  private final ColorMatch m_colorMatcher = new ColorMatch();


  private final Color kBlueTarget = new Color(0.198, 0.458, 0.343);
  private final Color kRedTarget =  new Color(0.324, 0.438, 0.238);
  Color detectedColor = m_colorSensor.getColor();
  
  ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
  String colorString;
  String ColorAlliance;


  boolean shooterBeamBreakState;
  boolean indexerBeamBreakState;
  boolean spitterBeamBreakState;

  /** Creates a new IndexerSubsystem. */
  public IndexerSubsystem() {
    indexerTopMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 500);
    indexerFrontMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 500);
    indexerBackMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 500);
  }
  
  public void ballToShooter(){
    if (shooterBeamBreakState == true){
      indexerFrontMotor.set(indexingSpeed);
      indexerTopMotor.set(indexingSpeed);
      }
      else {
        indexerFrontMotor.set(indexingSpeed);
        indexerTopMotor.set(indexingSpeed);
      }
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
    indexerFrontMotor.set(indexingSpeed);
  }

  public void feedShooterSlow(){
    indexerTopMotor.set(0.6);
    indexerFrontMotor.set(0.6);
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

  public void getColor(){
    detectedColor = m_colorSensor.getColor();
    match = m_colorMatcher.matchClosestColor(detectedColor);
    if (match.color == kRedTarget ) {
      colorString = "Red";

    }
    else if (match.color == kBlueTarget) {
      colorString = "Blue";
     
    }
    else {
      colorString = "Unknown";
    }

  }

  public String allianceColorCheck(){
    if (DriverStation.getAlliance() == Alliance.Blue){
      return ColorAlliance = "Blue";
    }
    else if (DriverStation.getAlliance() == Alliance.Red){
      return ColorAlliance = "Red";
    }
    else {
      return ColorAlliance = "Red";
    }
  }

  public void ballSorter(){
    if (colorString == ColorAlliance && shooterBeamBreakState == true){
      ballToShooter();
    }
    else if (colorString == ColorAlliance && shooterBeamBreakState == false){
      indexerStop();
    }
    else
    ballSpitter();
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
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putString("Color", colorString);
    SmartDashboard.putString(("AllianceColor"), ColorAlliance);
  }
}
