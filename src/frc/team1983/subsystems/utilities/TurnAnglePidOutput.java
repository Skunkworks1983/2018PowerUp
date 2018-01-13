package frc.team1983.subsystems.utilities;

import frc.team1983.subsystems.Drivebase;

public class TurnAnglePidOutput extends PidOutputWrapper
{
    Drivebase drivebase;
    public TurnAnglePidOutput(Drivebase drivebase)
    {
        this.drivebase = drivebase;
    }

    public void writeHelper(double out)
    {
        //TODO: Check directionality
        drivebase.setLeft(out);
        drivebase.setRight(-out);
    }
}
