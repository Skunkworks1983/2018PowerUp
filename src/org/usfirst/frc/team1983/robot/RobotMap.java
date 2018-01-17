package org.usfirst.frc.team1983.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{

    //Ports of the drivebase motors
    public static int LEFT_MASTER_PORT = 0;
    public static int LEFT_SLAVE_0_PORT = 1;
    public static int LEFT_SLAVE_1_PORT = 2;
    public static int RIGHT_MASTER_PORT = 3;
    public static int RIGHT_SLAVE_0_PORT = 4;
    public static int RIGHT_SLAVE_1_PORT = 5;

    //Ports of the ramp motors
    public static int RAMP_LEFT_DROP_PORT = 6; //TODO: find real ports
    public static int RAMP_LEFT_PROP_PORT = 7; //TODO: find real ports
    public static int RAMP_RIGHT_DROP_PORT = 8; //TODO: find real ports
    public static int RAMP_RIGHT_PROP_PORT = 9; //TODO: find real ports
    public static int RAMP_DROP_SERVO_GOAL = 1; //TODO: find actual goal
    public static int RAMP_PROP_SERVO_GOAL = 1;

    public static int RAMP_RIGHT_PORT = 7;

    //Which drivebase motors to reverse
    public static boolean LEFT_MASTER_REVERSE = false;
    public static boolean LEFT_SLAVE_0_REVERSE = false;
    public static boolean LEFT_SLAVE_1_REVERSE = false;
    public static boolean RIGHT_MASTER_REVERSE = false;
    public static boolean RIGHT_SLAVE_0_REVERSE = false;
    public static boolean RIGHT_SLAVE_1_REVERSE = false;

    public static int L_JOY_PORT = 0; //TODO: find actual port
    public static int R_JOY_PORT = 1; //TODO: find actual port
}
