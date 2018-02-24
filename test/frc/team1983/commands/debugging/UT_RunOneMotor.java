package frc.team1983.commands.debugging;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.hal.HAL;
import frc.team1983.subsystems.utilities.Motor;
import org.hamcrest.number.IsCloseTo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UT_RunOneMotor
{
    static
    {
        HAL.initialize(500, 0);
    }

    private RunOneMotor runOneMotor;

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

    @Mock
    private Motor motor2;

    private ArrayList<Motor> motors;

    @Before
    public void setup()
    {
        initMocks(this);
        runOneMotor = new RunOneMotor();

        motors = new ArrayList<>();
        motors.add(motor0);
        motors.add(motor1);
        motors.add(motor2);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void manualSpeedReturnsOneTenthForThree()
    {
        when(manualSpeed.getVoltage()).thenReturn(3.0); //3d is also accepted as a double
        runOneMotor.initialize(motors, motorUp, motorDown, manualSpeed);
        runOneMotor.execute();
        assertThat(runOneMotor.getSpeed(), is(new IsCloseTo(0.1, 0.01)));
    }

    @Test
    public void manualSpeedReturnsNegativeOneTenthforNegativeThree()
    {
        when(manualSpeed.getVoltage()).thenReturn(-3.0);
        runOneMotor.initialize(motors, motorUp, motorDown, manualSpeed);
        runOneMotor.execute();
        assertThat(runOneMotor.getSpeed(), is(new IsCloseTo(-1.1, 0.01)));
    }

    @Test
    public void motorUpTrueReturnsMotorIndexOneForMotorZero()
    {
        when(motorUp.get()).thenReturn(true);
        runOneMotor.initialize(motors, motorUp, motorDown, manualSpeed);
        runOneMotor.execute();
        assertThat(runOneMotor.getMotorIndex(), is(1));
    }

    @Test
    public void motorDownTrueReturnsMotorIndexZeroForMotorOne()
    {
        runOneMotor.initialize(motors, motorUp, motorDown, manualSpeed);
        when(motorUp.get()).thenReturn(false);
        when(motorDown.get()).thenReturn(true);
        runOneMotor.execute();
        assertThat(runOneMotor.getMotorIndex(), is(2));
    }

}
