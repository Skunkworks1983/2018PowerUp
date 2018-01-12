package org.usfirst.frc.team1983.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1983.robot.Robot;
import org.usfirst.frc.team1983.robot.subsystems.utilities.PidControlWrapper;

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