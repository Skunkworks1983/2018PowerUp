package frc.team1983.commands.debugging;

import frc.team1983.commands.CommandBase;
import frc.team1983.services.LoggerFactory;
import org.apache.logging.log4j.core.Logger;

public class Log extends CommandBase
{
    private Logger logger;
    private String message;

    public Log(String message)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());
        this.message = message;
    }

    @Override
    public void initialize()
    {
        logger.info(message);
    }

    @Override
    public void execute()
    {

    }

    @Override
    public boolean isFinished()
    {
        return true;
    }

    @Override
    public void interrupted()
    {
        end();
    }

    @Override
    public void end()
    {

    }
}
