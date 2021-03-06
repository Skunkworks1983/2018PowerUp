package frc.team1983.subsystems.utilities.outputwrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.subsystems.Drivebase;

/*
   Implementation of PidOutputWrapper, specifically for the SimpleTurnAngle command. The logic simply sets the drivebase sides
   opposite of each other, so that the robot rotates instead of simply traveling in a direction.
*/
public class SimpleTurnAnglePidOutput extends PidOutputWrapper
{
    private Drivebase drivebase;

    //Only used to store the instance of the drivebase.
    public SimpleTurnAnglePidOutput(Drivebase drivebase)
    {
        this.drivebase = drivebase;
    }

    //Sets the speed of the drivebase sides opposite to each other, based on pidWrite.
    public void writeHelper(double out)
    {
        //TODO: Check directionality
        drivebase.setLeft(ControlMode.PercentOutput, out);
        drivebase.setRight(ControlMode.PercentOutput, -out);
    }
}
