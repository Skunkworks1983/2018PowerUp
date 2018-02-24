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
        //Check to see if the oi is in slider position mode. If so, use the slider pos instead of the preset
        switch(setpoint)
        {
            default:
                newSetpoint = 0;
                break;

            case SCALE:
                newSetpoint = Constants.PidConstants.ElevatorControlPid.ELEVATOR_TOP-100;
                break;

            case BOTTOM:
                newSetpoint = 0;
                break;

            case SWITCH:
                newSetpoint = Constants.PidConstants.ElevatorControlPid.ELEVATOR_TOP/2;
                break;
        }
        elevator.setSetpoint(newSetpoint);

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