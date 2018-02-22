package frc.team1983.commands.collector;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.OI;
import frc.team1983.subsystems.Collector;

import javax.naming.ldap.Control;

public class SetRotateSpeed extends CommandBase
{
    private OI oi;
    private Collector collector;

    private double speed;

    public SetRotateSpeed(OI oi, Collector collector, double speed)
    {
        this.oi = oi;
        this.collector = collector;
        this.speed = speed;
    }

    @Override
    public void initialize()
    {}

    @Override
    public void execute()
    {
        collector.setRotate(ControlMode.PercentOutput, speed);
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end()
    {
        collector.setRotate(ControlMode.PercentOutput, 0);
    }

    @Override
    public void interrupted()
    {
        this.end();
    }

}
