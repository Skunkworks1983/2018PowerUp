package frc.team1983.services;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorIntake;
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

        left = new Joystick(Constants.OI.LEFTJOY_PORT);
        right = new Joystick(Constants.OI.RIGHTJOY_PORT);
        panel = new Joystick(Constants.OI.PANEL_PORT);

        joystickButtons = new HashMap<>();

        initializeButtons(Constants.OI.Joystick.LEFT);
        initializeButtons(Constants.OI.Joystick.RIGHT);
        initializeButtons(Constants.OI.Joystick.PANEL);
    }

    protected OI(Joystick left, Joystick right, Joystick panel, HashMap joystickButtons)
    {
        this.left = left;
        this.right = right;
        this.panel = panel;
        this.joystickButtons = joystickButtons;

        //this.joystickButtons = joystickButtons;

        joystickButtons = new HashMap<>();

        initializeButtons(Constants.OI.Joystick.LEFT);
        initializeButtons(Constants.OI.Joystick.RIGHT);
        initializeButtons(Constants.OI.Joystick.PANEL);
    }

    // put your command bindings in here :)
    public void initializeBindings(Robot robot)
    {
        //Intake will run until expel is pressed
        bindToPressed(Constants.OI.Joystick.PANEL, Constants.OI.CollectorButtons.INTAKE,
                      new CollectorIntake(robot.getCollector()));
        bindToHeld(Constants.OI.Joystick.PANEL, Constants.OI.CollectorButtons.EXPEL,
                   new CollectorExpel(robot.getCollector()));
    }

    public double getElevatorSliderPos()
    {
        //The 2017 slider on the OI was a joystick axis. All code taken from 2017
        double x = getAxis(Constants.OI.Joystick.PANEL, 0);
        x = Math.pow(x, 10);
        x = x / Constants.OI.OIConstants.SLIDER_SCALAR;
        x = 1 - x;
        return x;
    }

    public double getFixedAxis(Constants.OI.Joystick joystick, int axis)
    {
        double raw = getAxis(joystick, axis);
        double sign = raw < 0 ? -1 : 1;
        double deadzoned = (Math.abs(raw) > Constants.OI.JOYSTICK_DEADZONE ? raw : 0);
        return Math.pow(deadzoned, Constants.OI.JOYSTICK_RAMP_EXPONENT) * sign;
    }

    public void initializeButtons(Constants.OI.Joystick joystick)
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

    public boolean joystickExists(Constants.OI.Joystick joystick)
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

    private Joystick getJoystick(Constants.OI.Joystick joystick)
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

    private JoystickButton[] getJoystickButtons(Constants.OI.Joystick joystick)
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

    public boolean buttonExists(Constants.OI.Joystick joystick, int button)
    {
        return joystickExists(joystick) && (button + 1) <= getJoystick(joystick).getButtonCount();
    }

    public boolean axisExists(Constants.OI.Joystick joystick, int axis)
    {
        return joystickExists(joystick) && (axis + 1) <= getJoystick(joystick).getAxisCount();
    }

    public double getAxis(Constants.OI.Joystick joystick, int axis)
    {
        return axisExists(joystick, axis) ? -getJoystick(joystick).getRawAxis(axis) : 0;
    }

    public boolean isDown(Constants.OI.Joystick joystick, int button)
    {
        return buttonExists(joystick, button) && getJoystick(joystick).getRawButton(button + 1);
    }

    public boolean isPressed(Constants.OI.Joystick joystick, int button)
    {
        return buttonExists(joystick, button) && getJoystick(joystick).getRawButtonPressed(button + 1);
    }

    public boolean isReleased(Constants.OI.Joystick joystick, int button)
    {
        return buttonExists(joystick, button) && getJoystick(joystick).getRawButtonReleased(button + 1);
    }

    public void bindToPressed(Constants.OI.Joystick joystick, int button, Command command)
    {
        if(buttonExists(joystick, button))
            getJoystickButtons(joystick)[button].whenPressed(command);
    }

    public void bindToHeld(Constants.OI.Joystick joystick, int button, Command command)
    {
        if(buttonExists(joystick, button))
            getJoystickButtons(joystick)[button].whileHeld(command);
    }
}