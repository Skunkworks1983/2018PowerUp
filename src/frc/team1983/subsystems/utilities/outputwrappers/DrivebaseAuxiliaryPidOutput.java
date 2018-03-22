package frc.team1983.subsystems.utilities.outputwrappers;

import frc.team1983.subsystems.Drivebase;

public class DrivebaseAuxiliaryPidOutput extends PidOutputWrapper
{
    private Drivebase drivebase;

    public DrivebaseAuxiliaryPidOutput(Drivebase drivebase)
    {
        this.drivebase = drivebase;
    }

    @Override
    public void writeHelper(double out)
    {
        drivebase.setLeftAuxiliaryOutput(-out);
        drivebase.setRightAuxiliaryOutput(out);
    }
}
