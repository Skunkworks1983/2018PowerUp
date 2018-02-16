package frc.team1983.subsystems.utilities.outputwrappers;

import frc.team1983.subsystems.Drivebase;

public class DriveStraightPidOutput extends PidOutputWrapper
{
    Drivebase drivebase;
    double baseSpeed;

    public DriveStraightPidOutput(Drivebase drivebase, double baseSpeed)
    {
        this.drivebase = drivebase;
        this.baseSpeed = baseSpeed;
    }

    public void writeHelper(double out)
    {
        if(baseSpeed + out <= 1 && baseSpeed + out >= -1)
        {
            drivebase.setLeft(baseSpeed + out);
        }
        else if(baseSpeed + out > 1)
        {
            drivebase.setLeft(1);
        }
        else if(baseSpeed + out <-1)
        {
            drivebase.setLeft(-1);
        }

        if(baseSpeed - out <=1 && baseSpeed - out >= -1)
        {
            drivebase.setRight(baseSpeed - out);
        }
        else if(baseSpeed - out > 1)
        {
            drivebase.setRight(1);
        }
        else if(baseSpeed - out < -1)
        {
            drivebase.setRight(-1);
        }
    }
}
