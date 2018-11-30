package frc.team1983.commands.drivebase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.OI;
import frc.team1983.Constants;
import frc.team1983.subsystems.Drivebase;

public class RunTankDrive extends CommandBase
{
    private Drivebase drivebase;
    private OI oi;

    public RunTankDrive(Drivebase drivebase, OI oi)
    {
        requires(drivebase);

        this.drivebase = drivebase;
        this.oi = oi;
    }

    public RunTankDrive()
    {
        this(Robot.getInstance().getDrivebase(), Robot.getInstance().getOI());
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        double leftStick = Math.abs(oi.getLeftY()) > Constants.OIMap.JOYSTICK_DEADZONE ? oi.getLeftY() : 0;
        double leftThrottle = Math.pow(Math.abs(leftStick), Constants.OIMap.JOYSTICK_EXPONENT) * Math.signum(leftStick);
        drivebase.setLeft(ControlMode.PercentOutput, leftThrottle);

        double rightStick = Math.abs(oi.getRightY()) > Constants.OIMap.JOYSTICK_DEADZONE ? oi.getRightY() : 0;
        double rightThrottle = Math.pow(Math.abs(rightStick), Constants.OIMap.JOYSTICK_EXPONENT) * Math.signum(rightStick);
        drivebase.setRight(ControlMode.PercentOutput, rightThrottle);
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void interrupted()
    {
        end();
    }

    @Override
    public void end()
    {
        drivebase.setLeft(ControlMode.PercentOutput, 0);
        drivebase.setRight(ControlMode.PercentOutput, 0);
    }
}
