package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DriveCommand extends CommandBase {
    private final SwerveDriveSubsystem drivetrain;
    private final DoubleSupplier translationXSupplier;
    private final DoubleSupplier translationYSupplier;
    public final DoubleSupplier rotationXSupplier;
    public final DoubleSupplier rotationYSupplier;
    private final BooleanSupplier robotCentric;
    private BooleanSupplier lowPower;
    public double drivePower;
    public BooleanSupplier slowTurn;
    public final ProfiledPIDController rotationPID = new ProfiledPIDController(0.2, 0.0, 0.0, new TrapezoidProfile.Constraints(Constants.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND, 5));


    public DriveCommand(
            SwerveDriveSubsystem drivetrain,
            DoubleSupplier translationXSupplier,
            DoubleSupplier translationYSupplier,
            DoubleSupplier rotationXSupplier,
            DoubleSupplier rotationYSupplier,
            BooleanSupplier robotCentric,
            BooleanSupplier lowPower,
            BooleanSupplier slowTurn
    ) {
        this.drivetrain = drivetrain;
        this.translationXSupplier = translationXSupplier;
        this.translationYSupplier = translationYSupplier;
        this.robotCentric = robotCentric;
        this.lowPower = lowPower;
        this.slowTurn = slowTurn;
        this.rotationXSupplier = rotationXSupplier;
        this.rotationYSupplier = rotationYSupplier;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        double translationXPercent = translationXSupplier.getAsDouble();
        double translationYPercent = translationYSupplier.getAsDouble();
        double rotationTarget = Math.atan2(rotationYSupplier.getAsDouble(), rotationXSupplier.getAsDouble());
        if (this.lowPower.getAsBoolean()) {drivePower = 0.2;} 
        if (robotCentric.getAsBoolean()) {
            drivetrain.drive(
                new ChassisSpeeds(
                        translationXPercent * drivePower * Constants.MAX_VELOCITY_METERS_PER_SECOND,
                        translationYPercent * drivePower * Constants.MAX_VELOCITY_METERS_PER_SECOND,
                        rotationPID.calculate(rotationTarget)
                    )
            );
        } else {
            drivetrain.drive(
                    ChassisSpeeds.fromFieldRelativeSpeeds(
                            translationXPercent * drivePower * Constants.MAX_VELOCITY_METERS_PER_SECOND,
                            translationYPercent * drivePower * Constants.MAX_VELOCITY_METERS_PER_SECOND,
                            rotationPID.calculate(rotationTarget),
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