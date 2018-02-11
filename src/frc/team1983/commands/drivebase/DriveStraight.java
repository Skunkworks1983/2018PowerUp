package frc.team1983.commands.drivebase;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import org.apache.logging.log4j.core.Logger;

//Moves the robot forward a specified number of feet.
public class DriveStraight extends CommandBase
{
    private double distance;
    private Drivebase drivebase;
    private PIDController pidController;
    PIDSource source;
    private Logger logger;

    public DriveStraight(double distance, Drivebase drivebase)
    {
        logger = LoggerFactory.createNewLogger(DriveStraight.class);
        requires(drivebase);
        this.drivebase = drivebase;
        this.distance = distance * Constants.MotorMap.DrivebaseConstants.DRIVEBASE_TICKS_PER_FOOT;
        //needs a pidsource and pidoutput
        //PidController driveDistancePid = new PIDController(PidConstants.DRIVESTRAIGHT_PID_P, PidConstants.DRIVESTRAIGHT_PID_I, PidConstants.DRIVESTRAIGHT_PID_D);

    }

    @Override
    public void initialize()
    {
        //driveDistancePid.setSetpoint(distance)
        //driveDistancePid.Enable();
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished()
    {
        //return pidcontroller.onTarget();
        return false;
    }

    @Override
    public void end()
    {
        Robot.getInstance().getDrivebase().setLeft(0);
        Robot.getInstance().getDrivebase().setRight(0);
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}
