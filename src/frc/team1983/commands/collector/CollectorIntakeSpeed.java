package frc.team1983.commands.collector;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.OI;
import frc.team1983.services.LoggerFactory;
import frc.team1983.Constants;
import frc.team1983.subsystems.Collector;
import org.apache.logging.log4j.core.Logger;

//Runs the collector inwards, holding cube in place
public class CollectorIntakeSpeed extends CommandBase
{
    private Collector collector;
    private OI oi;

    private Logger logger;
    private double speed;
    private boolean isFinished;

    public CollectorIntakeSpeed(Collector collector, OI oi, double speed)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());
        requires(collector);
        this.collector = collector;
        this.oi = oi;
        this.speed = speed;

        isFinished = false;
    }

    @Override
    public void initialize()
    {
        if(oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.MANUAL_SWITCH))
        {
            collector.setLeft(ControlMode.PercentOutput, speed);
            collector.setRight(ControlMode.PercentOutput, speed);
        }
    }

    @Override
    public void execute()
    {
        if(!oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.MANUAL_SWITCH))
        {
            this.isFinished = true;
        }
    }

    @Override
    public boolean isFinished() //isFinished when expel starts
    {
        return isFinished;
    }

    @Override
    public void end()
    {
        collector.setLeft(ControlMode.PercentOutput, 0.0);
        collector.setRight(ControlMode.PercentOutput, 0.0);
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}
