package frc.robot;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Options implements Sendable {
    public Boolean reversed = false;
    public int CANID = 0;
    public Options() {
        SmartDashboard.putData("Options", this);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("PysID Options");
        builder.addBooleanProperty("Reversed", () -> reversed, (x) -> {reversed = x;});
        builder.addDoubleProperty("CANID", () -> CANID, (x) -> {CANID = (int)x;});
    }
}
