package frc.team1983.commands.autonomous;

import frc.team1983.commands.CommandBase;

public class Wait extends CommandBase
{
    public Wait(double seconds)
    {
        setTimeout(seconds);
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
        return isTimedOut();
    }

    @Override
    public void end()
    {

    }

    @Override
    public void interrupted()
    {

    }
}
