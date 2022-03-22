// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// 2800
//
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  public final WPI_TalonFX shooterBottomTalon = new WPI_TalonFX(41);
  public final WPI_TalonFX shooterTopTalon = new WPI_TalonFX(40);
  public final CANSparkMax hood = new CANSparkMax(34, MotorType.kBrushless); //FIXME motor ID
  public SparkMaxPIDController hoodPIDController;
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry ty = table.getEntry("ty");
  double closePosition = 10; //FIXME hood angle for close shot
  double farPosition = 200; //FIXME hood angle for far shot
  double closeTy = 8.8; //FIXME y coordinate of target on limelight cam for close shot
  double farTy = -8.8; //FIXME y coordinate of target on limelight cam for far shot
  double currentPosition;
  double targetPosition;
  RelativeEncoder hoodEncoder = hood.getEncoder();
  double positionAllowedError = 1;
  double velocityAllowedError = 50;
  double kP = 50; 
  double kI = 0;
  double kD = 0; 
  double kIz = 0; 
  double kFF = 5; 
  double kMaxOutput = 50; 
  double kMinOutput = -1;
  double topShooterTargetRPM;
  double bottomShooterTargetRPM;
  double topShooterCloseRPM = 10710;
  double bottomShooterCloseRPM = 9818;
  double topShooterFarRPM = 12810;
  double bottomShooterFarRPM = 10500;
  double topShooterTargetVelocity;
  double bottomShooterTargetVelocity;
  double bottomShooterCurrentVelocity;
  double topShooterCurrentVelocity;
  double RPMToVelocity = 3.57;
  boolean shooterReady;
  double currentTy;
  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    hoodPIDController = hood.getPIDController();
    hoodPIDController.setP(kP);
    hoodPIDController.setI(kI);
    hoodPIDController.setD(kD);
    hoodPIDController.setIZone(kIz);
    hoodPIDController.setFF(kFF);

    hood.setSoftLimit(SoftLimitDirection.kForward, 238);
    hood.setSoftLimit(SoftLimitDirection.kReverse, 0);
    //hood.setSmartCurrentLimit(20);

    TalonFXConfiguration flywheelTalonConfig = new TalonFXConfiguration();
    shooterBottomTalon.configAllSettings(flywheelTalonConfig);
    shooterBottomTalon.setNeutralMode(NeutralMode.Coast);
    shooterBottomTalon.setInverted(false);
    shooterBottomTalon.configSupplyCurrentLimit(
      new SupplyCurrentLimitConfiguration(true, 30, 35, 0.5));

      shooterTopTalon.configAllSettings(flywheelTalonConfig);
      shooterTopTalon.setNeutralMode(NeutralMode.Coast);
      shooterTopTalon.setInverted(true);
      shooterTopTalon.configSupplyCurrentLimit(
        new SupplyCurrentLimitConfiguration(true, 30, 35, 0.5));

    shooterBottomTalon.configSelectedFeedbackSensor(
      TalonFXFeedbackDevice.IntegratedSensor, // Sensor Type 
      Constants.shooterPID,      // PID Index
      Constants.shooterTimeout);      // Config Timeout

    shooterBottomTalon.config_kP(Constants.SLOT_0, Constants.kBottomGains.kP, Constants.shooterTimeout);
		shooterBottomTalon.config_kI(Constants.SLOT_0, Constants.kBottomGains.kI, Constants.shooterTimeout);
		shooterBottomTalon.config_kD(Constants.SLOT_0, Constants.kBottomGains.kD, Constants.shooterTimeout);
		shooterBottomTalon.config_kF(Constants.SLOT_0, Constants.kBottomGains.kF, Constants.shooterTimeout);

    shooterTopTalon.config_kP(Constants.SLOT_0, Constants.kTopGains.kP, Constants.shooterTimeout);
		shooterTopTalon.config_kI(Constants.SLOT_0, Constants.kTopGains.kI, Constants.shooterTimeout);
		shooterTopTalon.config_kD(Constants.SLOT_0, Constants.kTopGains.kD, Constants.shooterTimeout);
		shooterTopTalon.config_kF(Constants.SLOT_0, Constants.kTopGains.kF, Constants.shooterTimeout);
    
    shooterTopTalon.setInverted(TalonFXInvertType.OpposeMaster);
  }

  public void shooterRunAtCloseVelocity(){
    shooterBottomTalon.set(TalonFXControlMode.Velocity, bottomShooterCloseRPM*RPMToVelocity);
    shooterTopTalon.set(TalonFXControlMode.Velocity, topShooterCloseRPM*RPMToVelocity);
  }

  public void shooterRunAtVelocity(){
    shooterBottomTalon.set(TalonFXControlMode.Velocity, bottomShooterTargetVelocity);
    shooterTopTalon.set(TalonFXControlMode.Velocity, topShooterTargetVelocity);
  }

  public double getBottomShooterVelocity(ShooterSubsystem shooterSubsystem){
    bottomShooterCurrentVelocity = shooterBottomTalon.getSelectedSensorVelocity();
    //topShooterCurrentVelocity = shooterTopTalon.getSelectedSensorVelocity();
    return bottomShooterCurrentVelocity;
  }

  public boolean shooterReady(){
  return shooterReady;
}

  public void shootStop(){
    shooterBottomTalon.stopMotor();
    shooterTopTalon.stopMotor();
  }
  
  public void hoodPosition(){
    hoodPIDController.setReference(targetPosition, CANSparkMax.ControlType.kPosition);//sets the hood encoder position
  }

  public void hoodPositionReset(){ //use in initialization
    hoodEncoder.setPosition(0);
  }
  
  @Override
  public void periodic() {
    hoodPosition();//set current hood position to target hood position
    currentTy = ty.getDouble(0.0);
    currentPosition = hoodEncoder.getPosition();
    //PID stuff 
    /*hoodPIDController = hood.getPIDController();
    hoodPIDController.setP(kP);
    hoodPIDController.setI(kI);
    hoodPIDController.setD(kD);
    hoodPIDController.setIZone(kIz);
    hoodPIDController.setFF(kFF);
    hoodPIDController.setOutputRange(kMinOutput, kMaxOutput);*/
    //interpolation: finding the shooter RPMs based on min, max, and current values of limelight, hood position, and shooter RPM's
    targetPosition = (closePosition + ((currentTy - closeTy) * ((farPosition - closePosition) / (farTy - closeTy))));//interpolation for hood position
    topShooterTargetRPM = topShooterCloseRPM + ((targetPosition - closePosition) * ((topShooterFarRPM - topShooterCloseRPM) / (farPosition - closePosition)));//interpolation for top shooter wheel RPM
    bottomShooterTargetRPM = bottomShooterCloseRPM + ((targetPosition - closePosition) * ((bottomShooterFarRPM - bottomShooterCloseRPM) / (farPosition - closePosition)));//interpolation for bottom shooter wheel RPM
    //converts RPM to velocity
    topShooterTargetVelocity = topShooterTargetRPM * RPMToVelocity;
    topShooterTargetVelocity = topShooterTargetRPM * RPMToVelocity;
    //determine if shooter is up to velocity
    shooterReady = (topShooterCurrentVelocity < (topShooterTargetVelocity + velocityAllowedError) || (topShooterCurrentVelocity > topShooterTargetVelocity - velocityAllowedError));
    //display values to dashboard
    if ((currentPosition <= (targetPosition - positionAllowedError)) || (currentPosition >= (targetPosition + positionAllowedError))) {
      SmartDashboard.putString("Hood Status", "Waiting for motor to reach target");
    }
    else {
      SmartDashboard.putString("Hood Status", "at target");
    }
    SmartDashboard.putNumber("Current Hood Position", currentPosition);
    SmartDashboard.putNumber("Target Hood Position", targetPosition);
    SmartDashboard.putNumber("LimelightY", currentTy);
    // This method will be called once per scheduler run
  }
} 