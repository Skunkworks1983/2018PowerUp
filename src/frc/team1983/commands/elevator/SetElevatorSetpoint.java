package frc.team1983.commands.elevator;

import frc.team1983.commands.CommandBase;
import frc.team1983.services.OI;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Elevator;

public class SetElevatorSetpoint extends CommandBase
{
    private Elevator elevator;
    private OI oi;

    private Constants.OIMap.Setpoint setpoint;

    //A command for setting the setpoint of the elevator pid.
    public SetElevatorSetpoint(Constants.OIMap.Setpoint setpoint, Elevator elevator, OI oi)
    {
        this.setpoint = setpoint;
        this.elevator = elevator;
        this.oi = oi;
        requires(elevator);
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        //Check to see if the oi is in slider position mode. If so, use the slider pos instead of the preset
        if(oi.isDown(Constants.OIMap.Port.BUTTONS, Constants.OIMap.SliderConstants.sliderPresetsToggle))
        {
            elevator.setSetpoint(oi.getSliderPos());
        }
        else
        {
            elevator.setSetpoint(setpoint);
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

    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}
