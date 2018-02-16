package frc.team1983.commands.collector;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import org.apache.logging.log4j.core.Logger;

//Runs the collector inwards, holding cube in place
public class CollectorIntake extends CommandBase
{
    private int leftCounter, rightCounter;
    private Collector collector;

    private Logger logger;

    public CollectorIntake(Collector collector)
    {
        logger = LoggerFactory.createNewLogger(CollectorIntake.class);
        requires(collector);
        this.collector = collector;

        leftCounter = Constants.MotorSetpoints.COLLECTOR_SWITCH_DEBOUNCE_TIME;
        rightCounter = Constants.MotorSetpoints.COLLECTOR_SWITCH_DEBOUNCE_TIME;

        requires(collector);
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
        if(isLeftSwitchDown())
        {
            if(isRightSwitchDown())
            {
                collector.setLeft(ControlMode.PercentOutput, 0.0);
                collector.setRight(ControlMode.PercentOutput, 0.0);
            }
            else
            {
                collector.setLeft(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_ROTATE_SPEED);
                collector.setRight(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
            }
        }
        else
        {
            if(isRightSwitchDown())
            {
                collector.setLeft(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
                collector.setRight(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_ROTATE_SPEED);
            }
            else
            {
                collector.setLeft(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
                collector.setRight(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
            }

        }
    }

    @Override
    public boolean isFinished() //isFinished when expel starts
    {
        return false;
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
