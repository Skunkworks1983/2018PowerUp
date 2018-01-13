package frc.team1983.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.settings.RobotMap;

public class DriveStraight extends Command
{
    private double distance;
    private PIDController pidController;
    PIDSource source;

    //drives a distance in feet!
    public DriveStraight(double distance)
    {
        requires(Robot.getInstance().getDrivebase());
        this.distance = distance * RobotMap.DRIVEBASE_TICK_PER_FOOT;
        //needs a pidsource and pidoutput
        //PidController driveDistancePid = new PIDController(PidValues.DRIVESTRAIGHT_PID_P, PidValues.DRIVESTRAIGHT_PID_I, PidValues.DRIVESTRAIGHT_PID_D);

    }

    @Override
    protected void initialize()
    {
        //driveDistancePid.setSetpoint(distance)
        //driveDistancePid.Enable();
    }

    @Override
    protected void execute()
    {
        
    }

    @Override
    protected boolean isFinished()
    {
        //return pidcontroller.onTarget();
        return false;
    }

    @Override
    protected void end()
    {
        Robot.getInstance().getDrivebase().setLeft(0);
        Robot.getInstance().getDrivebase().setRight(0);
    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
        this.end();
    }
}
