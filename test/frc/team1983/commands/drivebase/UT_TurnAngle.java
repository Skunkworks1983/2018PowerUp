package frc.team1983.commands.drivebase;

import edu.wpi.first.wpilibj.HLUsageReporting;
import edu.wpi.first.wpilibj.Timer;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.sensors.Gyro;
import frc.team1983.subsystems.utilities.inputwrappers.EncoderTurnAnglePidInput;
import frc.team1983.subsystems.utilities.inputwrappers.GyroPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.DrivebaseRotationPidOutput;
import frc.team1983.testutility.FakeScheduler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class UT_TurnAngle
{
    private TurnAngle turnAngle;
    private FakeScheduler fakeScheduler;

    @Mock
    private Gyro gyro;
    @Mock
    private Drivebase drivebase;
    @Mock
    private GyroPidInput gyroPidInput;
    @Mock
    private Timer.StaticInterface timerStaticInterface;
    @Mock
    private Timer.Interface timerInterface;
    @Mock
    private HLUsageReporting.Interface hlUsageReporting;


    @Before
    public void setup()
    {
        initMocks(this);
        when(timerStaticInterface.newTimer()).thenReturn(timerInterface);
        when(timerStaticInterface.getFPGATimestamp()).then(new Answer<Double>()
        {
            @Override
            public Double answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                return System.currentTimeMillis() / 1000.;
            }
        });
        when(timerInterface.get()).then(new Answer<Double>()
        {
            @Override
            public Double answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                return System.currentTimeMillis() / 1000.;
            }
        });

        Timer.SetImplementation(timerStaticInterface);
        HLUsageReporting.SetImplementation(hlUsageReporting);
        fakeScheduler = new FakeScheduler();
        when(drivebase.getGyro()).thenReturn(gyro);
        turnAngle = new TurnAngle(10, drivebase);
    }

    @After
    public void teardown()
    {
        turnAngle.end();
    }

    @Test
    public void turnAngleUsesGyroWhenGyroIsWorking()
    {
        when(gyro.isDead()).thenReturn(false);
        fakeScheduler.add(turnAngle);
        fakeScheduler.run(1);
        verify(gyro, atLeastOnce()).getAngle();
    }

    @Test
    public void turnAngleUsesEncoderWhenGyroIsDead()
    {
        when(gyro.isDead()).thenReturn(true);
        fakeScheduler.add(turnAngle);
        fakeScheduler.run(1);
        verify(drivebase, atLeastOnce()).getLeftEncoderValue();

    }

    @Test
    public void turnPidStopsIfPidOvershootsAndRecorrects()
    {
        //answer gives us a different value every time as the turnPid gets us closer to targetAngle
        //returns 0 first time called for initial angle
        //mimics overshooting and then lining up
        when(gyro.isDead()).thenReturn(false);
        AtomicReference<Integer> counter = new AtomicReference<>(0);
        when(gyro.getAngle()).then(new Answer<Double>()
        {
            @Override
            public Double answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                if(counter.get() == 0)
                {
                    counter.set(counter.get() + 1);
                    return 0.;
                }
                else
                {
                    counter.set(counter.get() + 1);
                    return Math.sin(counter.get().doubleValue()) / counter.get().doubleValue() * (Constants.PidConstants.TurnAnglePid.ABSOLUTE_TOLERANCE +1) + 10;
                    //mimics oscillation of robot when trying get within absolute tolerance
                }

            }
        });

        fakeScheduler.add(turnAngle);
        fakeScheduler.run(25);
        assertThat(fakeScheduler.getOrderFinished().get(0), is(turnAngle));

    }

}
