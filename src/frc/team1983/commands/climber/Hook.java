package frc.team1983.commands.climber;

import frc.team1983.commands.CommandBase;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Climber;
import org.apache.logging.log4j.core.Logger;

public class Hook extends CommandBase
{
    private Logger logger;
    private Climber climber;

    public Hook(Climber climber)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());

        logger.info("HOOKING");
        requires(climber);
        this.climber = climber;
    }

    @Override
    public void initialize()
    {
        climber.hook();
    }

    @Override
    public void execute()
    {
    }

    @Override
    public boolean isFinished()
    {
        return false; //Run once
    }

    @Override
    public void end()
    {
        climber.stopHooking();
    }

    @Override
    public void interrupted()
    {
        end();
    }
}
