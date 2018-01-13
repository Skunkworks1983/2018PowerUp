package frc.team1983.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.RobotMap;

public class DriveStraight extends Command
{
    private double distance;
    private PIDController pidController;
    private double p;
    private double i;
    private double d;
    PIDSource source;

    //drives a distance in feet!
    public DriveStraight(double distance)
    {
        p = RobotMap.DRIVESTRAIGHT_PID_P;
        i = RobotMap.DRIVESTRAIGHT_PID_I;
        d = RobotMap.DRIVESTRAIGHT_PID_D;
        requires(Robot.getInstance().getDrivebase());
        this.distance = distance * RobotMap.DRIVEBASE_TICK_PER_FOOT;
        //needs a pidsource and pidoutput
        //PidController driveDistancePid = new PIDController(p, i, d);

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
        //return pidcontroller.onTarger();
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
    }
}
