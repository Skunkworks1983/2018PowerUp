package frc.team1983.settings;

public class Constants
{
    public static double JOYSTICK_DEADZONE = 0.15;
    public static double JOYSTICK_RAMP_EXPONENT = 2;

    // not really constants. not sure if should really be upper or not
    public static class OIInputType {
        public static int DOUBLEJOY = 0;
        public static int WHEEL = 1;
    }

    public static class OIJoystick {
        public static int LEFT = 0;
        public static int RIGHT = 1;
        public static int WHEEL = 0;
        public static int THROTTLE = 1;
        public static int BUTTONS = 2;
    }

    public static class OIJoystickAxis {
        public static int X = 0;
        public static int Y = 1;
        public static int Z = 2;
    }
}
