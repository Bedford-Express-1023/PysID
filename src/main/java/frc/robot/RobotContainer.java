package frc.robot;

import frc.robot.Utils.AxisButton;
import frc.robot.Utils.CommandXboxController;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ShootAtVelocity;
import frc.robot.commands.ShooterIdle;
import frc.robot.commands.SwerveXPattern;
import frc.robot.commands.Indexer.FeedShooter;
import frc.robot.commands.Indexer.IndexBalls;
import frc.robot.commands.Intake.DeployIntake;
import frc.robot.commands.Intake.StowIntake;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Button;

public class RobotContainer {
    private final SwerveDriveSubsystem m_drivetrain = new SwerveDriveSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final ShooterSubsystem m_shooter = new ShooterSubsystem();
    private final IndexerSubsystem m_indexer = new IndexerSubsystem();

    private final StowIntake stowIntake = new StowIntake(m_intake);
    private final ShooterIdle shooterIdle = new ShooterIdle(m_shooter);
    private final IndexBalls indexBalls = new IndexBalls(m_indexer);

    private final XboxController brendanController = new XboxController(0);
    private final CommandXboxController oliviaController = new CommandXboxController(1);

    public RobotContainer() {
        m_drivetrain.register();
        m_intake.register();
        m_shooter.register();
        m_indexer.register();

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
        m_shooter.setDefaultCommand(shooterIdle);
        m_indexer.setDefaultCommand(indexBalls);
    

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
        
        new Button(oliviaController.a())
                .whileHeld(new DeployIntake(m_intake));
        new Button(oliviaController.rightBumper())
                .whileHeld(new ShootAtVelocity(m_indexer, m_shooter));
        
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