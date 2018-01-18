package frc.team1983.commands;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.settings.Constants;
import frc.team1983.settings.OIMap;
import frc.team1983.subsystems.Elevator;

public class SetElevatorSetpoint extends Command
{
    Elevator elevator = Robot.getInstance().getElevator();

    double setpoint;

    public SetElevatorSetpoint(double setpoint)
    {
        this.setpoint = setpoint;
        requires(elevator);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        if(Robot.getInstance().getOI().isDown(Constants.OIJoystick.BUTTONS, OIMap.sliderPresetsToggle))
        {
            elevator.setPIDSetpoint(Robot.getInstance().getOI().getSliderPos());
        }
        else
        {
            elevator.setPIDSetpoint(setpoint);
        }
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {

    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
    }
}
