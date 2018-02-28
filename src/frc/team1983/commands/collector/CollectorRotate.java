package frc.team1983.commands.collector;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.utilities.PidControllerWrapper;
import frc.team1983.subsystems.utilities.inputwrappers.CollectorPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.CollectorPidOutput;
import org.apache.logging.log4j.core.Logger;

public class CollectorRotate extends CommandBase
{
    //If not up, assume down. Later, may implement a 45 degree position.
    private boolean up;
    private Collector collector;

    private double setpoint;

    private Logger logger;

    public CollectorRotate(Collector collector, boolean up)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());
        this.up = up;
        this.collector = collector;

        logger.info("Collector Rotate instantiated");

        if(up)
        {
            setpoint = Constants.PidConstants.CollectorRotate.UP_TICKS;
        }
        else
        {
            setpoint = Constants.PidConstants.CollectorRotate.DOWN_TICKS;
        }
    }

    @Override
    public void initialize()
    {
        logger.info("Rotate initialized");
        collector.setRotate(ControlMode.Position, setpoint);
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
    public void end()
    {
    }

    @Override
    public void interrupted()
    {
    }


}
