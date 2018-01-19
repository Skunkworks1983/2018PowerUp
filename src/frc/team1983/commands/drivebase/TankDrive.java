package frc.team1983.commands.drivebase;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.services.OI;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;

//Default command for drivebase during Teleop
public class TankDrive extends Command
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
    protected void initialize()
    {
        //TODO: look into talon setEnabled()
    }

    @Override
    protected void execute()
    {
        double leftSpeed = oi.getAxis(Constants.OIMap.Ports.LEFT_JOY, Constants.OIMap.JoyAxes.Y);
        double rightSpeed = oi.getAxis(Constants.OIMap.Ports.RIGHT_JOY, Constants.OIMap.JoyAxes.Y);

        drivebase.setLeft(leftSpeed);
        drivebase.setRight(rightSpeed);
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        drivebase.setLeft(0);
        drivebase.setRight(0);
    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
        this.end();
    }
}
