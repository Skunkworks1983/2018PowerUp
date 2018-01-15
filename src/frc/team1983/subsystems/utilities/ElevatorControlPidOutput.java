package frc.team1983.subsystems.utilities;

import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;

/* Implementation of PidOutputWrapper, specifically for the ElevatorControl command. Simply sets the winch speed of the
   Elevator.
*/
public class ElevatorControlPidOutput extends PidOutputWrapper
{
    Elevator elevator;

    //Only used to store the instance of the drivebase.
    public ElevatorControlPidOutput(Elevator elevator)
    {
        this.elevator = elevator;
    }

    //Sets the speed of the drivebase sides opposite to each other, based on pidWrite.
    public void writeHelper(double out)
    {
        elevator.setWinchSpeed(out);
    }
}
