package frc.team1983.commands;

import edu.wpi.first.wpilibj.command.Command;

public abstract class CommandBase extends Command
{
    public CommandBase()
    {

    }
    public CommandBase(double timeout)
    {
        super(timeout);
    }

    public void configTimeout(double timeout)
    {
        setTimeout(timeout);
    }

    public double getTimeout()
    {
        return getTimeout();
    }

    @Override
    public abstract void initialize();

    @Override
    public abstract void execute();

    @Override
    public abstract boolean isFinished();

    @Override
    public abstract void end();

    @Override
    public abstract void interrupted();

}
