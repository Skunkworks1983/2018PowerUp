package frc.team1983.commands.drivebase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.commands.CommandBase;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.utilities.inputwrappers.EncoderTurnAnglePidInput;
import frc.team1983.subsystems.utilities.inputwrappers.GyroPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.DrivebaseRotationPidOutput;
import frc.team1983.subsystems.sensors.Gyro;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.utilities.PidControllerWrapper;
import frc.team1983.subsystems.utilities.outputwrappers.TurnAnglePidOutput;
import org.apache.logging.log4j.core.Logger;

//Turns the robot a number of degrees, as an offset from the Robot's current position.
//has case for when gyro eventually explodes
public class TurnAngle extends CommandBase
{
    private double targetAngle;
    private Drivebase drivebase;
    private PIDSource pidSource;
    private PIDOutput pidOut;
    private PIDController turnPid;
    private Gyro gyro;
    private int counter;

    private Logger logger;

    public TurnAngle(double degrees, Drivebase drivebase)
    {
        this(degrees, drivebase, 3 / 2.);
    }

    public TurnAngle(double degrees, Drivebase drivebase, double timeout)
    {
        super(timeout);
        requires(drivebase);
        this.drivebase = drivebase;
        logger = LoggerFactory.createNewLogger(TurnAngle.class);

        //Uses the TurnAngle specific PidWrapper implementations.
        //pidSource = new TurnAnglePidInput(); Implementation of PidInputWrapper is available on another branch.
        //Next to touch this file gets to implement the PidInputWrapper.
        pidOut = new TurnAnglePidOutput(drivebase);
        targetAngle = degrees;
        gyro = drivebase.getGyro();

    }

    @Override
    public void initialize()
    {
        if(!gyro.isDead())
        {
            pidSource = new GyroPidInput(drivebase.getGyro());
            pidOut = new DrivebaseRotationPidOutput(drivebase);
            turnPid = new PIDController(Constants.PidConstants.TurnAnglePid.P,
                                        Constants.PidConstants.TurnAnglePid.I,
                                        Constants.PidConstants.TurnAnglePid.D,
                                        Constants.PidConstants.TurnAnglePid.F,
                                        pidSource, pidOut);
            turnPid.setAbsoluteTolerance(5);
            turnPid.setSetpoint(targetAngle + drivebase.getGyro().getAngle());
            turnPid.enable();
        }
        else if(gyro.isDead()) //switches to encoder if gyro doesn't work
        {
            pidSource = new EncoderTurnAnglePidInput(drivebase);
            pidOut = new DrivebaseRotationPidOutput(drivebase);
            turnPid = new PIDController(Constants.PidConstants.TurnAnglePid.P,
                                        Constants.PidConstants.TurnAnglePid.I,
                                        Constants.PidConstants.TurnAnglePid.D,
                                        Constants.PidConstants.TurnAnglePid.F,
                                        pidSource, pidOut);
            turnPid.setAbsoluteTolerance(5);
            turnPid.setSetpoint(targetAngle + pidSource.pidGet());
            turnPid.enable();

        }
    }

    @Override
    public void execute()
    {
        System.out.println("TurnAngle executed");
    }

    @Override
    public boolean isFinished()
    {
        if(turnPid.onTarget())
        {
            //counter allows for overshoot and recorrection
            counter++;
        }
        if(!turnPid.onTarget() && counter >= 1)
        {
            counter--;
        }
        if(isTimedOut() || counter >= 15)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void end()
    {
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