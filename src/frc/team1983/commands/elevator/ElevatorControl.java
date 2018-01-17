package frc.team1983.commands.elevator;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.settings.PidValues;
import frc.team1983.subsystems.Elevator;
import frc.team1983.subsystems.utilities.inputwrappers.ElevatorPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.ElevatorControlPidOutput;

public class ElevatorControl extends Command
{
    private Elevator elevator;

    private PIDController controller;
    private PIDSource pidIn;
    private PIDOutput pidOut;

    public ElevatorControl()
    {
        requires(Robot.getInstance().getElevator());
        elevator = Robot.getInstance().getElevator();

        pidIn = new ElevatorPidInput();
        pidOut = new ElevatorControlPidOutput(elevator);
        controller = new PIDController(PidValues.ELEVATOR_CONTROL_P, PidValues.ELEVATOR_CONTROL_I, PidValues.ELEVATOR_CONTROL_D,
                PidValues.ELEVATOR_CONTROL_F, pidIn, pidOut);
    }

    @Override
    protected void initialize() {}

    @Override
    protected void execute() {}

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end() {}

    @Override
    protected void interrupted ()
    {
        this.end();
    }
}