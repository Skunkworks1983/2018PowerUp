package frc.team1983.commands.drivebase;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.utilities.PidControllerWrapper;
import frc.team1983.subsystems.utilities.pidoutputs.TurnAnglePidOutput;

//Turns the robot a specified number of degrees
public class TurnAngle extends Command
{
    private double initialAngle;
    private double targetAngle;
    private Drivebase drivebase;
    private PIDSource pidSource;
    private PIDOutput pidOut;
    private PidControllerWrapper turnPid;

    public TurnAngle(double degrees, Drivebase drivebase)
    {
        requires(drivebase);
        this.drivebase = drivebase;

        //Uses the TurnAngle specific PidWrapper implementations.
        //pidSource = new TurnAnglePidInput(); Implementation of PidInputWrapper is available on another branch.
        //Next to touch this file gets to implement the PidInputWrapper.
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