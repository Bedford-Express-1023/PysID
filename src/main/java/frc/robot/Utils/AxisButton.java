package frc.robot.Utils;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * Makes a joystick trigger like a button using a threshold.
 *
 * @author Nicholas Contreras
 * @author Nathan Sariowan
 */
public class AxisButton extends Button {

    private double m_threshold;
    private GenericHID m_joystick;
    private int m_port;

    /**
     * 
     * @param joystick  The GenericHID object that has the button (e.g. XboxJoystick, KinectStick,
   *                     etc)
     * @param port      The port number of the joystick for the button.
     * @param threshold The minimum value the joystick must return to trigger a button. If negative, the button will
     *                  trigger when the joystick is below the threshold; otherwise, it will trigger when the joystick
     *                  is above the threshold.
     */
    public AxisButton(XboxController xboxController, int port, double threshold) {
        super();
        this.m_threshold = threshold;
        this.m_joystick = xboxController;
        this.m_port = port;
    }

    public AxisButton(CommandXboxController commandXboxController, int port, double threshold) {
    }

    /**
     * Returns true if the joystick value is greater than the given threshold.
     * If the m_threshold is negative, returns true if joystick value is less than the given threshold.
     */
    @Override
    public boolean get() {
        double joystickVal = this.m_joystick.getRawAxis(this.m_port);

        if (m_threshold < 0) {
            return joystickVal <= m_threshold;
        }
        return joystickVal >= m_threshold;
    }
}
