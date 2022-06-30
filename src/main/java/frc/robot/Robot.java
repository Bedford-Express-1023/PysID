// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private RobotContainer m_robotContainer;
  public Options options = new Options();
  public TalonFX motor;
  public int timer;
  public int ks = 0;
  public int kv = 0;
  public Boolean staticOvercome = false;

  public double targetVelocity;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousPeriodic(){
    int ramp = 25;
    timer++;
    if (targetVelocity >= 12000) {
      SmartDashboard.putNumber("kv", kv);
      SmartDashboard.putNumber("ks", ks);
    }
    motor.set(ControlMode.Velocity, targetVelocity);
    SmartDashboard.putNumber("Target Velocity", targetVelocity);
    SmartDashboard.putNumber("Actual Velocity", motor.getSelectedSensorVelocity());
    SmartDashboard.putNumber("Time Left", (((12000 / targetVelocity) / ramp) * 5) / 50);

    if (!staticOvercome && motor.getSelectedSensorVelocity() > 2) {
      targetVelocity = ks;
      staticOvercome = true;
      return;
    }
    if (timer % 5 == 0) {
      kv += motor.getSelectedSensorVelocity() / ((targetVelocity - ks) * (12000 / ramp));
      targetVelocity+= (options.reversed ? -ramp : ramp);
    }
  }

  @Override
  public void autonomousInit() {
    targetVelocity = 0;
    ks = 0;
    kv = 0;
    motor = new TalonFX(options.CANID);
  }
}
