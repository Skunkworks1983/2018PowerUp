package frc.team1983.commands.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.OI;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Elevator;

public class SetElevatorSpeed extends CommandBase
{
    private OI oi;
    private Elevator elevator;
    private double speed;

    public SetElevatorSpeed(OI oi, Elevator elevator, double speed)
    {
        this.oi = oi;
        this.elevator = elevator;
        this.speed = speed;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute()
    {
        //If the manual switch is not pressed, don't do anything.
        if(oi.isPressed(Constants.OIMap.Joystick.PANEL, Constants.OIMap.MANUAL_SWITCH)) //has not been tested
        {
            elevator.set(ControlMode.PercentOutput, speed);
        }
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end()
    {
        elevator.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}
