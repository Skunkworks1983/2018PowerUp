package frc.team1983.commands.elevator;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.settings.Misc;
import frc.team1983.settings.PidValues;
import frc.team1983.subsystems.Elevator;
import frc.team1983.subsystems.utilities.inputwrappers.ElevatorPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.ElevatorPidOutput;

public class ElevatorControl extends Command
{
    private PIDController controller;
    private PIDSource pidIn;
    private PIDOutput pidOut;

    private Elevator elevator;

    //This command controls the elevator with a PID. It should be the only class that
    // sets the speed of the elevator winches
    public ElevatorControl()
    {
        elevator = Robot.getInstance().getElevator();

        pidIn = new ElevatorPidInput(elevator);
        pidOut = new ElevatorPidOutput(elevator);
        controller = new PIDController(PidValues.ELEVATOR_CONTROL_P,
                                       PidValues.ELEVATOR_CONTROL_I,
                                       PidValues.ELEVATOR_CONTROL_D,
                                       PidValues.ELEVATOR_CONTROL_F,
                                       pidIn, pidOut);
    }

    @Override
    protected void initialize()
    {
        controller.enable();
    }

    @Override
    //Get the 0-1 range of setpoint, scale it to the encoder range, and make it an integer
    protected void execute()
    {
        controller.setSetpoint((int) elevator.getSetpoint() * Misc.ELEVATOR_ENCODER_SCALAR);
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        controller.disable();
    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
    }
}
