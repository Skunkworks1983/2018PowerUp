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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.verification.VerificationModeFactory.times;
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
    private DriverStation ds;

    @Before
    public void setup()
    {
        initMocks(this);

        when(left.getButtonCount()).thenReturn(2);

        oi = new OI(left, right, panel);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void returnsZeroForNonexistentAxis()
    {
        assertThat(oi.getAxis(Constants.OIMap.Joystick.LEFT, -1), is(0.0));
    }

    @Test
    public void returnsOneForOneAxis()
    {
        when(left.getAxisCount()).thenReturn(2);
        when(left.getRawAxis(1)).thenReturn(1.0);
        assertThat(oi.getAxis(Constants.OIMap.Joystick.LEFT, 1), is(1.0));
    }

    @Test
    public void returnFalseForNonexistentButton()
    {
        assertThat(oi.isDown(Constants.OIMap.Joystick.LEFT, -1), is(false));
    }

    @Test
    public void returnsTrueForButtonDown()
    {
        when(left.getRawButton(1)).thenReturn(true);
        assertThat(oi.isDown(Constants.OIMap.Joystick.LEFT, 1), is(true));
    }
}