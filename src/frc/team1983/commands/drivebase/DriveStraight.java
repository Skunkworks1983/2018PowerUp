package frc.team1983.commands.drivebase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;

//Moves the robot forward a specified number of feet.
public class DriveStraight extends CommandBase
{
    private double distance;
    private Drivebase drivebase;
    private PIDController pidController;
    PIDSource source;

    public DriveStraight(double distance, Drivebase drivebase)
    {
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
        Robot.getInstance().getDrivebase().setLeft(ControlMode.PercentOutput, 0);
        Robot.getInstance().getDrivebase().setRight(ControlMode.PercentOutput, 0);
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}
