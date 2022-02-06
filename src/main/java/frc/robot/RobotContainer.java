package frc.robot;

import frc.robot.commands.RobotCentricCommand;
import frc.robot.commands.DriveCommand;
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
                () -> -modifyAxis(controller.getRightX())
        ));
        drivetrain.setDefaultCommand(new DriveCommand(
                drivetrain,
                () -> -modifyAxis(controller.getLeftY()), // Axes are flipped here on purpose
                () -> -modifyAxis(controller.getLeftX()),
                () -> -modifyAxis(controller.getRightX())
        ));

        new Button(controller::getXButtonPressed)
                .whenPressed(drivetrain::zeroGyroscope);
        new Button(controller::getLeftBumper)
                .whenPressed(new ParallelCommandGroup(
                        new RobotCentricCommand(drivetrain, controller), 
                        new DriveCommand(
                                drivetrain, 
                                () -> -modifyAxis(controller.getLeftY()), // Axes are flipped here on purpose
                                () -> -modifyAxis(controller.getLeftX()),
                                () -> -modifyAxis(controller.getRightX()))));
                
        
        //new Button(controller::getYButton)
                //.whenReleased(new AllForwardCommand(drivetrain, 0.0, 0.0, 0.0));
        //new Button(controller::getAButton)
                //.whenPressed(new AllForwardCommand(drivetrain, 0.0, 0.0, 0.0));
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