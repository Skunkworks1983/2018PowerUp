package frc.team1983.services;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.hal.HAL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

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
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void returnsZeroForNonexistentAxis()
    {

    }

    @Test
    public void returnFalseForNonexistentButton()
    {

    }
}