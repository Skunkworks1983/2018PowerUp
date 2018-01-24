package frc.team1983.commands.collector;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;

//Runs the collector outward
public class CollectorExpel extends CommandBase
{
    private Collector collector;

    public CollectorExpel(Collector collector)
    {
        requires(collector);
        this.collector = collector;
    }

    @Override
    public void initialize(){}

    @Override
    public void execute()
    {
        collector.setSpeed(-Constants.PidValues.MotorSetpoints.COLLECTOR_EXPEL_SPEED);
    }


    @Override
    public boolean isFinished()
    {
        return false;
    }


    @Override
    public void end()
    {
        collector.setSpeed(0);
    }


    @Override
    public void interrupted()
    {
        this.end();
    }
}
