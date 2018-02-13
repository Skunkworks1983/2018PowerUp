package frc.team1983.subsystems.utilities.inputwrappers;

import frc.team1983.subsystems.Elevator;

/* Implementation of PidInputWrapper, specifically for the elevator subsystem. Simply gets the encoder value
of the elevator
*/
public class ElevatorPidInput extends PidInputWrapper
{
    private Elevator elevator;

    public ElevatorPidInput(Elevator elevator)
    {
        this.elevator = elevator;
    }

    public double pidGet()
    {
        return elevator.getEncoderValue();
    }
}
