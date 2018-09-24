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

        double adj_left = left;
        double adj_right = right;

        double SCALE = 1.4;

        if((Math.abs(left) < 0.03))
        {
            adj_left = 0;
        }

        if((Math.abs(right) < 0.03))
        {
            adj_right = 0;
        }

        if(left < 0)
        {
            adj_left = -Math.abs(Math.pow(Math.abs(adj_left), SCALE));
        }
        else
        {
            adj_left = Math.abs(Math.pow(Math.abs(adj_left), SCALE));
        }

        if(right < 0)
        {
            adj_right = -Math.abs(Math.pow(Math.abs(adj_right), SCALE));
        }
        else
        {
            adj_right = Math.abs(Math.pow(Math.abs(adj_right), SCALE));
        }

        drivebase.left1.set(ControlMode.PercentOutput, adj_left);

        drivebase.right1.set(ControlMode.PercentOutput, adj_right);

        drivebase.setLeft(ControlMode.PercentOutput, adj_left);
        drivebase.setRight(ControlMode.PercentOutput, adj_right);

        drivebase.right3.set(ControlMode.PercentOutput, adj_right);

        //logger.info("Left: {}\tRight: {}", adj_left, adj_right);
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
