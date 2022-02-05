package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.DoubleSupplier;

public class AllForwardCommand extends CommandBase {
    private final SwerveDriveSubsystem drivetrain;
    private final double translationXSupplier;
    private final double translationYSupplier;
    private final double rotationSupplier;

    public AllForwardCommand(
            SwerveDriveSubsystem drivetrain,
            double d,
            double e,
            double f
    ) {
        this.drivetrain = drivetrain;
        this.translationXSupplier = d;
        this.translationYSupplier = e;
        this.rotationSupplier = f;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        double translationXPercent;
        double translationYPercent;
        double rotationPercent;

        drivetrain.drive(new ChassisSpeeds(translationXSupplier, translationYSupplier, rotationSupplier));
    }

    @Override
    public void end(boolean interrupted) {
        // Stop the drivetrain
        drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
    }
}