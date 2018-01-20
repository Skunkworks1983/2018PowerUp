package frc.team1983.services;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.hal.HAL;

import frc.team1983.settings.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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
    private Joystick joy1, joy2, joy3;

    @Before
    public void setup() {
        initMocks(this);
        oi = new OI(Constants.OIMap.Mode.DOUBLE_JOY, ds);
    }

    @After
    public void teardown() {

    }

    @Test
    public void returnsZeroForNonexistentAxis() {

    }

    @Test
    public void returnsFalseForNonexistentButton() {

    }

    @Test
    public void returnsFalseForBindToNonexistentButton() {

    }

    @Test
    public void returnsDeadzonedValue() {

    }
}