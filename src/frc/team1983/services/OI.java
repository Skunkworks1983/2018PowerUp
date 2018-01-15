package frc.team1983.services;

import java.util.*;

import edu.wpi.first.wpilibj.DriverStation;
import frc.team1983.settings.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/*
    notes:
    joysticks are 0-indexed, while buttons are 1-indexed
    update: i fixed it, both joysticks and buttons are 0-indexed when using this wrapper

    the oi will update itself provided that oi_update() is called in teleop periodic.

    usage:
    double yv = Robot.getInstance().getOI().getAxis(Constants.OIJoystick.Left,
*/
public class OI {
    public static int m_type;

    private HashMap<Integer, Joystick> m_joysticks;
    private HashMap<Integer, JoystickButton[]> m_buttons;
    private HashMap<Integer, Boolean[]> m_lastButtonStates;

    // construct the hashmaps for joysticks, buttons, and lastbuttonstates
    public OI(int type) {
        m_type = type;

        m_joysticks = new HashMap<>();
        m_buttons = new HashMap<>();
        m_lastButtonStates = new HashMap<>();

        for(int joy = 0; joy < DriverStation.kJoystickPorts; joy++) {
            if(!DriverStation.getInstance().getJoystickName(joy).equals("")) {
                m_joysticks.put(joy, new Joystick(joy));
                m_buttons.put(joy, new JoystickButton[m_joysticks.get(joy).getButtonCount()]);
                m_lastButtonStates.put(joy, new Boolean[m_joysticks.get(joy).getButtonCount()]);

                for(int button = 1; button <= m_joysticks.get(joy).getButtonCount(); button++) {
                    m_buttons.get(joy)[button - 1] = new JoystickButton(m_joysticks.get(joy), button);
                }
            }
        }
    }

    // put your commands bound to oi in here
    public void initialize() {

    }

    // updates the laststates table
    public void update() {
        for(Integer joy : m_lastButtonStates.keySet()) {
            for(int button = 0; button < m_lastButtonStates.get(joy).length - 1; button++) {
                m_lastButtonStates.get(joy)[button] = isDown(joy, button);
            }
        }
    }

    // check if joy,button combination exists
    private boolean buttonExists(int joy, int button) {
        try
        {
            return m_buttons.get(joy)[button] != null;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    // check if joy,axis combination exists
    private boolean axisExists(int joy, int axis) {
        try
        {
            // not sure if this works todo
            return axis > m_joysticks.get(joy).getAxisCount();
        }
        catch (Exception e )
        {
            return false;
        }
    }

    // wraps whileHeld
    public void bindToHeld(int joy, int button, Command command) {
        if(buttonExists(joy, button))
            m_buttons.get(joy)[button].whileHeld(command);
    }

    // wraps whenPressed()
    public void bindToPressed(int joy, int button, Command command) {
        if(buttonExists(joy, button))
            m_buttons.get(joy)[button].whenPressed(command);
    }

    // gets the raw axis of a joystick, no deadzone
    public double getRawAxis(int joy, int axis) {
        return axisExists(joy, axis) ? m_joysticks.get(joy).getRawAxis(axis) : 0;
    }

    // gets the axis value with deadzone of joystick. deadzone set in Constants
    public double getAxis(int joy, int axis) {
        double axisValue = getRawAxis(joy, axis);
        double value = Math.abs(axisValue) > Constants.JOYSTICK_DEADZONE ? axisValue : 0;
        return axisExists(joy, axis) ? value : 0;
    }

    // gets whether a button is down, regardless of previous state
    public boolean isDown(int joy, int button) {
        return m_joysticks.get(joy).getRawButton(button);

    }

    // gets whether a button was previously up (last frame) and is now down
    public boolean isPressed(int joy, int button) {
        return isDown(joy, button) && !m_lastButtonStates.get(joy)[button];
    }

    // gets whether a button was previously down and is now down
    public boolean isReleased(int joy, int button) {
        return !isDown(joy, button) && m_lastButtonStates.get(joy)[button];
    }
}