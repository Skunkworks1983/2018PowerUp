package frc.team1983.services;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.settings.Constants;

import java.util.HashMap;

/*
    notes:
    both joysticks and buttons are 0-indexed
*/
public class OI
{
    private DriverStation ds;

    private HashMap<Integer, Joystick> joysticks;
    private HashMap<Integer, JoystickButton[]> buttons;

    private HashMap<Constants.OIMap.Port, Integer> portMap;

    // construct collections for joysticks and buttons
    public OI(DriverStation ds)
    {
        this.ds = ds;

        joysticks = new HashMap<>();
        buttons = new HashMap<>();
        portMap = new HashMap<>();

        String lastJoystickName = "";

        for(int joy = 0; joy < ds.kJoystickPorts; joy++)
        {
            // check if joysticks exist: if a joystick has no buttons or axes we assume id does not exist
            if((ds.getStickButtonCount(joy) + ds.getStickAxisCount(joy)) > 0)
            {
                joysticks.put(joy, new Joystick(joy));
                buttons.put(joy, new JoystickButton[ds.getStickButtonCount(joy)]);

                if(ds.getJoystickName(joy).equals(Constants.OIMap.CONSOLE_JOYSTICK_NAME))
                {
                    portMap.put(Constants.OIMap.Port.BUTTONS, joy);
                }
                else
                {
                    if(ds.getJoystickName(joy).equals(lastJoystickName))
                    {
                        portMap.put(Constants.OIMap.Port.RIGHT_JOY, joy);
                    }
                    else
                    {
                        portMap.put(Constants.OIMap.Port.LEFT_JOY, joy);
                    }

                    lastJoystickName = ds.getJoystickName(joy);
                }

                for(int button = 1; button <= ds.getStickButtonCount(joy); button++)
                {
                    buttons.get(joy)[button - 1] = new JoystickButton(joysticks.get(joy), button);
                }
            }
        }
    }

    // constructor for unit testing
    protected OI(HashMap joysticks, HashMap buttons, HashMap portMap)
    {
        this.joysticks = joysticks;
        this.buttons = buttons;
        this.portMap = portMap;
    }

    // add your commands bound to oi buttons in here
    public void initialize(Robot robot)
    {
        /*
        //Elevator presets
        bindToPressed(Constants.OIMap.Port.BUTTONS, Constants.OIMap.SliderConstants.bottomPreset,
                      new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, robot.getElevator(), this));
        bindToPressed(Constants.OIMap.Port.BUTTONS, Constants.OIMap.SliderConstants.switchPreset,
                      new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, robot.getElevator(), this));
        bindToPressed(Constants.OIMap.Port.BUTTONS, Constants.OIMap.SliderConstants.scalePreset,
                      new SetElevatorSetpoint(Constants.OIMap.Setpoint.SCALE, robot.getElevator(), this));

        //Intake will run until expel is pressed
        bindToPressed(Constants.OIMap.Port.BUTTONS, Constants.OIMap.CollectorButtons.INTAKE,
                      new CollectorIntake(robot.getCollector()));
        bindToHeld(Constants.OIMap.Port.BUTTONS, Constants.OIMap.CollectorButtons.EXPEL,
                   new CollectorExpel(robot.getCollector()));
                   */

        // usage example:
        // oi.bindToHeld(Constants.OIMap.LEFTJOY, 5, new TurnAngle(90));
    }

    // returns a joystick
    public Joystick getJoystick(Constants.OIMap.Port joy)
    {
        return joysticks.get(portMap.get(joy));
    }

    // returns whether a joystick exists
    public boolean joystickExists(Constants.OIMap.Port joy)
    {
        if(getJoystick(joy) == null)
        {
            System.out.println("tried to access joystick that does not exist (joy: " + joy + ")");
        }

        return getJoystick(joy) != null;
    }

    // returns whether a button exists
    public boolean buttonExists(Constants.OIMap.Port joy, int button)
    {
        if(joystickExists(joy))
        {
            if(button + 1 > getJoystick(joy).getButtonCount())
            {
                System.out.println("tried to access button that doesn't exist (joy: " + joy + ", button: " + button + ")");
            }
        }

        return joystickExists(joy) && button + 1 <= getJoystick(joy).getButtonCount();
    }

    // returns whether a joystick axis exists
    public boolean axisExists(Constants.OIMap.Port joy, int axis)
    {
        if(joystickExists(joy))
        {
            if(axis + 1 > getJoystick(joy).getAxisCount())
            {
                System.out.println("tried to access axis that doesn't exist (joy: " + joy + ", axis: " + axis + ")");
            }
        }

        return joystickExists(joy) && axis + 1 <= getJoystick(joy).getAxisCount();
    }

    // returns the number of joysticks tracked by the oi
    public int getJoystickCount()
    {
        return joysticks.size();
    }

    // returns the number of buttons on joystick
    public int getJoystickButtonCount(Constants.OIMap.Port joy)
    {
        return joystickExists(joy) ? buttons.get(portMap.get(joy)).length : 0;
    }

    // binds a command to a joystick button. runs while the button is held and terminates after released
    public void bindToHeld(Constants.OIMap.Port joy, int button, Command command)
    {
        if(buttonExists(joy, button))
        {
            buttons.get(portMap.get(joy))[button].whileHeld(command);
        }
    }

    // binds a command to a joystick button. runs when button is pressed and terminates based on command
    public void bindToPressed(Constants.OIMap.Port joy, int button, Command command)
    {
        if(buttonExists(joy, button))
        {
            buttons.get(portMap.get(joy))[button].whenPressed(command);
        }
    }

    // returns raw axis
    public double getRawAxis(Constants.OIMap.Port joy, int axis)
    {
        return axisExists(joy, axis) ? getJoystick(joy).getRawAxis(axis) : 0;
    }

    // returns scaled axis with deadzone
    public double getAxis(Constants.OIMap.Port joy, int axis)
    {
        double raw = getRawAxis(joy, axis);
        double sign = raw < 0 ? -1 : 1;
        double deadzoned = (Math.abs(raw) > Constants.OIMap.OIConstants.JOYSTICK_DEADZONE ? raw : 0);
        return Math.pow(deadzoned, Constants.OIMap.OIConstants.JOYSTICK_RAMP_EXPONENT) * sign;
    }

    //Slider for controlling the elevator
    public double getSliderPos()
    {
        //The 2017 slider on the OI was a joystick axis. All code taken from 2017
        double x = getRawAxis(Constants.OIMap.Port.BUTTONS, Constants.OIMap.JoyAxes.X);
        x = Math.pow(x, 10);
        x = x / Constants.OIMap.OIConstants.SLIDER_SCALAR;
        x = 1 - x;
        return x;
    }

    // returns whether or not a button is down
    public boolean isDown(Constants.OIMap.Port joy, int button)
    {
        return buttonExists(joy, button) && getJoystick(joy).getRawButton(button + 1);
    }

    // returns whether or not a button was previously up and is now down
    public boolean isPressed(Constants.OIMap.Port joy, int button)
    {
        return buttonExists(joy, button) && getJoystick(joy).getRawButtonPressed(button + 1);
    }

    // returns whether or not a button was previously down and is now up
    public boolean isReleased(Constants.OIMap.Port joy, int button)
    {
        return buttonExists(joy, button) && getJoystick(joy).getRawButtonReleased(button + 1);
    }
}