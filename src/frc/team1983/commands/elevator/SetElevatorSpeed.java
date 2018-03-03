package frc.team1983.commands.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.OI;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Elevator;
import org.apache.logging.log4j.core.Logger;

public class SetElevatorSpeed extends CommandBase
{
    private Elevator elevator;
    private double speed;
    private Logger logger;

    public SetElevatorSpeed(Elevator elevator, double speed)
    {
        this.elevator = elevator;
        this.speed = speed;

        logger = LoggerFactory.createNewLogger(this.getClass());
    }

    @Override
    public void initialize()
    {
        logger.debug("Set elevator to {}", speed);
        elevator.set(ControlMode.PercentOutput, speed);
    }

    @Override
    public void execute()
    {
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end()
    {
        elevator.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}
