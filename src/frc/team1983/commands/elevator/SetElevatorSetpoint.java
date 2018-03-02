package frc.team1983.commands.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
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

    }

    @Override
    public void execute()
    {
        logger.info("setpoint is being regiestered {}", elevator.getSetpoint());
        //Check to see if the oi is in slider position mode. If so, use the slider pos instead of the preset
        switch(setpoint)
        {
            case BOTTOM:
                newSetpoint = Constants.PidConstants.ElevatorControlPid.ELEVATOR_BOTTOM + 250;
                break;

            case SCALE_TOP:
                newSetpoint = Constants.PidConstants.ElevatorControlPid.SCALE_TOP-100;
                logger.info("Elevator set to scale");
                break;

            case SCALE_MID:
                newSetpoint = Constants.PidConstants.ElevatorControlPid.SCALE_NEUTRAL;
                break;

            case SCALE_LOW:
                newSetpoint = Constants.PidConstants.ElevatorControlPid.SCALE_LOW;
                break;

            case SWITCH:
                newSetpoint = Constants.PidConstants.ElevatorControlPid.SWITCH;
                break;

            default:
                newSetpoint = 0;
                break;
        }
        elevator.setSetpoint(newSetpoint);
        logger.info("setpoint is {}", elevator.getSetpoint());

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