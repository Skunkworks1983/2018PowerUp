package frc.team1983.commands.collector;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.Constants;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.subsystems.Collector;

public class Intake extends CommandBase
{
    private Collector collector;
    private double throttle;

    public Intake(Collector collector, double throttle, double seconds)
    {
        requires(collector);
        setTimeout(seconds);

        this.collector = collector;
        this.throttle = throttle;
    }

    public Intake(double throttle, double seconds)
    {
        this(Robot.getInstance().getCollector(), throttle, seconds);
    }

    public Intake(double seconds)
    {
        this(Constants.MotorSetpoints.COLLECTOR_INTAKE_THROTTLE, seconds);
    }

    public Intake()
    {
        this(60);
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        collector.setIntakeLeft(ControlMode.PercentOutput, throttle);
        collector.setIntakeRight(ControlMode.PercentOutput, throttle);
    }

    @Override
    public boolean isFinished()
    {
        return isTimedOut();
    }

    @Override
    public void end()
    {
        collector.setIntakeLeft(ControlMode.PercentOutput, 0.0);
        collector.setIntakeRight(ControlMode.PercentOutput, 0.0);
    }

    @Override
    public void interrupted()
    {
        end();
    }
}
