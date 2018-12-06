package frc.team1983.commands.drivebase;

import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.subsystems.Drivebase;

public class DriveFeet extends CommandBase
{
    private Drivebase drivebase;
    private double feet;

    public DriveFeet(Drivebase drivebase, double feet)
    {
        requires(drivebase);

        this.drivebase = drivebase;
        this.feet = feet;
    }

    public DriveFeet(double feet)
    {
        this(Robot.getInstance().getDrivebase(), feet);
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
