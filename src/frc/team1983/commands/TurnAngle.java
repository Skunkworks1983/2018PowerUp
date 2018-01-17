package frc.team1983.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.utilities.TurnAnglePidOutput;

public class TurnAngle extends Command
{
    double initialAngle;
    double targetAngle;
    Drivebase drivebase;
    PIDOutput pidOut;
    PIDController turnPid;

    //the constructor takes a number of degrees it wants to turn. Positive is clockwise, negative is counterclockwise.
    public TurnAngle(double degrees)
    {
        requires(Robot.getInstance().getDrivebase());
        drivebase = Robot.getInstance().getDrivebase();

        //Uses the TurnAngle specific PidOutputWrapper implementation.
        pidOut = new TurnAnglePidOutput(drivebase);
        targetAngle = degrees;
    }

    @Override
    protected void initialize()
    {
        initialAngle = 0;
        //TODO: get p i d values here
        //turnPid = new PIDController(p, i, d, f, getGyro(), pidOut);
        turnPid.setSetpoint(targetAngle);
    }

    @Override
    protected void execute() {}

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