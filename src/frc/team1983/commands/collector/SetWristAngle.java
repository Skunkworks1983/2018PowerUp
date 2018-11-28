package frc.team1983.commands.collector;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.subsystems.Collector;

public class SetWristAngle extends CommandBase
{
    private Collector collector;
    private double degrees;
    private boolean block;

    public SetWristAngle(Collector collector, double degrees, boolean block)
    {
        requires(collector);

        this.collector = collector;
        this.degrees = degrees;
        this.block = block;
    }

    public SetWristAngle(double degrees, boolean block)
    {
        this(Robot.getInstance().getCollector(), degrees, block);
    }

    public SetWristAngle(double degrees)
    {
        this(degrees, false);
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
        return !block || collector.isAtSetpoint();
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
