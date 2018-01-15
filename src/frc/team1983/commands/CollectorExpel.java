package frc.team1983.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;

public class CollectorExpel extends Command
{
    private static double expellSpeed = -1;

    public CollectorExpel()
    {
        requires(Robot.getInstance().getCollector());
    }

    @Override
    protected void initialize()
    {
    }


    @Override
    protected void execute()
    {
        Robot.getInstance().getCollector().setSpeed(expellSpeed);
    }


    @Override
    protected boolean isFinished()
    {
        return false;
    }


    @Override
    protected void end()
    {
        Robot.getInstance().getCollector().setSpeed(0);
    }


    @Override
    protected void interrupted()
    {
        super.interrupted();
    }
}
