package frc.team1983.subsystems.utilities.inputwrappers;

import frc.team1983.Robot;

public class ElevatorPidInput extends PidInputWrapper
{
    public double pidGet()
    {
        return Robot.getInstance().getElevator().getPosition();
    }
}
