package frc.robot;

import frc.robot.commands.DriveCommand;
import frc.robot.commands.SwerveXPattern;
import frc.robot.commands.Intake.DeployIntake;
import frc.robot.commands.Intake.StowIntake;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Button;

public class RobotContainer {
    private final SwerveDriveSubsystem m_drivetrain = new SwerveDriveSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();

    private final StowIntake stowIntake = new StowIntake(m_intake);

    private final XboxController brendanController = new XboxController(0);
    private final XboxController oliviaController = new XboxController(1);

    public RobotContainer() {
        m_drivetrain.register();
        m_intake.register();

        m_drivetrain.setDefaultCommand(new DriveCommand(
                m_drivetrain,
                () -> -modifyAxis(brendanController.getLeftY()), // Axes are flipped here on purpose
                () -> -modifyAxis(brendanController.getLeftX()),
                () -> -modifyAxis(brendanController.getRightX()),
                () -> brendanController.getLeftBumper(),
                1.0
        ));
        m_drivetrain.setDefaultCommand(new DriveCommand(
                m_drivetrain,
                () -> -modifyAxis(brendanController.getLeftY()), // Axes are flipped here on purpose
                () -> -modifyAxis(brendanController.getLeftX()),
                () -> -modifyAxis(brendanController.getRightX()),
                () -> brendanController.getLeftBumper(),
                1.0
        ));

        m_intake.setDefaultCommand(stowIntake);

            //Taken buttons: Left Stick, Right Stick, left stick button, right bumper, left bumper, X
        new Button(brendanController::getLeftStickButtonPressed)
                .whenPressed(m_drivetrain::zeroGyroscope);
        new Button(brendanController::getRightBumper)
                .whenPressed(new DriveCommand(
                        m_drivetrain, 
                        () -> -modifyAxis(brendanController.getLeftY()), // Axes are flipped here on purpose
                        () -> -modifyAxis(brendanController.getLeftX()),
                        () -> -modifyAxis(brendanController.getRightX()),
                        () -> brendanController.getLeftBumper(),
                        0.7));
        new Button(brendanController::getXButton)
                .whenPressed(new SwerveXPattern(m_drivetrain));
        
        new Button(oliviaController::getAButton)
                .whenPressed(new DeployIntake(m_intake));
        new Button(oliviaController::getAButton)
                .whenReleased(new StowIntake(m_intake));
    }

    public SwerveDriveSubsystem getDrivetrain() {
        return m_drivetrain;
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