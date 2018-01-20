package frc.team1983.commands.collector;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;

//Runs the collector inwards
public class CollectorIntake extends CommandBase
{
    private Collector collector;

    public CollectorIntake(Collector collector)
    {
        requires(collector);
        this.collector = collector;
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
        collector.setSpeed(Constants.MotorMap.MotorGoals.COLLECTOR_INTAKE_SPEED);
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
