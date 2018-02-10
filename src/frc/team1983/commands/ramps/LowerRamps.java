package frc.team1983.commands.ramps;

import frc.team1983.commands.CommandBase;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Ramps;
import org.apache.logging.log4j.core.Logger;

//Activates the servos that kick down the ramp
public class LowerRamps extends CommandBase
{
    private Ramps ramp;
    private Logger lowerRampsLogger;

    public LowerRamps(Ramps ramp)
    {
        requires(ramp);
        this.ramp = ramp;
        lowerRampsLogger = LoggerFactory.createNewLogger(LowerRamps.class);
    }

    @Override
    public void initialize(){}

    @Override
    public void execute()
    {
        ramp.drop();
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end(){}

    @Override
    public void interrupted(){this.end();}
}
