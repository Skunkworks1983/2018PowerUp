package frc.team1983.services;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.commands.drivebase.DriveStraight;
import frc.team1983.settings.Constants;

import java.util.HashMap;

/*
    notes:
    both joysticks and buttons are 0-indexed
*/
public class OI
{
    private DriverStation ds;

    private Joystick left, right, panel;
    private HashMap<Joystick, JoystickButton[]> joystickButtons;

    public OI(DriverStation ds)
    {
        this.ds = ds;

        left = new Joystick(Constants.OIMap.LEFTJOY_PORT);
        right = new Joystick(Constants.OIMap.RIGHTJOY_PORT);
        panel = new Joystick(Constants.OIMap.PANEL_PORT);

        joystickButtons = new HashMap<>();

        initializeButtons(Constants.OIMap.Joystick.LEFT);
        initializeButtons(Constants.OIMap.Joystick.RIGHT);
        initializeButtons(Constants.OIMap.Joystick.PANEL);
    }

    protected OI(Joystick left, Joystick right, Joystick panel)
    {
        this.left = left;
        this.right = right;
        this.panel = panel;

        //this.joystickButtons = joystickButtons;

        joystickButtons = new HashMap<>();

        initializeButtons(Constants.OIMap.Joystick.LEFT);
        initializeButtons(Constants.OIMap.Joystick.RIGHT);
        initializeButtons(Constants.OIMap.Joystick.PANEL);
    }

    // put your command bindings in here :)
    public void initializeBindings(Robot robot)
    {
        // usage:
        // bindToPressed(Constants.OIMap.Joystick.LEFT, 0, new Command());
    }

    public double getSliderPos()
    {
        return 0;
    }

    public void initializeButtons(Constants.OIMap.Joystick joystick)
    {
        if(joystickExists(joystick))
        {
            Joystick joy = getJoystick(joystick);
            JoystickButton[] buttons = new JoystickButton[joy.getButtonCount()];

            for(int i = 0; i < joy.getButtonCount(); i++)
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
        Joystick joy = left;

        switch(joystick)
        {
            case RIGHT:
                joy = right;
                break;
            case PANEL:
                joy = panel;
                break;
        }

        if(!joystickExists(joystick))
            System.out.println("attempted to access nonexistent joystick: " + joystick);

        return joy;
    }

    private JoystickButton[] getJoystickButtons(Constants.OIMap.Joystick joystick)
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
        return axisExists(joystick, axis) ? getJoystick(joystick).getRawAxis(axis) : 0;
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
        if(buttonExists(joystick, button))
            getJoystickButtons(joystick)[button].whenPressed(command);
    }

    public void bindToHeld(Constants.OIMap.Joystick joystick, int button, Command command)
    {
        if(buttonExists(joystick, button))
            getJoystickButtons(joystick)[button].whileHeld(command);
    }
}