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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  private final WPI_TalonFX shooterBottomTalon = new WPI_TalonFX(41);
  private final WPI_TalonFX shooterTopTalon = new WPI_TalonFX(40);

  double topShooterTargetRPM;
  double bottomShooterTargetRPM;
  double bottomShooterCurrentVelocity;
  double topShooterFenderRPM = 10710;
  double bottomShooterFenderRPM = 9818;
  double topShooterTarmacRPM = 12000;
  double bottomShooterTarmacRPM = 10500;
  double topShooterLaunchpadRPM = 13500;
  double bottomShooterLaunchpadRPM = 14000;
  double RPMToVelocity = 3.57;
  boolean shooterReady;
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

  public void shooterRunAtFenderVelocity(){
    shooterBottomTalon.set(TalonFXControlMode.Velocity, bottomShooterFenderRPM);
    shooterTopTalon.set(TalonFXControlMode.Velocity, topShooterFenderRPM);
  }

  public void shooterRunAtTarmacVelocity(){
    shooterBottomTalon.set(TalonFXControlMode.Velocity, bottomShooterTarmacRPM);
    shooterTopTalon.set(TalonFXControlMode.Velocity, topShooterTarmacRPM);
  }

  public void shooterRunAtLaunchpadVelocity(){
    shooterBottomTalon.set(TalonFXControlMode.Velocity, bottomShooterLaunchpadRPM);
    shooterTopTalon.set(TalonFXControlMode.Velocity, topShooterLaunchpadRPM);
  }

  public double getBottomShooterVelocity(ShooterSubsystem shooterSubsystem){
    bottomShooterCurrentVelocity = shooterBottomTalon.getSelectedSensorVelocity();
    return bottomShooterCurrentVelocity;
  }

  public boolean shooterReadyFender(){
  if (shooterBottomTalon.getSelectedSensorVelocity() > bottomShooterFenderRPM - 250 && 
            shooterBottomTalon.getSelectedSensorVelocity() < bottomShooterFenderRPM + 250){
      return true;
    }
  else {
    return false;
    } 
  }

  public boolean shooterReadyTarmac(){
    if (shooterBottomTalon.getSelectedSensorVelocity() > bottomShooterTarmacRPM - 250 && 
            shooterBottomTalon.getSelectedSensorVelocity() < bottomShooterTarmacRPM + 250){
      return true;
    }
    else {
      return false;
    }
  }

  public boolean shooterReadyLaunchpad(){
    if (shooterBottomTalon.getSelectedSensorVelocity() > bottomShooterLaunchpadRPM - 250 && 
            shooterBottomTalon.getSelectedSensorVelocity() < bottomShooterLaunchpadRPM + 250){
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
    SmartDashboard.putNumber("Shooter Velocity Bottom", shooterBottomTalon.getSelectedSensorVelocity());
    // This method will be called once per scheduler run
  }
} 