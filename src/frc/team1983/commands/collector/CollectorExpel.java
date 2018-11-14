package frc.team1983.commands.collector;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.LoggerFactory;
import frc.team1983.subsystems.Collector;
import org.apache.logging.log4j.core.Logger;

//Runs the collector outward
public class CollectorExpel extends CommandBase
{
    private Collector collector;

    private Logger logger;
    private double speed;

    public CollectorExpel(Collector collector, double speed) {
        this(collector, speed, 10); //timeout won't matter in teleop but can be applied if necessary
    }
    public CollectorExpel(Collector collector, double speed, double timeout)
    {
        logger = LoggerFactory.createNewLogger(CollectorExpel.class);

        logger.info("Collector speed: {}", speed);

        requires(collector);
        this.collector = collector;
        this.speed = speed;
        setTimeout(timeout);

    }

    /*public CollectorExpel(Collector collector, boolean shoot, double timeout)
    {
        this(collector, shoot);
        setTimeout(timeout);
    }*/

    @Override
    public void initialize()
    {
            collector.setLeft(ControlMode.PercentOutput, speed);
            collector.setRight(ControlMode.PercentOutput, speed);
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
