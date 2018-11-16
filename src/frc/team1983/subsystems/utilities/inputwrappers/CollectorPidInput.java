package frc.team1983.subsystems.utilities.inputwrappers;

import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Elevator;

/* Implementation of PidInputWrapper, specifically for the elevator subsystem. Simply gets the encoder value
of the elevator
*/
public class CollectorPidInput extends PidInputWrapper
{
    private Collector collector;

    public CollectorPidInput(Collector collector)
    {
        this.collector = collector;
    }

    public double pidGet()
    {
        return collector.getPosition();
    }
}
