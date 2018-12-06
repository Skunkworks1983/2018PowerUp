package frc.team1983.commands;

import frc.team1983.Robot;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Elevator;

public class Manual extends CommandBase
{
    private Elevator elevator;
    private Collector collector;

    public Manual(Elevator elevator, Collector collector)
    {
        requires(elevator);
        requires(collector);

        this.elevator = elevator;
        this.collector = collector;
    }

    public Manual()
    {
        this(Robot.getInstance().getElevator(), Robot.getInstance().getCollector());
    }

    @Override
    public void initialize()
    {

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
    public void interrupted()
    {
        end();
    }

    @Override
    public void end()
    {

    }
}
