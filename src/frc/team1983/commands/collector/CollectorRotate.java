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
    private Collector collector;

    private double setpoint;

    private Logger logger;

    public CollectorRotate(Collector collector, double setpoint)
    {
        requires(collector);
        logger = LoggerFactory.createNewLogger(this.getClass());
        this.collector = collector;

        logger.info("Collector Rotate instantiated");

        this.setpoint = setpoint;

    }

    @Override
    public void initialize()
    {
        logger.info("Rotate initialized {}", setpoint);
        collector.setRotate(ControlMode.Position, setpoint);
        logger.info("Collector setpoint{}", setpoint);
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
