package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.sensors.Pigeon2;

public class SwerveXPattern extends CommandBase {
    private final SwerveDriveSubsystem drivetrain;

    public SwerveXPattern(SwerveDriveSubsystem drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
            drivetrain.frontLeftModule.set(0, (Constants.FRONT_LEFT_MODULE_STEER_OFFSET     - 135) % 360);
            drivetrain.frontRightModule.set(0, (Constants.FRONT_RIGHT_MODULE_STEER_OFFSET   + 135) % 360);
            drivetrain.backLeftModule.set(0, (Constants.BACK_LEFT_MODULE_DRIVE_MOTOR        - 45) % 360);
            drivetrain.backRightModule.set(0, (Constants.BACK_RIGHT_MODULE_STEER_OFFSET     + 45) % 360);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        // Stop the drivetrain
        drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
    }
}