package frc.team1983.commands;

public class DoNothing extends CommandBase
{
    public DoNothing()
    {}

    public void initialize() {}

    public void execute() {}

    public boolean isFinished()
    {
        return true;
    }

    public void interrupted()
    {
        end();
    }

    public void end() {}
}
