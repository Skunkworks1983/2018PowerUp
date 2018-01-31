package frc.team1983.commands.collector;

import frc.team1983.commands.CommandBase;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;

//Runs the collector outward
public class CollectorExpel extends CommandBase
{
    private Collector collector;

    public CollectorExpel(Collector collector)
    {
        this.collector = collector;

        requires(collector);
    }

    @Override
    public void initialize()
    {
        collector.setLeft(Constants.MotorSetpoints.COLLECTOR_EXPEL_SPEED);
        System.out.println("CollectorExpel set left to " + Constants.MotorSetpoints.COLLECTOR_EXPEL_SPEED);
        collector.setRight(Constants.MotorSetpoints.COLLECTOR_EXPEL_SPEED);
        System.out.println("and right to " + Constants.MotorSetpoints.COLLECTOR_EXPEL_SPEED);
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
    public void end()
    {
        collector.setLeft(0);
        collector.setRight(0);
    }


    @Override
    public void interrupted()
    {
        this.end();
    }
}
