package frc.robot;

import com.ctre.phoenix.platform.DeviceType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Options implements Sendable {
    public Boolean reversed = false;
    public int CANID = 0;
    public Options() {
        SmartDashboard.putData(this);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addBooleanProperty("Reversed", () -> reversed, (x) -> {reversed = x;});
        builder.addDoubleProperty("CANID", () -> CANID, (x) -> {CANID = (int)x;});
    }
}
