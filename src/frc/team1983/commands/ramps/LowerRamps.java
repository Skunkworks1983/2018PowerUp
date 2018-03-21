package frc.team1983.commands.ramps;

import frc.team1983.commands.CommandBase;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Ramps;
import org.apache.logging.log4j.core.Logger;

//Activates the servos that kick down the ramp
public class LowerRamps extends CommandBase
{
    private Ramps ramp;
    private Logger logger;
    private boolean left;

    public LowerRamps(Ramps ramp, boolean left)
    {
        requires(ramp);
        this.ramp = ramp;
        this.left = left;
        logger = LoggerFactory.createNewLogger(LowerRamps.class);
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        ramp.unlock(left);
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end()
    {
        ramp.lock(left);
    }

    @Override
    public void interrupted()
    {
        end();
    }
}
