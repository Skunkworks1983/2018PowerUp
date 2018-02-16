package frc.team1983.commands.drivebase;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.sensors.Gyro;
import frc.team1983.subsystems.utilities.PidControllerWrapper;
import frc.team1983.subsystems.utilities.inputwrappers.DriveStraightPidInput;
import frc.team1983.subsystems.utilities.inputwrappers.EncoderTurnAnglePidInput;
import frc.team1983.subsystems.utilities.inputwrappers.GyroPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.DriveStraightPidOutput;

//Moves the robot forward a specified number of feet.
//has case for when gyro eventually explodes
public class DriveStraight extends CommandBase
{
    private double distance;
    private double leftEncoderStart;
    private double rightEncoderStart;
    private Drivebase drivebase;
    PIDSource pidSource;
    PIDOutput pidOut;
    private PidControllerWrapper driveStraightPid;
    private Gyro gyro;
    private double baseSpeed;

    public DriveStraight(double distance, Drivebase drivebase, double baseSpeed)
    {
        this(distance, drivebase, baseSpeed, 3 / 2.);
    }

    public DriveStraight(double distance, Drivebase drivebase)
    {
        this(distance, drivebase, Constants.PidConstants.DriveStrightPid.DEFAULT_BASE_SPEED);
    }

    public DriveStraight(double distance, Drivebase drivebase, double baseSpeed, double timeout)
    {
        requires(drivebase);
        this.drivebase = drivebase;
        this.distance = distance * Constants.MotorMap.DrivebaseConstants.DRIVEBASE_TICKS_PER_FOOT;
        gyro = drivebase.getGyro();
    }


    @Override
    public void initialize()
    {
        if(!gyro.isDead())
        {
            pidSource = new GyroPidInput(drivebase.getGyro());
            pidOut = new DriveStraightPidOutput(drivebase, baseSpeed);
            driveStraightPid = new PidControllerWrapper(Constants.PidConstants.DriveStrightPid.P,
                                                        Constants.PidConstants.DriveStrightPid.I,
                                                        Constants.PidConstants.DriveStrightPid.D,
                                                        Constants.PidConstants.DriveStrightPid.F,
                                                        pidSource, pidOut);
            driveStraightPid.setSetpoint(distance);
            driveStraightPid.enable();
        }
        else if(gyro.isDead()) //switches to encoder values if gyro is dead
        {
            pidSource = new EncoderTurnAnglePidInput(drivebase);
            pidOut = new DriveStraightPidOutput(drivebase, baseSpeed);
            driveStraightPid = new PidControllerWrapper(Constants.PidConstants.DriveStrightPid.P,
                                                        Constants.PidConstants.DriveStrightPid.I,
                                                        Constants.PidConstants.DriveStrightPid.D,
                                                        Constants.PidConstants.DriveStrightPid.F,
                                                        pidSource, pidOut);
            driveStraightPid.setSetpoint(distance);
            driveStraightPid.enable();
        }
        leftEncoderStart = drivebase.getLeftDist();
        rightEncoderStart = drivebase.getRightDist();


    }

    @Override
    public void execute()
    {
    }

    @Override
    public boolean isFinished()
    {
        double distanceTraveled = ((drivebase.getLeftDist() - leftEncoderStart) + (drivebase.getRightDist() - rightEncoderStart)) / 2;
        //Average the two offset distances traveled to tell if we're beyond the distance we want
        if(!isTimedOut() && distanceTraveled < distance)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void end()
    {
        driveStraightPid.disable();
        drivebase.setLeft(0);
        drivebase.setRight(0);
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}
