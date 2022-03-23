// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import static com.revrobotics.CANSparkMax.SoftLimitDirection.*;
import static com.revrobotics.CANSparkMax.ControlType.*;
import static com.revrobotics.CANSparkMaxLowLevel.MotorType.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HoodSubsystem extends SubsystemBase {
  private final CANSparkMax hood = new CANSparkMax(34, kBrushless);
  private RelativeEncoder hoodEncoder;
  private SparkMaxPIDController hoodPIDController;

  private double kP = 2; 
  private double kI = 0;
  private double kD = 0; 
  private double kIz = 0; 
  private double kFF = 0; 
  private double kMaxOutput = 1; 
  private double kMinOutput = -1;

  private double hoodPosition;
  private String hoodAngle;

  /** Creates a new HoodSubsystem. */
  public HoodSubsystem() {
    hoodEncoder = hood.getEncoder();

    hoodPIDController = hood.getPIDController();
    hoodPIDController.setP(kP);
    hoodPIDController.setI(kI);
    hoodPIDController.setD(kD);
    hoodPIDController.setIZone(kIz);
    hoodPIDController.setFF(kFF);
    hoodPIDController.setOutputRange(kMinOutput, kMaxOutput);

    hood.setSoftLimit(kForward, 238); //sets forward limit to 238 on internal encoder
    hood.setSoftLimit(kReverse, 0); //sets reverse limit to 0 on internal encoder
    hood.setSmartCurrentLimit(20); //sets current limit to 20 amps
  }

  public void hoodFenderShot(){
    hoodPIDController.setReference(1, kPosition);
    hoodAngle = "Fender Angle";
  }

  public void hoodTarmacShot(){
    hoodPIDController.setReference(100, kPosition);
    hoodAngle = "Tarmac Angle";
  }

  public void hoodLaunchpadShot(){
    hoodPIDController.setReference(155, kPosition);
    hoodAngle = "Launchpad Angle";
  }

  public boolean hoodLaunchpadCheck(){
    if (hoodEncoder.getPosition() < 156 && hoodEncoder.getPosition() > 153) {
      return true;
    }
    else {
      return false;
    }
  }

  public boolean hoodTarmacCheck(){
    if (hoodEncoder.getPosition() < 101 && hoodEncoder.getPosition() > 99) {
      return true;
    }
    else {
      return false;
    }
  }

  public boolean hoodFenderCheck(){
    if (hoodEncoder.getPosition() < 2 && hoodEncoder.getPosition() > 0){
      return true;
    }
    else {
      return false;
    }
  }

  public void hoodPositionReset(){ //use to zero hood as needed
    hoodEncoder.setPosition(0);
  }

  public void hoodReturnToZero(){
    hoodPIDController.setReference(0, ControlType.kPosition);
  }

  @Override
  public void periodic() {
    hoodPosition = hoodEncoder.getPosition();
    SmartDashboard.putNumber("Current Hood Position", hoodPosition);
    // This method will be called once per scheduler run
  }
}
