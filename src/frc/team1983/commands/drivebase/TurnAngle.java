package frc.team1983.commands.drivebase;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.utilities.PidControllerWrapper;
import frc.team1983.subsystems.utilities.outputwrappers.TurnAnglePidOutput;
import org.apache.logging.log4j.core.Logger;

//Turns the robot a specified number of degrees
public class TurnAngle extends CommandBase
{
    private double initialAngle;
    private double targetAngle;
    private Drivebase drivebase;
    private PIDSource pidSource;
    private PIDOutput pidOut;
    private PidControllerWrapper turnPid;

    private Logger logger;

    public TurnAngle(double degrees, Drivebase drivebase)
    {
        requires(drivebase);
        this.drivebase = drivebase;
        logger = LoggerFactory.createNewLogger(TurnAngle.class);

        //Uses the TurnAngle specific PidWrapper implementations.
        //pidSource = new TurnAnglePidInput(); Implementation of PidInputWrapper is available on another branch.
        //Next to touch this file gets to implement the PidInputWrapper.
        pidOut = new TurnAnglePidOutput(drivebase);
        targetAngle = degrees;
    }

    @Override
    public void initialize()
    {
        initialAngle = 0;
        //TODO: get p i d values here
        //turnPid = new PIDController(p, i, d, f, getGyro(), pidOut);
        turnPid.setSetpoint(targetAngle);
    }

    @Override
    public void execute(){}

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
        this.end();
    }
}