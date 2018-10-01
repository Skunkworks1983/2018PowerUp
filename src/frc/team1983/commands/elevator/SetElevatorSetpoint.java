package frc.team1983.commands.elevator;

import frc.team1983.commands.CommandBase;
import frc.team1983.services.OI;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Elevator;
import org.apache.logging.log4j.core.Logger;

public class SetElevatorSetpoint extends CommandBase
{
    private Elevator elevator;
    private OI oi;
    private Collector collector;
    private double newSetpoint;
    private double initialLocation;
    private Constants.ElevatorSetpoints setpoint;

    private Logger logger;

    //A command for setting the setpoint of the elevator pid.
    public SetElevatorSetpoint(Constants.ElevatorSetpoints setpoint, Elevator elevator, OI oi)
    {
        requires(elevator);
        logger = LoggerFactory.createNewLogger(this.getClass());
        this.setpoint = setpoint;
        this.oi = oi;
        this.collector = collector;
        this.elevator = elevator;
    }

    @Override
    public void initialize()
    {
        setElevatorSlot(setpoint, elevator.getEncoderValue());
        elevator.setWantedState(setpoint);
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

    public void setElevatorSlot(Constants.ElevatorSetpoints newSetpoint, double initialLocation)
    {
        if(newSetpoint.getEncoderTicks() < initialLocation)
        {
            elevator.switchSlots(false);
        }
        else
        {
            elevator.switchSlots(true);
        }
    }
}