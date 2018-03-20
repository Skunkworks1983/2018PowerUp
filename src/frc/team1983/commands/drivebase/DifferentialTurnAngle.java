package frc.team1983.commands.drivebase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.sensors.Gyro;
import frc.team1983.subsystems.utilities.inputwrappers.DifferentialTurnAnglePidInput;
import frc.team1983.subsystems.utilities.inputwrappers.EncoderTurnAnglePidInput;
import frc.team1983.subsystems.utilities.inputwrappers.GyroPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.DifferentialAdjustmentPidOutput;
import frc.team1983.subsystems.utilities.outputwrappers.DifferentialTurnAnglePidOutput;
import org.apache.logging.log4j.core.Logger;
//turns robot based off of distance each side has travelled, not the heading of the robot
public class DifferentialTurnAngle extends CommandBase
{
    private double targetAngle;
    private Drivebase drivebase;
    private PIDSource pivotPidSource;
    private PIDSource adjustmentPidSource;
    private DifferentialTurnAnglePidOutput pivotPidOut;
    private PIDOutput adjustmentPidOut;
    private PIDController pivotPid;
    private PIDController adjustmentPid;
    private Gyro gyro;
    //private EncoderTurnAnglePidInput encoderTurnAnglePidInput;
    private GyroPidInput gyroPidInput;
    private double initialAngle;
    private Logger logger;
    private StatefulDashboard dashboard;
    private double error;
    private int counter;

    public DifferentialTurnAngle(Drivebase drivebase, StatefulDashboard dashboard, double degrees)
    {
        this(drivebase, dashboard, degrees, Constants.PidConstants.TurnAnglePid.DEFAULT_TIMEOUT);
    }

    public DifferentialTurnAngle(Drivebase drivebase, StatefulDashboard dashboard, double degrees, double timeout)
    {
        super(timeout);
        logger = LoggerFactory.createNewLogger(DifferentialTurnAngle.class);
        requires(drivebase);
        this.drivebase = drivebase;
        this.dashboard = dashboard;
        //encoderTurnAnglePidInput = new EncoderTurnAnglePidInput(drivebase);
        gyro = drivebase.getGyro();
        gyroPidInput = new GyroPidInput(gyro);
        targetAngle = degrees;
        counter = 0;
        //logger.info("targetAngle{}", targetAngle);

        dashboard.add(this, "kP", 0.0);
        dashboard.add(this, "kI", 0.0);
        dashboard.add(this, "kD", 0.0);
        dashboard.add(this, "kF", 0.0);

        dashboard.add(this, "adjustmentP", 0.0);
        dashboard.add(this, "adjustmentI", 0.0);
        dashboard.add(this, "adjustmentD", 0.0);
        dashboard.add(this, "adjustmentF", 0.0);


    }

    @Override
    public void initialize()
    {
        //initialAngle = encoderTurnAnglePidInput.pidGet();
        gyro.initialize();
        initialAngle = gyroPidInput.pidGet();
        pivotPidSource = new DifferentialTurnAnglePidInput(drivebase);
        if(targetAngle < 0)
        {
            pivotPidOut = new DifferentialTurnAnglePidOutput(drivebase, -Constants.AutoValues.DIFFERENTIAL_TURN_ANGLE_BASESPEED);
        }
        else
        {
            pivotPidOut = new DifferentialTurnAnglePidOutput(drivebase, Constants.AutoValues.DIFFERENTIAL_TURN_ANGLE_BASESPEED); //magic number
        }
        pivotPid = new PIDController(dashboard.getDouble(this, "kP"),
                                     dashboard.getDouble(this, "kI"),
                                     dashboard.getDouble(this, "kD"),
                                     dashboard.getDouble(this, "kF"),
                                     pivotPidSource, pivotPidOut);
        pivotPid.setSetpoint(0);
        pivotPid.setOutputRange(-0.5, 0.5);
        pivotPid.enable();

        if(gyro.isDead())
        {
            adjustmentPidSource = new EncoderTurnAnglePidInput(drivebase);
        }
        else
        {
            adjustmentPidSource = new GyroPidInput(gyro);
        }
        adjustmentPidOut = new DifferentialAdjustmentPidOutput(pivotPidOut);
        adjustmentPid = new PIDController(dashboard.getDouble(this, "adjustmentP"),
                                          0,
                                          dashboard.getDouble(this, "adjustmentD"),
                                          dashboard.getDouble(this, "adjustmentF"),
                                          adjustmentPidSource, adjustmentPidOut);
        adjustmentPid.setSetpoint(targetAngle + drivebase.getGyro().getAngle());
        adjustmentPid.setAbsoluteTolerance(Constants.PidConstants.TurnAnglePid.ABSOLUTE_TOLERANCE);
        adjustmentPid.setOutputRange(-.5, .5);
        adjustmentPid.enable();

    }

    @Override
    public void execute()
    {

        //logger.debug("DifferentialTurnAngle executed");
        error = Math.abs(targetAngle - (gyroPidInput.pidGet() - initialAngle));
        logger.info("error{}", adjustmentPid.getError());
        //pidOut.setAdjustmentSpeed(error/(targetAngle) * .4);
        //logger.info("adjustmentSpeed {}", (error/targetAngle) * .4);

    }

    @Override
    public boolean isFinished()
    {
        if(adjustmentPid.onTarget())
        {
            counter++;
        }
        if(!adjustmentPid.onTarget() && counter >= 1)
        {
            counter--;
        }
        return isTimedOut() || counter >= 15;
    }

    @Override
    public void end()
    {
        pivotPid.disable();
        adjustmentPid.disable();
        drivebase.setLeft(ControlMode.PercentOutput, 0);
        drivebase.setRight(ControlMode.PercentOutput, 0);
        //logger.info("pid has ended");
    }

    @Override
    public void interrupted()
    {
        this.end();
    }


}
