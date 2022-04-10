// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.decimal4j.util.DoubleRounder;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  private final WPI_TalonFX shooterBottomTalon = new WPI_TalonFX(41);
  private final WPI_TalonFX shooterTopTalon = new WPI_TalonFX(40);

  double topShooterTargetRPM;
  double bottomShooterTargetRPM;
  double bottomShooterCurrentVelocity;
  double topShooterFenderRPM = 6518;
  double bottomShooterFenderRPM = 10710;
  double topShooterTarmacRPM = 11200;
  double bottomShooterTarmacRPM = 10200;
  double topShooterLaunchpadRPM = 12000;
  double bottomShooterLaunchpadRPM = 12000;
  double topShooterLowGoalRPM = 2000;
  double bottomShooterLowGoalRPM = 4000;
  double RPMToVelocity = 3.57;
  boolean shooterReady;

  double limelightY;
  double limelightX;
  double limelightHasTarget;
  double shooterTopTargetVelocity;
  double shooterBottomTargetVelocity;
  double bottomTarget = 13485;
  double topTarget = 10658;
  double roundLimelightY;

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
        Constants.shooterPID, // PID Index
        Constants.shooterTimeout); // Config Timeout

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

  public void shooterRunAtFenderVelocity() {
    shooterBottomTalon.set(TalonFXControlMode.Velocity, bottomShooterFenderRPM);
    shooterTopTalon.set(TalonFXControlMode.Velocity, topShooterFenderRPM);
  }

  public void shooterRunAtTarmacVelocity() {
    shooterBottomTalon.set(TalonFXControlMode.Velocity, bottomShooterTarmacRPM);
    shooterTopTalon.set(TalonFXControlMode.Velocity, topShooterTarmacRPM);
  }

  public void shooterRunAtLaunchpadVelocity() {
    shooterBottomTalon.set(TalonFXControlMode.Velocity, bottomShooterLaunchpadRPM);
    shooterTopTalon.set(TalonFXControlMode.Velocity, topShooterLaunchpadRPM);
  }

  public void shooterRunAtLowGoalVelocity() {
    shooterBottomTalon.set(TalonFXControlMode.Velocity, bottomShooterLowGoalRPM);
    shooterTopTalon.set(TalonFXControlMode.Velocity, topShooterLowGoalRPM);
  }

  public double getBottomShooterVelocity(ShooterSubsystem shooterSubsystem) {
    bottomShooterCurrentVelocity = shooterBottomTalon.getSelectedSensorVelocity();
    return bottomShooterCurrentVelocity;
  }

  public boolean shooterReadyFender() {
    if (shooterBottomTalon.getSelectedSensorVelocity() > bottomShooterFenderRPM - 400 &&
        shooterBottomTalon.getSelectedSensorVelocity() < bottomShooterFenderRPM + 400) {
      return true;
    } else {
      return false;
    }
  }

  public boolean shooterReadyTarmac() {
    if (shooterBottomTalon.getSelectedSensorVelocity() > bottomShooterTarmacRPM - 400 &&
        shooterBottomTalon.getSelectedSensorVelocity() < bottomShooterTarmacRPM + 400) {
      return true;
    } else {
      return false;
    }
  }

  public boolean shooterReadyLaunchpad() {
    if (shooterBottomTalon.getSelectedSensorVelocity() > bottomShooterLaunchpadRPM - 400 &&
        shooterBottomTalon.getSelectedSensorVelocity() < bottomShooterLaunchpadRPM + 400) {
      return true;
    } else {
      return false;
    }
  }

  public boolean shooterReadyLowGoal() {
    if (shooterBottomTalon.getSelectedSensorVelocity() > bottomShooterLowGoalRPM - 400 &&
        shooterBottomTalon.getSelectedSensorVelocity() < topShooterLowGoalRPM + 400) {
      return true;
    } else {
      return false;
    }
  }

  public boolean shooterReadyAuto(){
    if (shooterBottomTalon.getSelectedSensorVelocity() > shooterBottomTargetVelocity - 400
        && shooterBottomTalon.getSelectedSensorVelocity() < shooterBottomTargetVelocity + 400 && limelightAimReady() == true){
          return true;
        }
    else {
      return false;
    }
  }

  public void shootStop() {
    shooterBottomTalon.stopMotor();
    shooterTopTalon.stopMotor();
  }

  public boolean limelightAimReady(){
    limelightX = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    if (limelightX < 2 && limelightX > -2){
      return true;
    }
    else {
      return false;
    }
  }

  public double limelightGetY() {
    limelightY = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    roundLimelightY = DoubleRounder.round(limelightY, 1);
    return roundLimelightY;
  }

  public double limelightHasTarget() {
    limelightHasTarget = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    return limelightHasTarget;
  }

  public void setShooterSpeedsAuto() {
    shooterTopTargetVelocity = Constants.TargetConstants.SHOOTER_TOP_SPEED_INTERPOLATOR
        .getInterpolatedValue(roundLimelightY);
    shooterBottomTargetVelocity = Constants.TargetConstants.SHOOTER_BOTTOM_SPEED_INTERPOLATOR
        .getInterpolatedValue(roundLimelightY);
    shooterTopTalon.set(TalonFXControlMode.Velocity, shooterTopTargetVelocity);
    shooterBottomTalon.set(TalonFXControlMode.Velocity, shooterBottomTargetVelocity);
  }

  public void shooterTuningSpeeds() {
    shooterBottomTalon.set(TalonFXControlMode.Velocity, bottomTarget);
    shooterTopTalon.set(TalonFXControlMode.Velocity, topTarget);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Limelight Y", limelightGetY());
    SmartDashboard.putNumber("Shooter Top Target Speed",
      shooterTopTargetVelocity);
    SmartDashboard.putNumber("Shooter Top Actual Speed",
      shooterTopTalon.getSelectedSensorVelocity());
    SmartDashboard.putNumber("Shooter Bottom Target Speed",
      shooterBottomTargetVelocity);
    SmartDashboard.putNumber("Shooter Bottom Actual Speed",
      shooterBottomTalon.getSelectedSensorVelocity());
     
    SmartDashboard.putNumber("Limelight has target", limelightHasTarget());
    SmartDashboard.getNumber("Shooter Bottom Speed (Tuning)", 0);
    SmartDashboard.getNumber("Shooter Top Speed (Tuning)", 0);
    // This method will be called once per scheduler run
  }
}