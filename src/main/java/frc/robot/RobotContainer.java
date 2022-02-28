package frc.robot;

import frc.robot.commands.DriveCommand;
import frc.robot.commands.SwerveXPattern;
import frc.robot.commands.Indexer.BallSpitter;
import frc.robot.commands.Indexer.BallSpitterStop;
import frc.robot.commands.Indexer.FeedShooter;
import frc.robot.commands.Indexer.IndexBalls;
import frc.robot.commands.Indexer.IndexerUnjam;
import frc.robot.commands.Intake.DeployIntake;
import frc.robot.commands.Intake.StowIntake;
import frc.robot.commands.Shooter.ShootAtVelocity;
import frc.robot.commands.Shooter.ShooterRunAtVelocity;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.button.Button;

import org.opencv.features2d.FlannBasedMatcher;

import edu.wpi.first.math.filter.SlewRateLimiter;
import frc.robot.commands.Autos.DoNothing;
import frc.robot.commands.Autos.DriveBack;
import frc.robot.commands.Autos.ShootOneAndDriveBack;

public class RobotContainer {
    private final SwerveDriveSubsystem m_drivetrain = new SwerveDriveSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final ShooterSubsystem m_shooter = new ShooterSubsystem();
    private final IndexerSubsystem m_indexer = new IndexerSubsystem();
    private final ClimberSubsystem m_climber = new ClimberSubsystem();
    private final CameraSubsystem m_camera = new CameraSubsystem();
    private final SendableChooser<Command> autoChooser = new SendableChooser<Command>();
 
    //private final ClimberDown climberDown = new ClimberDown(m_climber);
    //private final ClimberUp climberUp = new ClimberUp(m_climber);
    private final StowIntake stowIntake = new StowIntake(m_intake);
    private final ShooterRunAtVelocity shooterIdle = new ShooterRunAtVelocity(m_shooter);
    private final IndexBalls indexBalls = new IndexBalls(m_indexer);
    private final FeedShooter feedShooter = new FeedShooter(m_indexer);
    private final IndexerUnjam indexerUnjam = new IndexerUnjam(m_indexer);
    private final BallSpitter ballSpitter = new BallSpitter(m_indexer);
    private final BallSpitterStop ballSpitterStop = new BallSpitterStop(m_indexer);
    private final DeployIntake deployIntake = new DeployIntake(m_intake);
    private final SwerveXPattern swerveXPattern = new SwerveXPattern(m_drivetrain);
    private final ShootAtVelocity shootAtVelocity = new ShootAtVelocity(m_indexer, m_shooter);
    private final DriveBack driveBack= new DriveBack(m_drivetrain);
    private final DoNothing doNothing = new DoNothing();
    private final ShootOneAndDriveBack shootOneAndDriveBack = new ShootOneAndDriveBack();

    private final XboxController brendanController = new XboxController(0);
    private final XboxController oliviaController = new XboxController(1);
    private double slewDouble = 3.0;

    private final SlewRateLimiter brendanControllerLeftY = new SlewRateLimiter(slewDouble);
    private final SlewRateLimiter brendanControllerLeftX = new SlewRateLimiter(slewDouble);
    private final SlewRateLimiter brendanControllerRightX = new SlewRateLimiter(slewDouble);

    public RobotContainer() {
        m_drivetrain.register();
        m_intake.register();
        m_shooter.register();
        m_indexer.register();

        autoChooser.setDefaultOption("Default", ballSpitter);
        autoChooser.addOption("Other Option", ballSpitter);
        SmartDashboard.putData(autoChooser);

        m_drivetrain.setDefaultCommand(new DriveCommand(
                m_drivetrain,
                () -> -modifyAxis(brendanControllerLeftY.calculate(brendanController.getLeftY())), // Axes are flipped here on purpose
                () -> -modifyAxis(brendanControllerLeftX.calculate(brendanController.getLeftX())),
                () -> -modifyAxis(brendanControllerRightX.calculate(brendanController.getRightX())),
                () -> brendanController.getLeftBumper(), //RobotCentric
                () -> brendanController.getRightBumper(), //lowPower
                () -> brendanController.getLeftTriggerAxis() > 0.5 //slowTurn
        ));
        
        m_intake.setDefaultCommand(stowIntake);

        m_shooter.setDefaultCommand(shooterIdle);
        m_indexer.setDefaultCommand(indexBalls);
    
        //m_shooter.setDefaultCommand(shooterIdle);
        m_indexer.setDefaultCommand(indexBalls);


        new Button(brendanController::getBButtonPressed)
                .whenPressed(m_drivetrain::zeroGyroscope);
        new Button(brendanController::getXButton)
                .whileHeld(swerveXPattern);
        new Button(brendanController::getAButton)
                .whileHeld(deployIntake);
        new Button(brendanController::getYButton)
                .whenPressed(driveBack);
     
        new Button(oliviaController::getXButton)
                .whileHeld(indexerUnjam);
        new Button(() -> oliviaController.getLeftTriggerAxis() > 0.5) //done differently because the triggers return 0-1 instead of a boolean
                .whileHeld(shooterIdle);
        new Button(() -> oliviaController.getLeftTriggerAxis() > 0.5)
                .whileHeld(feedShooter);
        new Button(() -> oliviaController.getLeftTriggerAxis() < 0.5)
                .whenReleased(indexBalls);
        new Button(() -> oliviaController.getRightTriggerAxis() > 0.5)//not tested
                .whileHeld(ballSpitter);
        new Button(() -> oliviaController.getRightTriggerAxis() > 0.5)//not tested
                .whenReleased(ballSpitterStop);
        new Button(oliviaController::getBButton)
                .whileHeld(deployIntake);
        //new Button(oliviaController::getLeftBumper)
           //     .whenHeld(climberUp);
       // new Button(oliviaController::getRightBumper)
        //        .whenHeld(climberDown);
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