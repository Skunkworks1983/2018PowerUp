package frc.team1983.commands;

import edu.wpi.first.wpilibj.command.Command;

public abstract class CommandBase extends Command
{
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
