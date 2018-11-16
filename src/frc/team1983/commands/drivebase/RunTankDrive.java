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
        double leftStick = oi.getAxis(Constants.OIMap.Joystick.LEFT, Constants.OIMap.Axis.Y);
        double leftThrottle = Math.pow(Math.max(Math.abs(leftStick), Constants.OIMap.JOYSTICK_DEADZONE) * Math.signum(leftStick), Constants.OIMap.JOYSTICK_EXPONENT);
        drivebase.setLeft(ControlMode.PercentOutput, leftThrottle);

        double rightStick = oi.getAxis(Constants.OIMap.Joystick.RIGHT, Constants.OIMap.Axis.Y);
        double rightThrottle = Math.pow(Math.max(Math.abs(rightStick), Constants.OIMap.JOYSTICK_DEADZONE) * Math.signum(rightStick), Constants.OIMap.JOYSTICK_EXPONENT);
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
