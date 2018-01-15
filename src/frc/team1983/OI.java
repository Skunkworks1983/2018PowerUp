package frc.team1983;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team1983.settings.Misc;
import frc.team1983.settings.RobotMap;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public Joystick leftStick = new Joystick(RobotMap.OI_L_JOY_PORT);
    public Joystick rightStick = new Joystick(RobotMap.OI_R_JOY_PORT);
    public Joystick buttons = new Joystick(RobotMap.OI_BUTTONS);

    //Probably returns a value between 0 and 1
    public double getSliderPos()
    {
        //The 2017 slider on the OI was a joystick axis. All code taken from 2017
        double x = buttons.getX();
        x = Math.pow(x, 10);
        x = x / Misc.SLIDER_SCALAR;
        x = 1 - x;
        return x;
    }
}
