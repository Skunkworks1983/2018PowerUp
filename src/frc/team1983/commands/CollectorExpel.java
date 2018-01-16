package frc.team1983.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.settings.Misc;
import frc.team1983.subsystems.Collector;

//Runs the collector outward
public class CollectorExpel extends Command
{
    private Collector collector = Robot.getInstance().getCollector();
    public CollectorExpel()
    {
        requires(collector);
    }

    @Override
    protected void initialize()
    {
    }


    @Override
    protected void execute()
    {
        collector.setSpeed(-Misc.COLLECTOR_EXPEL_SPEED);
    }


    @Override
    protected boolean isFinished()
    {
        return false;
    }


    @Override
    protected void end()
    {
        collector.setSpeed(0);
    }


    @Override
    protected void interrupted()
    {
        super.interrupted();
    }
}
