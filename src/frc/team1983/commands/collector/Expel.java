package frc.team1983.commands.collector;

import frc.team1983.Constants;

public class Expel extends Intake
{
    public Expel(double seconds)
    {
        super(Constants.MotorSetpoints.COLLECTOR_EXPEL_THROTTLE, seconds);
    }

    public Expel()
    {
        this(60);
    }
}
