package frc.team1983.commands.climber;

import frc.team1983.commands.CommandBase;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Climber;
import frc.team1983.subsystems.utilities.climberexceptions.HookNotEngagedException;
import frc.team1983.subsystems.utilities.climberexceptions.NoTensionException;
import org.apache.logging.log4j.core.Logger;

public class EngageDogGears extends CommandBase
{
    private Climber climber;
    private Logger logger;

    public EngageDogGears(Climber climber)
    {
        this.climber = climber;
        logger = LoggerFactory.createNewLogger(this.getClass());
    }

    @Override
    public void initialize()
    {
        //try {
            climber.engageDogGear();/*
        }
        catch(HookNotEngagedException e)
        {
            logger.warn("Attempted to engage dog gear before hook was engaged", e);
        }
        catch(NoTensionException e)
        {
            logger.warn("Attempted to engage dog gear before tension was created", e);
        }*/
    }

    @Override
    public void execute()
    {

    }

    @Override
    public boolean isFinished()
    {
        return false; //Cancel this when switch is undone
    }

    @Override
    public void end()
    {
        climber.disengageDogGear();
    }

    @Override
    public void interrupted()
    {

    }
}
