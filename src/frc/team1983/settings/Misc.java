package frc.team1983.settings;

public class Misc
{
    public static final double JOYSTICK_TOLERANCE = 0.5;

    //Scalar coefficient of the slider on the OI
    public static final double SLIDER_SCALAR = 0.618726;

    /*
    We give the elevator a double between 0 and 1, but the encoder operates
    on a currently unknown scale from 0 to whatever, so multiply the the input
    by this value to have 1 be the maximum
    */
    public static final int ELEVATOR_ENCODER_SCALAR = 1; //TODO get the real max value

    //The percentage the elevator needs to be at to drop the cube into the switch
    public static final double SWITCH_HEIGHT = .3; //TODO find the actual switch height

    //The speed at which to run the collector when intaking or expelling. I'm assuming we want it at full.
    public static final double COLLECTOR_INTAKE_SPEED = 1;
    public static final double COLLECTOR_EXPEL_SPEED = 1;

    public static double RAMP_DROP_SERVO_GOAL = 1; //TODO: find actual goal
    public static double RAMP_PROP_SERVO_GOAL = 1;
}
