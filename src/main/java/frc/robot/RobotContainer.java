package frc.robot;

import frc.robot.commands.DriveCommand;
import frc.robot.commands.SwerveXPattern;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Button;

public class RobotContainer {
    private final SwerveDriveSubsystem drivetrain = new SwerveDriveSubsystem();

    private final XboxController controller = new XboxController(0);

    public RobotContainer() {
        drivetrain.register();

        drivetrain.setDefaultCommand(new DriveCommand(
                drivetrain,
                () -> -modifyAxis(controller.getLeftY()), // Axes are flipped here on purpose
                () -> -modifyAxis(controller.getLeftX()),
                () -> -modifyAxis(controller.getRightX()),
                () -> controller.getLeftBumper(),
                1.0
        ));
        drivetrain.setDefaultCommand(new DriveCommand(
                drivetrain,
                () -> -modifyAxis(controller.getLeftY()), // Axes are flipped here on purpose
                () -> -modifyAxis(controller.getLeftX()),
                () -> -modifyAxis(controller.getRightX()),
                () -> controller.getLeftBumper(),
                1.0
        ));
            //Taken buttons: Left Stick, Right Stick, left stick button, right bumper, left bumper, X
        new Button(controller::getLeftStickButtonPressed)
                .whenPressed(drivetrain::zeroGyroscope);
        new Button(controller::getRightBumper)
                .whenPressed(new DriveCommand(
                        drivetrain, 
                        () -> -modifyAxis(controller.getLeftY()), // Axes are flipped here on purpose
                        () -> -modifyAxis(controller.getLeftX()),
                        () -> -modifyAxis(controller.getRightX()),
                        () -> controller.getLeftBumper(),
                        0.7));
        new Button(controller::getXButton)
                .whenPressed(new SwerveXPattern(drivetrain));
    }

    public SwerveDriveSubsystem getDrivetrain() {
        return drivetrain;
    }

    private static double deadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } else {
                return (value + deadband) / (1.0 - deadband);
            }
        } else {
            return 0.0;
        }
    }

    private static double modifyAxis(double value) {
        // Deadband
        value = deadband(value, 0.05);

        // Square the axis
        value = Math.copySign(value * value, value);

        return value;
    }
}