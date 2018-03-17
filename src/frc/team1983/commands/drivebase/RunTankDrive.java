package frc.team1983.commands.drivebase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.OI;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import org.apache.logging.log4j.core.Logger;

//Default command for drivebase during Teleop
public class RunTankDrive extends CommandBase
{
    private Drivebase drivebase;
    private OI oi;
    private Logger logger;

    public RunTankDrive(Drivebase drivebase, frc.team1983.services.OI oi)
    {
        requires(drivebase);

        this.drivebase = drivebase;
        this.oi = oi;

        logger = LoggerFactory.createNewLogger(RunTankDrive.class);
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        double left = oi.getAxis(Constants.OIMap.Joystick.LEFT, Constants.OIMap.Axis.Y);
        double right = oi.getAxis(Constants.OIMap.Joystick.RIGHT, Constants.OIMap.Axis.Y);

        if(!(Math.abs(left) < 0.03))
        {
            left = 0;
        }

        if(!(Math.abs(right) < 0.03))
        {
            right = 0;
        }

        drivebase.setLeft(ControlMode.PercentOutput, left);
        drivebase.setRight(ControlMode.PercentOutput, right);
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
