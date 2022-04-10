// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import org.decimal4j.util.DoubleRounder;

import static com.revrobotics.CANSparkMax.SoftLimitDirection.*;
import static com.revrobotics.CANSparkMax.ControlType.*;
import static com.revrobotics.CANSparkMaxLowLevel.MotorType.*;

import java.text.DecimalFormat;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;

public class HoodSubsystem extends SubsystemBase {
  private final CANSparkMax hood = new CANSparkMax(34, kBrushless);
  private RelativeEncoder hoodEncoder;
  private SparkMaxPIDController hoodPIDController;

  DecimalFormat decimalFormat = new DecimalFormat("#.#");

  private double kP = 2; 
  private double kI = 0;
  private double kD = 0; 
  private double kIz = 0; 
  private double kFF = 0; 
  private double kMaxOutput = 1; 
  private double kMinOutput = -1;

  private double hoodPositionFender = 1;
  private double hoodPositionTarmac = 100;
  private double hoodPositionLaunchpad = 155;
  private double hoodPositionLowGoal = 100;
  double limelightY;
  double roundLimelightY;
double hoodTargetPosition = 180;
  double hoodPositionAuto;

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
    hoodPIDController.setReference(hoodPositionFender, kPosition);
  }

  public void hoodTarmacShot(){
    hoodPIDController.setReference(hoodPositionTarmac, kPosition);
  }

  public void hoodLaunchpadShot(){
    hoodPIDController.setReference(hoodPositionLaunchpad, kPosition);
  }

  public void hoodLowGoalShot(){
    hoodPIDController.setReference(hoodPositionLowGoal, kPosition);
  }

  public boolean hoodLaunchpadCheck(){
    if (hoodEncoder.getPosition() < (hoodPositionLaunchpad + 3) && hoodEncoder.getPosition() > (hoodPositionLaunchpad - 3)) {
      return true;
    }
    else {
      return false;
    }
  }

  public boolean hoodTarmacCheck(){
    if (hoodEncoder.getPosition() < (hoodPositionTarmac + 2) && hoodEncoder.getPosition() > (hoodPositionTarmac - 2)) {
      return true;
    }
    else {
      return false;
    }
  }

  public boolean hoodFenderCheck(){
    if (hoodEncoder.getPosition() < (hoodPositionFender + 2) && hoodEncoder.getPosition() > (hoodPositionFender - 2)){
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

  public double limelightGetY(){
    limelightY = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    roundLimelightY = DoubleRounder.round(limelightY, 1);
    return roundLimelightY;
  }

  public void setHoodPositionAuto(){
    limelightGetY();
    hoodPositionAuto = Constants.TargetConstants.HOOD_INTERPOLATOR.getInterpolatedValue(roundLimelightY);
    hoodPIDController.setReference(hoodPositionAuto, ControlType.kPosition);
  }

  public void hoodTuningPosition(){
    hoodPIDController.setReference(hoodTargetPosition, ControlType.kPosition);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Current Hood Position", hoodEncoder.getPosition());
    SmartDashboard.getNumber("Hood Position (Tuning, range 0-238)", 0);
    // This method will be called once per scheduler run
  }
}
