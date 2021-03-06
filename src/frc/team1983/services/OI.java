package frc.team1983.services;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.commands.Manual;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.commands.collector.CollectorRotate;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import org.apache.logging.log4j.core.Logger;

import java.util.HashMap;

/*
    notes:
    both joysticks and buttons are 0-indexed
*/
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

    // put your command bindings in here :)
    public void initializeBindings(Robot robot)
    {
        initializeButtons(Constants.OIMap.Joystick.LEFT);
        initializeButtons(Constants.OIMap.Joystick.RIGHT);
        initializeButtons(Constants.OIMap.Joystick.PANEL);

        logger.info("Initializing Bindings");

        //Collector intake/expel
        bindToPressed(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.INTAKE,
                      new CollectorIntake(robot.getCollector(), false));


        bindToReleased(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.INTAKE,
                       new CollectorIntake(robot.getCollector(), true));


        bindToPressed(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.EXPEL,
                      new CollectorExpel(robot.getCollector(), Constants.MotorSetpoints.COLLECTOR_EXPEL_SPEED));


        bindToReleased(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.EXPEL,
                       new CollectorExpel(robot.getCollector(), 0));


        bindToPressed(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.BOOP,
                      new CollectorExpel(robot.getCollector(), Constants.MotorSetpoints.COLLECTOR_SLOW_EXPEL_SPEED));


        bindToReleased(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.BOOP,
                       new CollectorExpel(robot.getCollector(), 0));

        //TODO tune this pid
        //Collector rotate
        bindToReleased(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.UP,
                       new CollectorRotate(robot.getCollector(), Constants.PidConstants.CollectorRotate.UP_TICKS));
        bindToPressed(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.UP,
                      new CollectorRotate(robot.getCollector(), Constants.PidConstants.CollectorRotate.BACK_TICKS));


        bindToReleased(Constants.OIMap.Joystick.PANEL,
                       Constants.OIMap.CollectorButtons.DOWN,
                       new CollectorRotate(robot.getCollector(),
                                           Constants.PidConstants.CollectorRotate.DOWN_TICKS));

        bindToPressed(Constants.OIMap.Joystick.PANEL,
                      Constants.OIMap.CollectorButtons.DOWN,
                      new CollectorRotate(robot.getCollector(),
                                          Constants.PidConstants.CollectorRotate.MID_TICKS));

        //TODO tune this pid
        //Elevator setpoints
        bindToPressed(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ElevatorButtons.BOTTOM,
                      new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, robot.getElevator()));
        bindToPressed(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ElevatorButtons.SWITCH,
                      new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, robot.getElevator()));
        bindToPressed(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ElevatorButtons.LOW,
                      new SetElevatorSetpoint(Constants.OIMap.Setpoint.LOW, robot.getElevator()));
        bindToPressed(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ElevatorButtons.MID,
                      new SetElevatorSetpoint(Constants.OIMap.Setpoint.MID, robot.getElevator()));
        bindToPressed(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ElevatorButtons.TOP,
                      new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, robot.getElevator()));
        bindToReleased(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ElevatorButtons.BOTTOM,
                       new SetElevatorSetpoint(Constants.OIMap.Setpoint.TRAVEL, robot.getElevator()));

        bindToPressed(Constants.OIMap.Joystick.PANEL, Constants.OIMap.MANUAL_SWITCH,
                   new Manual(this, robot.getCollector(), robot.getElevator()));
    }

    public double getElevatorSliderPos()
    {
        //The 2017 slider on the OIMap was a joystick axis. All code taken from 2017
        double x = getAxis(Constants.OIMap.Joystick.PANEL, 0);
        x = Math.pow(x, 10);
        x = x / Constants.OIMap.OIConstants.SLIDER_SCALAR;
        x = 1 - x;
        return x;
    }

    public double getFixedAxis(Constants.OIMap.Joystick joystick, int axis)
    {
        double raw = getAxis(joystick, axis);
        double sign = raw < 0 ? -1 : 1;
        double deadzoned = (Math.abs(raw) > Constants.OIMap.JOYSTICK_DEADZONE ? raw : 0);
        return Math.pow(deadzoned, Constants.OIMap.JOYSTICK_RAMP_EXPONENT) * sign;
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
        //if(buttonExists(joystick, button))
        //{
            getJoystickButtons(joystick)[button].whenPressed(command);
        //}
    }

    public void bindToHeld(Constants.OIMap.Joystick joystick, int button, Command command)
    {
        //if(buttonExists(joystick, button))
        //{
            getJoystickButtons(joystick)[button].whileHeld(command);
        //}
    }

    public void bindToReleased(Constants.OIMap.Joystick joystick, int button, Command command)
    {
        //if(buttonExists(joystick, button))
        //{
            getJoystickButtons(joystick)[button].whenReleased(command);
        //}
    }
}