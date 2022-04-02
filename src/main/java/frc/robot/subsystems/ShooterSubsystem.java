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
import java.lang.Boolean;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  public final WPI_TalonFX shooterBottomTalon = new WPI_TalonFX(41);
  public final WPI_TalonFX shooterTopTalon = new WPI_TalonFX(40);
  
  double topShooterTargetRPM;
  double bottomShooterTargetRPM;
  double topShooterFenderRPM = 1825.77;
  double bottomShooterFenderRPM = 3000;
  double topShooterLaunchpadRPM = 3100;
  double bottomShooterLaunchpadRPM = 3100;

  double topShooterTargetVelocity;
  double bottomShooterTargetVelocity;
  double bottomShooterCurrentVelocity;
  double topShooterCurrentVelocity;
  double RPMToVelocity = 3.57;
  double velocityAllowedError = 400;
  boolean shooterReady;
  double currentTy;

  double topShooterFenderVelocity = topShooterFenderRPM * RPMToVelocity;
  double bottomShooterFenderVelocity = bottomShooterFenderRPM * RPMToVelocity;
  double topShooterLaunchpadVelocity = topShooterLaunchpadRPM * RPMToVelocity;
  double bottomShooterLaunchpadVelocity = bottomShooterLaunchpadRPM * RPMToVelocity;

  double closePosition = 1; //FIXME hood angle for close shot
  double farPosition = 155; //FIXME hood angle for far shot
  double closeTy = 8.8; //FIXME y coordinate of target on limelight cam for close shot
  double farTy = -8.8; //FIXME y coordinate of target on limelight cam for far shot

  double targetPosition;

  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
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

  public void shooterRunAtVelocity(){
    shooterBottomTalon.set(TalonFXControlMode.Velocity, bottomShooterTargetRPM);
    shooterTopTalon.set(TalonFXControlMode.Velocity, topShooterTargetRPM);
  }

  public double getBottomShooterVelocity(ShooterSubsystem shooterSubsystem){
    bottomShooterCurrentVelocity = shooterBottomTalon.getSelectedSensorVelocity();
    //topShooterCurrentVelocity = shooterTopTalon.getSelectedSensorVelocity();
    return bottomShooterCurrentVelocity;
  }

  public boolean shooterReady(){
  if (bottomShooterCurrentVelocity > bottomShooterTargetVelocity - velocityAllowedError &&
    bottomShooterCurrentVelocity < bottomShooterTargetVelocity + velocityAllowedError){
      return true;
      }
    else {
      return false;
    }
  }

  public void shootStop(){
    shooterBottomTalon.stopMotor();
    shooterTopTalon.stopMotor();
  }

  @Override
  public void periodic() {
    targetPosition = (closePosition + ((currentTy - closeTy) * ((farPosition - closePosition) / (farTy - closeTy))));//interpolation for hood position
    topShooterTargetVelocity = topShooterFenderVelocity + ((targetPosition - closePosition) * ((topShooterLaunchpadVelocity - topShooterFenderVelocity) / (farPosition - closePosition)));//interpolation for top shooter wheel RPM
    bottomShooterTargetVelocity = bottomShooterFenderVelocity + ((targetPosition - closePosition) * ((bottomShooterLaunchpadVelocity - bottomShooterFenderVelocity) / (farPosition - closePosition)));//interpolation for bottom shooter wheel RPM
    //display values to dashboard
    // This method will be called once per scheduler run
  }
} 