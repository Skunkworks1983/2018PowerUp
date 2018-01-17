package frc.team1983.settings;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
    //Ports of the drivebase motors TODO: Get actual ports
    public static int LEFT_MASTER_PORT = 0;
    public static int LEFT_SLAVE_0_PORT = 1;
    public static int LEFT_SLAVE_1_PORT = 2;
    public static int RIGHT_MASTER_PORT = 3;
    public static int RIGHT_SLAVE_0_PORT = 4;
    public static int RIGHT_SLAVE_1_PORT = 5;

    //Which drivebase motors to reverse TODO: Verify reverse modes
    public static boolean LEFT_MASTER_REVERSE = false;
    public static boolean LEFT_SLAVE_0_REVERSE = false;
    public static boolean LEFT_SLAVE_1_REVERSE = false;
    public static boolean RIGHT_MASTER_REVERSE = false;
    public static boolean RIGHT_SLAVE_0_REVERSE = false;
    public static boolean RIGHT_SLAVE_1_REVERSE = false;

    public static int L_JOY_PORT = 0; //TODO: find actual port
    public static int R_JOY_PORT = 1;

    //Ramp servo ports
    public static int RAMP_LEFT_DROP_PORT = 6;
    public static int RAMP_LEFT_PROP_PORT = 7;
    public static int RAMP_RIGHT_DROP_PORT = 8;
    public static int RAMP_RIGHT_PROP_PORT = 9;

    //Collector motor ports TODO: Get actual ports
    public static final int LEFT_COLLECTOR_PORT = 6;
    public static final int RIGHT_COLLECTOR_PORT = 7;

    //Collector motor reverse TODO: Verify reverse modes
    public static final boolean LEFT_COLLECTOR_REVERSE = false;
    public static final boolean RIGHT_COLLECTOR_REVERSE = true;
}
