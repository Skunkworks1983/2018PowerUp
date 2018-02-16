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
        //TODO: Verify directionality
        drivebase.setLeft(baseSpeed + out);
        drivebase.setRight(baseSpeed - out);
    }
}
