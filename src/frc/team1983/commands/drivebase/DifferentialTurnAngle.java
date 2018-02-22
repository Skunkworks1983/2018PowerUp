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
import frc.team1983.subsystems.utilities.outputwrappers.DifferentialTurnAnglePidOutput;
import org.apache.logging.log4j.core.Logger;

public class DifferentialTurnAngle extends CommandBase
{
    private double targetAngle;
    private Drivebase drivebase;
    private PIDSource pidSource;
    private PIDOutput pidOut;
    private PIDController turnPid;
    private Gyro gyro;
    private EncoderTurnAnglePidInput encoderTurnAnglePidInput;
    private double initialAngle;
    private Logger logger;
    private StatefulDashboard dashboard;

    public DifferentialTurnAngle(StatefulDashboard dashboard, double degrees, Drivebase drivebase)
    {
        this(dashboard, degrees, drivebase, Constants.PidConstants.TurnAnglePid.DEFAULT_TIMEOUT);
    }

    public DifferentialTurnAngle(StatefulDashboard dashboard, double degrees, Drivebase drivebase, double timeout)
    {
        super(timeout);
        requires(drivebase);
        this.drivebase = drivebase;
        this.dashboard = dashboard;
        encoderTurnAnglePidInput = new EncoderTurnAnglePidInput(drivebase);
        pidOut = new DifferentialTurnAnglePidOutput(drivebase, .5); //magic number
        targetAngle = degrees;

        dashboard.add(this, "kP", 0.0);
        dashboard.add(this, "kI", 0.0);
        dashboard.add(this, "kD", 0.0);
        dashboard.add(this, "kF", 0.0);

        logger = LoggerFactory.createNewLogger(SimpleTurnAngle.class);
    }

    @Override
    public void initialize()
    {
        initialAngle = encoderTurnAnglePidInput.pidGet();
        pidSource = new DifferentialTurnAnglePidInput(drivebase);
        pidOut = new DifferentialTurnAnglePidOutput(drivebase, .5); //magic number
        turnPid = new PIDController(dashboard.getDouble(this, "kP"),
                                    dashboard.getDouble(this, "kI"),
                                    dashboard.getDouble(this, "kD"),
                                    dashboard.getDouble(this, "kF"),
                                    pidSource, pidOut);
        turnPid.setSetpoint(0);
        turnPid.setOutputRange(-0.5, 0.5);
        turnPid.enable();
    }

    @Override
    public void execute()
    {
        logger.debug("DifferentialTurnAngle executed");
    }

    @Override
    public boolean isFinished()
    {
        return isTimedOut() || Math.abs(targetAngle - (encoderTurnAnglePidInput.pidGet() - initialAngle)) < 5;

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
