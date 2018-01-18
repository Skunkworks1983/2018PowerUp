package frc.team1983.subsystems.utilities.pidoutputs;

import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.utilities.pidoutputs.PidOutputWrapper;

/*
   Implementation of PidOutputWrapper, specifically for the TurnAngle command. The logic simply sets the drivebase sides
   opposite of each other, so that the robot rotates instead of simply traveling in a direction.
*/
public class TurnAnglePidOutput extends PidOutputWrapper
{
    private Drivebase drivebase;

    //Only used to store the instance of the drivebase.
    public TurnAnglePidOutput(Drivebase drivebase)
    {
        this.drivebase = drivebase;
    }

    //Sets the speed of the drivebase sides opposite to each other, based on pidWrite.
    public void writeHelper(double out)
    {
        //TODO: Check directionality
        drivebase.setLeft(out);
        drivebase.setRight(-out);
    }
}
