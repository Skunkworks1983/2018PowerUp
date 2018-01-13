package frc.team1983.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.subsystems.utilities.PidControlWrapper;

public class TurnAngle extends Command
{
    double initialAngle;
    double targetAngle;
    PidControlWrapper turnPid;

    //the constructor takes a number of degrees it wants to turn. Positive is clockwise, negative is counterclockwise.
    public TurnAngle(double degrees)
    {
        requires(Robot.getInstance().getDrivebase());
        targetAngle = degrees;
    }

    @Override
    protected void initialize()
    {

        initialAngle = 0;
        //TODO: get p i d values here
        // turnPid = new PidControlWrapper();
        turnPid.setSetpoint(targetAngle);

    }

    @Override
    protected void execute()
    {

    }

    @Override
    protected boolean isFinished()
    {
        return turnPid.onTarget();
    }

    @Override
    protected void end()
    {

        turnPid.disable();

    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
    }
}