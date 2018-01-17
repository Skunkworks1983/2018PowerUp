package frc.team1983.commands.elevator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.OI;
import frc.team1983.Robot;
import frc.team1983.settings.Misc;
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
    private OI oi;

    public ElevatorControl()
    {
        elevator = Robot.getInstance().getElevator();
        requires(elevator);

        pidIn = new ElevatorPidInput();
        pidOut = new ElevatorControlPidOutput(elevator);
        controller = new PIDController(PidValues.ELEVATOR_CONTROL_P, PidValues.ELEVATOR_CONTROL_I, PidValues.ELEVATOR_CONTROL_D,
                                       PidValues.ELEVATOR_CONTROL_F, pidIn, pidOut);
        controller.setSetpoint(0);
        oi = Robot.getInstance().getOI();
    }

    public void setPIDSetpoint(double setPoint)
    {
        controller.setSetpoint((int) setPoint * Misc.ELEVATOR_ENCODER_SCALAR);
    }

    public int getPIDSetpoint()
    {
        return (int) controller.getSetpoint();
    }

    @Override
    protected void initialize()
    {
        controller.enable();
    }

    @Override
    protected void execute()
    {
        if(oi.buttons.isDown(0))
        {
            setPIDSetpoint(oi.getSliderPos());
        }
        else
        {
            
        }
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        controller.free();
        //todo make a fuss in the logger, because this command should always have control over the elevator
    }

    @Override
    protected void interrupted()
    {
        this.end();
    }
}
