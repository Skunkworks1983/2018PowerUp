package frc.team1983.commands.collector;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.LoggerFactory;
import frc.team1983.Constants;
import frc.team1983.subsystems.Collector;
import org.apache.logging.log4j.core.Logger;

//Runs the collector inwards, holding cube in place
public class CollectorIntake extends CommandBase
{
    private int leftCounter, rightCounter;
    private Collector collector;
    private boolean stop;

    private Logger logger;

    public CollectorIntake(Collector collector, boolean stop)
    {
        logger = LoggerFactory.createNewLogger(CollectorIntake.class);
        requires(collector);
        this.collector = collector;
        this.stop = stop;
        leftCounter = Constants.MotorSetpoints.COLLECTOR_SWITCH_DEBOUNCE_TIME;
        rightCounter = Constants.MotorSetpoints.COLLECTOR_SWITCH_DEBOUNCE_TIME;
    }

    public CollectorIntake(Collector collector, double timeout)
    {
        this(collector, false);
        setTimeout(timeout);
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
        if(!stop)
        {

            collector.setLeft(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
            collector.setRight(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
        }
        else
        {
            collector.setLeft(ControlMode.PercentOutput, 0.0);
            collector.setRight(ControlMode.PercentOutput, 0.0);
        }


    }

    @Override
    public boolean isFinished() //isFinished when expel starts
    {
        return isTimedOut();
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

    private boolean isLeftSwitchDown()
    {
        if(collector.isLeftSwitchDown())
        {
            leftCounter = 0;
            return true;
        }
        return leftCounter++ < Constants.MotorSetpoints.COLLECTOR_SWITCH_DEBOUNCE_TIME;
    }

    private boolean isRightSwitchDown()
    {
        if(collector.isRightSwitchDown())
        {
            rightCounter = 0;
            return true;
        }

        return rightCounter++ < Constants.MotorSetpoints.COLLECTOR_SWITCH_DEBOUNCE_TIME;
    }
}
