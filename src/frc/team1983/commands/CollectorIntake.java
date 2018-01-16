package frc.team1983.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.settings.Misc;

public class CollectorIntake extends Command
{

    //Runs the collector inwards
    public CollectorIntake()
    {
        requires(Robot.getInstance().getCollector());
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        Robot.getInstance().getCollector().setSpeed(Misc.COLLECTOR_INTAKE_SPEED);
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        Robot.getInstance().getCollector().setSpeed(0);
    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
    }
}
