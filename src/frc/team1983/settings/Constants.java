package frc.team1983.settings;


import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.control.PIDFGains;
import frc.team1983.util.control.PIDFSVAGains;

import java.io.File;

//This file contains all constants used across the entire robot, stored within subclasses.
public class Constants
{
    //MotorMap contains all constants that motors require (ports, reversed values, etc)
    public static class MotorMap
    {
        public enum Mode
        {
            DISABLED, AUTO, TELEOP, TEST
        }

        //Ports for the drivebase motors
        public static class Drivebase
        {
            public static final int LEFT_1 = 14;
            public static final int LEFT_2 = 13;
            public static final int LEFT_3 = 15;

            public static final int RIGHT_1 = 1;
            public static final int RIGHT_2 = 0;
            public static final int RIGHT_3 = 2;

            public static final boolean LEFT1_REVERSED = true;
            public static final boolean LEFT2_REVERSED = true;
            public static final boolean LEFT3_REVERSED = true;

            public static final boolean RIGHT1_REVERSED = false;
            public static final boolean RIGHT2_REVERSED = false;
            public static final boolean RIGHT3_REVERSED = false;

            public static final double WHEELBASE_WIDTH = 28.0 / 12.0;

            public static final double WHEEL_CIRCUMFERENCE = (6.0 / 12.0) * Math.PI;
            public static final double ENCODER_REDUCTION = 18.0 / 24.0;
            public static final double ENCODER_RESOLUTION = 1000;

            public static final double DRIVEBASE_TICKS_PER_FOOT = 1;
        }

        public static class Elevator
        {
            public static final int RIGHT1 = 5;
            public static final int RIGHT2 = 6;

            public static final int LEFT1 = 9;
            public static final int LEFT2 = 4;

            public static final boolean LEFT1_REVERSED = true;
            public static final boolean LEFT2_REVERSED = true;

            public static final boolean RIGHT1_REVERSED = false;
            public static final boolean RIGHT2_REVERSED = false;

            public static final double SPROCKET_CIRCUMFERENCE = (1.888 / 12.0) * Math.PI;
            public static final double TRAVEL_LENGTH = 38.0 / 12.0;
            public static final double ENCODER_RESOLUTION = 4500;
        }

        public static class Collector
        {
            public static final int LEFT = 10;
            public static final int RIGHT = 11;

            public static final int ROTATE = 12;

            public static final boolean LEFT_REVERSED = false;
            public static final boolean RIGHT_REVERSED = true;
            public static final boolean ROTATE_REVERSED = false;

            public static final int LEFT_SWITCH = 1;
            public static final int RIGHT_SWITCH = 0;

            public static final boolean LEFT_SWITCH_REVERSED = true;
            public static final boolean RIGHT_SWITCH_REVERSED = true;
        }

        public static class Climber // magic numbers
        {
            //two drop
            //two hook
            //two cam servos
            //two limit switches on cam servos

            public static final int TENSION_MOTOR = 3;
            public static final boolean TENSION_MOTOR_REVERSED = false;

            public static final int DROP_SERVO_LEFT = 0;
            public static final int DROP_SERVO_RIGHT = 7;

            public static final int HOOK_SERVO_0 = 4;
            public static final int HOOK_SERVO_1 = 6;

            public static final int CAM_SERVO_LEFT = 2;
            public static final int CAM_SERVO_RIGHT = 3;

            public static final int CAM_SWITCH_LEFT = 8;
            public static final int CAME_SWTICH_RIGHT = 5;

            //free current 1.8 amps
            //stall current 41 amps
            public static final double UPPER_TENSION_MOTOR_CURRENT = 30;
        }
    }

    public static class MotorSetpoints
    {
        //The speed at which to run the collector when intaking or expelling. I'm assuming we want it at full.
        public static final double COLLECTOR_INTAKE_SPEED = -0.75;
        public static final double COLLECTOR_EXPEL_SPEED = 0.75;
        public static final double COLLECTOR_SLOW_EXPEL_SPEED = 0.2;
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
        public static class Drivebase
        {
            public static class Left
            {
                public static final PIDFSVAGains MAIN = new PIDFSVAGains(
                        0.65,
                        0,
                        0.75,
                        0,
                        0,
                        1024 / Motion.DRIVEBASE_LEFT_MAX_VELOCITY,
                        0 / Motion.DRIVEBASE_LEFT_MAX_ACCELERATION
                );

            }

            public static class Right
            {
                public static final PIDFSVAGains MAIN = new PIDFSVAGains(
                        0.65,
                        0,
                        0.75,
                        0,
                        0,
                        1024 / Motion.DRIVEBASE_RIGHT_MAX_VELOCITY,
                        0 / Motion.DRIVEBASE_RIGHT_MAX_ACCELERATION
                );
            }

            public static PIDFSVAGains AUX_STRAIGHT = new PIDFGains(
                    0.04, 0, 0, 0
            );

            public static PIDFSVAGains AUX_ARC = new PIDFGains(
                    0.09, 0, 0.04, 0
            );
        }

        public static class Elevator
        {
            public static final PIDFSVAGains MAIN = new PIDFSVAGains(
                    0,
                    0,
                    0,
                    0,
                    Motion.ELEVATOR_MAINTAIN_FEEDFORWARD,
                    1024 / Motion.ELEVATOR_MAX_VELOCITY,
                    1024 / Motion.ELEVATOR_MAX_ACCELERATION
            );
        }

        //PIDF values for the DriveStraight command
        public static class DriveStraightPid
        {
            public static final double P = 0.11;
            public static final double I = 0;
            public static final double D = 0;
            public static final double F = 0;

            public static final double DEFAULT_BASE_SPEED = 0.5;
            public static final double DEFAULT_TIMEOUT = 3;
        }

        //PIDF values for the SimpleTurnAngle command
        public static class TurnAnglePid
        {
            public static class DifferentialAdjustmentPid
            {
                public static final double P = 0.0195; //TODO: tune pid
                public static final double I = 0;
                public static final double D = 0.08;
                public static final double F = 0;
            }
            public static class DifferentialPivotPid
            {
                public static final double P = 0.00001;
                public static final double I = 0;
                public static final double D = 0;
                public static final double F = 0;
            }
            public static class SimpleTurnPid
            {
                public static final double P = 0;
                public static final double I = 0;
                public static final double D = 0;
                public static final double F = 0;
            }

            public static final double ABSOLUTE_TOLERANCE = 3;

            public static final double DEFAULT_TIMEOUT = 2.;
        }

        public static class CollectorRotate
        {
            public static final double P = 1.8;//.01;
            public static final double I = 0;
            public static final double D = 0;
            public static final double F = 0;//.002;

            public static final double UP_TICKS = -100; //response to slop in chain
            public static final double DOWN_TICKS = 1300;
            public static final double MID_TICKS = 300;//response to slop in chain
        }
    }

    public static class AutoValues
    {
        public static final double WHEELBASE_RADIUS = 25.625 / 2 / 12; //T fix magic number
        public static final double DISTANCE_FROM_ENCODER_TO_END_OF_ROBOT = 1.4;

        public static final double MAX_OUTPUT = 0.5;

        public static final double DIFFERENTIAL_TURN_ANGLE_BASESPEED = .26;
    }

    //this contains all values relevant to the OIMap.
    public static class OIMap
    {
        public static final int MANUAL_SWITCH = 4; //button 9 on the 2016 oi

        public static final double JOYSTICK_DEADZONE = 0.15;
        public static final double JOYSTICK_RAMP_EXPONENT = 2;

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

        public static class ClimberButtons
        {
            public static final int HOOK = 21;
            public static final int DROP_FORKS = 20;
            public static final int CREATE_TENSION = 22;
            public static final int ENGAGE_DOG_GEARS = 23;
        }
    }


    public static class SensorMap
    {
        public static class GyroConstants
        {
            public static final int IS_CONNECTED_TIMEOUT = 500;
            public static final int IS_CALIBRATING_TIMEOUT = 500;
        }

        public static class Collector
        {
            public static final int LEFT_CAM_SWITCH = 4;
            public static final int RIGHT_CAM_SWITCH = 5;
            public static final boolean LEFT_CAM_REVERSED = false;
            public static final boolean RIGHT_CAM_REVERSED = false;
        }
    }

    public static class Motion
    {
        public static final double DEFAULT_MOTIONPROFILE_ACCEL_TIME = 0.5; // [0-1]
        public static final int MIN_POINTS_IN_TALON = 3;

        public static final double DRIVEBASE_LEFT_MAX_VELOCITY = 14000.0; // sensor units per second
        public static final double DRIVEBASE_LEFT_MAX_ACCELERATION = 0.01; // sensor units per second per second

        public static final double DRIVEBASE_RIGHT_MAX_VELOCITY = 14600.0; // sensor units per second
        public static final double DRIVEBASE_RIGHT_MAX_ACCELERATION = 0.01; // sensor units per second per second

        public static final double ELEVATOR_MAINTAIN_FEEDFORWARD = 0.01; // percent output
        public static final double ELEVATOR_MAX_VELOCITY = 0.01; // sensor units per second
        public static final double ELEVATOR_MAX_ACCELERATION = 0.01; // sensor units per second per second

        public static final double DRIVEBASE_TICKS_END_RANGE = Drivebase.getTicks(3/12);
        public static final double DRIVEBASE_IN_RANGE_END_TIME = 0.75;
        public static final double DRIVEBASE_HEADING_END_RANGE = 2;
    }
}
