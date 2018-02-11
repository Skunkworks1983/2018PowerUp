package frc.team1983.commands.debugging;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.hal.HAL;
import frc.team1983.subsystems.utilities.Motor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UT_DSButtons
{
    static
    {
        HAL.initialize(500, 0);
    }
    private DSButtons button;

    @Mock
    private DigitalInput motorUp;

    @Mock
    private DigitalInput motorDown;

    @Mock
    private AnalogInput manualSpeed;

    @Mock
    private Motor motor0;

    @Mock
    private Motor motor1;

    private ArrayList<Motor> motors;

    @Before
    public void setup()
    {
        initMocks(this);
        button = new DSButtons();

        motors = new ArrayList<>();
        motors.add(motor0);
        motors.add(motor1);
    }

    @After
    public void teardown()
    {

    }

   /*  @Test
    public void manualSpeedReturnsOneTenthForThree()
    {

    } */

    @Test
    public void motorUpTrueReturnsMotorIndexOneForMotorZero()
    {
        when(motorUp.get()).thenReturn(true);
        button.initialize(motors);
        button.execute();
        assertThat(button.getMotorIndex(), is(1));
    }

    @Test
    public void motorDownTrueReturnsMotorIndexFifteenForMotorZero()
    {
        button.initialize(motors);
        when(motorUp.get()).thenReturn(false);
        when(motorDown.get()).thenReturn(true);
        button.execute();
        assertThat(button.getMotorIndex(), is(0));
    }
}
