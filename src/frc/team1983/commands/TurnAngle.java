package frc.team1983.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1983.robot.Robot;
import frc.team1983.subsystems.Utilities.PidControlWrapper;

public class TurnAngle extends CommandBase
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
    public void initialize()
    {

        initialAngle = 0;
        //TODO: get p i d values here
        // turnPid = new PidControlWrapper();
        turnPid.setSetpoint(targetAngle);

    }

    @Override
    public void execute()
    {

    }

    @Override
    public boolean isFinished()
    {
        return turnPid.onTarget();
    }

    @Override
    public void end()
    {

        turnPid.disable();

    }

    @Override
    public void interrupted()
    {
        super.interrupted();
    }
}