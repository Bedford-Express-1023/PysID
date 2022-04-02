// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import static com.revrobotics.CANSparkMax.ControlType.*;
import static com.revrobotics.CANSparkMax.SoftLimitDirection.*;
import static com.revrobotics.CANSparkMaxLowLevel.MotorType.*;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HoodSubsystem extends SubsystemBase {
  private final CANSparkMax hood = new CANSparkMax(34, kBrushless);
  private RelativeEncoder hoodEncoder;
  private SparkMaxPIDController hoodPIDController;

  NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry ty = limelightTable.getEntry("ty");
  NetworkTableEntry tv = limelightTable.getEntry("tv");
  NetworkTableEntry tx = limelightTable.getEntry("tx");

  double topShooterFenderRPM = 1825.77;
  double bottomShooterFenderRPM = 3000;
  double topShooterLaunchpadRPM = 3100;
  double bottomShooterLaunchpadRPM = 3100;

  double topShooterTargetVelocity;
  double bottomShooterTargetVelocity;
  double bottomShooterCurrentVelocity;
  double topShooterCurrentVelocity;
  double RPMToVelocity = 3.57;
  boolean shooterReady;
  double currentTy;
  boolean currentTv;
  double currentTx;

  double topShooterFenderVelocity = topShooterFenderRPM * RPMToVelocity;
  double bottomShooterFenderVelocity = bottomShooterFenderRPM * RPMToVelocity;
  double topShooterLaunchpadVelocity = topShooterLaunchpadRPM * RPMToVelocity;
  double bottomShooterLaunchpadVelocity = bottomShooterLaunchpadRPM * RPMToVelocity;


  private double kP = 2;
  private double kI = 0;
  private double kD = 0;
  private double kIz = 0;
  private double kFF = 0;
  private double kMaxOutput = 1;
  private double kMinOutput = -1;

  private String hoodAngle;

  double closePosition = 1; //FIXME hood angle for close shot
  double farPosition = 155; //FIXME hood angle for far shot
  double closeTy = 8.8; //FIXME y coordinate of target on limelight cam for close shot
  double farTy = -8.8; //FIXME y coordinate of target on limelight cam for far shot
  double currentPosition;
  double targetPosition;
  double positionAllowedError = 5;
  double rotationAllowedError = 0.5;
  double velocityAllowedError = 400;

  /** Creates a new HoodSubsystem. */
  public HoodSubsystem() {
    hoodEncoder = hood.getEncoder();

    hoodPIDController = hood.getPIDController();
    hoodPIDController.setP(kP);
    hoodPIDController.setP(kI);
    hoodPIDController.setP(kD);
    hoodPIDController.setP(kIz);
    hoodPIDController.setP(kFF);
    hoodPIDController.setOutputRange(kMinOutput, kMaxOutput);

    hood.setSoftLimit(kForward, 238);
    hood.setSoftLimit(kReverse, 0);
    hood.setSmartCurrentLimit(20);
  }

public boolean hoodCheck(){
  if (currentPosition > (targetPosition - positionAllowedError) && currentPosition < (targetPosition + positionAllowedError)) {
    return true;
  }
  else {
    return false;
  }
}

public boolean centeredCheck(){
  if (currentTx < rotationAllowedError && currentTx > -rotationAllowedError) {
    return true;
  }
  else {
    return false;
  }
}

public void setHoodPosition(){
  hoodPIDController.setReference(targetPosition, kPosition);
}

public void hoodPositionReset(){
  hoodEncoder.setPosition(0);
}

public void hoodReturnToZero(){
  hoodPIDController.setReference(0, kPosition);
}

public void hoodReturnToMiddle(){
  hoodPIDController.setReference(farPosition-closePosition, kPosition);
}

@Override
public void periodic() {
  currentTy = ty.getDouble(0.0);
  currentTv = tv.getBoolean(false);
  currentPosition = hoodEncoder.getPosition();
  currentTx = tx.getDouble(0.0);
  //interpolation: finding the shooter RPMs based on min, max, and current values of limelight, hood position, and shooter velocity
  targetPosition = (closePosition + ((currentTy - closeTy) * ((farPosition - closePosition) / (farTy - closeTy))));//interpolation for hood position
  //display values to dashboard
  if ((currentPosition > (targetPosition - positionAllowedError)) && (currentPosition < (targetPosition + positionAllowedError))) {
    SmartDashboard.putString("Hood Status", "at target");
  }
  else {
    SmartDashboard.putString("Hood Status", "Waiting for motor to reach target");
  }
  SmartDashboard.putNumber("Current Hood Position", currentPosition);
  SmartDashboard.putNumber("Target Hood Position", targetPosition);
  SmartDashboard.putNumber("LimelightY", currentTy);
  SmartDashboard.putBoolean("Target Found", currentTv);
  // This method will be called once per scheduler run
  }
}
