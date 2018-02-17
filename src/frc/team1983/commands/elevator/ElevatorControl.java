package frc.team1983.commands.elevator;

import com.ctre.phoenix.Logger;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.OI;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Elevator;
import frc.team1983.subsystems.utilities.PidControllerWrapper;
import frc.team1983.subsystems.utilities.inputwrappers.ElevatorPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.ElevatorPidOutput;

//This command controls the elevator with a PID. It should be the only class that
// sets the speed of the elevator winches
public class ElevatorControl extends CommandBase
{
    private PidControllerWrapper controller;
    private ElevatorPidInput pidIn;
    private ElevatorPidOutput pidOut;
    private OI oi;
    private static org.apache.logging.log4j.core.Logger elevlog;

    private Elevator elevator;

    public ElevatorControl(Elevator elevator)
    {
        elevlog = LoggerFactory.createNewLogger(ElevatorControl.class);
        this.elevator = elevator;

        pidIn = new ElevatorPidInput(elevator);
        pidOut = new ElevatorPidOutput(elevator);
        controller = new PidControllerWrapper(Constants.PidConstants.ElevatorControlPid.P,
                                              Constants.PidConstants.ElevatorControlPid.I,
                                              Constants.PidConstants.ElevatorControlPid.D,
                                              Constants.PidConstants.ElevatorControlPid.F,
                                              pidIn, pidOut);
    }

    @Override
    public void initialize()
    {
        controller.enable();
        elevlog.info("elevator control initalized");
    }

    @Override
    //Get the 0-1 range of setpoint, scale it to the encoder range, and make it an integer
    public void execute()
    {
        elevlog.info("elevator controller execute is running");
        //if(oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ManualControl.MANUAL_SWITCH)) returns null pointer
        // exception when run, attempting to figure out issue
        if(oi.isPressed(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ManualControl.MANUAL_SWITCH)) //has not been tested
        {
            elevlog.info("manual switch enabled");
            if(controller.isEnabled())
            {
                controller.disable();
            }
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
            elevlog.info("manual switch is disabled currently");
            if(!controller.isEnabled())
            {
                controller.enable();
            }
            controller.setSetpoint(elevator.getSetpoint());
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
        controller.disable();
        //TODO: maybe set speed to 0?
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}