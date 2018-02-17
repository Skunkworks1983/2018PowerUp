package frc.team1983.commands.drivebase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.sensors.Gyro;
import frc.team1983.subsystems.utilities.PidControllerWrapper;
import frc.team1983.subsystems.utilities.inputwrappers.DriveStraightPidInput;
import frc.team1983.subsystems.utilities.inputwrappers.EncoderTurnAnglePidInput;
import frc.team1983.subsystems.utilities.inputwrappers.GyroPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.DriveStraightPidOutput;
import org.apache.logging.log4j.core.Logger;


//Moves the robot forward a specified number of feet.
//has case for when gyro eventually explodes
public class DriveStraight extends CommandBase
{
    private double distance;
    private double leftEncoderStart;
    private double rightEncoderStart;
    private Drivebase drivebase;
    private PIDSource pidSource;
    private PIDOutput pidOut;
    private PidControllerWrapper driveStraightPid;
    private Gyro gyro;
    private double baseSpeed;
    private StatefulDashboard dashboard;

    private Logger logger;

    public DriveStraight(StatefulDashboard dashboard, double distance, Drivebase drivebase)
    {
        this(dashboard, distance, drivebase, Constants.PidConstants.DriveStrightPid.DEFAULT_BASE_SPEED);
    }

    public DriveStraight(StatefulDashboard dashboard, double distance, Drivebase drivebase, double baseSpeed)
    {
        this(dashboard, distance, drivebase, baseSpeed, Constants.PidConstants.DriveStrightPid.DEFAULT_TIMEOUT);
    }

    public DriveStraight(StatefulDashboard dashboard, double distance,
                         Drivebase drivebase, double baseSpeed, double timeout)
    {

        logger = LoggerFactory.createNewLogger(DriveStraight.class);
        requires(drivebase);
        this.drivebase = drivebase;
        this.dashboard = dashboard;
        this.distance = distance * Constants.MotorMap.DrivebaseConstants.DRIVEBASE_TICKS_PER_FOOT;
        gyro = drivebase.getGyro();

        dashboard.add(this, "kP", 0.0);
        dashboard.add(this, "kI", 0.0);
        dashboard.add(this, "kD", 0.0);
        dashboard.add(this, "kF", 0.0);
    }


    @Override
    public void initialize()
    {
        if(!gyro.isDead())
        {
            pidSource = new GyroPidInput(drivebase.getGyro());
            pidOut = new DriveStraightPidOutput(drivebase, baseSpeed);
            driveStraightPid = new PidControllerWrapper(dashboard.getDouble(this, "kP"),
                                                        dashboard.getDouble(this, "kI"),
                                                        dashboard.getDouble(this, "kD"),
                                                        dashboard.getDouble(this, "kF"),
                                                        pidSource, pidOut);
            driveStraightPid.setSetpoint(distance);
            driveStraightPid.enable();
        }
        else if(gyro.isDead()) //switches to encoder values if gyro is dead
        {
            pidSource = new EncoderTurnAnglePidInput(drivebase);
            pidOut = new DriveStraightPidOutput(drivebase, baseSpeed);
            driveStraightPid = new PidControllerWrapper(dashboard.getDouble(this, "kP"),
                                                        dashboard.getDouble(this, "kI"),
                                                        dashboard.getDouble(this, "kD"),
                                                        dashboard.getDouble(this, "kF"),
                                                        pidSource, pidOut);
            driveStraightPid.setSetpoint(distance);
            driveStraightPid.enable();
        }
        leftEncoderStart = drivebase.getLeftEncoderValue();
        rightEncoderStart = drivebase.getRightEncoderValue();


    }

    @Override
    public void execute()
    {
    }

    @Override
    public boolean isFinished()
    {
        //Average the two offset distances traveled to tell if we're beyond the distance we want
        return isTimedOut() || ((drivebase.getLeftEncoderValue() - leftEncoderStart) +
                (drivebase.getRightEncoderValue() - rightEncoderStart)) / 2 >= distance;
    }

    @Override
    public void end()
    {
        driveStraightPid.disable();
        drivebase.setLeft(ControlMode.PercentOutput, 0);
        drivebase.setRight(ControlMode.PercentOutput, 0);
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}
