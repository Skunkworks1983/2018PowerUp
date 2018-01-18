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
    public static final int LEFT_MASTER_PORT = 0;
    public static final int LEFT_SLAVE_0_PORT = 1;
    public static final int LEFT_SLAVE_1_PORT = 2;
    public static final int RIGHT_MASTER_PORT = 3;
    public static final int RIGHT_SLAVE_0_PORT = 4;
    public static final int RIGHT_SLAVE_1_PORT = 5;

    //DriveStraight constants
    public static double DRIVEBASE_TICK_PER_FOOT = 0; //TODO: find this ratio

    //Drivebase reversal TODO: Verify reverse modes
    public static final boolean LEFT_MASTER_REVERSE = false;
    public static final boolean LEFT_SLAVE_0_REVERSE = false;
    public static final boolean LEFT_SLAVE_1_REVERSE = false;
    public static final boolean RIGHT_MASTER_REVERSE = false;
    public static final boolean RIGHT_SLAVE_0_REVERSE = false;
    public static final boolean RIGHT_SLAVE_1_REVERSE = false;

    //Elevator Winch Ports TODO: Get actual ports
    public static final int LEFT_WINCH_PORT = 6;
    public static final int RIGHT_WINCH_PORT = 7;

    //Elevator Winch reversal TODO: Get actual ports
    public static final boolean LEFT_WINCH_REVERSE = false;
    public static final boolean RIGHT_WINCH_REVERSE = true;

    //Rail encoder ports TODO: Get actual ports
    public static final int RAIL_ENCODER_A_PORT = 0; //diO port on the RIO for channel A
    public static final int RAIL_ENCODER_B_PORT = 0; //diO port on the RIO for channel B

    //Rail encoder reversals
    public static final boolean RAIL_ENCODER_REVERSE = false;

    //Ramp servo ports TODO: Get actual ports
    public static int RAMP_LEFT_DROP_PORT = 6;
    public static int RAMP_LEFT_PROP_PORT = 7;
    public static int RAMP_RIGHT_DROP_PORT = 8;
    public static int RAMP_RIGHT_PROP_PORT = 9;

    //OI Input Ports TODO: Get actual ports
    public static final int OI_L_JOY_PORT = 0;
    public static final int OI_R_JOY_PORT = 1;
    public static final int OI_BUTTONS = 3; //The joystick emulated by the psock in the OI

    //Collector motor ports TODO: Get actual ports
    public static final int LEFT_COLLECTOR_PORT = 6;
    public static final int RIGHT_COLLECTOR_PORT = 7;

    //collector motor reverse TODO: Verify reverse modes
    public static final boolean LEFT_COLLECTOR_REVERSE = false;
    public static final boolean RIGHT_COLLECTOR_REVERSE = true;
}
