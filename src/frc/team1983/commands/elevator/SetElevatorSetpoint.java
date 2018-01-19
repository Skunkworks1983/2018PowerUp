package frc.team1983.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.services.OI;
import frc.team1983.Robot;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Elevator;

public class SetElevatorSetpoint extends Command
{
    private Elevator elevator;
    private OI oi;

    private Elevator.Setpoint setpoint;

    //A command for setting the setpoint of the elevator pid.
    public SetElevatorSetpoint(Elevator.Setpoint setpoint, Elevator elevator, OI oi)
    {
        this.setpoint = setpoint;
        this.elevator = elevator;
        this.oi = oi;
        requires(elevator);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        //Check to see if the oi is in slider position mode. If so, use the slider pos instead of the preset
        if(oi.isDown(Constants.OIMap.Ports.BUTTONS, Constants.OIMap.SliderConstants.sliderPresetsToggle))
        {
            elevator.setSetpoint(oi.getSliderPos());
        }
        else
        {
            elevator.setSetpoint(setpoint);
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
        this.end();
    }
}
