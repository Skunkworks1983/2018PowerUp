package frc.team1983.commands.collector;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;

//Runs the collector outward
public class CollectorExpel extends Command
{
    private Collector collector;

    public CollectorExpel(Collector collector)
    {
        requires(collector);
        this.collector = collector;
    }

    @Override
    protected void initialize() {}


    @Override
    protected void execute()
    {
        collector.setSpeed(-Constants.PidValues.MotorSetpoints.COLLECTOR_EXPEL_SPEED);
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
