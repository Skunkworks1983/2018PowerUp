package frc.team1983;

import edu.wpi.first.wpilibj.Joystick;
import frc.team1983.settings.RobotMap;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public Joystick leftStick = new Joystick(RobotMap.L_JOY_PORT);
    public Joystick rightStick = new Joystick(RobotMap.R_JOY_PORT);

    public boolean getLeftTriggerPressed (){

        return leftStick.getButton(Joystick.kTriggerButton);
    }
    public boolean getRightTriggerPressed (){

        return rightStick.getButton(Joystick.kTriggerButton);
    }
    public boolean button2Pushed (){
        return leftStick.getRawButton(2);
    }
    public float getLeftStickY (){
        return leftStick.getY();
    }
}