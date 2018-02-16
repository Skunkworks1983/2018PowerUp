package frc.team1983.subsystems.utilities.outputwrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.subsystems.Elevator;

/* Implementation of PidOutputWrapper, specifically for the elevator subsystem. Simply sets the winch speed of the
   Elevator.
*/
public class ElevatorPidOutput extends PidOutputWrapper
{
    private Elevator elevator;

    //Only used to store the instance of the drivebase.
    public ElevatorPidOutput(Elevator elevator)
    {
        this.elevator = elevator;
    }

    //Sets the speed of the drivebase sides opposite to each other, based on pidWrite.
    public void writeHelper(double out)
    {
        elevator.set(ControlMode.PercentOutput, out);
    }
}
