package frc.team1983.commands.collector;

import frc.team1983.commands.CommandBase;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import org.apache.logging.log4j.core.Logger;

//Runs the collector outward
public class CollectorExpel extends CommandBase
{
    private Collector collector;

    private Logger logger;

    public CollectorExpel(Collector collector)
    {
        logger = LoggerFactory.createNewLogger(CollectorExpel.class);
        requires(collector);
        this.collector = collector;
    }

    @Override
    public void initialize()
    {
        collector.setLeft(Constants.MotorSetpoints.COLLECTOR_EXPEL_SPEED);
        collector.setRight(Constants.MotorSetpoints.COLLECTOR_EXPEL_SPEED);
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
