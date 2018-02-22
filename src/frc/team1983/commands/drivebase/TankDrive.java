package frc.team1983.commands.drivebase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.OI;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import org.apache.logging.log4j.core.Logger;

//Default command for drivebase during Teleop
public class TankDrive extends CommandBase
{
    private Drivebase drivebase;
    private OI oi;
    private Logger logger;

    public TankDrive(Drivebase drivebase, OI oi)
    {
        requires(drivebase);

        this.drivebase = drivebase;
        this.oi = oi;

        logger = LoggerFactory.createNewLogger(TankDrive.class);
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        double leftSpeed = oi.getAxis(Constants.OIMap.Joystick.LEFT, Constants.OIMap.Axis.Y);
        double rightSpeed = oi.getAxis(Constants.OIMap.Joystick.RIGHT, Constants.OIMap.Axis.Y);

        drivebase.setLeft(ControlMode.PercentOutput, -leftSpeed);
        drivebase.setRight(ControlMode.PercentOutput, -rightSpeed);
        logger.info("leftSpeed{}\t rightSpeed{}", leftSpeed, rightSpeed);
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
