package frc.team1983.services;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.Constants;
import org.apache.logging.log4j.core.Logger;

import java.util.HashMap;

public class OI
{
    private Logger logger;

    private Joystick left, right, panel;
    private HashMap<Joystick, JoystickButton[]> joystickButtons;

    public OI()
    {
        this(new Joystick(Constants.OIMap.Joystick.LEFT.ordinal()), new Joystick(Constants.OIMap.Joystick.RIGHT.ordinal()),
             new Joystick(Constants.OIMap.Joystick.PANEL.ordinal()), new HashMap<>());
    }

    protected OI(Joystick left, Joystick right, Joystick panel, HashMap<Joystick, JoystickButton[]> joystickButtons)
    {
        this.left = left;
        this.right = right;
        this.panel = panel;
        this.joystickButtons = joystickButtons;

        this.logger = LoggerFactory.createNewLogger(this.getClass());
    }

    public void initializeBindings(Robot robot)
    {
        initializeButtons(Constants.OIMap.Joystick.LEFT);
        initializeButtons(Constants.OIMap.Joystick.RIGHT);
        initializeButtons(Constants.OIMap.Joystick.PANEL);
    }

    public void initializeButtons(Constants.OIMap.Joystick joystick)
    {
        if(joystickExists(joystick))
        {
            Joystick joy = getJoystick(joystick);
            int count = 0;

            switch(joystick)
            {
                case LEFT:
                case RIGHT:
                    count = Constants.OIMap.JOY_BUTTON_COUNT;
                    break;
                case PANEL:
                    count = Constants.OIMap.OI_BUTTON_COUNT;
            }
            JoystickButton[] buttons = new JoystickButton[count];

            for(int i = 0; i < count; i++)
            {
                buttons[i] = new JoystickButton(joy, i + 1);
            }

            joystickButtons.put(joy, buttons);
        }
    }

    public boolean joystickExists(Constants.OIMap.Joystick joystick)
    {
        switch(joystick)
        {
            case LEFT:
                return left != null;
            case RIGHT:
                return right != null;
            case PANEL:
                return panel != null;
        }

        return false;
    }

    private Joystick getJoystick(Constants.OIMap.Joystick joystick)
    {
        if(joystickExists(joystick))
        {
            switch(joystick)
            {
                case LEFT:
                    return left;
                case RIGHT:
                    return right;
                case PANEL:
                    return panel;
            }
        }

        System.out.println("tried to acces joystick that doesn't exist: " + joystick);
        return new Joystick(0);
    }

    public JoystickButton[] getJoystickButtons(Constants.OIMap.Joystick joystick)
    {
        if(joystickExists(joystick))
        {
            switch(joystick)
            {
                case LEFT:
                    return joystickButtons.get(left);
                case RIGHT:
                    return joystickButtons.get(right);
                case PANEL:
                    return joystickButtons.get(panel);
            }
        }

        return new JoystickButton[]{};
    }

    public boolean buttonExists(Constants.OIMap.Joystick joystick, int button)
    {
        return joystickExists(joystick) && (button + 1) <= getJoystick(joystick).getButtonCount();
    }

    public boolean axisExists(Constants.OIMap.Joystick joystick, int axis)
    {
        return joystickExists(joystick) && (axis + 1) <= getJoystick(joystick).getAxisCount();
    }

    public double getAxis(Constants.OIMap.Joystick joystick, int axis)
    {
        return axisExists(joystick, axis) ? -getJoystick(joystick).getRawAxis(axis) : 0;
    }

    public boolean isDown(Constants.OIMap.Joystick joystick, int button)
    {
        return buttonExists(joystick, button) && getJoystick(joystick).getRawButton(button + 1);
    }

    public boolean isPressed(Constants.OIMap.Joystick joystick, int button)
    {
        return buttonExists(joystick, button) && getJoystick(joystick).getRawButtonPressed(button + 1);
    }

    public boolean isReleased(Constants.OIMap.Joystick joystick, int button)
    {
        return buttonExists(joystick, button) && getJoystick(joystick).getRawButtonReleased(button + 1);
    }

    public void bindToPressed(Constants.OIMap.Joystick joystick, int button, Command command)
    {
        getJoystickButtons(joystick)[button].whenPressed(command);
    }

    public void bindToHeld(Constants.OIMap.Joystick joystick, int button, Command command)
    {
        getJoystickButtons(joystick)[button].whileHeld(command);
    }

    public void bindToReleased(Constants.OIMap.Joystick joystick, int button, Command command)
    {
        getJoystickButtons(joystick)[button].whenReleased(command);
    }
}