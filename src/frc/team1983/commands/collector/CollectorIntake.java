package frc.team1983.commands.collector;

import frc.team1983.commands.CommandBase;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;

//Runs the collector inwards, holding cube in place
public class CollectorIntake extends CommandBase
{
    private Collector collector;

    public CollectorIntake(Collector collector)
    {
        this.collector = collector;

        requires(collector);
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
        if(collector.isLeftPressed())
        {
            if(collector.isRightPressed())
            {
                collector.setLeft(0.0);
                collector.setRight(0.0);
            }
            else
            {
                collector.setLeft(Constants.MotorSetpoints.COLLECTOR_ROTATE_SPEED);
                collector.setRight(Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
            }
        }
        else
        {
            if(collector.isRightPressed())
            {
                collector.setLeft(Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
                collector.setRight(Constants.MotorSetpoints.COLLECTOR_ROTATE_SPEED);
            }
            else
            {
                collector.setLeft(Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
                collector.setRight(Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
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
        collector.setLeft(0.0);
        collector.setRight(0.0);
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}
