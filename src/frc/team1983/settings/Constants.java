package frc.team1983.settings;


import java.util.ArrayList;

//This file contains all constants used across the entire robot, stored within subclasses.
public class Constants
{
    //MotorMap contains all constants that motors require (ports, reversed values, etc)
    public static class MotorMap
    {

        //Ports for the drivebase motors
        public static class DrivebasePorts
        {
            public static final int LEFT_MASTER_PORT = 0; //TODO: Get actual ports
            public static final int LEFT_SLAVE_0_PORT = 1;
            public static final int LEFT_SLAVE_1_PORT = 2;
            public static final int RIGHT_MASTER_PORT = 3;
            public static final int RIGHT_SLAVE_0_PORT = 4;
            public static final int RIGHT_SLAVE_1_PORT = 5;
        }

        //Constants determining if the drivebase motors should run in the negative direction
        public static class DrivebaseReversed
        {
            public static final boolean LEFT_MASTER_REVERSE = false; //TODO: Verify reverse modes
            public static final boolean LEFT_SLAVE_0_REVERSE = false;
            public static final boolean LEFT_SLAVE_1_REVERSE = false;
            public static final boolean RIGHT_MASTER_REVERSE = false;
            public static final boolean RIGHT_SLAVE_0_REVERSE = false;
            public static final boolean RIGHT_SLAVE_1_REVERSE = false;
        }

        //Ports for the ramp servos
        public static class RampPorts
        {
            public static final int RAMP_LEFT_DROP_PORT = 6; //TODO: find actual port
            public static final int RAMP_LEFT_PROP_PORT = 7;
            public static final int RAMP_RIGHT_DROP_PORT = 8;
            public static final int RAMP_RIGHT_PROP_PORT = 9;
        }

        //motor goals ie positions to rotate to, speeds to achieve
        public static class MotorGoals {
            public static final double RAMP_DROP_SERVO_GOAL = 1; //TODO: find actual goal
            public static final double RAMP_PROP_SERVO_GOAL = 1;

            //The speed at which to run the collector when intaking or expelling. I'm assuming we want it at full.
            public static final double COLLECTOR_INTAKE_SPEED = 1;
            public static final double COLLECTOR_EXPEL_SPEED = 1;
        }

        //Ports for the collector motors
        public static class CollectorPorts
        {
            public static final int LEFT_COLLECTOR_PORT = 6; //TODO: Get actual ports
            public static final int RIGHT_COLLECTOR_PORT = 7;
        }

        //Constants determining if the collector motors should run in the negative direction
        public static class CollectorReversed
        {
            public static final boolean LEFT_COLLECTOR_REVERSE = false;
            public static final boolean RIGHT_COLLECTOR_REVERSE = true;
        }

        //Random and hard to classify drivebase constants
        public static class DrivebaseConstants
        {
            public static final double DRIVEBASE_TICKS_PER_FOOT = 0;
        }
    }

    //this class contains subclasses that contain PIDF values used in commands.
    public static class PidNames
    {
        //This *has* to be 0.0, otherwise pids
        //with no f value will acquire this as one
        public static final double PLACEHOLDER_VALUE = 0.0;

        //PIDF values for the DriveStraight command
        public static class DriveStrightPid
        {
            public static final double P = 0; //TODO: find pid values
            public static final double I = 0;
            public static final double D = 0;
            public static final double F = 0;
        }

        //PIDF values for the TurnAngle command
        public static class TurnAnglePid
        {
            public static final double P = 0; //TODO: find pid values
            public static final double I = 0;
            public static final double D = 0;
            public static final double F = 0;
        }
    }

    //what goes here? God only knows
    public static class AutoValues {

    }

    //this contains all values relevant to the OI.
    public static class OIMap
    {

        //Ports for the OI joysticks, buttons, steering wheel, etc.
        public static class Ports
        {
            public static final int LEFT_JOY = 0;
            public static final int RIGHT_JOY = 1;
            public static final int WHEEL = 0;
            public static final int THROTTLE = 1;
            public static final int BUTTONS = 2;
        }

        //Axes of each joystick
        public static class JoyAxes
        {
            public static final int X = 0;
            public static final int Y = 1;
        }

        //Control mode for the robot (Joysticks versus steering wheel)
        public static class Mode
        {
            public static final int DOUBLE_JOY = 0;
            public static final int WHEEL = 1;
        }

        //Agglomeration of constants relating to the joysticks
        public static class JoyConstants
        {
            public static final double JOYSTICK_DEADZONE = 0.15;
            public static final double JOYSTICK_RAMP_EXPONENT = 2;
            public static final double JOYSTICK_TOLERANCE = 0.5;
        }
    }

}