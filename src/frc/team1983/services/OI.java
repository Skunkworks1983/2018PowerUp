package frc.team1983.services;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team1983.Constants;

import java.util.HashMap;

public class OI
{
    private Joystick left, right, panel;
    private HashMap<Integer, HashMap<Integer, JoystickButton>> buttons;

    public OI(Joystick left, Joystick right, Joystick panel)
    {
        this.left = left;
        this.right = right;
        this.panel = panel;

        buttons = new HashMap<>();
    }

    public OI()
    {
        this(new Joystick(Constants.OIMap.LEFT_JOYSTICK),
             new Joystick(Constants.OIMap.RIGHT_JOYSTICK),
             new Joystick(Constants.OIMap.PANEL)
        );
    }

    public double getLeftY()
    {
        return left.getY();
    }

    public double getRightY()
    {
        return right.getY();
    }

    public JoystickButton getButton(int joystickPort, int button)
    {
        Joystick joystick = null;
        switch(joystickPort)
        {
            case Constants.OIMap.LEFT_JOYSTICK:
                joystick = left;
                break;
            case Constants.OIMap.RIGHT_JOYSTICK:
                joystick = right;
                break;
            case Constants.OIMap.PANEL:
                joystick = panel;
                break;
        }
        if(joystick == null)
            throw new IllegalArgumentException("Joystick on port " + joystickPort + " does not exist");

        if(!buttons.containsKey(joystickPort))
            buttons.put(joystickPort, new HashMap<>());
        if(!buttons.get(joystickPort).containsKey(button))
            buttons.get(joystickPort).put(button, new JoystickButton(joystick, button));

        return buttons.get(joystickPort).get(button);
    }

    public void initializeBindings()
    {

    }
}