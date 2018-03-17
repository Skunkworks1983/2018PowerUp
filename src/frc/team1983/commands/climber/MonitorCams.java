package frc.team1983.commands.climber;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Climber;
import org.apache.logging.log4j.core.Logger;

public class MonitorCams extends CommandBase
{
    private Logger logger;
    private Climber climber;

    public MonitorCams(Climber climber)
    {
        this.climber = climber;

        logger = LoggerFactory.createNewLogger(this.getClass());
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        if(climber.getCamSwitch(true))
        {
            if(!climber.haveTension())
            {
                logger.warn("Left cam limit switch engaged, but the climber does not have tension!");
            }
            SmartDashboard.putBoolean("Left cam limit switch engaged", true);
        }
        else
        {
            SmartDashboard.putBoolean("Left cam limit switch engaged", false);
        }
        if(climber.getCamSwitch(false))
        {
            if(!climber.haveTension())
            {
                logger.warn("Right cam limit switch engaged, but the climber does not have tension!");
            }
            SmartDashboard.putBoolean("Right cam limit switch engaged", true);
        }
        else
        {
            SmartDashboard.putBoolean("Right cam limit switch engaged", false);
        }
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end()
    {

    }

    @Override
    public void interrupted()
    {

    }
}
