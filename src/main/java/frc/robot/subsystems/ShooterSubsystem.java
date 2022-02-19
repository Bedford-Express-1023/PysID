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

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  public final WPI_TalonFX shooterBottomTalon = new WPI_TalonFX(41);
  public final WPI_TalonFX shooterTopTalon = new WPI_TalonFX(40);
  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    TalonFXConfiguration flywheelTalonConfig = new TalonFXConfiguration();
    shooterBottomTalon.configAllSettings(flywheelTalonConfig);
    shooterBottomTalon.setNeutralMode(NeutralMode.Coast);
    shooterBottomTalon.setInverted(false);
    shooterBottomTalon.configSupplyCurrentLimit(
      new SupplyCurrentLimitConfiguration(true, 30, 35, 0.5));

    shooterBottomTalon.configSelectedFeedbackSensor(
      TalonFXFeedbackDevice.IntegratedSensor, // Sensor Type 
      Constants.shooterPID,      // PID Index
      Constants.shooterTimeout);      // Config Timeout

    shooterBottomTalon.config_kP(Constants.SLOT_0, Constants.kGains.kP, Constants.shooterTimeout);
		shooterBottomTalon.config_kI(Constants.SLOT_0, Constants.kGains.kI, Constants.shooterTimeout);
		shooterBottomTalon.config_kD(Constants.SLOT_0, Constants.kGains.kD, Constants.shooterTimeout);
		shooterBottomTalon.config_kF(Constants.SLOT_0, Constants.kGains.kF, Constants.shooterTimeout);

    shooterTopTalon.follow(shooterBottomTalon);
    shooterTopTalon.setInverted(TalonFXInvertType.OpposeMaster);
  }

  public void shooterRunAtVelocity(int velocity){
    shooterBottomTalon.set(TalonFXControlMode.Velocity, velocity); //FIXME set correct velocity
  }

  public double getShooterVelocity(ShooterSubsystem shooterSubsystem){
    double velocity = shooterBottomTalon.getSelectedSensorVelocity();
    return velocity;
  }

  public void shootStop(){
    shooterBottomTalon.stopMotor();
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
