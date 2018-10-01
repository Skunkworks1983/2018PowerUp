package frc.team1983.commands.drivebase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.sensors.Pigeon;
import frc.team1983.subsystems.utilities.inputwrappers.EncoderTurnAnglePidInput;
import frc.team1983.subsystems.utilities.inputwrappers.GyroPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.DrivebaseRotationPidOutput;
import frc.team1983.subsystems.sensors.Gyro;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.utilities.outputwrappers.SimpleTurnAnglePidOutput;
import org.apache.logging.log4j.core.Logger;

//Turns the robot a number of degrees, as an offset from the MotorMap's current position.
//has case for when gyro eventually explodes
public class SimpleTurnAngle extends CommandBase
{
    private double targetAngle;
    private Drivebase drivebase;
    private PIDSource pidSource;
    private PIDOutput pidOut;
    private PIDController turnPid;
    private Pigeon gyro;
    private int counter;

    private Logger logger;
    private StatefulDashboard dashboard;

    public SimpleTurnAngle(StatefulDashboard dashboard, double degrees, Drivebase drivebase)
    {
        this(dashboard, degrees, drivebase, Constants.PidConstants.TurnAnglePid.DEFAULT_TIMEOUT);
    }

    public SimpleTurnAngle(StatefulDashboard dashboard, double degrees, Drivebase drivebase, double timeout)
    {
        super(timeout);
        requires(drivebase);
        this.drivebase = drivebase;
        this.dashboard = dashboard;
        logger = LoggerFactory.createNewLogger(SimpleTurnAngle.class);

        //Uses the SimpleTurnAngle specific PidWrapper implementations.
        //pidSource = new DifferentialTurnAnglePidInput(); Implementation of PidInputWrapper is available on another branch.
        //Next to touch this file gets to implement the PidInputWrapper.
        pidOut = new SimpleTurnAnglePidOutput(drivebase);
        targetAngle = degrees;
        gyro = drivebase.getGyro();

        dashboard.add(this, "kP", 0.11);
        dashboard.add(this, "kI", 0.0);
        dashboard.add(this, "kD", 0.0);
        dashboard.add(this, "kF", 0.0);
    }

    @Override
    public void initialize()
    {
        logger.info("initialize start");
        if(!gyro.isDead())
        {
            pidSource = new GyroPidInput(drivebase.getGyro());
        }
        else if(gyro.isDead()) //switches to encoder if gyro doesn't work
        {
            pidSource = new EncoderTurnAnglePidInput(drivebase);
        }
        pidOut = new DrivebaseRotationPidOutput(drivebase);
        turnPid = new PIDController(.11, 0, 0, 0, pidSource, pidOut);
        turnPid.setAbsoluteTolerance(Constants.PidConstants.TurnAnglePid.ABSOLUTE_TOLERANCE);
        turnPid.setSetpoint(targetAngle + drivebase.getGyro().getAngle());
        turnPid.setOutputRange(-0.5, 0.5);
        turnPid.enable();
        logger.info("initialized finished");
    }

    @Override
    public void execute()
    {
        logger.info("SimpleTurnAngle executed");
        //logger.info("SimpleTurnAngle error{}", turnPid.getError());
    }

    @Override
    public boolean isFinished()
    {
        logger.info("heading{}", pidSource.pidGet());
        if(turnPid.onTarget())
        {
            //counter allows for overshoot and recorrection
            logger.info("on target");
            counter++;
        }
        if(!turnPid.onTarget() && counter >= 1)
        {
            counter--;
        }
        return isTimedOut() || counter >= 15;
    }

    @Override
    public void end()
    {
        logger.info("SimpleTurnAngle ends, which means it began in the first place");
        turnPid.disable();
        drivebase.setLeft(ControlMode.PercentOutput, 0);
        drivebase.setRight(ControlMode.PercentOutput, 0);
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}