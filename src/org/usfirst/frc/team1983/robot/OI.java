package org.usfirst.frc.team1983.robot;

import edu.wpi.first.wpilibj.Joystick;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public Joystick leftStick = new Joystick(RobotMap.L_JOY_PORT);
    public Joystick rightStick = new Joystick(RobotMap.R_JOY_PORT);
}
