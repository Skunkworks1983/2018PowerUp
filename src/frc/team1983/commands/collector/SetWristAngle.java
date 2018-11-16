package frc.team1983.commands.collector;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;

public class SetWristAngle extends CommandBase
{
    private double setpoint;

    public SetWristAngle(double setpoint)
    {
        requires(Robot.getInstance().getCollector());

        this.setpoint = setpoint;
    }

    @Override
    public void initialize()
    {
        Robot.getInstance().getCollector().setRotate(ControlMode.Position, setpoint);
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
