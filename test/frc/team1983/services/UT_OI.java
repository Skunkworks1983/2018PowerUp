package frc.team1983.services;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.hal.HAL;

import frc.team1983.settings.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UT_OI
{
    static {
        HAL.initialize(500, 0);
    }

    private OI oi;

    @Mock
    private DriverStation ds;

    @Mock
    private Joystick joy1;

    @Mock
    private Joystick joy2;

    @Mock
    private JoystickButton joy1button1;

    private HashMap<Integer, Joystick> joysticks;
    private HashMap<Integer, JoystickButton[]> buttons;
    private HashMap<Constants.OIMap.Port, Integer> portMap;

    @Before
    public void setup()
    {
        initMocks(this);

        joysticks = new HashMap<>();
        buttons = new HashMap<>();
        portMap = new HashMap<>();

        joysticks.put(0, joy1);
        joysticks.put(1, joy2);

        buttons.put(0, new JoystickButton[]{joy1button1});
        buttons.put(1, new JoystickButton[]{});

        portMap.put(Constants.OIMap.Port.LEFT_JOY, 0);
        portMap.put(Constants.OIMap.Port.RIGHT_JOY, 1);

        oi = new OI(joysticks, buttons, portMap);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void successfullyCreatesJoysticks()
    {
        when(ds.getStickButtonCount(0)).thenReturn(1);
        when(ds.getStickAxisCount(1)).thenReturn(1);

        when(ds.getJoystickName(0)).thenReturn("jimmy");
        when(ds.getJoystickName(1)).thenReturn("jimmy");

        oi = new OI(ds);

        assertThat(oi.getJoystickCount(), is(2));
        assertThat(oi.getJoystickButtonCount(Constants.OIMap.Port.LEFT_JOY), is(1));
        assertThat(oi.getJoystickButtonCount(Constants.OIMap.Port.RIGHT_JOY), is(0));
    }

    @Test
    public void createsPortMapSuccessfully()
    {
        when(ds.getJoystickName(0)).thenReturn("tommy");
        when(ds.getJoystickName(1)).thenReturn("tommy");

        when(joy1.getAxisCount()).thenReturn(1);
        when(joy2.getAxisCount()).thenReturn(1);

        when(joy1.getRawAxis(0)).thenReturn(1.0);
        when(joy2.getRawAxis(0)).thenReturn(0.5);

        assertThat(oi.getRawAxis(Constants.OIMap.Port.LEFT_JOY, 0), is(1.0));
        assertThat(oi.getRawAxis(Constants.OIMap.Port.RIGHT_JOY, 0), is(0.5));
    }

    @Test
    public void returnsJoystickValueForValidJoystick()
    {
        when(joy1.getAxisCount()).thenReturn(1);
        when(joy1.getRawAxis(0)).thenReturn(1.0);
        assertThat(oi.getAxis(Constants.OIMap.Port.LEFT_JOY, 0), is(1.0));
    }

    @Test
    public void returnsZeroForNonexistentAxis()
    {
        when(joy1.getAxisCount()).thenReturn(1);
        assertThat(oi.getAxis(Constants.OIMap.Port.LEFT_JOY, 1), is(0.0));
    }

    @Test
    public void returnsTrueForExistentButton()
    {
        when(joy1.getButtonCount()).thenReturn(1);
        when(joy1.getRawButton(1)).thenReturn(true);
        assertThat(oi.isDown(Constants.OIMap.Port.LEFT_JOY, 0), is(true));
    }

    @Test
    public void returnsFalseForNonexistentButton()
    {
        when(joy1.getButtonCount()).thenReturn(1);
        assertThat(oi.isDown(Constants.OIMap.Port.LEFT_JOY, 1), is(false));
    }

    @Test
    public void successfullyBindsCommandToButton()
    {
        Command wanted = new CommandGroup();

        when(joy1.getButtonCount()).thenReturn(1);
        oi.bindToPressed(Constants.OIMap.Port.LEFT_JOY, 0, wanted);
        verify(joy1button1, times(1)).whenPressed(wanted);
    }

    @Test
    public void failsToBindCommandToNonexistentButton()
    {
        Command wanted = new CommandGroup();

        when(joy1.getButtonCount()).thenReturn(1);
        oi.bindToPressed(Constants.OIMap.Port.LEFT_JOY, 1, wanted);
        verify(joy1button1, times(0)).whenPressed(wanted);
    }

    @Test
    public void returnsDeadzonedValue()
    {
        when(joy1.getAxisCount()).thenReturn(1);
        when(joy1.getRawAxis(0)).thenReturn(Constants.OIMap.OIConstants.JOYSTICK_DEADZONE * 0.5);
        assertThat(oi.getAxis(Constants.OIMap.Port.LEFT_JOY, 0), is(0.0));
    }

    @Test
    public void joystickGetAxisReturnsOneForOne()
    {
        when(joy1.getAxisCount()).thenReturn(1);
        when(joy1.getRawAxis(0)).thenReturn(1.0);
        assertThat(oi.getAxis(Constants.OIMap.Port.LEFT_JOY, 0), is(1.0));

        when(joy1.getRawAxis(0)).thenReturn(-1.0);
        assertThat(oi.getAxis(Constants.OIMap.Port.LEFT_JOY, 0), is(-1.0));
    }
}