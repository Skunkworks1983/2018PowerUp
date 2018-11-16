package frc.team1983.commands.collector;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.subsystems.Collector;

public class SetWristAngle extends CommandBase
{
    private Collector collector;
    private double degrees;

    public SetWristAngle(Collector collector, double degrees)
    {
        requires(collector);

        this.collector = collector;
        this.degrees = degrees;
    }

    public SetWristAngle(double degrees)
    {
        this(Robot.getInstance().getCollector(), degrees);
    }

    @Override
    public void initialize()
    {
        collector.setWrist(ControlMode.Position, Collector.toTicks(degrees));
    }

    @Override
    public void execute()
    {

    }

    @Override
    public boolean isFinished()
    {
        return true;
    }

    @Override
    public void end()
    {

    }

    @Override
    public void interrupted()
    {
        end();
    }
}
