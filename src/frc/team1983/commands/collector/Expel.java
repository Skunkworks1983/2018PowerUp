package frc.team1983.commands.collector;

import frc.team1983.Constants;

public class Expel extends Intake
{
    public Expel(double throttle)
    {
        super(throttle);
    }

    public Expel()
    {
        this(Constants.COLLECTOR_EXPEL_THROTTLE);
    }
}
