package frc.team1983.commands.elevator;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.commands.CommandBase;
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
    private PIDSource pidIn;
    private PIDOutput pidOut;

    private Elevator elevator;

    public ElevatorControl(Elevator elevator)
    {
        this.elevator = elevator;

        pidIn = new ElevatorPidInput(elevator);
        pidOut = new ElevatorPidOutput(elevator);
        controller = new PidControllerWrapper(Constants.PidConstants.ElevatorPid.P,
                                              Constants.PidConstants.ElevatorPid.I,
                                              Constants.PidConstants.ElevatorPid.D,
                                              Constants.PidConstants.ElevatorPid.F,
                                              pidIn, pidOut);
    }

    @Override
    public void initialize()
    {
        controller.enable();
    }

    @Override
    //Get the 0-1 range of setpoint, scale it to the encoder range, and make it an integer
    public void execute()
    {
        controller.setSetpoint((int) elevator.getSetpoint());
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
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}