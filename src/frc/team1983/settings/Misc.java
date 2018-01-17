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
}
