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

            public static final int ROTATE_LEFT = 3;
            public static final boolean ROTATE_LEFT_REVERSED = true;
            public static final int ROTATE_RIGHT = 12;
            public static final boolean ROTATE_RIGHT_REVERSED = false;
        }
    }

    public static class MotorSetpoints
    {
        public static final double COLLECTOR_INTAKE_THROTTLE = 1;
        public static final double COLLECTOR_EXPEL_THROTTLE = 0.75;
    }

    //this class contains subclasses that contain PIDF values used in commands.
    public static class PidConstants
    {
        public static class CollectorRotate
        {
            public static final double P = 1.5;//.01;
            public static final double I = 0;
            public static final double D = 2;
            public static final double F = 0;//.002;
        }

        public static class ElevatorControlPid
        {
            public static class Slot0
            {
                public static final double P = 0.17;
                public static final double I = 0.002;
                public static final double D = 23;
                public static final double F = 0;
                public static final int I_ZONE = 1000;

                public static final int ALLOWABLE_CLOSED_LOOP_ERROR = 50;
            }
            public static class Slot1
            {
                public static final double P = 0.08;
                public static final double I = 0.00005;
                public static final double D = 23;
                public static final double F = 0;
                public static final int I_ZONE = 800;

                public static final int ALLOWABLE_CLOSED_LOOP_ERROR = 50;
            }

        }
    }

    public static class OIMap
    {
        public static final int MANUAL_SWITCH = 4;

        public static final double JOYSTICK_DEADZONE = 0.15;
        public static final double JOYSTICK_EXPONENT = 2;

        public static final int JOY_BUTTON_COUNT = 5; //TODO TODO TODO: CHANGE
        public static final int OI_BUTTON_COUNT = 24; //TODO TODO TODO: CHANGE

        public enum Joystick
        {
            LEFT, RIGHT, PANEL
        }

        public class Axis
        {
            public static final int X = 0;
            public static final int Y = 1;
            public static final int Z = 2;
        }

        public static class CollectorButtons
        {
            public static final int INTAKE = 17;
            public static final int EXPEL = 19;
            public static final int BOOP = 13;
            public static final int M_INTAKE = 1;
            public static final int M_EXPEL = 0;
            public static final int UP = 18;
            public static final int DOWN = 16;
            public static final int M_UP = 2;
            public static final int M_DOWN = 3;
        }

        public static class ElevatorButtons
        {
            public static final int TOP = 8;
            public static final int MID = 9;
            public static final int LOW = 10;
            public static final int SWITCH = 11;
            public static final int BOTTOM = 12;
            public static final int M_UP = 6;
            public static final int M_DOWN = 5;
        }
    }
}
