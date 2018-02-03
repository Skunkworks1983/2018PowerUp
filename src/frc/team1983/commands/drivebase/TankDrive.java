package frc.team1983.commands.drivebase;

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
        //TODO: look into talon setEnabled()
    }

    @Override
    public void execute()
    {
        double leftSpeed = oi.getAxis(Constants.OIMap.Port.LEFT_JOY, Constants.OIMap.JoyAxes.Y);
        double rightSpeed = oi.getAxis(Constants.OIMap.Port.RIGHT_JOY, Constants.OIMap.JoyAxes.Y);

        drivebase.setLeft(leftSpeed);
        drivebase.setRight(rightSpeed);

        getLeftSpeed(leftSpeed);
        getRightSpeed(rightSpeed);
    }

    public double getLeftSpeed(double leftSpeed)
    {
        return leftSpeed;
    }

    public double getRightSpeed(double rightSpeed)
    {
        return rightSpeed;
    }

    @Override
    public boolean isFinished()
    {
        return false;
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
