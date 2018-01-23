package frc.team1983.services;

import edu.wpi.first.wpilibj.DriverStation;

import frc.team1983.settings.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.settings.Constants;

import java.util.HashMap;

/*
    notes:
    both joysticks and buttons are 0-indexed
*/
public class OI
{
    public int type;

    private DriverStation ds;

    private HashMap<Integer, Joystick> joysticks;
    private HashMap<Integer, JoystickButton[]> buttons;

    // construct collections for joysticks and buttons
    public OI(int type, DriverStation ds)
    {
        this.ds = ds;
        this.type = type;

        joysticks = new HashMap<>();
        buttons = new HashMap<>();

        for(int joy = 0; joy < ds.kJoystickPorts; joy++)
        {
            if(ds.getJoystickName(joy) != null)
            {
                joysticks.put(joy, new Joystick(joy));
                buttons.put(joy, new JoystickButton[ds.getStickButtonCount(joy)]);

                for(int button = 1; button <= ds.getStickButtonCount(joy); button++) {
                    buttons.get(joy)[button - 1] = new JoystickButton(joysticks.get(joy), button);
                }
            }
        }
    }

    // constructor for unit testing
    protected OI(HashMap<Integer, Joystick> joysticks, HashMap<Integer, JoystickButton[]> buttons) {
        this.joysticks = joysticks;
        this.buttons = buttons;
    }

    // put your commands bound to oi buttons in here
    public void initialize()
    {
        // usage example:
        // oi.bindToHeld(Constants.OIMap.LEFTJOY, 5, new TurnAngle(90));
    }

    // returns a joystick
    public Joystick getJoystick(int joy)
    {
        return joysticks.get(joy);
    }

    // returns whether a joystick exists
    public boolean joystickExists(int joy)
    {
        if(getJoystick(joy) == null)
            System.out.println("tried to access joystick that does not exist");

        return getJoystick(joy) != null;
    }

    // returns whether a button exists
    public boolean buttonExists(int joy, int button)
    {
        if(joystickExists(joy))
        {
            if(button + 1 > getJoystick(joy).getButtonCount())
                System.out.println("tried to access button that doesn't exist");
        }
        else
        {
            System.out.println("tried to access joystick that doesn't exist");
        }

        return joystickExists(joy) && button + 1 <= getJoystick(joy).getButtonCount();
    }

    // returns whether a joystick axis exists
    public boolean axisExists(int joy, int axis)
    {
        if(joystickExists(joy))
        {
            if(axis + 1 > getJoystick(joy).getAxisCount())
                System.out.println("tried to access axis that doesn't exist");
        }
        else
        {
            System.out.println("tried to access joystick that doesn't exist");
        }

        return joystickExists(joy) && axis + 1 <= getJoystick(joy).getAxisCount();
    }

    // returns the number of joysticks tracked by the oi
    public int getJoystickCount()
    {
        return joysticks.size();
    }

    // returns the number of buttons on joystick
    public int getJoystickButtonCount(int joy)
    {
        return joystickExists(joy) ? buttons.get(joy).length : 0;
    }

    // binds a command to a joystick button. runs while the button is held and terminates after released
    public void bindToHeld(int joy, int button, Command command)
    {
        if(buttonExists(joy, button))
            buttons.get(joy)[button].whileHeld(command);
    }

    // binds a command to a joystick button. runs when button is pressed and terminates based on command
    public void bindToPressed(int joy, int button, Command command)
    {
        if(buttonExists(joy, button))
            buttons.get(joy)[button].whenPressed(command);
    }

    // returns raw axis
    public double getRawAxis(int joy, int axis)
    {
        return axisExists(joy, axis) ? getJoystick(joy).getRawAxis(axis) : 0;
    }

    // returns scaled axis with deadzone
    public double getAxis(int joy, int axis)
    {
        double raw = getRawAxis(joy, axis);
        double sign = raw < 0 ? -1 : 1;
        double deadzoned = (Math.abs(raw) > Constants.OIMap.JoyConstants.JOYSTICK_DEADZONE ? raw : 0);
        return Math.pow(deadzoned, Constants.OIMap.JoyConstants.JOYSTICK_RAMP_EXPONENT) * sign;
    }

    // returns whether or not a button is down
    public boolean isDown(int joy, int button)
    {
        return buttonExists(joy, button) && getJoystick(joy).getRawButton(button + 1);
    }

    // returns whether or not a button was previously up and is now down
    public boolean isPressed(int joy, int button)
    {
        return buttonExists(joy, button) && getJoystick(joy).getRawButtonPressed(button + 1);
    }

    // returns whether or not a button was previously down and is now up
    public boolean isReleased(int joy, int button)
    {
        return buttonExists(joy, button) && getJoystick(joy).getRawButtonReleased(button + 1);
    }
}