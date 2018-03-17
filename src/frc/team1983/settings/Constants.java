package frc.team1983.settings;


import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.control.ClosedLoopGains;

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

        public static class Ramps
        {
            public static final int LEFT_RAMPLOCK = 0;
            public static final int RIGHT_RAMPLOCK = 2;

            public static final int LEFT_LEG = 1;
            public static final int RIGHT_LEG = 3;
        }
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
            public static final int DROP_SERVO_RIGHT = 4;

            public static final int HOOK_SERVO_0 = 1;
            public static final int HOOK_SERVO_1 = 5;

            public static final int CAM_SERVO_LEFT = 2;
            public static final int CAM_SERVO_RIGHT = 3;

            public static final int CAM_SWITCH_LEFT = 4;
            public static final int CAME_SWTICH_RIGHT = 5;

            //free current 1.8 amps
            //stall current 41 amps
            public static final double UPPER_TENSION_MOTOR_CURRENT = 30;
        }


        //Random and hard to classify drivebase constants
        public static class DrivebaseConstants
        {
            public static final double DRIVEBASE_TICKS_PER_FOOT = 1000 / AutoValues.EFFECTIVE_REDUCTION_DRIVEBASE
                    / AutoValues.WHEEL_CIRCUMFERENCE;
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
                public static final ClosedLoopGains MAIN = new ClosedLoopGains(
                        0.65,
                        0,
                        0.75,
                        0,
                        0,
                        1500 / Motion.DRIVEBASE_LEFT_MAX_TICKS_PER_SEC,
                        0 / (Motion.DRIVEBASE_LEFT_MAX_TICKS_PER_SEC / 0.75)
                );

            }

            public static class Right
            {
                public static final ClosedLoopGains MAIN = new ClosedLoopGains(
                        0.65,
                        0,
                        0.75,
                        0,
                        0,
                        1500 / Motion.DRIVEBASE_RIGHT_MAX_TICKS_PER_SEC,
                        0 / (Motion.DRIVEBASE_RIGHT_MAX_TICKS_PER_SEC / 0.75)
                );
            }

            public static ClosedLoopGains HEADINGCORRECTION = new ClosedLoopGains(
                    0.12, 0.002, 0, 0
            );
        }

        public static class Elevator
        {
            public static final ClosedLoopGains MAIN = new ClosedLoopGains(
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0
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
            public static final int ELEVATOR_TOP = 29000 - 300; //Addition is to keep it from hitting the max position
            public static final double ELEVATOR_MID_PERCENT = 0.979; //constant chosen to reproduce trial and error values
            public static final double ELEVATOR_LOW_PERCENT = 0.96;
            public static final double ELEVATOR_TRAVEL_PERCENT = 0.03; //taking thomas' 800 number, actual percent is 2.8
            public static final double ELEVATOR_TOP_PERCENT = 0.99;

            public static class Slot0
            {
                public static final double P = 0.13;
                public static final double I = 0.000155;
                public static final double D = 0.8;
                public static final double F = 0;
                public static final int I_ZONE = 1000;

                public static final int ALLOWABLE_CLOSED_LOOP_ERROR = 50;
            }
            public static class Slot1
            {
                public static final double P = 0.08;
                public static final double I = 0.00005;
                public static final double D = 0.5;
                public static final double F = 0;
                public static final int I_ZONE = 800;

                public static final int ALLOWABLE_CLOSED_LOOP_ERROR = 50;
            }

        }
    }

    public static class AutoValues
    {
        public static final double WHEELBASE_RADIUS = 25.625 / 2 / 12; //T fix magic number
        public static final double WHEELBASE_CIRCUMFERENCE = 2 * Math.PI * WHEELBASE_RADIUS;
        public static final double WHEELBASE_DEGREES = WHEELBASE_CIRCUMFERENCE / 360.;
        public static final double EFFECTIVE_REDUCTION_DRIVEBASE = 18 / 24.;
        public static final double WHEEL_CIRCUMFERENCE = 6 * Math.PI / 12.;
        public static final double DISTANCE_FROM_ENCODER_TO_END_OF_ROBOT = 1.4;
        public static final double DRIVEBASE_ENCODER_FUDGE_FACTOR = 6 / 6.3;

        public static final double MAX_OUTPUT = 0.5;
        public static final double DISTANCE_SCALAR = 1000;

        public static final double DIFFERENTIAL_TURN_ANGLE_BASESPEED = .26;
    }

    //this contains all values relevant to the OIMap.
    public static class OIMap
    {
        public static final int MANUAL_SWITCH = 4; //button 9 on the 2016 oi

        public static final double JOYSTICK_DEADZONE = 0.15;
        public static final double JOYSTICK_RAMP_EXPONENT = 2;

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

        //Agglomeration of constants relating to the joysticks
        public static class OIConstants
        {
            //Joystick constants
            public static final double JOYSTICK_TOLERANCE = 0.5;

            //Scalar coefficient of the slider on the OIMap
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

        public static class ClimberButtons
        {
            public static final int HOOK = 21;
            public static final int DROP_FORKS = 20;
            public static final int CREATE_TENSION = 22;
            public static final int ENGAGE_DOG_GEARS = 23;
        }

        //Enums for presets
        public enum Setpoint
        {
            BOTTOM(0),
            TRAVEL(1860),
            SWITCH(8925+400), // elevator halfway point
            LOW(22700 +400),
            MID(25700 +400),
            TOP(28600); //TODO: add 400 once magnet moves

            private final double encoderTicks;

            Setpoint(double encoderTicks)
            {
                this.encoderTicks = encoderTicks;
            }

            public double getEncoderTicks()
            {
                return encoderTicks;
            }

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

        public static final double DRIVEBASE_LEFT_MAX_TICKS_PER_SEC = 14000.0;
        public static final double DRIVEBASE_RIGHT_MAX_TICKS_PER_SEC = 14600.0;

        public static final double ELEVATOR_MAX_TICKS_PER_SEC = 0;

        public static final double DRIVEBASE_TICKS_END_RANGE = Drivebase.getTicks(3/12);
        public static final double DRIVEBASE_IN_RANGE_END_TIME = 0.75;
        public static final double DRIVEBASE_HEADING_END_RANGE = 2;
    }
}
