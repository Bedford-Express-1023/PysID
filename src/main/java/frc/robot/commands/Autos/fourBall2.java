// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

/** Add your docs here. */
public class fourBall2 extends PPSwerveControllerCommand {
    public SwerveDriveSubsystem drivetrain;
    public static final PathPlannerTrajectory trajectory = PathPlanner.loadPath("4 ball 2", 3, 4);

    private fourBall2(PathPlannerTrajectory trajectory, Supplier<Pose2d> pose, SwerveDriveKinematics kinematics,
            PIDController xController, PIDController yController, ProfiledPIDController thetaController,
            Consumer<SwerveModuleState[]> outputModuleStates, Subsystem[] requirements) {
        super(trajectory, pose, kinematics, xController, yController, thetaController, outputModuleStates, requirements);
    }
    public fourBall2(SwerveDriveSubsystem drivetrain) {
        super(
            trajectory, 
            drivetrain::getRealOdometry, 
            drivetrain.kinematics, 
            new PIDController(8, 0, 0.0), 
            new PIDController(8, 0.0, 0.0), 
            new ProfiledPIDController(8, 0.0, 0.0, new TrapezoidProfile.Constraints(5.0,6.0)), 
            (states) -> {
                    drivetrain.updateOdometry();
                    drivetrain.frontLeftModule.set(states[0].speedMetersPerSecond, states[0].angle.getRadians()-Math.PI); //not sure why the Math.pi is there but don't remove it because it works
                    drivetrain.frontRightModule.set(states[1].speedMetersPerSecond, states[1].angle.getRadians());
                    drivetrain.backLeftModule.set(states[2].speedMetersPerSecond, states[2].angle.getRadians()); 
                    drivetrain.backRightModule.set(states[3].speedMetersPerSecond, states[3].angle.getRadians()); 
            }, 
            drivetrain);
            this.drivetrain = drivetrain;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.frontLeftModule.set(0, 0); //not sure why the Math.pi is there but don't remove it because it works
        drivetrain.frontRightModule.set(0, 0);
        drivetrain.backLeftModule.set(0, 0); 
        drivetrain.backRightModule.set(0, 0); 
        super.end(false);
    }
}
