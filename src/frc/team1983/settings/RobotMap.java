package frc.team1983.settings;

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


    //DriveStraight constants
    public static double DRIVEBASE_TICK_PER_FOOT = 0; //TODO: find this ratio

    //Which drivebase motors to reverse
    public static boolean LEFT_MASTER_REVERSE = false;
    public static boolean LEFT_SLAVE_0_REVERSE = false;
    public static boolean LEFT_SLAVE_1_REVERSE = false;
    public static boolean RIGHT_MASTER_REVERSE = false;
    public static boolean RIGHT_SLAVE_0_REVERSE = false;
    public static boolean RIGHT_SLAVE_1_REVERSE = false;


    //OI ports
    public static int L_JOY_PORT = 0; //TODO: find actual port
    public static int R_JOY_PORT = 1; //TODO: find actual port
}
