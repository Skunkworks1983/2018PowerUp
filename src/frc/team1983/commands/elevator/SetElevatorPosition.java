package frc.team1983.commands.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.subsystems.Elevator;

public class SetElevatorPosition extends CommandBase
{
    private Elevator elevator;
    private double inches;
    private boolean block;

    public SetElevatorPosition(Elevator elevator, double inches, boolean block)
    {
        requires(elevator);

        this.elevator = elevator;
        this.inches = inches;
        this.block = block;
    }

    public SetElevatorPosition(double inches, boolean block)
    {
        this(Robot.getInstance().getElevator(), inches, block);
    }

    public SetElevatorPosition(double inches)
    {
        this(Robot.getInstance().getElevator(), inches, false);
    }

    @Override
    public void initialize()
    {
        elevator.set(ControlMode.Position, Elevator.toTicks(inches));
    }

    @Override
    public void execute()
    {

    }

    @Override
    public boolean isFinished()
    {
        return !block || elevator.isAtSetpoint();
    }

    @Override
    public void interrupted()
    {
        end();
    }

    @Override
    public void end()
    {

    }
}
