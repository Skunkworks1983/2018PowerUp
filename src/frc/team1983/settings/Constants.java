package frc.team1983.settings;


import java.io.File;

//This file contains all constants used across the entire robot, stored within subclasses.
public class Constants
{
    //MotorMap contains all constants that motors require (ports, reversed values, etc)
    public static class MotorMap
    {
        //Ports for the drivebase motors
        public static class Drivebase
        {

            public static final int LEFT_1 = 1;
            public static final int LEFT_2 = 0;
            public static final int LEFT_3 = 2;

            public static final int RIGHT_1 = 14;
            public static final int RIGHT_2 = 13;
            public static final int RIGHT_3 = 15;

            public static final boolean LEFT1_REVERSED = true;
            public static final boolean LEFT2_REVERSED = true;
            public static final boolean LEFT3_REVERSED = true;

            public static final boolean RIGHT1_REVERSED = false;
            public static final boolean RIGHT2_REVERSED = false;
            public static final boolean RIGHT3_REVERSED = false;
        }

        public static class Elevator
        {
            public static final int RIGHT1 = 2;
            public static final int RIGHT2 = 3;

            public static final int LEFT1 = 12;
            public static final int LEFT2 = 13;

            public static final boolean RIGHT1_REVERSED = false;
            public static final boolean RIGHT2_REVERSED = false;

            public static final boolean LEFT1_REVERSED = true;
            public static final boolean LEFT2_REVERSED = true;
        }

        public static class Collector
        {
            public static final int LEFT = 10;
            public static final int RIGHT = 9;
            public static final int ROTATE = 5;

            public static final boolean LEFT_REVERSED = true;
            public static final boolean RIGHT_REVERSED = false;
            public static final boolean ROTATE_REVERSED = false;

            public static final int LEFT_SWITCH = 1;
            public static final int RIGHT_SWITCH = 0;

            public static final boolean LEFT_SWITCH_REVERSED = true;
            public static final boolean RIGHT_SWITCH_REVERSED = true;
        }

        public static class Ramps
        {
            public static final int LEFT_RAMPLOCK = 0;
            public static final int RIGHT_RAMPLOCK = 2;

            public static final int LEFT_LEG = 1;
            public static final int RIGHT_LEG = 3;
        }

        //Random and hard to classify drivebase constants
        public static class DrivebaseConstants
        {
            public static final double DRIVEBASE_TICKS_PER_FOOT = 3 / 2.;
            public static final int RIGHT1 = 0;
            public static final int RIGHT2 = 0;

            public static final boolean LEFT1_REVERSED = true;
            public static final boolean LEFT2_REVERSED = false;
            public static final boolean RIGHT1_REVERSED = true;
            public static final boolean RIGHT2_REVERSED = false;
        }
    }

    public static class MotorSetpoints
    {
        //The speed at which to run the collector when intaking or expelling. I'm assuming we want it at full.
        public static final double COLLECTOR_INTAKE_SPEED = 0.75;
        public static final double COLLECTOR_EXPEL_SPEED = -0.5;
        public static final double COLLECTOR_ROTATE_SPEED = -0.5;

        //The number of command cycles (runs at 50 Hertz) after a limit switch
        //is activated that it will always return true (for debouncing)
        public static final int COLLECTOR_SWITCH_DEBOUNCE_TIME = 10;
    }

    public static class DashboardConstants
    {
        public static final File FILE = new File("/home/lvuser/DashboardValues.txt");
        public static final Double DEFAULT_DOUBLE = 0.0;
        public static final Boolean DEFAULT_BOOLEAN = false;
        public static final String DEFAULT_STRING = "You're a dum-dum for not initializing this correctly";
        public static final String KEY_SEPARATOR = "-";
        public static final String VALUE_SEPARATOR = ":";
        public static final String ELEVATOR_CONTROL_NAME = "elevator";
    }

    //this class contains subclasses that contain PIDF values used in commands.
    public static class PidConstants
    {
        //PIDF values for the DriveStraight command
        public static class DriveStrightPid
        {
            public static final double P = 0; //TODO: tune pid
            public static final double I = 0;
            public static final double D = 0;
            public static final double F = 0;

            public static final double DEFAULT_BASE_SPEED = 0.5;
        }

        //PIDF values for the TurnAngle command
        public static class TurnAnglePid
        {
            public static final double P = 0.005; //TODO: tune pid
            public static final double I = 0;
            public static final double D = 0;
            public static final double F = 0;

            public static final double ABSOLUTE_TOLERANCE = 5;
        }

        public static class CollectorRotate
        {
            public static final double P = 1.8;//.01; //TODO: find pid values
            public static final double I = 0;
            public static final double D = 0;
            public static final double F = 0;//.002;

            public static final double UP_TICKS = -100;
            public static final double DOWN_TICKS = 3450;
        }

        //setpoints for motors
        //TODO: Refactor out of this scope
        public static class MotorSetpoints
        {
            //the position to which the ramp servos rotate.
            public static final double RAMP_DROP_SERVO_GOAL = 1; //TODO: find actual goal
            public static final double RAMP_PROP_SERVO_GOAL = 1;
        }

        public static class ElevatorControlPid
        {
            public static final int ELEVATOR_BOTTOM = 0;
            //Actually negative, but ya know,
            public static final int ELEVATOR_TOP = 29000 - 485; //Addition is to keep it from hitting the max position

            public static final double P = 0.13;
            public static final double I = 0;
            public static final double D = 3;
            public static final double F = 0.001;
        }
    }

    public static class AutoValues
    {
        public static final double WHEELBASE_RADIUS = 25.625/2/12; //T fix magic number
        public static final double WHEELBASE_CIRCUMFERENCE = 2 * Math.PI * WHEELBASE_RADIUS;
        public static final double WHEELBASE_DEGREES = WHEELBASE_CIRCUMFERENCE / 360.;
        public static final double EFFECTIVE_REDUCTION_DRIVEBASE = 18 / 24.;
        public static final double WHEEL_CIRCUMFERENCE = 6/12. * Math.PI;
    }

    //this contains all values relevant to the OI.
    public static class OIMap
    {
        public static final int LEFTJOY_PORT = 0;
        public static final int RIGHTJOY_PORT = 1;
        public static final int PANEL_PORT = 2;
        public static final int MANUAL_PORT = 3;

        public enum Joystick
        {
            LEFT, RIGHT, PANEL, MANUAL
        }

        public class Axis
        {
            public static final int X = 0;
            public static final int Y = 1;
            public static final int Z = 2;
        }

        //Agglomeration of constants relating to the joysticks
        public static class OIConstants
        {
            //Joystick constants
            public static final double JOYSTICK_DEADZONE = 0.15;
            public static final double JOYSTICK_RAMP_EXPONENT = 2;
            public static final double JOYSTICK_TOLERANCE = 0.5;

            //Scalar coefficient of the slider on the OI
            public static final double SLIDER_SCALAR = 0.618726;
        }

        public static class SliderConstants
        {
            public static final int SLIDER_PRESETS_TOGGLE = 0;
            public static final int BOTTOM_PRESET = 1;
            public static final int SWITCH_PRESET = 2;
            public static final int SCALE_PRESET = 3;
        }

        public static class CollectorButtons
        {
            public static final int INTAKE = 3;
            public static final int EXPEL = 4;
            public static final int MANUAL_ROTATE_UP = 15;
            public static final int MANUAL_ROTATE_DOWN = 14;
        }

        public static class ElevatorButtons
        {
            public static final int TOP = 0;
            public static final int MID = 1;
            public static final int MANUAL_UP = 2;
            public static final int MANUAL_DOWN = 3;
        }

        public static class RampButtons
        {
            public static final int DROP = 10;
            public static final int PROP = 6;
        }

        public static class ManualControl
        {
            public static final int ELEVATOR_UP = 5; //button 6 on 2016 oi
            public static final int ELEVATOR_DOWN = 6; //button 7 on 2016 oi
            public static final int MANUAL_SWITCH = 8; //button 9 on the 2016 oi
        }
        //Enums for presets
        public enum Setpoint
        {
            SCALE,
            SWITCH,
            BOTTOM,
            RESTING
        }
    }


    public static class SensorMap
    {
        public static class GyroConstants
        {
            public static final int IS_CONNECTED_TIMEOUT = 500;
            public static final int IS_CALIBRATING_TIMEOUT = 500;
        }
    }

    public static class Motion
    {
        public static final double DEFAULT_MOTIONPROFILE_ACCEL_TIME = 0.5; // [0-1]

    }
}
