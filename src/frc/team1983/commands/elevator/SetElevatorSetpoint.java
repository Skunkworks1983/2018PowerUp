package frc.team1983.commands.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.OI;
import frc.team1983.Robot;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Elevator;
import org.apache.logging.log4j.core.Logger;

public class SetElevatorSetpoint extends CommandBase
{
    private Elevator elevator;
    private OI oi;
    private double newSetpoint;
    private double initialLocation;
    private Constants.OIMap.Setpoint setpoint;

    private Logger logger;

    //A command for setting the setpoint of the elevator pid.
    public SetElevatorSetpoint(Constants.OIMap.Setpoint setpoint, Elevator elevator, OI oi)
    {
        requires(elevator);
        logger = LoggerFactory.createNewLogger(this.getClass());
        this.setpoint = setpoint;
        this.oi = oi;
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