package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.BooleanSupplier;

public class RobotCentricCommand extends CommandBase {
    private final SwerveDriveSubsystem drivetrain;
    private XboxController xboxController;

    public RobotCentricCommand(SwerveDriveSubsystem drivetrain, XboxController controller) {
        this.drivetrain = drivetrain;
        this.xboxController = controller;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.RobotCentric = true;
    }

    @Override
    public boolean isFinished() {
        return xboxController.getLeftBumperReleased();
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.RobotCentric = false;
    }
}