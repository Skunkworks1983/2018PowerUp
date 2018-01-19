package frc.team1983.services;

import edu.wpi.first.wpilibj.DriverStation;
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
            if(ds.getJoystickName(joy) != "")
            {
                joysticks.put(joy, new Joystick(joy));
                buttons.put(joy, new JoystickButton[joysticks.get(joy).getButtonCount()]);

                for(int button = 1; button <= joysticks.get(joy).getButtonCount(); button++) {
                    buttons.get(joy)[button - 1] = new JoystickButton(joysticks.get(joy), button);
                }
            }
        }
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
        if(button + 1 > getJoystick(joy).getButtonCount())
            System.out.println("tried to access button that doesn't exist");

        return joystickExists(joy) && button + 1 <= getJoystick(joy).getButtonCount();
    }

    // returns whether a joystick axis exists
    public boolean axisExists(int joy, int axis)
    {
        if(axis + 1 > getJoystick(joy).getAxisCount())
            System.out.println("tried to access axis that doesn't exist");

        return joystickExists(joy) && axis + 1 <= getJoystick(joy).getAxisCount();
    }

    // binds a command to a joystick button. runs while the button is held and terminates after released
    // returns whether command was successfully bound
    public boolean bindToHeld(int joy, int button, Command command)
    {
        if(buttonExists(joy, button))
            buttons.get(joy)[button].whileHeld(command);

        return buttonExists(joy, button);
    }

    // binds a command to a joystick button. runs when button is pressed and terminates based on command
    // returns whether command was successfully bound
    public boolean bindToPressed(int joy, int button, Command command)
    {
        if(buttonExists(joy, button))
            buttons.get(joy)[button].whenPressed(command);

        return buttonExists(joy, button);
    }

    // returns raw axis
    public double getRawAxis(int joy, int axis)
    {
        if(axisExists(joy, axis))
            return getJoystick(joy).getRawAxis(axis);
        else
            return 0;
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
        return buttonExists(joy, button) && getJoystick(joy).getRawButton(button);
    }

    // returns whether or not a button was previously up and is now down
    public boolean isPressed(int joy, int button)
    {
        return buttonExists(joy, button) && getJoystick(joy).getRawButtonPressed(button);
    }

    // returns whether or not a button was previously down and is now up
    public boolean isReleased(int joy, int button)
    {
        return buttonExists(joy, button) && getJoystick(joy).getRawButtonReleased(button);
    }
}