package frc.team1983.commands.drivebase;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.settings.RobotMap;
import frc.team1983.subsystems.Drivebase;

//Moves the robot forward a specified number of feet.
public class DriveStraight extends Command
{
    private double distance;
    private Drivebase drivebase;
    private PIDController pidController;
    PIDSource source;

    public DriveStraight(double distance, Drivebase drivebase)
    {
        requires(drivebase);
        this.drivebase = drivebase;
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
    protected void execute() {}

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
