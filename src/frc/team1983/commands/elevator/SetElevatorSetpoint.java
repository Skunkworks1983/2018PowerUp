package frc.team1983.commands.elevator;

import frc.team1983.commands.CommandBase;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.Constants;
import frc.team1983.subsystems.Elevator;
import org.apache.logging.log4j.core.Logger;

public class SetElevatorSetpoint extends CommandBase
{
    private Elevator elevator;
    private double newSetpoint;
    private Constants.OIMap.Setpoint setpoint;

    private Logger logger;

    //A command for setting the setpoint of the elevator pid.
    public SetElevatorSetpoint(Constants.OIMap.Setpoint setpoint, Elevator elevator)
    {
        requires(elevator);
        logger = LoggerFactory.createNewLogger(this.getClass());
        this.setpoint = setpoint;
        this.elevator = elevator;
    }

    @Override
    public void initialize()
    {
        switch(setpoint)
        {
            case BOTTOM:
                newSetpoint = Constants.OIMap.Setpoint.BOTTOM.getEncoderTicks();
                break;

            case TRAVEL:
                newSetpoint = Constants.OIMap.Setpoint.TRAVEL.getEncoderTicks();
                break;

            case SWITCH:
                newSetpoint = Constants.OIMap.Setpoint.SWITCH.getEncoderTicks();
                break;

            case LOW:
                newSetpoint = Constants.OIMap.Setpoint.LOW.getEncoderTicks();
                break;

            case MID:
                newSetpoint = Constants.OIMap.Setpoint.MID.getEncoderTicks();
                break;

            case TOP:
                newSetpoint = Constants.OIMap.Setpoint.TOP.getEncoderTicks();
                break;

            case BARF:
                newSetpoint = Constants.OIMap.Setpoint.BARF.getEncoderTicks();
                break;

            case NEW_AGE_BARF:
                newSetpoint = Constants.OIMap.Setpoint.NEW_AGE_BARF.getEncoderTicks();
                break;

            default:
                newSetpoint = 0;
                break;
        }
        setElevatorSlot(newSetpoint, elevator.getEncoderValue());
        elevator.setSetpoint(newSetpoint);
        logger.info("Setting elevator to {}", elevator.getSetpoint());
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
        this.end();
    }

    public void setElevatorSlot(double newSetpoint, double initialLocation)
    {
        if(newSetpoint < initialLocation)
        {
            elevator.switchSlots(false);
        }
        else
        {
            elevator.switchSlots(true);
        }
    }
}