package frc.team1983.services;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.hal.HAL;
import frc.team1983.settings.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

public class UT_OI
{
    static
    {
        HAL.initialize(500, 0);
    }

    private OI oi;

    @Mock
    private Joystick left;

    @Mock
    private Joystick right;

    @Mock
    private Joystick panel;

    @Mock
    private JoystickButton joy1button1;

    @Mock
    private DriverStation ds;

    @Before
    public void setup()
    {
        initMocks(this);

        when(left.getButtonCount()).thenReturn(2);

        HashMap joystickButtons = new HashMap<Joystick, JoystickButton[]>();

        joystickButtons.put(left, joy1button1);

        oi = new OI(left, right, panel, joystickButtons);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void returnsJoystickValueForValidJoystick()
    {
        when(left.getAxisCount()).thenReturn(1);
        when(left.getRawAxis(0)).thenReturn(1.0);
        assertThat(oi.getAxis(Constants.OIMap.Joystick.LEFT, 0), is(1.0));
    }

    @Test
    public void returnsZeroForNonexistentAxis()
    {
        when(left.getAxisCount()).thenReturn(1);
        assertThat(oi.getAxis(Constants.OIMap.Joystick.LEFT, 1), is(0.0));
    }

    @Test
    public void returnsTrueForExistentButton()
    {
        when(left.getButtonCount()).thenReturn(1);
        when(left.getRawButton(1)).thenReturn(true);
        assertThat(oi.isDown(Constants.OIMap.Joystick.LEFT, 0), is(true));
    }

    @Test
    public void returnsFalseForNonexistentButton()
    {
        when(left.getButtonCount()).thenReturn(1);
        assertThat(oi.isDown(Constants.OIMap.Joystick.LEFT, 1), is(false));
    }
    
    @Test
    public void returnsDeadzonedValue()
    {
        when(left.getAxisCount()).thenReturn(1);
        when(left.getRawAxis(0)).thenReturn(Constants.OIMap.JOYSTICK_DEADZONE * 0.5);
        assertThat(oi.getAxis(Constants.OIMap.Joystick.PANEL, 0), is(0.0));
    }

    @Test
    public void returnsZeroForNonexistentJoystick()
    {
        assertThat(oi.getAxis(Constants.OIMap.Joystick.PANEL, 0), is(0.0));
    }

    @Test
    public void joystickGetAxisReturnsOneForOne()
    {
        when(left.getAxisCount()).thenReturn(1);
        when(left.getRawAxis(0)).thenReturn(1.0);
        assertThat(oi.getAxis(Constants.OIMap.Joystick.LEFT, 0), is(1.0));

        when(left.getRawAxis(0)).thenReturn(-1.0);
        assertThat(oi.getAxis(Constants.OIMap.Joystick.LEFT, 0), is(-1.0));
    }
}