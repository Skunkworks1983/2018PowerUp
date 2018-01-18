package frc.team1983.settings;


//This file contains all constants used across the entire robot, stored within subclasses.
public class Constants
{
    public static double JOYSTICK_DEADZONE = 0.15;
    public static double JOYSTICK_RAMP_EXPONENT = 2;

    // not really constants. not sure if should really be upper or not
    public static class OIInputType
    {
        public static int DOUBLEJOY = 0;
        public static int WHEEL = 1;
    }

    public static class OIJoystickAxis
    {

    }
    //MotorMap contains all motor and servo ports, goals, reverse settings,
    public static class MotorMap
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
        public static int RAMP_LEFT_DROP_PORT = 6; //TODO: find actual port
        public static int RAMP_LEFT_PROP_PORT = 7;
        public static int RAMP_RIGHT_DROP_PORT = 8;
        public static int RAMP_RIGHT_PROP_PORT = 9;

        public static double RAMP_DROP_SERVO_GOAL = 1; //TODO: find actual goal
        public static double RAMP_PROP_SERVO_GOAL = 1;

        //Collector motor ports TODO: Get actual ports
        public static final int LEFT_COLLECTOR_PORT = 6;
        public static final int RIGHT_COLLECTOR_PORT = 7;

        //Collector motor reverse TODO: Verify reverse modes
        public static final boolean LEFT_COLLECTOR_REVERSE = false;
        public static final boolean RIGHT_COLLECTOR_REVERSE = true;

        //The speed at which to run the collector when intaking or expelling. I'm assuming we want it at full.
        public static final double COLLECTOR_INTAKE_SPEED = 1;
        public static final double COLLECTOR_EXPEL_SPEED = 1;
    }

    public static class PidValues
    {

    }

    public static class AutoValues {

    }
    //this contains all values relevant to the OI.
    public static class OIMap
    {

        public static int LEFTJOY = 0;
        public static int RIGHTJOY = 1;
        public static int WHEEL = 0;
        public static int THROTTLE = 1;
        public static int BUTTONS = 2;
        public static int JOYAXISX = 0;
        public static int JOYAXISY = 1;


        public static double JOYSTICK_TOLERANCE = 0.5;

    }

}
