package frc.team1983.settings;

public class Constants
{
    public static double JOYSTICK_DEADZONE = 0.15;

    public static class OIInputType {
        public static int DoubleJoy = 0;
        public static int Wheel = 1;
    }

    public static class OIJoystick {
        public static final int Left = 0;
        public static final int Right = 1;
        public static final int Wheel = 0;
        public static final int Throttle = 1;
        public static final int Buttons = 2;
    }

    // uhhh TODO: confirm
    public static class OIJoystickAxis {
        public static final int X = 0;
        public static final int Y = 1;
        public static final int Z = 2;
    }
}
