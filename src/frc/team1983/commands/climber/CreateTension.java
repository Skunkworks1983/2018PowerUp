package frc.team1983.commands.climber;

import frc.team1983.commands.CommandBase;
import frc.team1983.services.OI;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Climber;
import org.apache.logging.log4j.core.Logger;

public class CreateTension extends CommandBase
{
    private Climber climber;
    private OI oi;
    private Logger logger;
    private boolean finished;

    public CreateTension(Climber climber, OI oi)
    {
        this.climber = climber;
        this.oi = oi;
        requires(climber);
        logger = LoggerFactory.createNewLogger(this.getClass());
        finished = false;
    }

    @Override
    public void initialize()
    {
        try
        {
            climber.setTensionMotor(1); //TODO find a good speed
        }
        catch(Exception e) //Should be HookNotEngaged exception
        {
            logger.warn("Tried to create tension, but hooks were not engaged", e);
            finished = true;
        }
    }

    @Override
    public void execute()
    {
        if(/*climber.getTensionCurrent() > Constants.MotorMap.Climber.UPPER_TENSION_MOTOR_CURRENT ||*/
                !oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ClimberButtons.CREATE_TENSION))
        {
            finished = true;
            climber.confirmTensionCreation();
        }

    }

    @Override
    public boolean isFinished()
    {
        return finished;
    }

    @Override
    public void end()
    {
        climber.stopTensionMotor();
    }

    @Override
    public void interrupted()
    {
        end();
    }
}
