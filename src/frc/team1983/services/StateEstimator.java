package frc.team1983.services;

import frc.team1983.Robot;
import frc.team1983.subsystems.Drivebase;

public class StateEstimator
{
    private Drivebase drivebase;

    public StateEstimator(Drivebase drivebase)
    {
        this.drivebase = drivebase;
    }

    public StateEstimator()
    {
        this(Robot.getInstance().getDrivebase());
    }
}
