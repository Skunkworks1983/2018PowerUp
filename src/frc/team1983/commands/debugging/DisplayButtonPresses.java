package frc.team1983.commands.debugging;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.services.OI;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import org.apache.logging.log4j.core.Logger;


public class DisplayButtonPresses extends Command
{
    private OI oi;
    private JoystickButton[] joystickButtons;
    private Logger logger;

    public DisplayButtonPresses(OI oi)
    {
        this.oi = oi;
        logger = LoggerFactory.createNewLogger(this.getClass());
        logger.info("Created DisplayButtonPresses");
    }

    @Override
    protected void initialize()
    {
        joystickButtons = oi.getJoystickButtons(Constants.OIMap.Joystick.PANEL);
    }

    @Override
    protected void execute()
    {
        for(int i = 0; i < joystickButtons.length; i++)
        {
            if(oi.isPressed(Constants.OIMap.Joystick.PANEL, i))
            {
                logger.info("Button {} pressed", i);
            }
        }
        //logger.info("DisplayButtonPresses Executed");
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        logger.info("Ended DisplayButtonPresses");
    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
    }
}
