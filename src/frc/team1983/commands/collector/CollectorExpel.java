package frc.team1983.commands.collector;

import com.ctre.phoenix.motorcontrol.ControlMode;
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
    private boolean shoot;

    public CollectorExpel(Collector collector, boolean shoot)
    {
        logger = LoggerFactory.createNewLogger(CollectorExpel.class);
        requires(collector);
        this.collector = collector;
        this.shoot = shoot;
    }

    public CollectorExpel(Collector collector, boolean shoot, double timeout)
    {
        this(collector, shoot);
        setTimeout(timeout);
    }

    @Override
    public void initialize()
    {
        if(shoot)
        {
            collector.setLeft(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_EXPEL_SPEED);
            collector.setRight(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_EXPEL_SPEED);
        }
        else
        {
            collector.setLeft(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_SLOW_EXPEL_SPEED);
            collector.setRight(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_SLOW_EXPEL_SPEED);
        }
    }

    @Override
    public void execute()
    {
    }


    @Override
    public boolean isFinished()
    {
        return isTimedOut();
    }


    @Override
    public void end()
    {
        collector.setLeft(ControlMode.PercentOutput, 0);
        collector.setRight(ControlMode.PercentOutput, 0);
    }


    @Override
    public void interrupted()
    {
        this.end();
    }
}
