package frc.team1983.services;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team1983.Constants;
import frc.team1983.Constants.OIMap;
import frc.team1983.Constants.Setpoints;
import frc.team1983.commands.collector.Expel;
import frc.team1983.commands.collector.Intake;
import frc.team1983.commands.collector.SetWristAngle;
import frc.team1983.commands.elevator.SetElevatorPosition;

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
        this(new Joystick(OIMap.LEFT_JOYSTICK),
             new Joystick(OIMap.RIGHT_JOYSTICK),
             new Joystick(OIMap.PANEL)
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
            case OIMap.LEFT_JOYSTICK:
                joystick = left;
                break;
            case OIMap.RIGHT_JOYSTICK:
                joystick = right;
                break;
            case OIMap.PANEL:
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
        getButton(OIMap.PANEL, OIMap.INTAKE).whileHeld(new Intake());
        getButton(OIMap.PANEL, OIMap.EXPEL).whileHeld(new Expel());
        getButton(OIMap.PANEL, OIMap.EXPEL_SLOW).whileHeld(new Expel(Constants.COLLECTOR_EXPEL_SLOW_THROTTLE));

        getButton(OIMap.PANEL, OIMap.ELEVATOR_BOTTOM).whileHeld(new SetElevatorPosition(Setpoints.Elevator.BOTTOM));
        getButton(OIMap.PANEL, OIMap.ELEVATOR_BOTTOM).whenReleased(new SetElevatorPosition(Setpoints.Elevator.TRAVEL));
        getButton(OIMap.PANEL, OIMap.ELEVATOR_2).whenPressed(new SetElevatorPosition(Setpoints.Elevator.POSITION_2));
        getButton(OIMap.PANEL, OIMap.ELEVATOR_3).whenPressed(new SetElevatorPosition(Setpoints.Elevator.POSITION_3));
        getButton(OIMap.PANEL, OIMap.ELEVATOR_4).whenPressed(new SetElevatorPosition(Setpoints.Elevator.POSITION_4));
        getButton(OIMap.PANEL, OIMap.ELEVATOR_TOP).whenPressed(new SetElevatorPosition(Setpoints.Elevator.TOP));

        getButton(OIMap.PANEL, OIMap.COLLECTOR_DOWN).whenPressed(new SetWristAngle(Setpoints.Wrist.DOWN));
        getButton(OIMap.PANEL, OIMap.COLLECTOR_UP).whenPressed(new SetWristAngle(Setpoints.Wrist.UP));
    }
}