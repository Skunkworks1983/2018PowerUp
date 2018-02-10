package frc.team1983.commands.collector;

import frc.team1983.commands.CommandBase;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import org.apache.logging.log4j.core.Logger;

//Runs the collector inwards
public class CollectorIntake extends CommandBase
{
    private Collector collector;

    private Logger collectorIntakeLogger;
    public CollectorIntake(Collector collector)
    {
        collectorIntakeLogger = LoggerFactory.createNewLogger(CollectorIntake.class);
        requires(collector);
        this.collector = collector;
    }

    @Override
    public void initialize(){}

    @Override
    public void execute()
    {
        collector.setSpeed(Constants.PidConstants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
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
