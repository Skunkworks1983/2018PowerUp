package frc.team1983.commands.drivebase;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.commands.CommandBase;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.utilities.PidControllerWrapper;
import frc.team1983.subsystems.utilities.inputwrappers.GyroPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.DrivebaseRotationPidOutput;

//Turns the robot a number of degrees, as an offset from the Robot's current position.
public class TurnAngle extends CommandBase
{
    private double targetAngle;
    private Drivebase drivebase;
    private PIDSource pidSource;
    private PIDOutput pidOut;
    private PIDController turnPid;

    public TurnAngle(double degrees, Drivebase drivebase)
    {
        requires(drivebase);
        this.drivebase = drivebase;
        targetAngle = degrees;
    }

    @Override
    public void initialize()
    {
        pidSource = new GyroPidInput(drivebase.getGyro());
        pidOut = new DrivebaseRotationPidOutput(drivebase);

        turnPid = new PIDController(Constants.PidConstants.TurnAnglePid.P,
                                    Constants.PidConstants.TurnAnglePid.I,
                                    Constants.PidConstants.TurnAnglePid.D,
                                    Constants.PidConstants.TurnAnglePid.F,
                                    pidSource, pidOut);
        turnPid.setSetpoint(targetAngle + drivebase.getGyro().getAngle());
        turnPid.enable();
    }

    @Override
    public void execute()
    {
        System.out.println("TurnAngle executed");
    }

    @Override
    public boolean isFinished()
    {
        return false;
        //return turnPid.onTarget();
    }

    @Override
    public void end()
    {
        turnPid.disable();
        drivebase.setLeft(0);
        drivebase.setRight(0);
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}