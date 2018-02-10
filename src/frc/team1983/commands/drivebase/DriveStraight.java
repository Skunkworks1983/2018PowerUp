package frc.team1983.commands.drivebase;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.utilities.PidControllerWrapper;
import frc.team1983.subsystems.utilities.inputwrappers.GyroPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.DriveStraightPidOutput;

//Moves the robot forward a specified number of feet.
public class DriveStraight extends CommandBase
{
    private double distance;
    private double leftEncoderStart;
    private double rightEncoderStart;
    private Drivebase drivebase;
    PIDSource pidSource;
    PIDOutput pidOut;
    private PidControllerWrapper driveStraightPid;

    public DriveStraight(double distance, Drivebase drivebase, double baseSpeed)
    {
        requires(drivebase);
        this.drivebase = drivebase;
        this.distance = distance * Constants.MotorMap.DrivebaseConstants.DRIVEBASE_TICKS_PER_FOOT;

        pidSource = new GyroPidInput(drivebase.getGyro());
        pidOut = new DriveStraightPidOutput(drivebase, baseSpeed);

        driveStraightPid = new PidControllerWrapper(Constants.PidConstants.DriveStrightPid.P,
                                                    Constants.PidConstants.DriveStrightPid.I,
                                                    Constants.PidConstants.DriveStrightPid.D,
                                                    Constants.PidConstants.DriveStrightPid.F,
                                                    pidSource, pidOut);

    }

    public DriveStraight(double distance, Drivebase drivebase)
    {
        this(distance, drivebase, Constants.PidConstants.DriveStrightPid.DEFAULT_BASE_SPEED);
    }

    @Override
    public void initialize()
    {
        leftEncoderStart = drivebase.getLeftDist();
        rightEncoderStart = drivebase.getRightDist();

        driveStraightPid.setSetpoint(distance);
        driveStraightPid.enable();
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished()
    {
        //Average the two offset distances traveled to tell if we're beyond the distance we want
        double distanceTraveled = ((drivebase.getLeftDist() - leftEncoderStart) + (drivebase.getRightDist() - rightEncoderStart))/2;
        return distanceTraveled >= distance;
    }

    @Override
    public void end()
    {
        drivebase.setLeft(0);
        drivebase.setRight(0);
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}
