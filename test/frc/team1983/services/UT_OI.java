package frc.team1983.services;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

import frc.team1983.settings.Constants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UT_OI
{
    private OI oi;

    @Mock
    private DriverStation ds;

    @Before
    public void setup() {
        initMocks(this);
        oi = new OI(Constants.OIInputType.DOUBLEJOY, ds);
    }

    @After
    public void teardown() {

    }

    @Test
    public void handlesInvalidJoystickButtonPairGetAxis() {
        when(ds.getJoystickName(0)).thenReturn("billy");

        Joystick joy = new Joystick(0);
        when(joy.getButtonCount()).thenReturn(15);

        // need to decide behavior for oi
        assertThat(oi.getAxis(10, 10), is(0.0));
    }
}