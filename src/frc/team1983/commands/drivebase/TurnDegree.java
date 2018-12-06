package frc.team1983.commands.drivebase;

import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.subsystems.Drivebase;

public class TurnDegree extends CommandBase
{
    private Drivebase drivebase;
    private double degrees;

    public TurnDegree(Drivebase drivebase, double degrees)
    {
        requires(drivebase);

        this.drivebase = drivebase;
        this.degrees = degrees;
    }

    public TurnDegree(double degrees)
    {
        this(Robot.getInstance().getDrivebase(), degrees);
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {

    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void interrupted()
    {
        end();
    }

    @Override
    public void end()
    {

    }
}
