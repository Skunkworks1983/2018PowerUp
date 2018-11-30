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
            public static final boolean WRIST_LEFT_REVERSED = false;
            public static final int WRIST_RIGHT = 12;
            public static final boolean WRIST_RIGHT_REVERSED = false;
        }
    }

    public static final double COLLECTOR_INTAKE_THROTTLE = -1;
    public static final double COLLECTOR_EXPEL_THROTTLE = 0.75;
    public static final double COLLECTOR_EXPEL_SLOW_THROTTLE = 0.5;

    public static final double ELEVATOR_ALLOWABLE_ERROR = 10;
    public static final double WRIST_ALLOWABLE_ERROR = 15;

    public static final double ELEVATOR_INCHES_PER_TICK = 76.5 / 29500.0;
    public static final double WRIST_DEGREES_PER_TICK = 90.0 / 1050.0;
    public static final double DRIVEBASE_INCHES_PER_TICK = (6 * Math.PI) / 1360.0;

    public static class Gains
    {
        public static class Drivebase
        {
            public static class Left
            {
                public static final double P = 0;
                public static final double I = 0;
                public static final double D = 0;
                public static final double F = 0;
                public static final double V = 0;
                public static final double A = 0;

                public static final int I_ZONE = 0;
            }

            public static class Right
            {
                public static final double P = 0;
                public static final double I = 0;
                public static final double D = 0;
                public static final double F = 0;
                public static final double V = 0;
                public static final double A = 0;

                public static final int I_ZONE = 0;
            }
        }

        public static class Wrist
        {
            public static final double P = 1.5;
            public static final double I = 0;
            public static final double D = 1.5;
            public static final double F = 0;

            public static final int I_ZONE = 0;
        }

        public static class Elevator
        {
            public static final double P = 0.2;
            public static final double I = 0;
            public static final double D = 2;
            public static final double F = 0.1;

            public static final int I_ZONE = 0;
        }
    }

    public static class Setpoints
    {
        public static class Wrist
        {
            public static final double DOWN = 90;
            public static final double MIDDLE = 45;
            public static final double UP = 0;
            public static final double BACK = -45;
        }

        public static class Elevator
        {
            public static final double BOTTOM = 0;
            public static final double TRAVEL = 6;
            public static final double POSITION_2 = 12;
            public static final double POSITION_3 = 35;
            public static final double POSITION_4 = 60;
            public static final double TOP = 70;
        }
    }

    public static class OIMap
    {
        public static final int LEFT_JOYSTICK = 0;
        public static final int RIGHT_JOYSTICK = 1;
        public static final int PANEL = 2;

        public static final double JOYSTICK_DEADZONE = 0.1;
        public static final double JOYSTICK_EXPONENT = 1.4;

        public static final int INTAKE = 18;
        public static final int EXPEL = 20;
        public static final int EXPEL_SLOW = 14;

        public static final int COLLECTOR_DOWN = 17;
        public static final int COLLECTOR_UP = 19;

        public static final int ELEVATOR_BOTTOM = 13;
        public static final int ELEVATOR_2 = 12;
        public static final int ELEVATOR_3 = 11;
        public static final int ELEVATOR_4 = 10;
        public static final int ELEVATOR_TOP = 9;

        public static final int MANUAL_TOGGLE = 4;

        public static final int MANUAL_ELEVATOR_UP = 7;
        public static final int MANUAL_ELEVATOR_DOWN = 6;

        public static final int MANUAL_COLLECTOR_UP = 3;
        public static final int MANUAL_COLLECTOR_DOWN = 4;

        public static final int MANUAL_INTAKE = 2;
        public static final int MANUAL_EXPEL = 1;
    }
}
