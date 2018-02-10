package frc.team1983.commands.ramps;

import frc.team1983.commands.CommandBase;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Ramps;
import org.apache.logging.log4j.core.Logger;

//Activates the servos that release the gas tubes, lifting each ramp up.
public class PropRamps extends CommandBase
{
    private Ramps ramp;
    private Logger propRampsLogger;

    public PropRamps(Ramps ramp)
    {
        requires(ramp);
        this.ramp = ramp;
        propRampsLogger = LoggerFactory.createNewLogger(PropRamps.class);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute()
    {
        ramp.prop();
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end() {}

    @Override
    public void interrupted(){this.end();}
}
