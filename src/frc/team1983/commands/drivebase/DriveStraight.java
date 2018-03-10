package frc.team1983.commands.drivebase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.sensors.Gyro;
import frc.team1983.subsystems.utilities.PidControllerWrapper;
import frc.team1983.subsystems.utilities.inputwrappers.EncoderTurnAnglePidInput;
import frc.team1983.subsystems.utilities.inputwrappers.GyroPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.DriveStraightPidOutput;
import org.apache.logging.log4j.core.Logger;

import static java.lang.Math.abs;


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

    public DriveStraight(Drivebase drivebase, StatefulDashboard dashboard, double distance)
    {
        this(drivebase, dashboard, distance, Constants.PidConstants.DriveStraightPid.DEFAULT_BASE_SPEED);
    }

    public DriveStraight(Drivebase drivebase, StatefulDashboard dashboard, double distance, double baseSpeed)
    {
        this(drivebase, dashboard, distance, baseSpeed, Constants.PidConstants.DriveStraightPid.DEFAULT_TIMEOUT);
    }

    public DriveStraight(Drivebase drivebase, StatefulDashboard dashboard, double distance,
                         double baseSpeed, double timeout)
    {
        this.baseSpeed = baseSpeed;
        setTimeout(timeout);

        logger = LoggerFactory.createNewLogger(DriveStraight.class);
        requires(drivebase);
        this.drivebase = drivebase;
        this.distance = distance;
        this.dashboard = dashboard;

        gyro = drivebase.getGyro();

        dashboard.add(this, "kP", 0.0);
        dashboard.add(this, "kI", 0.0);
        dashboard.add(this, "kD", 0.0);
        dashboard.add(this, "kF", 0.0);

        logger.info("Drivestraight constructed");
        logger.info("distance{}", distance);
    }


    @Override
    public void initialize()
    {
        logger.info("gyro status{}", gyro.isDead());
        if(distance > 0)
        {
            logger.info("distance greater than zero");
            pidOut = new DriveStraightPidOutput(drivebase, baseSpeed);
        }
        else
        {
            logger.info("distance less than zero");
            pidOut = new DriveStraightPidOutput(drivebase, -baseSpeed);

        }
        pidSource = gyro.isDead() ? new EncoderTurnAnglePidInput(drivebase) : new GyroPidInput(drivebase.getGyro());

        driveStraightPid = new PidControllerWrapper(dashboard.getDouble(this, "kP"),
                                                    dashboard.getDouble(this, "kI"),
                                                    dashboard.getDouble(this, "kD"),
                                                    dashboard.getDouble(this, "kF"),
                                                    pidSource, pidOut);
//urgay
        driveStraightPid.setSetpoint(pidSource.pidGet());
        driveStraightPid.setOutputRange(-Constants.AutoValues.MAX_OUTPUT, Constants.AutoValues.MAX_OUTPUT);
        driveStraightPid.enable();
        leftEncoderStart = drivebase.getLeftDist();
        rightEncoderStart = drivebase.getRightDist();
        logger.info("Left encoder start {}", leftEncoderStart);
        logger.info("Distance setpoint {}", distance);


    }

    @Override
    public void execute()
    {
        //logger.info("angle{}", driveStraightPid.getError());
    }

    @Override
    public boolean isFinished()
    {
        //condition checks if command is timed out or if we have gone the desired distance
        //using the average of the two offset distances travelled
        logger.info("getLeftDistance {}", drivebase.getLeftDist());
        return isTimedOut() || ((abs(abs(drivebase.getLeftDist()) - abs(leftEncoderStart)) +
                abs(abs(drivebase.getRightDist()) - abs(rightEncoderStart)))) / 2 >= abs(distance);


    }

    @Override
    public void end()
    {
        logger.info("Reached end");
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