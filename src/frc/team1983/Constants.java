package frc.team1983;

public class Constants
{
    public static class MotorMap
    {
        public static class Drivebase
        {
            public static final int LEFT_1 = 14;
            public static final boolean LEFT1_REVERSED = true;
            public static final int LEFT_2 = 13;
            public static final boolean LEFT2_REVERSED = true;
            public static final int LEFT_3 = 15;
            public static final boolean LEFT3_REVERSED = true;

            public static final int RIGHT_1 = 1;
            public static final boolean RIGHT1_REVERSED = false;
            public static final int RIGHT_2 = 0;
            public static final boolean RIGHT2_REVERSED = false;
            public static final int RIGHT_3 = 2;
            public static final boolean RIGHT3_REVERSED = false;
        }

        public static class Elevator
        {
            public static final int LEFT1 = 9;
            public static final boolean LEFT1_REVERSED = true;
            public static final int LEFT2 = 4;
            public static final boolean LEFT2_REVERSED = true;

            public static final int RIGHT1 = 5;
            public static final boolean RIGHT1_REVERSED = false;
            public static final int RIGHT2 = 6;
            public static final boolean RIGHT2_REVERSED = false;
        }

        public static class Collector
        {
            public static final int INTAKE_LEFT = 10;
            public static final boolean INTAKE_LEFT_REVERSED = true;
            public static final int INTAKE_RIGHT = 11;
            public static final boolean INTAKE_RIGHT_REVERSED = false;

            public static final int WRIST_LEFT = 3;
            public static final boolean WRIST_LEFT_REVERSED = true;
            public static final int WRIST_RIGHT = 12;
            public static final boolean WRIST_RIGHT_REVERSED = false;
        }
    }

    public static final double COLLECTOR_INTAKE_THROTTLE = 1;
    public static final double COLLECTOR_EXPEL_THROTTLE = 0.75;
    public static final double COLLECTOR_EXPEL_SLOW_THROTTLE = 0.5;

    public static final double ELEVATOR_ALLOWABLE_ERROR = 10;

    public static class Gains
    {
        public static class Wrist
        {
            public static final double P = 1.5;
            public static final double I = 0;
            public static final double D = 2;
            public static final double F = 0;
        }

        public static class Elevator
        {
            public static final double P = 0.17;
            public static final double I = 0.002;
            public static final double D = 23;
            public static final double F = 0;

            public static final int I_ZONE = 1000;
        }
    }

    public static class OIMap
    {
        public static final int LEFT_JOYSTICK = 0;
        public static final int RIGHT_JOYSTICK = 1;
        public static final int PANEL = 2;

        public static final double JOYSTICK_DEADZONE = 0.15;
        public static final double JOYSTICK_EXPONENT = 2;

        public static final int INTAKE = 0;
        public static final int EXPEL = 0;
        public static final int EXPEL_SLOW = 0;
    }
}
