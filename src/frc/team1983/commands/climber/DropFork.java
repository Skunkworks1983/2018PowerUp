package frc.team1983.commands.climber;

import frc.team1983.commands.CommandBase;
import frc.team1983.subsystems.Climber;

public class DropFork extends CommandBase
{
    private Climber climber;

    public DropFork(Climber climber)
    {
        this.climber = climber;
    }

    @Override
    public void initialize()
    {
        climber.unlockForks();
    }

    @Override
    public void execute()
    {
    }

    @Override
    public boolean isFinished()
    {
        return true; //Only run once
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
