package frc.team1983.commands.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.OI;
import frc.team1983.Robot;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Elevator;

public class SetElevatorSetpoint extends CommandBase
{
    private Elevator elevator;
    private OI oi;
    private double newSetpoint;

    private Constants.OIMap.Setpoint setpoint;

    //A command for setting the setpoint of the elevator pid.
    public SetElevatorSetpoint(Constants.OIMap.Setpoint setpoint, Elevator elevator, OI oi)
    {
        requires(elevator);
        this.setpoint = setpoint;
        this.oi = oi;
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        //Check to see if the oi is in slider position mode. If so, use the slider pos instead of the preset
        if(oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ManualControl.MANUAL_SWITCH))
        {
            if(oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ManualControl.ELEVATOR_UP))
            {
                elevator.set(ControlMode.PercentOutput, 0.5);
            }
            else if(oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ManualControl.ELEVATOR_DOWN))
            {
                elevator.set(ControlMode.PercentOutput, -0.5);
            }
        }
        else
        {
            switch(setpoint)
            {
                default:
                    newSetpoint = 0;
                case SCALE:
                    newSetpoint = Constants.OIMap.SliderConstants.SCALE_PRESET;
                    break;

                case BOTTOM:
                    newSetpoint = Constants.OIMap.SliderConstants.BOTTOM_PRESET;
                    break;

                case SWITCH:
                    newSetpoint = Constants.OIMap.SliderConstants.SWITCH_PRESET;
                    break;
            }
            elevator.setSetpoint(newSetpoint);
        }
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
        this.end();
    }
}