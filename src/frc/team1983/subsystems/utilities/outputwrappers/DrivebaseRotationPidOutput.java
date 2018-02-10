package frc.team1983.subsystems.utilities.outputwrappers;

import frc.team1983.subsystems.Drivebase;

public class DrivebaseRotationPidOutput extends PidOutputWrapper
{
    private Drivebase drivebase;

    public DrivebaseRotationPidOutput(Drivebase drivebase)
    {
        this.drivebase = drivebase;
    }

    public void writeHelper(double out)
    {
        //TODO: Verify directionality
        drivebase.setLeft(-out);
        drivebase.setRight(out);
    }
}
