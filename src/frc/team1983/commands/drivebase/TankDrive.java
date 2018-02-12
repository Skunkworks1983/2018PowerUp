package frc.team1983.commands.drivebase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.OI;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;

//Default command for drivebase during Teleop
public class TankDrive extends CommandBase
{
    private Drivebase drivebase;
    private OI oi;

    public TankDrive(Drivebase drivebase, OI oi)
    {
        requires(drivebase);

        this.drivebase = drivebase;
        this.oi = oi;
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        double leftSpeed = oi.getAxis(Constants.OIMap.Port.LEFT_JOY, Constants.OIMap.JoyAxes.Y);
        double rightSpeed = oi.getAxis(Constants.OIMap.Port.RIGHT_JOY, Constants.OIMap.JoyAxes.Y);

        drivebase.setLeft(ControlMode.PercentOutput, leftSpeed);
        drivebase.setRight(ControlMode.PercentOutput, rightSpeed);
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end()
    {
        drivebase.setLeft(ControlMode.PercentOutput, 0);
        drivebase.setRight(ControlMode.PercentOutput, 0);
    }

    @Override
    public void interrupted()
    {
        this.end();
    }


}
