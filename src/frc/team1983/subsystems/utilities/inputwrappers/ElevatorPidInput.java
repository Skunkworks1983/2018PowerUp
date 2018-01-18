package frc.team1983.subsystems.utilities.inputwrappers;

import frc.team1983.subsystems.Elevator;

public class ElevatorPidInput extends PidInputWrapper
{
    private Elevator elevator;

    public ElevatorPidInput(Elevator elevator)
    {
        this.elevator = elevator;
    }

    public double pidGet()
    {
        return elevator.getPosition();
    }
}
