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
        drivebase.setLeft(ControlMode.PercentOutput, oi.getFixedAxis(Constants.OI.Joystick.LEFT, Constants.OI.Axis.Y));
        drivebase.setRight(ControlMode.PercentOutput, oi.getFixedAxis(Constants.OI.Joystick.RIGHT, Constants.OI.Axis.Y));
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
