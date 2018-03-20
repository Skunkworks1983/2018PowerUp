package frc.team1983.commands.climber;

import frc.team1983.commands.CommandBase;
import frc.team1983.subsystems.Climber;

public class StopTensioning extends CommandBase
{
    private Climber climber;

    public StopTensioning(Climber climber)
    {
        requires(climber);

        this.climber = climber;
    }

    @Override
    public void initialize()
    {
        climber.setTensionMotor(0);
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished()
    {
        return true;
    }

    public void end() {}

    public void interrupted()
    {
        end();
    }
}
