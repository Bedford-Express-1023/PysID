package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.PointTowardsHub;

import frc.robot.commands.Autos.DoNothing;
import frc.robot.commands.Autos.TwoBallAtTarmac;
import frc.robot.commands.Autos.fourBall;
import frc.robot.commands.Autos.threeBall;
import frc.robot.commands.Climber.ClimbLock;
import frc.robot.commands.Climber.ClimbStop;
import frc.robot.commands.Climber.ClimbUp;
import frc.robot.commands.Climber.ClimberUnlock;
import frc.robot.commands.Indexer.BallSpitter;
import frc.robot.commands.Indexer.FeedShooter;
import frc.robot.commands.Indexer.StupidIndexer;
import frc.robot.commands.Indexer.IndexerStop;
//import frc.robot.commands.Indexer.ReactToColor;
import frc.robot.commands.Intake.DeployIntake;
import frc.robot.commands.Intake.StowIntake;
import frc.robot.commands.Shooter.AutoShootCommand;
import frc.robot.commands.Shooter.HoodReturnToZero;
import frc.robot.commands.Shooter.SetHoodPositionAuto;
import frc.robot.commands.Shooter.ShootStop;
import frc.robot.commands.Shooter.ShooterTuningCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.cameraserver.CameraServer;

public class RobotContainer {
    private final SwerveDriveSubsystem m_drivetrain = new SwerveDriveSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final ShooterSubsystem m_shooter = new ShooterSubsystem();
    private final HoodSubsystem m_hood = new HoodSubsystem();
    private final IndexerSubsystem m_indexer = new IndexerSubsystem();
    private final ClimberSubsystem m_climber = new ClimberSubsystem();
    public final SendableChooser<Command> autoChooser = new SendableChooser<Command>();
    public final SendableChooser<Command> autoDelay = new SendableChooser<Command>();
    public final SendableChooser<Command> teamColorChooser = new SendableChooser<Command>();
 
    private final ClimbUp climbUp = new ClimbUp(m_climber);
    
    private final ClimbStop climbStop = new ClimbStop(m_climber);
    private final ClimberUnlock climberUnlock = new ClimberUnlock(m_climber);
    private final ClimbLock climberLock = new ClimbLock(m_climber);
    private final TwoBallAtTarmac twoBallAtTarmac = new TwoBallAtTarmac(m_drivetrain, m_intake, m_shooter, m_hood, m_indexer);
    private final HoodReturnToZero hoodReturnToZero = new HoodReturnToZero(m_hood);
    private final StowIntake stowIntake = new StowIntake(m_intake);
    private final BallSpitter ballSpitter = new BallSpitter(m_indexer);
    private final DeployIntake deployIntake = new DeployIntake(m_intake);
    private final AutoShootCommand autoShootCommand = new AutoShootCommand(m_hood, m_shooter, m_indexer);
    private final ShootStop shootStop = new ShootStop(m_shooter);
    private final SetHoodPositionAuto setHoodPositionAuto = new SetHoodPositionAuto(m_hood);
    private final DoNothing doNothing = new DoNothing();
    private final IndexerStop indexerStop = new IndexerStop(m_indexer);
    private final PointTowardsHub pointTowardsHub = new PointTowardsHub(m_drivetrain);
   // private final ReactToColor reactToColor = new ReactToColor(m_indexer);
    private final ShooterTuningCommand shooterTuningCommand = new ShooterTuningCommand(m_shooter, m_hood);
    private final StupidIndexer stupidIndexer = new StupidIndexer(m_indexer);
    private final FeedShooter feedShooter = new FeedShooter(m_indexer);
    private final XboxController brendanController = new XboxController(0);
    private final XboxController oliviaController = new XboxController(1);


    
    public RobotContainer() {
        m_drivetrain.register();
        m_intake.register();
        m_shooter.register();
        m_indexer.register();
        m_climber.register();
        m_hood.register();

        CameraServer.startAutomaticCapture(0);

        autoChooser.setDefaultOption("Do Nothing", doNothing);
        autoChooser.addOption("2-ball", twoBallAtTarmac);
        autoChooser.addOption("3-ball", new threeBall(m_intake, m_indexer, m_drivetrain, m_shooter, m_hood));
        autoChooser.addOption("4-ball", new fourBall(m_intake, m_indexer, m_drivetrain, m_shooter, m_hood));

        SmartDashboard.putData(autoChooser);

        autoDelay.setDefaultOption("none", new WaitCommand(0.0));
        autoDelay.addOption("1.0", new WaitCommand(1.0));
        autoDelay.addOption("2.0", new WaitCommand(2.0));
        autoDelay.addOption("3.0", new WaitCommand(3.0));
        autoDelay.addOption("4.0", new WaitCommand(4.0));
        autoDelay.addOption("5.0", new WaitCommand(5.0));
        autoDelay.addOption("6.0", new WaitCommand(6.0));
        autoDelay.addOption("7.0", new WaitCommand(7.0));
        autoDelay.addOption("8.0", new WaitCommand(8.0));
        autoDelay.addOption("9.0", new WaitCommand(9.0));
        autoDelay.addOption("10.0", new WaitCommand(10.0));
        SmartDashboard.putData(autoDelay);

        m_drivetrain.setDefaultCommand(new DriveCommand(
                m_drivetrain,
                () -> -modifyAxis(brendanController.getLeftY()), // Axes are flipped here on purpose
                () -> -modifyAxis(brendanController.getLeftX()),
                () -> -modifyAxis(brendanController.getRightX()),
                () -> brendanController.getLeftBumper(), //RobotCentric
                () -> brendanController.getRightBumper(), //lowPower
                () -> !(brendanController.getLeftTriggerAxis() > 0.5) //slowTurn
        ));
        
        m_intake.setDefaultCommand(stowIntake);
        m_indexer.setDefaultCommand(stupidIndexer);
        m_shooter.setDefaultCommand(shootStop);
        m_climber.setDefaultCommand(climberLock);
        m_hood.setDefaultCommand(setHoodPositionAuto);
       
      
        new Button(brendanController::getBButtonPressed)
                .whenPressed(m_drivetrain::zeroGyroscope);
        new Button(oliviaController::getXButton)
                .whileHeld(m_indexer::indexerUnjam);
        new Button(oliviaController::getXButton)
                .whileHeld(m_intake::unjamIntake);
        new Button(() -> oliviaController.getRightTriggerAxis() > 0.5)//not tested
                .whileHeld(ballSpitter);
        new Button(oliviaController::getBButton)
                .whileHeld(deployIntake);
        new POVButton(oliviaController, 0)
                .whileHeld(climbUp);
        new POVButton(oliviaController, 0)
                .whileHeld(m_hood::hoodReturnToZero);
        new POVButton(oliviaController, 0)
                .whenReleased(climbStop);
        new Button(oliviaController::getStartButton)
                .toggleWhenPressed(climberUnlock, true);
        new Button(oliviaController::getStartButton)
                .whenPressed(hoodReturnToZero);
        new Button(oliviaController::getStartButton)
                .whenPressed(indexerStop);

        new Button(() -> brendanController.getRightTriggerAxis() > 0.5)
                .whileHeld(autoShootCommand);
               
                //.whileHeld(shooterTuningCommand);
        new Button(() -> brendanController.getRightTriggerAxis() > 0.5)
                .whileHeld(pointTowardsHub);
        new Button(() -> brendanController.getLeftTriggerAxis() > 0.5)
       .whileHeld(feedShooter);
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