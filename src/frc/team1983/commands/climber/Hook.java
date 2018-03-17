package frc.team1983.commands.climber;

import frc.team1983.commands.CommandBase;
import frc.team1983.subsystems.Climber;

public class Hook extends CommandBase
{
    private Climber climber;

    public Hook(Climber climber)
    {
        this.climber = climber;
    }

    @Override
    public void initialize()
    {
        climber.hook();
    }

    @Override
    public void execute()
    {
    }

    @Override
    public boolean isFinished()
    {
        return true; //Run once
    }

    @Override
    public void end()
    {

    }

    @Override
    public void interrupted()
    {
        end();
    }
}
