package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SwerveXPattern extends CommandBase {
    private final SwerveDriveSubsystem drivetrain;

    public SwerveXPattern(SwerveDriveSubsystem drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.CommandVariable = "Swerve X";
            drivetrain.frontLeftModule.set(0, (Constants.FRONT_LEFT_MODULE_STEER_OFFSET     - 135));
            drivetrain.frontRightModule.set(0, (Constants.FRONT_RIGHT_MODULE_STEER_OFFSET   + 135));
            drivetrain.backLeftModule.set(0, (Constants.BACK_LEFT_MODULE_DRIVE_MOTOR        - 45));
            drivetrain.backRightModule.set(0, (Constants.BACK_RIGHT_MODULE_STEER_OFFSET     + 45));
    }

    @Override
    public void end(boolean interrupted) {
        // Stop the drivetrain
        drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
    }
}