package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix.sensors.Pigeon2;

public class DriveCommand extends CommandBase {
    private final SwerveDriveSubsystem drivetrain;
    private final DoubleSupplier translationXSupplier;
    private final DoubleSupplier translationYSupplier;
    private final DoubleSupplier rotationSupplier;
    private final BooleanSupplier robotCentric;
    private BooleanSupplier lowPower;
    public double drivePower;
    public BooleanSupplier slowTurn;

    public DriveCommand(
            SwerveDriveSubsystem drivetrain,
            DoubleSupplier translationXSupplier,
            DoubleSupplier translationYSupplier,
            DoubleSupplier rotationSupplier,
            BooleanSupplier robotCentric,
            BooleanSupplier lowPower,
            BooleanSupplier slowTurn
    ) {
        this.drivetrain = drivetrain;
        this.translationXSupplier = translationXSupplier;
        this.translationYSupplier = translationYSupplier;
        this.rotationSupplier = rotationSupplier;
        this.robotCentric = robotCentric;
        this.lowPower = lowPower;
        this.slowTurn = slowTurn;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        double translationXPercent = translationXSupplier.getAsDouble();
        double translationYPercent = translationYSupplier.getAsDouble();
        double rotationPercent = rotationSupplier.getAsDouble();
        if (this.lowPower.getAsBoolean()) {drivePower = 0.3;} 
        if (robotCentric.getAsBoolean()) {
            drivetrain.drive(
                new ChassisSpeeds(
                        translationXPercent * drivePower * Constants.MAX_VELOCITY_METERS_PER_SECOND,
                        translationYPercent * drivePower * Constants.MAX_VELOCITY_METERS_PER_SECOND,
                        rotationPercent * (slowTurn.getAsBoolean() ? 0.3 : 1.0) * Constants.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND
                    )
            );
        } else {
            drivetrain.drive(
                    ChassisSpeeds.fromFieldRelativeSpeeds(
                            translationXPercent * Constants.MAX_VELOCITY_METERS_PER_SECOND,
                            translationYPercent * Constants.MAX_VELOCITY_METERS_PER_SECOND,
                            rotationPercent * Constants.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND,
                            drivetrain.getRotation()
                )
            );
        }
        this.drivePower = 1.0;
    }

    @Override
    public void end(boolean interrupted) {
        // Stop the drivetrain
        drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
    }
}